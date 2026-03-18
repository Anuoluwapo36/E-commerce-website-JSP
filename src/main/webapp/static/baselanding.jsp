<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Centrum - Global Trade</title>

    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600;700&display=swap" rel="stylesheet">
       <link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/baselanding.css">

</head>

<body>
<div class="main">

<div class="navbar">
    <div class="nav-left"><span>Centrum</span></div>

    <div class="nav-items">
        <a href="#">All categories</a>
        <a href="#">Featured selections</a>
        <a href="#">Order protections</a>
        <a href="#">Buyer space</a>
        <a href="#">Help Center</a>
        <a href="#">Become a seller</a>
    </div>
</div>

<!-- Hero Section -->
<div class="hero">
    <div class="hero-content">
        <p style="opacity:0.8; font-size:15px; margin-bottom:10px;">● Learn about quality control</p>

        <h1>
            Quality control B2B , B2P global marketplace 
        </h1>
        
        

     
    </div>
</div>

<!-- Feature Cards -->
<div class="features">
    <div class="feature-card">
        <h3>Millions of business offerings</h3>
        <p>Explore factories, wholesalers & verified suppliers.</p>
    </div>

    <div class="feature-card">
        <h3>Assured quality & transactions</h3>
        <p>Production monitoring, secure payments & trade assurance.</p>
    </div>

    <div class="feature-card">
        <h3>One-stop trading solutions</h3>
        <p>Logistics, inspection, customization and more.</p>
    </div>

    <div class="feature-card">
        <h3>Tailored buying experience</h3>
        <p>Smart sourcing tools and personalized suggestions.</p>
    </div>
</div>

</div>
<!-- Footer (China-Africa Focus + Informative Text) -->
<footer id="help">
    <div class="container">
        <!-- Map & Core Value Section -->
        <div class="footer-map-section">
            <div class="map-container">
                <!-- China-Africa Map Graphic (Use a high-quality SVG/PNG for best results) -->
                <img src="${pageContext.request.contextPath}/Image/china-africa-map.svg" alt="China to Africa Trade Route Map" class="trade-map" loading="lazy">
                <div class="map-overlay">
                    <h3>Connecting China’s Factories to Africa’s Markets</h3>
                    <p>Our curated logistics network bridges continents, ensuring fast, reliable trade for businesses of all sizes.</p>
                </div>
            </div>

            <div class="footer-value-props">
                <div class="value-prop">
                    <h4>Direct Supplier Access</h4>
                    <p>Cut out middlemen—source directly from verified Chinese factories and manufacturers.</p>
                </div>
                <div class="value-prop">
                    <h4>Africa-Centric Logistics</h4>
                    <p>Tailored shipping solutions for 15+ African countries, with dedicated customs support.</p>
                </div>
                <div class="value-prop">
                    <h4>Localized Support</h4>
                    <p>Multilingual teams in both China and Africa to assist with orders, payments, and disputes.</p>
                </div>
            </div>
        </div>

        <!-- Standard Footer Links -->
        <div class="footer-container">
            <div class="footer-left">
                <a href="${pageContext.request.contextPath}/baselanding.jsp" class="footer-logo">Centrum</a>
                <p>Trusted by 50,000+ businesses across China and Africa—your partner for transparent, quality-driven global trade.</p>
                <div class="social-links">
                    <a href="#" aria-label="Facebook"><i class="fab fa-facebook-f"></i></a>
                    <a href="#" aria-label="Instagram"><i class="fab fa-instagram"></i></a>
                    <a href="#" aria-label="LinkedIn"><i class="fab fa-linkedin-in"></i></a>
                    <a href="#" aria-label="Twitter"><i class="fab fa-twitter"></i></a>
                    <a href="#" aria-label="WhatsApp"><i class="fab fa-whatsapp"></i></a>
                </div>
            </div>

            <div class="footer-links">
                <h4>About Us</h4>
                <a href="#">Our Mission</a>
                <a href="#">Logistics Network</a>
                <a href="#">Quality Checks</a>
                <a href="#">Partnerships</a>
                <a href="#">Careers</a>
            </div>

            <div class="footer-links">
                <h4>Help & Support</h4>
                <a href="#">FAQ</a>
                <a href="#protection" id="protection">Order Protection</a>
                <a href="#shipping" id="shipping">Shipping Information</a>
                <a href="#">Contact Support</a>
                <a href="#">How to Buy</a>
                <a href="#">How to Sell</a>
            </div>

            <div class="footer-links">
                <h4>Legal</h4>
                <a href="#">Privacy Policy</a>
                <a href="#">Terms & Conditions</a>
                <a href="#">Cookie Policy</a>
                <a href="#">Dispute Resolution</a>
                <a href="#">Anti-Fraud Policy</a>
            </div>

            <div class="footer-links">
                <h4>Contact</h4>
                <a href="mailto:support@centrumtrade.com">support@centrumtrade.com</a>
                <a href="tel:+861234567890">China: +86 123 4567 890</a>
                <a href="tel:+2348012345678">Nigeria: +234 801 234 5678</a>
                <a href="tel:+254701234567">Kenya: +254 701 234 567</a>
            </div>
        </div>

        <!-- Bottom Footer -->
        <div class="footer-bottom">
            <div class="footer-disclaimer">
                <p>Centrum is a licensed cross-border trade platform, compliant with Chinese and African trade regulations. We verify all suppliers and inspect goods to ensure quality and safety.</p>
            </div>
            <p>© 2025 Centrum Global Trade. All Rights Reserved.</p>
        </div>
    </div>
</footer>
</body>
</html>
