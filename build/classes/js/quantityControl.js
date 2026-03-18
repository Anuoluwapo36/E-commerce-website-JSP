// Reusable quantity controller (works for productdetails.jsp AND confirmation.jsp)
function initQuantityController(config) {
    // Required config validation (adjust based on page needs)
    const requiredConfig = [
        'minusBtnSelector', 'plusBtnSelector', 'quantityDisplayId', 
        'hiddenQuantityId', 'productDataId', 'maxStock'
    ];
    const missingConfig = requiredConfig.filter(key => !config[key]);
    if (missingConfig.length > 0) {
        console.error('Missing required config:', missingConfig);
        return;
    }

    // 1. LocalStorage Setup (from your original productdetails.js)
    const EXPIRY_MINUTES = 5;
    const productId = config.productId || (document.getElementById(config.productDataId)?.dataset.productId);
    const saveKey = `qty_${productId}`;
    const timeoutKey = `qty_timeout_${productId}`;
    const maxAllowed = parseInt(config.maxStock) || 1;
    const expiryMs = EXPIRY_MINUTES * 60 * 1000;

    // Check if saved quantity is stale
    const savedTimeout = localStorage.getItem(timeoutKey);
    const isStale = savedTimeout && (Date.now() - parseInt(savedTimeout)) > expiryMs;
    if (isStale) {
        localStorage.removeItem(saveKey);
        localStorage.removeItem(timeoutKey);
    }

    // 2. Initialize quantity (from localStorage or config)
    let currentQty = localStorage.getItem(saveKey) 
        ? Math.max(1, Math.min(parseInt(localStorage.getItem(saveKey)), maxAllowed))
        : parseInt(config.initialQty) || 1;

    // 3. Get UI elements (supports both pages)
    const elements = {
        minusBtn: document.querySelector(config.minusBtnSelector),
        plusBtn: document.querySelector(config.plusBtnSelector),
        quantityDisplay: document.getElementById(config.quantityDisplayId),
        hiddenQuantity: document.getElementById(config.hiddenQuantityId),
        productData: document.getElementById(config.productDataId),
        totalPriceElement: config.totalPriceId ? document.getElementById(config.totalPriceId) : null,
        totalInput: config.totalInputId ? document.getElementById(config.totalInputId) : null,
        tableTotal: config.tableTotalSelector ? document.querySelector(config.tableTotalSelector) : null,
        confirmHiddenQuantity: config.confirmHiddenQuantityId ? document.getElementById(config.confirmHiddenQuantityId) : null,
        stockAlert: document.getElementById('stock-alert'),
        unitPriceElement: config.unitPriceId ? document.getElementById(config.unitPriceId) : null
    };

    // Validate critical elements
    if (!elements.minusBtn || !elements.plusBtn || !elements.quantityDisplay || !elements.hiddenQuantity) {
        console.error('Missing critical UI elements for quantity control');
        return;
    }

    // 4. Get product data (price from productData div or unitPrice element)
    const price = elements.productData 
        ? parseFloat(elements.productData.dataset.price) || 0
        : (elements.unitPriceElement ? parseFloat(elements.unitPriceElement.dataset.value) || 0 : 0);

    // 5. Update UI (works for both pages)
    function updateUI() {
        // Update quantity displays
        elements.quantityDisplay.textContent = currentQty;
        elements.hiddenQuantity.value = currentQty;
        if (elements.confirmHiddenQuantity) elements.confirmHiddenQuantity.value = currentQty;

        // Update total price (supports productdetails.jsp total and confirmation.jsp total)
        const total = (price * currentQty).toFixed(2);
        if (elements.totalPriceElement) elements.totalPriceElement.textContent = `$${total}`;
        if (elements.totalInput) elements.totalInput.value = total;
        if (elements.tableTotal) elements.tableTotal.textContent = `$${total}`;

        // Toggle buttons and stock alert
        elements.minusBtn.disabled = currentQty <= 1;
        elements.plusBtn.disabled = currentQty >= maxAllowed;
        if (elements.stockAlert) {
            elements.stockAlert.style.display = currentQty >= maxAllowed ? 'inline' : 'none';
        }

        // Save to localStorage
        localStorage.setItem(saveKey, currentQty);
        localStorage.setItem(timeoutKey, Date.now().toString());
    }

    // 6. Sync with UpdateQuantityServlet (backend validation)
    function syncWithBackend() {
        if (!productId) return;

        fetch(`${window.appContextPath}/update-quantity`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
            body: `productId=${encodeURIComponent(productId)}&newQty=${currentQty}`
        })
        .then(response => response.ok ? response.json() : { error: 'Server error' })
        .then(data => {
            if (data.error) {
                console.error('Backend sync error:', data.error);
                // Revert to server-validated quantity
                currentQty = data.newQty ? Math.max(1, Math.min(data.newQty, maxAllowed)) : 1;
                updateUI();
            } else {
                // Update maxStock if server returns a different value (e.g., stock changed)
                if (data.maxStock && data.maxStock !== maxAllowed) {
                    maxAllowed = data.maxStock;
                    updateUI();
                }
            }
        })
        .catch(error => {
            console.error('Network error (quantity sync):', error);
            // Keep UI working offline
            updateUI();
        });
    }

    // 7. Button click listeners
    elements.minusBtn.addEventListener('click', () => {
        if (currentQty > 1) {
            currentQty--;
            updateUI();
            syncWithBackend();
        }
    });

    elements.plusBtn.addEventListener('click', () => {
        if (currentQty < maxAllowed) {
            currentQty++;
            updateUI();
            syncWithBackend();
        }
    });

    // 8. Initial UI update
    updateUI();

    // 9. Optional: Add to Cart handler (productdetails.jsp only)
    if (config.addCartBtnId && config.cartFormId) {
        const addCartBtn = document.getElementById(config.addCartBtnId);
        const cartForm = document.getElementById(config.cartFormId);
        if (addCartBtn && cartForm) {
            addCartBtn.addEventListener('click', (e) => {
                e.preventDefault();
                elements.hiddenQuantity.value = currentQty;
                cartForm.submit();
            });
        }
    }

    console.log(`Quantity controller initialized for product ${productId} (qty: ${currentQty})`);
}

// ------------------------------
// Page-Specific Initialization
// ------------------------------

// Initialize for productdetails.jsp
document.addEventListener('DOMContentLoaded', function() {
    const productData = document.getElementById('product-data');
    if (productData) { // Only run if product-data div exists (productdetails.jsp)
        initQuantityController({
            minusBtnSelector: '.minus-btn',
            plusBtnSelector: '.plus-btn',
            quantityDisplayId: 'quantity-value', // productdetails.jsp's quantity ID
            hiddenQuantityId: 'hidden-quantity',
            productDataId: 'product-data',
            maxStock: productData.dataset.stock,
            initialQty: productData.dataset.initialQty,
            unitPriceId: 'unit-price', // productdetails.jsp's unit price ID
            totalPriceId: 'total-price', // productdetails.jsp's total price ID
            confirmHiddenQuantityId: 'confirm-hidden-quantity', // productdetails.jsp's confirm quantity
            addCartBtnId: 'add-cart-btn', // productdetails.jsp's add to cart button
            cartFormId: 'cart-form' // productdetails.jsp's add to cart form
        });
    }

    // Initialize for confirmation.jsp
    const confirmationProductData = document.getElementById('product-data');
    if (confirmationProductData && document.querySelector('.summary-row')) { // Only run for confirmation.jsp
        initQuantityController({
            minusBtnSelector: '.minus-btn',
            plusBtnSelector: '.plus-btn',
            quantityDisplayId: 'quantity-display', // confirmation.jsp's quantity ID
            hiddenQuantityId: 'hidden-quantity',
            productDataId: 'product-data',
            maxStock: confirmationProductData.dataset.stock,
            initialQty: confirmationProductData.dataset.initialQty,
            totalInputId: 'total-input', // confirmation.jsp's hidden total input
            tableTotalSelector: '.summary-row .data-total' // confirmation.jsp's table total
        });
    }

    // Your existing address toggle logic
    function addAddress() {
        const clickBtn = document.getElementById('change-address');
        const openContainer = document.querySelector('.show-up');
        const close = document.querySelector('.close-x');

        if (clickBtn && openContainer) {
            clickBtn.addEventListener('click', () => {
                openContainer.classList.toggle('active');
            });
        }

        if (close && openContainer) {
            close.addEventListener('click', () => {
                openContainer.classList.remove('active');
            });
        }
    }
    addAddress();
});