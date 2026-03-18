/**
 * 
 */


/**
 * this allow selection of diffrent payment option in 
 */
function showPaymentForm(method) {
    const allForms = document.querySelectorAll('.payment-form');
    allForms.forEach(form => form.style.display = 'none');

    // Show overlay
    const loadingOverlay = document.getElementById('loading-overlay');
    if (loadingOverlay) {
        loadingOverlay.classList.remove('hidden');
        loadingOverlay.style.opacity = "1";
        loadingOverlay.style.visibility = "visible";
    }

    // Show selected form **after loading**
    const selectedForm = document.getElementById(`form-${method}`);
    if (selectedForm) {
        // simulate loading delay (replace this with real content load trigger)
        setTimeout(() => {
            selectedForm.style.display = 'block';
            formLoaded(method); // hide overlay when form is ready
        }, 300); // adjust delay or trigger dynamically from your JSP
    } else {
        alert(`Error: ${method} form not found`);
        loadingOverlay.classList.add('hidden');
        loadingOverlay.style.opacity = "0";
        loadingOverlay.style.visibility = "hidden";
    }
}

function showPaymentForm(method) {
	const allForms = document.querySelectorAll('.payment-form');
	allForms.forEach(form => form.style.display = 'none');

	const selectedForm = document.getElementById(`form-${method}`);
	if (selectedForm) {
		selectedForm.style.display = 'block';
	} else {
		alert(`Error: ${method} form not found`);
	}
}


//to bring out address container
function addAddress() {
	const clickBtn = document.getElementById('change-address');
	const openContainer = document.querySelector('.show-up');
	const close = document.querySelector('.close-x');

	clickBtn.addEventListener('click', () => {
		openContainer.classList.toggle('active')

	});

	close.addEventListener('click', () => {
		openContainer.classList.remove('active');

	});


}

addAddress();









function formatCardNumber(input) {
	// Remove non-digit characters (for easier validation)
	let value = input.value.replace(/\D/g, '');

	// Format with spaces (e.g., 4111 1111 1111 1111)
	let formattedValue = value.replace(/(\d{4})/g, '$1 ').trim();
	input.value = formattedValue;

	// Detect card type and set cardType hidden field
	detectCardType(value);
}

// Detect if the card is Visa (starts with 4) or Mastercard (starts with 5)
function detectCardType(cardNumber) {
	const cardTypeInput = document.getElementById('cardType');

	if (cardNumber.startsWith('4')) {
		cardTypeInput.value = '1'; // Visa = 1
	} else if (cardNumber.startsWith('5')) {
		cardTypeInput.value = '2'; // Mastercard = 2
	} else {
		cardTypeInput.value = ''; // Not Visa/Mastercard
	}
}



    // Card number formatting (keeps your existing logic)
    function formatCardNumber(input) {
        let value = input.value.replace(/\D/g, ''); // Remove non-digits
        if (value.length > 16) value = value.substring(0, 19); // Limit to 19 digits (for Amex)
        let formatted = '';
        for (let i = 0; i < value.length; i++) {
            if (i > 0 && i % 4 === 0) formatted += '-';
            formatted += value[i];
        }
        input.value = formatted;
    }

    // Expiry date formatting (keeps your existing logic)
    function formatExpiry(input) {
        let value = input.value.replace(/\D/g, ''); // Remove non-digits
        if (value.length > 4) value = value.substring(0, 4); // Limit to 4 digits
        if (value.length >= 2) {
            input.value = value.substring(0, 2) + '/' + value.substring(2);
        } else {
            input.value = value;
        }
    }

    // Auto-set cardType based on card number (Visa=1, Mastercard=2)
    function setCardType() {
        const cardNumber = document.getElementById("cardNumber").value.replace(/\D/g, '');
        const cardTypeInput = document.getElementById("cardType");
        
        if (cardNumber.startsWith('4')) {
            cardTypeInput.value = '1'; // Visa
        } else if (cardNumber.startsWith('5')) {
            cardTypeInput.value = '2'; // Mastercard
        } else {
            cardTypeInput.value = ''; // Unknown (will trigger validation)
        }
    }

    // Add event listener to cardNumber to update cardType
    document.addEventListener('DOMContentLoaded', function() {
        const cardNumberInput = document.getElementById("cardNumber");
        if (cardNumberInput) {
            cardNumberInput.addEventListener('input', setCardType);
        }
    });
