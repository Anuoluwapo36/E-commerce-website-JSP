


/**
 * 
 */

//thsi code explain product name suggestion before you complete the input 


let debounceTime;
function showSuggestions(inputValue) {
	clearTimeout(debounceTime);


	let userInput = inputValue;

	if (userInput.length < 2) {
		document.getElementById("suggestionsDropdown").style.display = "none";
		return;
	}
	debounceTime = setTimeout(() => {
		let url = contextPath + '//get-suggestions?query=' + encodeURIComponent(userInput);

		fetch(url)
			.then(response => response.json())
			.then(suggestions => {
				let dropdown = document.getElementById("suggestionsDropdown");
				dropdown.innerHTML = "";
				if (suggestions.length === 0) {
					dropdown.style.display = "none";
					return;
				}

				suggestions.forEach(suggestion => {
					let item = document.createElement("div");
					item.className = "suggestion-items";
					item.textContent = suggestion.product_name;

					item.onclick = function() {
						document.getElementById("searchInput").value = suggestion.product_name;
						dropdown.style.display = "none";
					};
					dropdown.appendChild(item)

				});

				dropdown.style.display = "block";
			})
			.catch(error => {
				console.error("failed to load suggestion: ", error);
				document.getElementById("suggestionsDropdown").style.display = "none";
			});
	}, 300);


}



// product image hover in productdetails .jsp 

function initImagePreview() {
	const thumbnails = document.querySelectorAll('.img-sub');
	const mainImage = document.getElementById('product-image');



	thumbnails.forEach(thumbnail => {
		thumbnail.addEventListener('click', () => {
			mainImage.src = thumbnail.src;
		})

	})

}
window.addEventListener('load', initImagePreview)






//delete show in cart in cart.jsp
document.addEventListener('DOMContentLoaded', function() {
	setupDeleteOverlays();
});

function setupDeleteOverlays() {
	const deleteTriggers = document.querySelectorAll('.delete');
	deleteTriggers.forEach(trigger => {
		const cartItem = trigger.closest('.cart-space');
		if (!cartItem) return;

		const overlay = cartItem.querySelector('.delete-overlay');
		const noButton = overlay.querySelector('button[type="button"]');
		const yesForm = overlay.querySelector('form');

		const itemId = cartItem.dataset.itemId;
		const POPUP_STATE_KEY = `deleteOverlayActive_${itemId}`;

		overlay.style.opacity = '0';
		overlay.style.visibility = 'hidden';
		overlay.style.transition = 'none';
		overlay.style.display = 'flex';

		const isOverlayActive = sessionStorage.getItem(POPUP_STATE_KEY) === 'true';
		if (isOverlayActive) {
			overlay.style.opacity = '1';
			overlay.style.visibility = 'visible';
		} else {
			overlay.style.opacity = '0';
			overlay.style.visibility = 'hidden';
		}

		setTimeout(() => {

			setTimeout(() => {
			    if (overlay) {
			        overlay.style.transition = 'opacity 0.2s ease, visibility 0.2s ease';
			    }
			}, 10);
		}, 10);


		trigger.addEventListener('click', (e) => {
			e.stopPropagation();
			overlay.style.opacity = '1';
			overlay.style.visibility = 'visible';
			sessionStorage.setItem(POPUP_STATE_KEY, 'true');
		});

		noButton.addEventListener('click', () => {
			overlay.style.opacity = '0';
			overlay.style.visibility = 'hidden';
			sessionStorage.setItem(POPUP_STATE_KEY, 'false');
		});

		overlay.addEventListener('click', (e) => {
			if (e.target === overlay) {
				overlay.style.opacity = '0';
				overlay.style.visibility = 'hidden';
				sessionStorage.setItem(POPUP_STATE_KEY, 'false');
			}
		});
		yesForm.addEventListener('submit', () => {
			sessionStorage.removeItem(POPUP_STATE_KEY);
		});

		yesForm.action = contextPath + '/cart';
	});
}








//clear all cart at once

document.addEventListener('DOMContentLoaded', function() {
	setupClearCart();
});

function setupClearCart() {
	const deleteTriggers = document.querySelectorAll('.trigger-btn-display');
	deleteTriggers.forEach(trigger => {
		const cartItems = trigger.closest('.action-check');
		if (!cartItems) return;

		const overlay = cartItems.querySelector('.clear-cart-btn');
		const noButton = overlay.querySelector('button[type="button"]');
		const yesForm = overlay.querySelector('form');
		const clearAllCheckbox = cartItems.querySelector('#clear-all');
		const errorMessage = cartItems.querySelector('#no-selection-error');

		const allCartItems = document.querySelectorAll('.cart-space');
		const isCartEmpty = allCartItems.length === 0;

		const POPUP_STATE_KEY = 'clearCartOverlayActive';

		if (isCartEmpty) {
			trigger.disabled = true;
			trigger.style.opacity = '0.5';
			clearAllCheckbox.disabled = true;
			clearAllCheckbox.checked = false;
			overlay.classList.remove('active');
			sessionStorage.removeItem(POPUP_STATE_KEY);
			return;
		}

		const isOverlayActive = sessionStorage.getItem(POPUP_STATE_KEY) === 'true';
		if (isOverlayActive) {
			overlay.classList.add('active');
		} else {
			overlay.classList.remove('active');
		}

		setTimeout(() => {

			if (overlay) {


				overlay.style.transition = 'opacity 0.2s ease, visibility 0.2s ease';


			}
		}, 10);

		trigger.addEventListener('click', (e) => {
			e.stopPropagation();

			const selectedItemCheckboxes = cartItems.querySelectorAll('.item-checkbox:checked');
			const isClearAllChecked = clearAllCheckbox.checked;

			if (!isClearAllChecked && selectedItemCheckboxes.length === 0) {
				errorMessage.style.display = 'block';
				sessionStorage.setItem(POPUP_STATE_KEY, 'false');
				return;
			}

			errorMessage.style.display = 'none';

			if (isClearAllChecked) {
				overlay.classList.add('active');
				sessionStorage.setItem(POPUP_STATE_KEY, 'true');
			} else {
				yesForm.submit();
				sessionStorage.setItem(POPUP_STATE_KEY, 'false');
			}
		});

		if (noButton) { 
		    noButton.addEventListener('click', () => {
		        overlay.style.opacity = '0';
		        overlay.style.visibility = 'hidden';
		        sessionStorage.setItem(POPUP_STATE_KEY, 'false');
		    });
		}


		overlay.addEventListener('click', (e) => {
			if (e.target === overlay) {
				overlay.classList.remove('active');
				sessionStorage.setItem(POPUP_STATE_KEY, 'false');
			}
		});
		if (yesForm) {
			yesForm.addEventListener('submit', () => {
				sessionStorage.removeItem(POPUP_STATE_KEY);
			});
			
			yesForm.action = contextPath + '/cart';
		}


		
	});
}



















//product-details add more unit before been send to bank cart table ot not 

document.addEventListener('DOMContentLoaded', function() {
	// Initialize quantity controls (call this once)
	setupCartQuantityControls();
});

function setupCartQuantityControls() {
	// Use EVENT DELEGATION for +/- buttons (works even if items load dynamically)
	document.addEventListener('click', function(e) {
		// Check if clicked element is a minus button
		if (e.target.classList.contains('minus-btn') && e.target.hasAttribute('data-cart-id')) {
			handleMinusClick(e.target);
		}
		// Check if clicked element is a plus button
		else if (e.target.classList.contains('plus-btn') && e.target.hasAttribute('data-cart-id')) {
			handlePlusClick(e.target);
		}
	});
}

// Handle minus button clicks (proven to work)
function handleMinusClick(btn) {
	const cartId = btn.dataset.cartId;
	const quantityDisplay = document.getElementById(`quantity-value-${cartId}`);
	const hiddenInput = document.getElementById(`updated-quantity-${cartId}`);

	// Debug: Log elements to confirm they exist
	console.log('Minus Click - Cart ID:', cartId);
	console.log('Quantity Display:', quantityDisplay);
	console.log('Hidden Input:', hiddenInput);

	let currentQuantity = parseInt(quantityDisplay.textContent.trim()) || 1;
	if (currentQuantity > 1) {
		currentQuantity--;
		updateCartQuantity(quantityDisplay, hiddenInput, currentQuantity);
	}
}

// Handle plus button clicks (fixed!)
function handlePlusClick(btn) {
	const cartId = btn.dataset.cartId;
	const quantityDisplay = document.getElementById(`quantity-value-${cartId}`);
	const hiddenInput = document.getElementById(`updated-quantity-${cartId}`);

	// Debug: Log elements to confirm they exist (check console!)
	console.log('Plus Click - Cart ID:', cartId);
	console.log('Quantity Display:', quantityDisplay);
	console.log('Hidden Input:', hiddenInput);

	// Get current quantity (fix NaN with default 1)
	let currentQuantity = parseInt(quantityDisplay.textContent.trim()) || 1;
	// Increase quantity (no max limit for now—add later if needed)
	currentQuantity++;
	// Update display and hidden input
	updateCartQuantity(quantityDisplay, hiddenInput, currentQuantity);
}

// Reusable update function
function updateCartQuantity(displayElement, hiddenInput, newQuantity) {
	displayElement.textContent = newQuantity;
	hiddenInput.value = newQuantity;
	// Feedback animation
	displayElement.style.transform = 'scale(1.2)';
	setTimeout(() => displayElement.style.transform = 'scale(1)', 150);
}









































//prevent error success and information showing after the action is done and browser is refresh
document.addEventListener('DOMContentLoaded', function() {
	resetMessageStateOnCartActions();

	setupAutoHideMessages();
});

function resetMessageStateOnCartActions() {
	const cartTriggers = document.querySelectorAll(
		'form[action$="/cart"], .delete, .trigger-btn-display, .add-to-cart-btn'
	);

	cartTriggers.forEach(trigger => {
		if (trigger.tagName === 'FORM') {
			trigger.addEventListener('submit', () => {
				clearAllMessageStates();
			});
		}
		else {
			trigger.addEventListener('click', () => {
				clearAllMessageStates();
			});
		}
	});
}

function clearAllMessageStates() {
	const messageClasses = ['load-cart-error', 'success-mgs', 'error', 'info-message'];
	messageClasses.forEach(cls => {
		sessionStorage.removeItem(`hidden_${cls}`);
	});
}

function setupAutoHideMessages() {
	const messageDivs = document.querySelectorAll(
		'.load-cart-error, .success-mgs, .error, .info-message'
	);
	const hideDelay = 3000;

	messageDivs.forEach(div => {
		const messageClass = Array.from(div.classList)[0];
		const HIDDEN_STATE_KEY = `hidden_${messageClass}`;

		const isMessageHidden = sessionStorage.getItem(HIDDEN_STATE_KEY) === 'true';
		if (isMessageHidden || div.textContent.trim() === '') {
			div.style.opacity = '0';
			div.style.display = 'none';
			return;
		}

		setTimeout(() => {
			div.style.opacity = '0';
			div.style.transition = 'opacity 0.5s ease';

			setTimeout(() => {
				div.style.display = 'none';
				sessionStorage.setItem(HIDDEN_STATE_KEY, 'true');
			}, 500);
		}, hideDelay);
	});
}




















