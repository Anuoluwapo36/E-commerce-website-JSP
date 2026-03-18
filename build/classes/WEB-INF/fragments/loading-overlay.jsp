<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<link
	href="https://fonts.googleapis.com/css2?family=Pacifico&display=swap"
	rel="stylesheet">

<div id="loading-overlay"
	style="position: fixed; top: 0; left: 0; background: rgba(255, 255, 255, 0.95); width: 100%; height: 100%; z-index: 99999; display: flex; flex-direction: column; align-items: center; justify-content: center; opacity: 1; visibility: visible; transition: opacity 0.3s ease-in-out, visibility 0.3s ease-in-out;">
	<div
		style="font-size: 56px; font-weight: 400; font-family: 'Pacifico', cursive, Brush Script MT, cursive; margin-bottom: 32px; background: linear-gradient(90deg, #007bff, #00b4d8, #0056b3); -webkit-background-clip: text; background-clip: text; -webkit-text-fill-color: transparent; text-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);">
		Centrum</div>

	<div
		style="width: 45px; height: 45px; border: 4px solid rgba(255, 255, 255, 0.2); border-top: 4px solid orange; border-right: 4px solid #007bff; border-bottom: 4px solid #00b4d8; border-left: 4px solid #0056b3; border-radius: 50%; animation: spin 1.2s linear infinite; box-sizing: border-box;"></div>

	<div
		style="font-size: 14px; color: orange; opacity: 0.8; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', sans-serif; margin-top: 18px;">
		Loading...</div>
</div>


<style>
	body { background: white !important; margin: 0; padding: 0; }
	
	body.loading {
		overflow: hidden !important; 
		height: 100vh !important; 
	}

@keyframes spin { 0% {
	transform: rotate(0deg);
}100%{transform:rotate(360deg);}
}
#loading-overlay.hidden {
	opacity: 0;
	visibility: hidden;
	pointer-events: none;
}
</style>

<script>
    const loadingOverlay = document.getElementById('loading-overlay');
    let productFetchComplete = false;
    let domReady = false;

    function lockScroll() {
        document.body.classList.add('loading');
    }

    function unlockScroll() {
        document.body.classList.remove('loading');
    }

    window.hideLoadingOverlay = function() {
        productFetchComplete = true;
        hideOverlayIfReady();
    };

    function hideOverlayIfReady() {
        if ((domReady || productFetchComplete) && loadingOverlay) {
            setTimeout(() => {
                loadingOverlay.classList.add('hidden');
                loadingOverlay.style.opacity = "0";
                loadingOverlay.style.visibility = "hidden";
                unlockScroll();  
            }, 300);
        }
    }

    document.addEventListener('DOMContentLoaded', () => {
        lockScroll();   

        setTimeout(() => {
            domReady = true;
            hideOverlayIfReady();
        }, 100);
    });

    window.addEventListener('pageshow', (event) => {
        if (event.persisted) {
            productFetchComplete = false;
            domReady = false;

            loadingOverlay.classList.remove('hidden');
            loadingOverlay.style.opacity = "1";
            loadingOverlay.style.visibility = "visible";
            lockScroll();   

            setTimeout(() => {
                hideLoadingOverlay();
            }, 500);
        }
    });

    document.addEventListener('DOMContentLoaded', () => {
        document.querySelectorAll('a:not([target="_blank"])').forEach(link => {
            link.addEventListener('click', () => {
                if (link.href.startsWith(window.location.origin)) {
                    loadingOverlay.classList.remove('hidden');
                    loadingOverlay.style.opacity = "1";
                    loadingOverlay.style.visibility = "visible";
                    lockScroll();  
                }
            });
        });

        document.querySelectorAll('form').forEach(form => {
            form.addEventListener('submit', () => {
                loadingOverlay.classList.remove('hidden');
                loadingOverlay.style.opacity = "1";
                loadingOverlay.style.visibility = "visible";
                lockScroll();   
            });
        });
    });

    setTimeout(() => {
        hideOverlayIfReady();
    }, 2000);
</script>
