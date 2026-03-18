/**
 * 
 */
//pop up toggle for safeguard if usewr want to deactive there account or not 
onDeactivate();


function onDeactivate() {
	const ontriggerShow = document.getElementById('accountdelete');
	const ontriggerPop = document.querySelector('.deactivate-show');
	const closeDeactivate = document.getElementById('close');

	const POPUP_STATE_KEY = 'deletePopupActive';


	const isPopActive = sessionStorage.getItem(POPUP_STATE_KEY) === 'true';
	if (isPopActive) {
		ontriggerPop.classList.add('active');
	}

	setTimeout(() => {
		ontriggerPop.style.transition = 'opacity 0.2s ease, visibility 0.2s ease';
	}, 10);

	ontriggerShow.addEventListener('click', () => {
		const isCurrentlyActive = ontriggerPop.classList.contains('active');

		ontriggerPop.classList.toggle('active')
		sessionStorage.setItem(POPUP_STATE_KEY, !isCurrentlyActive);

	});


	closeDeactivate.addEventListener('click', () => {
		ontriggerPop.classList.remove('active');
		sessionStorage.setItem(POPUP_STATE_KEY, 'false');
	});


	window.addEventListener('beforeunload', () => {
		if (!ontriggerPop.classList.contains('active')) {
			sessionStorage.removeItem(POPUP_STATE_KEY);
		}
	});

}

