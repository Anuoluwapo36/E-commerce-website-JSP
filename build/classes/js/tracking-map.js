// Wait for the page to fully load before initializing the map
document.addEventListener('DOMContentLoaded', function() {
    // 1. Get the map container element
    const mapContainer = document.getElementById('tracking-map');
    
    // 2. Check if the container exists (avoid errors)
    if (!mapContainer) {
        console.log("Map container not found!");
        return;
    }

    // 3. Initialize the map: Set center (latitude/longitude) and zoom level
    const map = L.map('tracking-map').setView([-33.8688, 151.2093], 13); // Example: Sydney, Australia

    // 4. Add tile layer (required—Leaflet doesn't provide maps by default; use OpenStreetMap)
    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
        attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors',
        maxZoom: 19
    }).addTo(map);

    // Optional: Add a marker (e.g., delivery location)
    const deliveryMarker = L.marker([-33.8688, 151.2093]).addTo(map)
        .bindPopup('Your order is here!')
        .openPopup();
});




//document.addEventListener('DOMContentLoaded', function() {
    const mapContainer = document.getElementById('tracking-map');
    if (!mapContainer) return;

    // Initialize map (keep your coordinates/zoom)
    const map = L.map('tracking-map').setView([-33.8688, 151.2093], 13);

    // Replace OSM with OpenTopoMap (firewall-friendly)
    L.tileLayer('https://{s}.tile.opentopomap.org/{z}/{x}/{y}.png', {
        attribution: 'Map data: &copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors, <a href="http://viewfinderpanoramas.org">SRTM</a> | Map style: &copy; <a href="https://opentopomap.org">OpenTopoMap</a> (<a href="https://creativecommons.org/licenses/by-sa/3.0/">CC-BY-SA</a>)',
        maxZoom: 17
    }).addTo(map);

    // Add marker (keep your existing code)
    L.marker([-33.8688, 151.2093]).addTo(map)
        .bindPopup('Your order is here!')
        .openPopup();
});