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











//for banner animation effect
document.addEventListener('DOMContentLoaded', function() {
    const carousel = document.querySelector('.banner-carousel');
    const slides = document.querySelectorAll('.carousel-slide');
    const dots = document.querySelectorAll('.carousel-dot');
    const prevBtn = document.querySelector('.carousel-prev');
    const nextBtn = document.querySelector('.carousel-next');
    
    const SWITCH_INTERVAL = 40000; 
    const ANIMATION_DURATION = 300; 
    const SETTLE_DELAY = 2000; 
    const COOLDOWN_TIME = 500; 
    
    let currentIndex = 0;
    let autoSwitchTimer;
    let isAnimating = false;
    let animationsEnabled = false; 
    
	
    function initInitialSlide() {
        const savedState = localStorage.getItem('carouselState');
        if (savedState) {
            try {
                const { index } = JSON.parse(savedState);
                currentIndex = Math.max(0, Math.min(index, slides.length - 1)); 
            } catch (e) {
                currentIndex = 0; 
            }
        }

        slides.forEach((slide, i) => {
            slide.classList.toggle('active', i === currentIndex);
            slide.style.transform = i === currentIndex ? 'translateX(0)' : 'translateX(100%)';
            slide.style.visibility = i === currentIndex ? 'visible' : 'hidden';
        });

        dots.forEach((dot, i) => dot.classList.toggle('active', i === currentIndex));
    }

    
    function enableAnimations() {
        setTimeout(() => {
            animationsEnabled = true;
            carousel.classList.add('carousel-animate-switches'); 
            startAutoSwitch(); 
        }, SETTLE_DELAY);
    }

    
    function startAutoSwitch() {
        const savedState = localStorage.getItem('carouselState');
        let timeLeft = SWITCH_INTERVAL;

        
        if (savedState) {
            try {
                const { timeLeft: savedTimeLeft } = JSON.parse(savedState);
                timeLeft = Math.max(0, savedTimeLeft);
            } catch (e) {
                timeLeft = SWITCH_INTERVAL;
            }
        }

        if (autoSwitchTimer) clearTimeout(autoSwitchTimer);
        autoSwitchTimer = setTimeout(() => {
            nextSlide();
        }, timeLeft);

        setInterval(saveState, 1000);
    }

    
    function nextSlide() {
        updateSlide((currentIndex + 1) % slides.length);
    }

    function prevSlide() {
        updateSlide((currentIndex - 1 + slides.length) % slides.length);
    }

    function updateSlide(targetIndex) {
        if (isAnimating || targetIndex === currentIndex || !animationsEnabled) return;

        isAnimating = true;
        const prevIndex = currentIndex;
        currentIndex = targetIndex;

        slides[currentIndex].style.transform = 'translateX(100%)';
        slides[currentIndex].style.visibility = 'visible';

        slides[prevIndex].classList.remove('active');
        slides[currentIndex].classList.add('active');
        dots.forEach((dot, i) => dot.classList.toggle('active', i === currentIndex));

        setTimeout(() => {
            isAnimating = false;
            if (autoSwitchTimer) clearTimeout(autoSwitchTimer);
            autoSwitchTimer = setTimeout(() => {
                nextSlide();
            }, SWITCH_INTERVAL);
        }, ANIMATION_DURATION);
    }

    
    function saveState() {
        if (!autoSwitchTimer) return;

        const timeLeft = SWITCH_INTERVAL - (Date.now() - autoSwitchTimer._startTime);
        const state = {
            index: currentIndex,
            timeLeft: Math.max(0, timeLeft)
        };
        localStorage.setItem('carouselState', JSON.stringify(state));
    }

  
    function debounce(func, delay) {
        let timeoutId;
        return (...args) => {
            clearTimeout(timeoutId);
            timeoutId = setTimeout(() => func.apply(this, args), delay);
        };
    }

    
    const debouncedUpdateSlide = debounce(updateSlide, COOLDOWN_TIME);

    nextBtn.addEventListener('click', () => {
        if (animationsEnabled) nextSlide(); 
    });

    prevBtn.addEventListener('click', () => {
        if (animationsEnabled) prevSlide();
    });

    dots.forEach(dot => {
        dot.addEventListener('click', () => {
            if (animationsEnabled) {
                const targetIndex = parseInt(dot.dataset.index);
                debouncedUpdateSlide(targetIndex);
            }
        });
    });

    
    initInitialSlide(); 
    enableAnimations(); 
    window.addEventListener('beforeunload', saveState); 
});