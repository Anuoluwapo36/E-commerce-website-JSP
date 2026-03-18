<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Centrum | Premium E-Commerce Platform</title>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
<style>
:root {
	--primary: #000000; 
	--primary-dark: #333333; 
	--primary-light: #666666;
	--secondary: #f1f5f9; 
	--accent: #000000; 
	--success: #27ae60; 
	--warning: #f39c12; 
	--light: #ffffff;
	--dark: #000000;
	--gray: #666666; 
	--gray-light: #e2e8f0;
	--white: #ffffff; 
	--gradient: linear-gradient(135deg, #000000, #333333);
	--gradient-dark: linear-gradient(135deg, #000000, #1a1a1a);
	--shadow-sm: 0 2px 8px rgba(0, 0, 0, 0.08);
	--shadow-md: 0 4px 20px rgba(0, 0, 0, 0.1);
	--shadow-lg: 0 20px 60px rgba(0, 0, 0, 0.1);
}

* {
	margin: 0;
	padding: 0;
	box-sizing: border-box;
}

body {
	font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto,
		Oxygen, Ubuntu, Cantarell, sans-serif;
	color: var(--dark);
	background: var(--white);
	line-height: 1.6;
	overflow-x: hidden;
}

/* Header */
.header {
	position: fixed;
	top: 0;
	left: 0;
	right: 0;
	z-index: 1000;
	padding: 1.5rem 5%;
	background: rgba(255, 255, 255, 0.95);
	backdrop-filter: blur(10px);
	border-bottom: 1px solid var(--gray-light);
	animation: slideDown 0.5s ease;
}

.nav-container {
	max-width: 1400px;
	margin: 0 auto;
	display: flex;
	justify-content: space-between;
	align-items: center;
}

.logo {
	display: flex;
	align-items: center;
	gap: 0.75rem;
	font-size: 1.75rem;
	font-weight: 600;
	color: var(--dark);
	text-decoration: none;
}

.logo-icon {
	width: 45px;
	height: 45px;
	background: var(--primary);
	border-radius: 8px;
	display: flex;
	align-items: center;
	justify-content: center;
	color: white;
	font-size: 1.5rem;
	font-weight: 600;
}

.logo-text {
	color: var(--primary);
	font-weight: 600;
}

.nav-links {
	display: flex;
	align-items: center;
	gap: 2.5rem;
}

.nav-link {
	color: var(--gray);
	text-decoration: none;
	font-weight: 500;
	position: relative;
	padding: 0.5rem 0;
	transition: color 0.3s ease;
	font-size: 14px;
}

.nav-link:hover {
	color: var(--primary);
}

.nav-link.active {
	color: var(--primary);
}

.nav-link.active::after {
	content: '';
	position: absolute;
	bottom: 0;
	left: 0;
	right: 0;
	height: 2px;
	background: var(--primary);
	border-radius: 2px;
}

.nav-actions {
	display: flex;
	gap: 1rem;
	align-items: center;
}

.btn {
	padding: 0.75rem 1.75rem;
	border-radius: 8px;
	font-weight: 500;
	text-decoration: none;
	display: inline-flex;
	align-items: center;
	gap: 0.5rem;
	transition: all 0.2s;
	border: none;
	cursor: pointer;
	font-size: 14px;
	font-family: inherit;
}

.btn-primary {
	background: var(--primary);
	color: white;
	box-shadow: var(--shadow-md);
}

.btn-primary:hover {
	background: var(--primary-dark);
	transform: translateY(-1px);
	box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
}

.btn-secondary {
	background: transparent;
	color: var(--primary);
	border: 1px solid #d1d5db;
}

.btn-secondary:hover {
	background: #f9fafb;
	border-color: #9ca3af;
	transform: translateY(-1px);
}

.btn-dark {
	background: var(--primary);
	color: white;
}

.btn-dark:hover {
	background: var(--primary-dark);
	transform: translateY(-2px);
}

/* Hero Section */
.hero {
	min-height: 100vh;
	display: flex;
	align-items: center;
	padding: 8rem 5% 4rem;
	position: relative;
	overflow: hidden;
}

.hero-content {
	max-width: 1400px;
	margin: 0 auto;
	display: grid;
	grid-template-columns: 1fr 1fr;
	gap: 4rem;
	align-items: center;
	position: relative;
	z-index: 2;
}

.hero-text h1 {
	font-size: 3.5rem;
	font-weight: 600;
	line-height: 1.2;
	margin-bottom: 1.5rem;
	color: var(--dark);
}

.hero-text p {
	font-size: 1.25rem;
	color: var(--gray);
	margin-bottom: 2.5rem;
	max-width: 500px;
}

.hero-buttons {
	display: flex;
	gap: 1rem;
	margin-bottom: 3rem;
	flex-wrap: wrap;
}

.hero-stats {
	display: flex;
	gap: 3rem;
	margin-top: 2rem;
}

.stat {
	text-align: center;
}

.stat-value {
	display: block;
	font-size: 2.5rem;
	font-weight: 600;
	color: var(--primary);
	line-height: 1;
}

.stat-label {
	color: var(--gray);
	font-size: 0.9rem;
	text-transform: uppercase;
	letter-spacing: 1px;
	margin-top: 0.5rem;
}

.hero-visual {
	position: relative;
}

.floating-cards {
	position: relative;
	height: 500px;
}

.card-1, .card-2, .card-3 {
	position: absolute;
	background: white;
	border-radius: 16px;
	padding: 2rem;
	box-shadow: var(--shadow-lg);
	border: 1px solid var(--gray-light);
	transition: all 0.3s ease;
}

.card-1 {
	top: 0;
	right: 0;
	width: 300px;
	z-index: 3;
	animation: float 6s ease-in-out infinite;
}

.card-2 {
	top: 100px;
	left: 0;
	width: 280px;
	z-index: 2;
	animation: float 6s ease-in-out infinite 0.2s;
}

.card-3 {
	bottom: 0;
	right: 50px;
	width: 320px;
	z-index: 1;
	animation: float 6s ease-in-out infinite 0.4s;
}

.card-header {
	display: flex;
	justify-content: space-between;
	align-items: center;
	margin-bottom: 1.5rem;
}

.card-title {
	font-size: 1.1rem;
	font-weight: 600;
	color: var(--dark);
}

.card-badge {
	background: var(--primary);
	color: white;
	padding: 0.25rem 0.75rem;
	border-radius: 20px;
	font-size: 0.8rem;
	font-weight: 600;
}

.card-stats {
	display: grid;
	grid-template-columns: 1fr 1fr;
	gap: 1rem;
}

.mini-stat {
	text-align: center;
}

.mini-value {
	font-size: 1.5rem;
	font-weight: 600;
	color: var(--dark);
	display: block;
}

.mini-label {
	font-size: 0.8rem;
	color: var(--gray);
}

/* Features Section */
.features {
	padding: 6rem 5%;
	background: var(--secondary);
	position: relative;
}

.section-header {
	text-align: center;
	max-width: 800px;
	margin: 0 auto 5rem;
}

.section-title {
	font-size: 3rem;
	font-weight: 600;
	margin-bottom: 1rem;
	color: var(--dark);
}

.section-subtitle {
	font-size: 1.25rem;
	color: var(--gray);
}

.features-grid {
	display: grid;
	grid-template-columns: repeat(auto-fit, minmax(350px, 1fr));
	gap: 2.5rem;
	max-width: 1400px;
	margin: 0 auto;
}

.feature-card {
	background: white;
	padding: 3rem 2.5rem;
	border-radius: 16px;
	box-shadow: var(--shadow-md);
	border: 1px solid var(--gray-light);
	transition: all 0.3s ease;
	position: relative;
	overflow: hidden;
}

.feature-card:hover {
	transform: translateY(-10px);
	box-shadow: var(--shadow-lg);
	border-color: var(--primary);
}

.feature-card::before {
	content: '';
	position: absolute;
	top: 0;
	left: 0;
	right: 0;
	height: 4px;
	background: var(--primary);
}

.feature-icon {
	width: 70px;
	height: 70px;
	background: var(--primary);
	border-radius: 12px;
	display: flex;
	align-items: center;
	justify-content: center;
	margin-bottom: 2rem;
	color: white;
	font-size: 1.75rem;
}

.feature-title {
	font-size: 1.5rem;
	margin-bottom: 1rem;
	color: var(--dark);
	font-weight: 600;
}

.feature-desc {
	color: var(--gray);
	margin-bottom: 1.5rem;
	font-size: 1rem;
}

/* CTA Section */
.cta {
	padding: 8rem 5%;
	background: var(--gradient-dark);
	color: white;
	position: relative;
	overflow: hidden;
}

.cta-content {
	max-width: 800px;
	margin: 0 auto;
	text-align: center;
	position: relative;
	z-index: 2;
}

.cta h2 {
	font-size: 3.5rem;
	font-weight: 600;
	margin-bottom: 1.5rem;
	line-height: 1.2;
}

.cta p {
	font-size: 1.25rem;
	opacity: 0.9;
	margin-bottom: 3rem;
	max-width: 600px;
	margin-left: auto;
	margin-right: auto;
}

.btn-white {
	background: white;
	color: var(--primary);
	border: none;
}

.btn-white:hover {
	background: #f3f4f6;
	transform: translateY(-2px);
	box-shadow: var(--shadow-lg);
}

/* Testimonials */
.testimonials {
	padding: 6rem 5%;
	background: white;
}

.testimonials-grid {
	display: grid;
	grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
	gap: 2rem;
	max-width: 1200px;
	margin: 3rem auto 0;
}

.testimonial-card {
	background: white;
	padding: 2.5rem;
	border-radius: 16px;
	box-shadow: var(--shadow-md);
	border: 1px solid var(--gray-light);
}

.testimonial-text {
	font-size: 1.1rem;
	color: var(--gray);
	font-style: italic;
	margin-bottom: 2rem;
	line-height: 1.7;
}

.testimonial-author {
	display: flex;
	align-items: center;
	gap: 1rem;
}

.author-avatar {
	width: 50px;
	height: 50px;
	background: var(--primary);
	border-radius: 8px;
	display: flex;
	align-items: center;
	justify-content: center;
	color: white;
	font-weight: 600;
}

.author-info h4 {
	color: var(--dark);
	margin-bottom: 0.25rem;
	font-weight: 600;
}

.author-info p {
	color: var(--gray);
	font-size: 0.9rem;
}

/* Footer */
.footer {
	background: var(--primary);
	color: white;
	padding: 5rem 5% 2rem;
}

.footer-grid {
	display: grid;
	grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
	gap: 3rem;
	margin-bottom: 3rem;
	max-width: 1400px;
	margin-left: auto;
	margin-right: auto;
}

.footer-section h3 {
	font-size: 1.25rem;
	margin-bottom: 1.5rem;
	color: white;
	font-weight: 600;
}

.footer-links {
	list-style: none;
}

.footer-links li {
	margin-bottom: 0.75rem;
}

.footer-links a {
	color: rgba(255, 255, 255, 0.7);
	text-decoration: none;
	transition: color 0.3s ease;
	font-size: 14px;
}

.footer-links a:hover {
	color: white;
}

.social-links {
	display: flex;
	gap: 1rem;
	margin-top: 1.5rem;
}

.social-links a {
	width: 40px;
	height: 40px;
	background: rgba(255, 255, 255, 0.1);
	border-radius: 8px;
	display: flex;
	align-items: center;
	justify-content: center;
	color: white;
	text-decoration: none;
	transition: all 0.3s ease;
}

.social-links a:hover {
	background: rgba(255, 255, 255, 0.2);
	transform: translateY(-2px);
}

.copyright {
	text-align: center;
	padding-top: 2rem;
	border-top: 1px solid rgba(255, 255, 255, 0.1);
	color: rgba(255, 255, 255, 0.5);
	font-size: 0.9rem;
	max-width: 1400px;
	margin-left: auto;
	margin-right: auto;
}

/* Animations */
@
keyframes slideDown {from { transform:translateY(-100%);
	opacity: 0;
}

to {
	transform: translateY(0);
	opacity: 1;
}

}
@keyframes float { 
0%, 
100% {transform: translateY(0);
}

50%{transform:translateY(-20px);}
@keyframes fadeInUp {from { opacity:0;
	transform: translateY(30px);
}

to {
	opacity: 1;
	transform: translateY(0);
}

}
.animate {
	animation: fadeInUp 0.6s ease forwards;
}

/* Responsive */
@media ( max-width : 1024px) {
	.hero-content {
		grid-template-columns: 1fr;
		text-align: center;
	}
	.hero-text p {
		margin-left: auto;
		margin-right: auto;
	}
	.hero-buttons {
		justify-content: center;
	}
	.floating-cards {
		display: none;
	}
	.features-grid {
		grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
	}
}

@media ( max-width : 768px) {
	.header {
		padding: 1rem 5%;
	}
	.nav-links {
		display: none;
	}
	.mobile-menu-btn {
		display: block;
		background: none;
		border: none;
		color: var(--dark);
		font-size: 1.5rem;
		cursor: pointer;
	}
	.hero-text h1 {
		font-size: 2.5rem;
	}
	.section-title {
		font-size: 2.5rem;
	}
	.cta h2 {
		font-size: 2.5rem;
	}
	.hero-buttons {
		flex-direction: column;
		align-items: center;
	}
	.btn {
		width: 100%;
		max-width: 300px;
		justify-content: center;
	}
}

@media ( min-width : 769px) {
	.mobile-menu-btn {
		display: none;
	}
}

@media ( max-width : 480px) {
	.hero-text h1 {
		font-size: 2rem;
	}
	.section-title {
		font-size: 2rem;
	}
	.feature-card {
		padding: 2rem 1.5rem;
	}
	.hero-stats {
		flex-direction: column;
		gap: 2rem;
	}
}

/* Custom Scrollbar */
::-webkit-scrollbar {
	width: 10px;
}

::-webkit-scrollbar-track {
	background: #f1f1f1;
}

::-webkit-scrollbar-thumb {
	background: var(--primary);
	border-radius: 5px;
}

::-webkit-scrollbar-thumb:hover {
	background: var(--primary-dark);
}
</style>
</head>
<body>
	<!-- Header -->
	<header class="header">
		<div class="nav-container">
			<a href="#" class="logo">
				<div class="logo-icon">
					<i class="fas fa-store"></i>
				</div> <span class="logo-text">Centrum</span>
			</a>

			<nav class="nav-links">
				<a href="#features" class="nav-link active">Features</a> <a
					href="#testimonials" class="nav-link">Testimonials</a> <a
					href="#pricing" class="nav-link">Pricing</a> <a href="#about"
					class="nav-link">About</a>
			</nav>

			<div class="nav-actions">
				<a href="<%=request.getContextPath()%>/logins"
					class="btn btn-secondary"> <i class="fas fa-sign-in-alt"></i>
					seller Log In
				</a> <a href="<%=request.getContextPath()%>/register"
					class="btn btn-primary"> <i class="fas fa-user-plus"></i>
					Seller Register
				</a> <a href="<%=request.getContextPath()%>/public/login-user.jsp"
					class="btn btn-dark"> <i class="fas fa-chart-line"></i> Buyer Space
				</a>
			</div>

			<button class="mobile-menu-btn" id="mobileMenuBtn">
				<i class="fas fa-bars"></i>
			</button>
		</div>
	</header>

	<!-- Hero Section -->
	<section class="hero">
		<div class="hero-content">
			<div class="hero-text animate">
				<h1>Launch Your E-Commerce Empire with Centrum</h1>
				<p>Everything you need to start, run, and scale your online
					business. Join thousands of successful sellers who trust Centrum
					for their e-commerce success.</p>

				<div class="hero-buttons">
					<a href="seller/register-step1.jsp" class="btn btn-primary"> <i
						class="fas fa-rocket"></i> Start Selling Free
					</a> <a href="seller/login.jsp" class="btn btn-secondary"> <i
						class="fas fa-key"></i> Log In to Account
					</a> <a href="#demo" class="btn btn-secondary"> <i
						class="fas fa-play-circle"></i> Watch Demo
					</a>
				</div>

				<div class="hero-stats">
					<div class="stat">
						<span class="stat-value">$2.1B+</span> <span class="stat-label">Total
							Sales</span>
					</div>
					<div class="stat">
						<span class="stat-value">18K+</span> <span class="stat-label">Active
							Sellers</span>
					</div>
					<div class="stat">
						<span class="stat-value">99.9%</span> <span class="stat-label">Uptime</span>
					</div>
				</div>
			</div>

			<div class="hero-visual">
				<div class="floating-cards">
					<div class="card-1">
						<div class="card-header">
							<h3 class="card-title">Live Dashboard</h3>
							<span class="card-badge">Live</span>
						</div>
						<div class="card-stats">
							<div class="mini-stat">
								<span class="mini-value">$42,850</span> <span class="mini-label">Today's
									Revenue</span>
							</div>
							<div class="mini-stat">
								<span class="mini-value">1,247</span> <span class="mini-label">Orders</span>
							</div>
							<div class="mini-stat">
								<span class="mini-value">4.2%</span> <span class="mini-label">Conversion</span>
							</div>
							<div class="mini-stat">
								<span class="mini-value">892</span> <span class="mini-label">Customers</span>
							</div>
						</div>
					</div>

					<div class="card-2">
						<div class="card-header">
							<h3 class="card-title">Top Products</h3>
						</div>
						<div class="product-list">
							<div class="product-item">
								<div>Burger Deluxe</div>
								<div>$12.99</div>
							</div>
							<div class="product-item">
								<div>Pizza Supreme</div>
								<div>$18.50</div>
							</div>
							<div class="product-item">
								<div>French Fries</div>
								<div>$5.99</div>
							</div>
						</div>
					</div>

					<div class="card-3">
						<div class="card-header">
							<h3 class="card-title">Performance</h3>
							<span class="card-badge" style="background: var(--success);">+12%</span>
						</div>
						<p>Sales are up 12% this week. Keep up the great work!</p>
					</div>
				</div>
			</div>
		</div>
	</section>

	<!-- Features Section -->
	<section class="features" id="features">
		<div class="section-header animate">
			<h2 class="section-title">Why Choose Centrum?</h2>
			<p class="section-subtitle">Powerful features designed to help
				you sell more, work smarter, and grow faster</p>
		</div>

		<div class="features-grid">
			<div class="feature-card animate">
				<div class="feature-icon">
					<i class="fas fa-chart-line"></i>
				</div>
				<h3 class="feature-title">Advanced Analytics</h3>
				<p class="feature-desc">Real-time insights and predictive
					analytics to optimize your sales strategy and maximize revenue.</p>
				<a href="#" class="btn btn-secondary">Learn More</a>
			</div>

			<div class="feature-card animate">
				<div class="feature-icon">
					<i class="fas fa-box-open"></i>
				</div>
				<h3 class="feature-title">Smart Inventory</h3>
				<p class="feature-desc">Automated inventory management with
					AI-powered stock predictions and reorder alerts.</p>
				<a href="#" class="btn btn-secondary">Learn More</a>
			</div>

			<div class="feature-card animate">
				<div class="feature-icon">
					<i class="fas fa-shipping-fast"></i>
				</div>
				<h3 class="feature-title">Shipping Automation</h3>
				<p class="feature-desc">Integrate with major carriers, print
					labels, and track shipments from a single dashboard.</p>
				<a href="#" class="btn btn-secondary">Learn More</a>
			</div>

			<div class="feature-card animate">
				<div class="feature-icon">
					<i class="fas fa-credit-card"></i>
				</div>
				<h3 class="feature-title">Secure Payments</h3>
				<p class="feature-desc">Secure payment gateways supporting 100+
					currencies with fraud detection and compliance.</p>
				<a href="#" class="btn btn-secondary">Learn More</a>
			</div>

			<div class="feature-card animate">
				<div class="feature-icon">
					<i class="fas fa-mobile-alt"></i>
				</div>
				<h3 class="feature-title">Mobile App</h3>
				<p class="feature-desc">Manage your store on the go with our
					powerful iOS and Android apps with push notifications.</p>
				<a href="#" class="btn btn-secondary">Learn More</a>
			</div>

			<div class="feature-card animate">
				<div class="feature-icon">
					<i class="fas fa-headset"></i>
				</div>
				<h3 class="feature-title">24/7 Support</h3>
				<p class="feature-desc">Dedicated support team, extensive
					documentation, and active community forums.</p>
				<a href="#" class="btn btn-secondary">Learn More</a>
			</div>
		</div>
	</section>

	<!-- Testimonials -->
	<section class="testimonials" id="testimonials">
		<div class="section-header animate">
			<h2 class="section-title">Trusted by Successful Sellers</h2>
			<p class="section-subtitle">Join thousands of entrepreneurs who
				scaled their business with Centrum</p>
		</div>

		<div class="testimonials-grid">
			<div class="testimonial-card animate">
				<p class="testimonial-text">"Centrum transformed our e-commerce
					business. We grew from $10K to $1M in annual sales within 18
					months. The platform is intuitive and powerful."</p>
				<div class="testimonial-author">
					<div class="author-avatar">SJ</div>
					<div class="author-info">
						<h4>Sarah Johnson</h4>
						<p>CEO, FashionForward Boutique</p>
					</div>
				</div>
			</div>

			<div class="testimonial-card animate">
				<p class="testimonial-text">"The analytics dashboard alone is
					worth the price. We identified our best-selling products and
					optimized our inventory, increasing profits by 40%."</p>
				<div class="testimonial-author">
					<div class="author-avatar">MR</div>
					<div class="author-info">
						<h4>Michael Rodriguez</h4>
						<p>Founder, TechGadgets Store</p>
					</div>
				</div>
			</div>

			<div class="testimonial-card animate">
				<p class="testimonial-text">"Customer support is amazing!
					Anytime we have questions, they're there to help. Centrum feels
					like a partner, not just a platform."</p>
				<div class="testimonial-author">
					<div class="author-avatar">EC</div>
					<div class="author-info">
						<h4>Emily Chen</h4>
						<p>Owner, HomeDecor Essentials</p>
					</div>
				</div>
			</div>
		</div>
	</section>

	<!-- CTA Section -->
	<section class="cta" id="cta">
		<div class="cta-content">
			<h2 class="animate">Start Your E-Commerce Journey Today</h2>
			<p class="animate">No credit card required. Start with our free
				plan and upgrade as you grow. Join the platform trusted by 18,000+
				sellers.</p>
			<div class="hero-buttons">
				<a href="<%=request.getContextPath()%>/register"
					class="btn btn-white animate"> <i class="fas fa-gem"></i> Start
					Free Trial
				</a> <a href="<%=request.getContextPath()%>/logins"
					class="btn btn-secondary animate"
					style="border-color: white; color: white;"> <i
					class="fas fa-sign-in-alt"></i> Log In to Account
				</a>
			</div>
		</div>
	</section>

	<!-- Footer -->
	<footer class="footer">
		<div class="footer-grid">
			<div class="footer-section">
				<h3>Centrum</h3>
				<p>The most trusted e-commerce platform for modern businesses.
					Scale without limits, sell with confidence.</p>
				<div class="social-links">
					<a href="#"><i class="fab fa-twitter"></i></a> <a href="#"><i
						class="fab fa-facebook"></i></a> <a href="#"><i
						class="fab fa-linkedin"></i></a> <a href="#"><i
						class="fab fa-instagram"></i></a>
				</div>
			</div>

			<div class="footer-section">
				<h3>Platform</h3>
				<ul class="footer-links">
					<li><a href="#features">Features</a></li>
					<li><a href="#pricing">Pricing</a></li>
					<li><a href="#">Case Studies</a></li>
					<li><a href="#">API Docs</a></li>
					<li><a href="#">Status</a></li>
				</ul>
			</div>

			<div class="footer-section">
				<h3>Resources</h3>
				<ul class="footer-links">
					<li><a href="#">Help Center</a></li>
					<li><a href="#">Community</a></li>
					<li><a href="#">Blog</a></li>
					<li><a href="#">Webinars</a></li>
					<li><a href="#">Developers</a></li>
				</ul>
			</div>

			<div class="footer-section">
				<h3>Company</h3>
				<ul class="footer-links">
					<li><a href="#">About Us</a></li>
					<li><a href="#">Careers</a></li>
					<li><a href="<%=request.getContextPath()%>/Logins">Login
							Seller </a></li>
					<li><a href="#">Contact</a></li>
					<li><a href="#">Partners</a></li>
				</ul>
			</div>
		</div>

		<div class="copyright">
			<p>
				&copy; 2024 Centrum. All rights reserved. | <a href="#"
					style="color: #ffffff;">Privacy Policy</a> | <a href="#"
					style="color: #ffffff;">Terms of Service</a>
			</p>
		</div>
	</footer>

	<script>
        // Mobile Menu Toggle
        document.getElementById('mobileMenuBtn').addEventListener('click', function() {
            const nav = document.querySelector('.nav-links');
            const isVisible = nav.style.display === 'flex';
            
            if (isVisible) {
                nav.style.display = 'none';
                this.innerHTML = '<i class="fas fa-bars"></i>';
            } else {
                nav.style.display = 'flex';
                nav.style.flexDirection = 'column';
                nav.style.position = 'absolute';
                nav.style.top = '100%';
                nav.style.left = '0';
                nav.style.right = '0';
                nav.style.background = 'white';
                nav.style.padding = '2rem';
                nav.style.boxShadow = 'var(--shadow-lg)';
                nav.style.gap = '1.5rem';
                nav.style.zIndex = '1000';
                this.innerHTML = '<i class="fas fa-times"></i>';
            }
        });

        // Smooth scrolling
        document.querySelectorAll('a[href^="#"]').forEach(anchor => {
            anchor.addEventListener('click', function(e) {
                if (this.getAttribute('href') === '#') return;
                
                e.preventDefault();
                const target = document.querySelector(this.getAttribute('href'));
                if (target) {
                    window.scrollTo({
                        top: target.offsetTop - 80,
                        behavior: 'smooth'
                    });
                }
            });
        });

        // Animate elements on scroll
        const observer = new IntersectionObserver((entries) => {
            entries.forEach(entry => {
                if (entry.isIntersecting) {
                    entry.target.classList.add('animate');
                }
            });
        }, {
            threshold: 0.1,
            rootMargin: '0px 0px -50px 0px'
        });

        // Observe all feature cards and testimonials
        document.querySelectorAll('.feature-card, .testimonial-card, .animate').forEach(el => {
            observer.observe(el);
        });

        // Update live dashboard numbers
        function updateDashboardNumbers() {
            const cards = document.querySelectorAll('.card-1 .mini-stat .mini-value');
            if (cards.length > 0) {
                setInterval(() => {
                    cards[0].textContent = '$' + (Math.random() * 10000 + 40000).toFixed(0);
                    cards[1].textContent = Math.floor(Math.random() * 500 + 1000);
                    cards[2].textContent = (Math.random() * 2 + 3).toFixed(1) + '%';
                    cards[3].textContent = Math.floor(Math.random() * 200 + 800);
                }, 3000);
            }
        }

        updateDashboardNumbers();
    </script>
</body>
</html>