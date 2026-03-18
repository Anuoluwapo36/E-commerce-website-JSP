/**
 * 
 */


//for thumbnail to Main Image
function initProductImagePreview() {
    const thumbnails = document.querySelectorAll('.product-thumb-img');
    const mainImage = document.getElementById('product-image');
    if (mainImage && thumbnails.length) {
        thumbnails.forEach((thumbnail, index) => {
            //this select  Set first thumbnail as active by default
            if (index === 0) {
                thumbnail.parentElement.classList.add('active');
            }

            thumbnail.addEventListener('click', () => {
                mainImage.src = thumbnail.src;
                // and update  active thumbnail
                thumbnails.forEach(thumb => thumb.parentElement.classList.remove('active'));
                thumbnail.parentElement.classList.add('active');
            });
        });
    }
}

//this is use for  quantity Selector if ypou want more unit of goods 
function initProductQuantity() {
    const minusBtn = document.querySelector('.quantity-minus');
    const plusBtn = document.querySelector('.quantity-plus');
    const quantityValue = document.getElementById('quantity-value');
    const hiddenQuantity = document.getElementById('hidden-quantity');
    const confirmHiddenQuantity = document.getElementById('confirm-hidden-quantity');
    const stockAlert = document.getElementById('stock-alert');
    const productData = document.getElementById('product-data');
    
    if (!minusBtn || !plusBtn || !quantityValue || !productData) return;

    const maxStock = parseInt(productData.dataset.stock);
    let currentQty = parseInt(productData.dataset.initialQty) || 1;

    // Update quantity display
    function updateQuantity() {
        quantityValue.textContent = currentQty;
        hiddenQuantity.value = currentQty;
        confirmHiddenQuantity.value = currentQty;
        
        // Show/hide stock alert
        stockAlert.style.display = currentQty >= maxStock ? 'inline' : 'none';
        
        // Update total price
        const unitPrice = parseFloat(productData.dataset.price);
        const totalPrice = document.getElementById('total-price');
        if (totalPrice) {
            totalPrice.textContent = `$${(unitPrice * currentQty).toFixed(2)}`;
        }
    }

    // Minus button
    minusBtn.addEventListener('click', () => {
        if (currentQty > 1) {
            currentQty--;
            updateQuantity();
        }
    });

    // Plus button
    plusBtn.addEventListener('click', () => {
        if (currentQty < maxStock) {
            currentQty++;
            updateQuantity();
        }
    });

    // Initial update
    updateQuantity();
}

// 3. Image Zoom Functionality
function initProductImageZoom() {
    const zoomWrapper = document.querySelector('.image-zoom-wrapper');
    const mainImage = document.getElementById('product-image');
    const zoomBtn = document.querySelector('.image-action-btn[title="Zoom"]');
    
    if (!zoomWrapper || !mainImage || !zoomBtn) return;

    let isZoomed = false;
    const originalScale = 1;
    const zoomScale = 1.5;

    // Zoom button click
    zoomBtn.addEventListener('click', (e) => {
        e.preventDefault();
        isZoomed = !isZoomed;
        mainImage.style.transform = isZoomed ? `scale(${zoomScale})` : `scale(${originalScale})`;
        zoomBtn.title = isZoomed ? 'Zoom Out' : 'Zoom In';
    });

    // Click outside to zoom out
    zoomWrapper.addEventListener('click', (e) => {
        if (e.target === zoomWrapper && isZoomed) {
            isZoomed = false;
            mainImage.style.transform = `scale(${originalScale})`;
            zoomBtn.title = 'Zoom In';
        }
    });
}

// Initialize all functions
window.addEventListener('load', () => {
    initProductImagePreview();
    initProductQuantity();
    initProductImageZoom();
    document.body.classList.add('loaded');
    if (window.hideLoadingOverlay) {
        hideLoadingOverlay();
    }
});

















// Wait for the entire page to load before running JS
document.addEventListener('DOMContentLoaded', function() {
    // --------------------------
    // 1. Related Products Carousel
    // --------------------------
    const initAdCarousel = function() {
        // Get all carousels on the page (works if you have multiple)
        const carousels = document.querySelectorAll('.ads-carousel');
        
        carousels.forEach(carousel => {
            const track = carousel.querySelector('.carousel-track');
            const prevBtn = carousel.querySelector('.prev-btn');
            const nextBtn = carousel.querySelector('.next-btn');
            
            // Only run if all elements exist (avoids errors)
            if (!track || !prevBtn || !nextBtn) return;
            
            // Calculate card width + gap (matches CSS: 280px card + 16px gap)
            const cardWidth = 280 + 16;
            
            // Previous button: Scroll left
            prevBtn.addEventListener('click', () => {
                track.scrollBy({
                    left: -cardWidth,
                    behavior: 'smooth' // Smooth scroll animation
                });
            });
            
            // Next button: Scroll right
            nextBtn.addEventListener('click', () => {
                track.scrollBy({
                    left: cardWidth,
                    behavior: 'smooth'
                });
            });
        });
    };

 
    const initFAQ = function() {
        const faqQuestions = document.querySelectorAll('.faq-question');
        
        faqQuestions.forEach(question => {
            question.addEventListener('click', () => {
                question.classList.toggle('active');
                
                faqQuestions.forEach(otherQuestion => {
                    if (otherQuestion !== question && otherQuestion.classList.contains('active')) {
                        otherQuestion.classList.remove('active');
                    }
                });
            });
        });
    };


    const initSupplementaryImages = function() {
        const supplementaryImages = document.querySelectorAll('.supplementary-img');
        const mainProductImage = document.getElementById('product-image');
        
        if (!mainProductImage) return;
        
        supplementaryImages.forEach(img => {
            img.addEventListener('click', () => {
                mainProductImage.src = img.src;
                mainProductImage.alt = img.alt;
            });
        });
    };


    initAdCarousel();
    initFAQ();
    initSupplementaryImages();
});