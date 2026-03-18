/**
 * 
 */

document.addEventListener('DOMContentLoaded', function() {
	const minusBtn = document.querySelector('.minus-btn');
	const plusBtn = document.querySelector('.plus-btn');
	const quantityDisplay = document.getElementById('quantity-display');
	const hiddenQty = document.getElementById('hidden-quantity');
	const tableQty = document.querySelector('.data-qty');
	const tableTotal = document.querySelector('.data-total');
	const totalInput = document.querySelector('input[name="total"]');

	const productData = document.getElementById('product-data');

	const unitPrice = parseFloat(productData.dataset.price);
	const maxStock = parseInt(productData.dataset.stock);
	let currentQty = parseInt(productData.dataset.initialQty);

	function syncAll() {
		quantityDisplay.textContent = currentQty;
		tableQty.textContent = currentQty;
		const newTotal = unitPrice * currentQty;
		tableTotal.textContent = "$" + newTotal.toFixed(2);
		hiddenQty.value = currentQty;
		totalInput.value = newTotal.toFixed(2);
		minusBtn.disabled = currentQty <= 1;
		plusBtn.disabled = currentQty >= maxStock;
	}

	minusBtn.addEventListener('click', () => {
		if (currentQty > 1) {
			currentQty--;
			syncAll();
		}
	});

	plusBtn.addEventListener('click', () => {
		if (currentQty < maxStock) {
			currentQty++;
			syncAll();
		}
	});

	syncAll();
});


