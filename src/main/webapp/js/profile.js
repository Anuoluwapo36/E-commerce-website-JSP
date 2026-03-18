/**
 * this for tooggle to change account address 
 *
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

/**
 * Show selected file name for profile image upload
 *  *
const fileInput = document.getElementById('profile-image-input');
const fileNamePreview = document.getElementById('file-name-preview');

if (fileInput && fileNamePreview) {
	fileInput.addEventListener('change', (e) => {
		if (e.target.files.length > 0) {
			fileNamePreview.textContent = e.target.files[0].name;
		} else {
			fileNamePreview.textContent = "";
		}
	});
}
/**
 * this for tooggle decativate safe net before the account get disable by the user 
 
document.addEventListener('DOMContentLoaded', () => {
	const deactivateYesBtn = document.getElementById('accountdelete');
	const deactivateModal = document.querySelector('.deactivate-show');

	const closeCancelBtn = document.getElementById('close');

	if (deactivateYesBtn && deactivateModal) {
		deactivateYesBtn.addEventListener('click', () => {
			deactivateModal.classList.add('active');
			document.body.style.overflow = 'hidden';
		});
	}

	if (closeCancelBtn && deactivateModal) {
		closeCancelBtn.addEventListener('click', () => {
			deactivateModal.classList.remove('active');
			document.body.style.overflow = 'auto';
		});
	}

	if (deactivateModal) {
		deactivateModal.addEventListener('click', (e) => {
			if (e.target === deactivateModal) {
				deactivateModal.classList.remove('active');
				document.body.style.overflow = 'auto';
			}
		});
	}

	document.addEventListener('keydown', (e) => {
		if (e.key === 'Escape' && deactivateModal?.classList.contains('active')) {
			deactivateModal.classList.remove('active');
			document.body.style.overflow = 'auto';
		}
	});
});









/**
 * Toggle Address Modal 
 */
function addAddress() {
    const clickBtn = document.getElementById('change-address');
    const openContainer = document.querySelector('.show-up');
    const close = document.querySelector('.close-x');

    //exit if elements are missing
    if (!clickBtn || !openContainer || !close) {
        console.warn("Address modal elements not found");
        return;
    }

    // Open modal and Lock scroll 
    clickBtn.addEventListener('click', () => {
        if (!openContainer.classList.contains('active')) {
            openContainer.classList.add('active');
            lockScroll(); 
        }
    });

    // Close modal and Unlock scroll
    const closeAddressModal = () => {
        if (openContainer.classList.contains('active')) {
            openContainer.classList.remove('active');
            unlockScroll(); 
        }
    };

    // Close triggers
    close.addEventListener('click', closeAddressModal);
    openContainer.addEventListener('click', (e) => e.target === openContainer && closeAddressModal());
    document.addEventListener('keydown', (e) => e.key === 'Escape' && openContainer.classList.contains('active') && closeAddressModal());
}

/**
 * Toggle Deactivate Modal 
 */
document.addEventListener('DOMContentLoaded', () => {
    const deactivateYesBtn = document.getElementById('accountdelete');
    const deactivateModal = document.querySelector('.deactivate-show');
    const closeCancelBtn = document.getElementById('close');

    // exit if elements are missing
    if (!deactivateYesBtn || !deactivateModal || !closeCancelBtn) {
        console.warn("Deactivate modal elements not found");
        return;
    }

    // Open modal and Lock scroll
    deactivateYesBtn.addEventListener('click', () => {
        if (!deactivateModal.classList.contains('active')) {
            deactivateModal.classList.add('active');
            lockScroll(); 
        }
    });

    // Close modal and Unlock scroll
    const closeDeactivateModal = () => {
        if (deactivateModal.classList.contains('active')) {
            deactivateModal.classList.remove('active');
            unlockScroll();
        }
    };

    // Close triggers
    closeCancelBtn.addEventListener('click', closeDeactivateModal);
    deactivateModal.addEventListener('click', (e) => e.target === deactivateModal && closeDeactivateModal());
    document.addEventListener('keydown', (e) => e.key === 'Escape' && deactivateModal.classList.contains('active') && closeDeactivateModal());
});

/**
 * Reusable Scroll Lock Function this avoid writing duplicate code 
 */
function lockScroll() {
    // Save current scroll position to prevent jump
    const scrollTop = window.pageYOffset || document.documentElement.scrollTop;
    const scrollLeft = window.pageXOffset || document.documentElement.scrollLeft;

    // Lock scroll
    document.body.style.position = 'fixed';
    document.body.style.top = `-${scrollTop}px`;
    document.body.style.left = `-${scrollLeft}px`;
    document.body.style.width = '100%';
    document.documentElement.style.overflow = 'hidden';
}

/**
 * Reusable Scroll Unlock Function same as this 
 */
function unlockScroll() {
    // Restore scroll position
    const scrollTop = -parseInt(document.body.style.top || '0');
    const scrollLeft = -parseInt(document.body.style.left || '0');

    // Unlock scroll
    document.body.style.position = '';
    document.body.style.top = '';
    document.body.style.left = '';
    document.body.style.width = '';
    document.documentElement.style.overflow = '';

    // Scroll to original position
    window.scrollTo(scrollLeft, scrollTop);
}

/**
 * Show Selected File Name for Profile Image Upload
 */
const fileInput = document.getElementById('profile-image-input');
const fileNamePreview = document.getElementById('file-name-preview');

if (fileInput && fileNamePreview) {
    fileInput.addEventListener('change', (e) => {
        fileNamePreview.textContent = e.target.files.length > 0 ? e.target.files[0].name : "";
    });
}

// Initialize address modal for actions 
addAddress();