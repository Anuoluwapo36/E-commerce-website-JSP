/**
 * 
 */
/**


/**
 * @param {HTMLElement} element -
 */
function toggleSubcat(element) {
    const subcatDiv = element.nextElementSibling; 
    const arrowSvg = element.querySelector('.tb-arrow-icon'); 

    if (subcatDiv.style.display === 'none' || subcatDiv.style.display === '') {
        subcatDiv.style.display = 'block';
        arrowSvg.style.transform = 'rotate(180deg)'; 
    } else {
        subcatDiv.style.display = 'none';
        arrowSvg.style.transform = 'rotate(0)'; 
    }
}

/**
 * Highlight active filters on page load 
 */
document.addEventListener('DOMContentLoaded', function() {
    const urlParams = new URLSearchParams(window.location.search);
    const activeCategory = urlParams.get('category');
    const activePriceRange = urlParams.get('priceRange');
    const activeStock = urlParams.get('stock');

    // 1. Highlight active category
    if (activeCategory) {
        document.querySelectorAll('.tb-subcategories a').forEach(link => {
            if (link.href.includes(`category=${activeCategory}`)) {
                link.classList.add('active'); 
                // Expand parent category
                const subcatDiv = link.parentElement;
                subcatDiv.style.display = 'block';
                subcatDiv.previousElementSibling.querySelector('.tb-arrow-icon').style.transform = 'rotate(180deg)';
            }
        });
    }

    // 2. Highlight active price range
    if (activePriceRange) {
        document.querySelectorAll('.tb-price-tags a').forEach(tag => {
            if (tag.href.includes(`priceRange=${activePriceRange}`)) {
                tag.classList.add('active'); // Use CSS class
            }
        });
    }

    // 3. Highlight active stock filter
    if (activeStock === 'inStock') {
        document.querySelector('.tb-instock-link').classList.add('active');
        document.querySelector('.tb-allproducts-link').classList.remove('active');
    } else {
        document.querySelector('.tb-allproducts-link').classList.add('active');
        document.querySelector('.tb-instock-link').classList.remove('active');
    }
});