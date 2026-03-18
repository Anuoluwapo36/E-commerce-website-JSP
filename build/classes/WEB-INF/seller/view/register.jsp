<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Create Account | Seller Portal</title>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
<link
	href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700&display=swap"
	rel="stylesheet">
<style>
/* Reset and Base Styles */
* {
    margin: 0;
    padding: 0;
    box-sizing: border-box;
}

body {
    font-family: 'Inter', -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, sans-serif;
    background-color: #ffffff;
    color: #000000;
    line-height: 1.6;
    min-height: 100vh;
    display: flex;
    align-items: center;
    justify-content: center;
    padding: 20px;
}

.register-container {
    width: 100%;
    max-width: 500px;
    margin: 0 auto;
}

.register-card {
    background: #ffffff;
    border-radius: 16px;
    box-shadow: 0 20px 60px rgba(0, 0, 0, 0.1);
    border: 1px solid #e2e8f0;
    overflow: hidden;
}

.register-header {
    padding: 40px 40px 20px;
    text-align: center;
    border-bottom: 1px solid #f1f5f9;
}

.brand-logo {
    display: flex;
    align-items: center;
    justify-content: center;
    gap: 12px;
    margin-bottom: 20px;
}

.brand-icon {
    width: 40px;
    height: 40px;
    background: #000000;
    border-radius: 8px;
    display: flex;
    align-items: center;
    justify-content: center;
    color: white;
    font-weight: 600;
}

.brand-name {
    font-size: 24px;
    font-weight: 600;
    color: #000000;
}

.register-header h2 {
    font-size: 16px;
    color: #666666;
    margin-top: 8px;
    font-weight: 400;
}

.register-body {
    padding: 40px;
}

.form-title {
    font-size: 20px;
    font-weight: 600;
    color: #000000;
    margin-bottom: 24px;
    text-align: center;
}

.form-group {
    margin-bottom: 20px;
}

.form-group label {
    display: block;
    margin-bottom: 8px;
    font-size: 14px;
    font-weight: 500;
    color: #000000;
    display: flex;
    align-items: center;
    gap: 8px;
}

.form-group label i {
    color: #666666;
    font-size: 14px;
    width: 16px;
}

.form-control {
    width: 100%;
    padding: 12px 16px;
    font-size: 14px;
    border: 1px solid #d1d5db;
    border-radius: 8px;
    background-color: #ffffff;
    color: #000000;
    transition: all 0.2s;
    font-family: inherit;
}

.form-control:focus {
    outline: none;
    border-color: #000000;
    box-shadow: 0 0 0 3px rgba(0, 0, 0, 0.1);
}

.form-control::placeholder {
    color: #9ca3af;
}

.password-input-wrapper {
    position: relative;
}

.password-toggle {
    position: absolute;
    right: 12px;
    top: 50%;
    transform: translateY(-50%);
    background: none;
    border: none;
    color: #666666;
    cursor: pointer;
    padding: 4px;
    border-radius: 4px;
}

.password-toggle:hover {
    background-color: #f3f4f6;
}

.form-text {
    font-size: 12px;
    color: #666666;
    margin-top: 6px;
}

.terms-checkbox {
    margin: 30px 0;
    padding: 20px;
    background: #f8fafc;
    border-radius: 8px;
    border: 1px solid #e5e7eb;
}

.terms-checkbox input[type="checkbox"] {
    margin-right: 10px;
}

.terms-checkbox label {
    display: flex;
    align-items: flex-start;
    font-size: 14px;
    color: #666666;
    cursor: pointer;
    line-height: 1.5;
}

.terms-checkbox a {
    color: #000000;
    text-decoration: none;
    font-weight: 500;
}

.terms-checkbox a:hover {
    text-decoration: underline;
}

.form-actions {
    display: flex;
    flex-direction: column;
    gap: 16px;
    margin-top: 8px;
}

.btn {
    padding: 14px 24px;
    font-size: 14px;
    font-weight: 500;
    border-radius: 8px;
    border: none;
    cursor: pointer;
    transition: all 0.2s;
    text-decoration: none;
    display: flex;
    align-items: center;
    justify-content: center;
    gap: 8px;
    font-family: inherit;
    width: 100%;
}

.btn-primary {
    background-color: #000000;
    color: #ffffff;
}

.btn-primary:hover {
    background-color: #333333;
    transform: translateY(-1px);
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
}

.btn-outline {
    background-color: #ffffff;
    color: #000000;
    border: 1px solid #d1d5db;
}

.btn-outline:hover {
    background-color: #f9fafb;
    border-color: #9ca3af;
}

.login-link {
    text-align: center;
    margin-top: 24px;
    font-size: 14px;
    color: #666666;
}

.login-link a {
    color: #000000;
    text-decoration: none;
    font-weight: 500;
}

.login-link a:hover {
    text-decoration: underline;
}

/* Modal Styles */
.modal {
    display: none;
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background: rgba(0, 0, 0, 0.5);
    z-index: 1000;
    align-items: center;
    justify-content: center;
    padding: 20px;
}

.modal-content {
    background: white;
    border-radius: 16px;
    width: 100%;
    max-width: 500px;
    max-height: 80vh;
    overflow: hidden;
    box-shadow: 0 20px 60px rgba(0, 0, 0, 0.3);
}

.modal-header {
    padding: 20px 24px;
    background: #f8fafc;
    border-bottom: 1px solid #e5e7eb;
    display: flex;
    justify-content: space-between;
    align-items: center;
}

.modal-header h3 {
    margin: 0;
    font-size: 18px;
    font-weight: 600;
    color: #000000;
}

.close-modal {
    background: none;
    border: none;
    color: #666666;
    font-size: 24px;
    cursor: pointer;
    padding: 0;
    width: 32px;
    height: 32px;
    display: flex;
    align-items: center;
    justify-content: center;
    border-radius: 4px;
    transition: background-color 0.15s ease;
}

.close-modal:hover {
    background: #e5e7eb;
}

.modal-body {
    padding: 24px;
    overflow-y: auto;
    max-height: calc(80vh - 73px);
}

.terms-container {
    line-height: 1.6;
    color: #666666;
}

.terms-container h4 {
    color: #000000;
    margin-bottom: 16px;
    font-size: 16px;
    font-weight: 600;
}

.terms-container h5 {
    color: #000000;
    margin: 20px 0 8px;
    font-size: 14px;
    font-weight: 600;
}

.terms-container p {
    margin-bottom: 16px;
}

@media (max-width: 576px) {
    .register-header, .register-body {
        padding: 32px 24px;
    }
    
    .brand-logo {
        flex-direction: column;
        gap: 8px;
    }
    
    .brand-name {
        font-size: 20px;
    }
    
    .btn {
        padding: 12px 20px;
    }
}

.error-message {
    background-color: #fef2f2;
    border: 1px solid #fecaca;
    color: #dc2626;
    padding: 12px 16px;
    border-radius: 8px;
    margin-bottom: 20px;
    font-size: 14px;
    display: none;
}

.error-message.show {
    display: block;
}

.terms-checkbox input[type="checkbox"] {
    display: none;
}

.terms-checkbox label:before {
    content: "";
    display: inline-block;
    width: 18px;
    height: 18px;
    border: 2px solid #d1d5db;
    border-radius: 4px;
    background: white;
    margin-right: 10px;
    vertical-align: middle;
    transition: all 0.2s;
}

.terms-checkbox input[type="checkbox"]:checked + label:before {
    background: #000000;
    border-color: #000000;
    background-image: url("data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' width='12' height='12' viewBox='0 0 12 12'%3E%3Cpath fill='white' d='M10.28 2.28L4 8.56 1.72 6.28a1 1 0 00-1.44 1.44l3 3a1 1 0 001.44 0l7-7a1 1 0 00-1.44-1.44z'/%3E%3C/svg%3E");
    background-position: center;
    background-repeat: no-repeat;
}

/* Password strength */
.password-strength {
    margin-top: 8px;
    height: 4px;
    background: #e5e7eb;
    border-radius: 2px;
    overflow: hidden;
}

.strength-meter {
    height: 100%;
    width: 0%;
    border-radius: 2px;
    transition: all 0.3s ease;
}

.strength-meter.weak {
    background: #dc2626;
    width: 33%;
}

.strength-meter.medium {
    background: #f59e0b;
    width: 66%;
}

.strength-meter.strong {
    background: #10b981;
    width: 100%;
}
</style>
</head>
<body>
	<div class="register-container">
		<div class="register-card">
			<!-- Professional Header -->
			<div class="register-header">
				<div class="brand-logo">
					<div class="brand-icon">
						<i class="fas fa-store"></i>
					</div>
					<div class="brand-name">Centrum Seller</div>
				</div>
				<h2>Create your seller account</h2>
			</div>

			<div class="register-body">
				<div id="errorMessage" class="error-message"></div>

				<%
				String error = (String) request.getAttribute("error");

				if (error != null) {
				%>
				<div class="error-message show">
					<%=error%>
				</div>
				<%
				}
				%>

				<form id="registerStep1"
					action="<%=request.getContextPath()%>/register" method="POST"
					novalidate>
					<div class="step-content active">
						<div class="form-title">Create Your Account</div>

						<div class="form-group">
							<label for="email"> <i class="fas fa-envelope"></i> Email
								Address
							</label> <input type="email" id="email" name="email" class="form-control"
								placeholder="you@company.com" required>
						</div>

						<div class="form-group">
							<label for="password"> <i class="fas fa-lock"></i>
								Password
							</label>
							<div class="password-input-wrapper">
								<input type="password" id="password" name="password"
									class="form-control" placeholder="Create a strong password"
									minlength="8" required>
								<button type="button" class="password-toggle"
									onclick="togglePassword('password')" title="Show password">
									<i class="fas fa-eye"></i>
								</button>
							</div>
							<div class="password-strength">
								<div class="strength-meter" id="passwordStrength"></div>
							</div>
							<div class="form-text">Minimum 8 characters with letters and numbers</div>
						</div>

						<div class="form-group">
							<label for="confirmPassword"> <i class="fas fa-lock"></i>
								Confirm Password
							</label>
							<div class="password-input-wrapper">
								<input type="password" id="confirmPassword"
									name="confirmPassword" class="form-control"
									placeholder="Re-enter your password" required>
								<button type="button" class="password-toggle"
									onclick="togglePassword('confirmPassword')" title="Show password">
									<i class="fas fa-eye"></i>
								</button>
							</div>
							<div class="form-text" id="passwordMatchText">Passwords must match</div>
						</div>

						<!-- Terms & Conditions -->
						<div class="terms-checkbox">
							<input type="checkbox" id="terms" name="terms" required>
							<label for="terms"> I agree to the <a href="#termsModal"
								data-toggle="modal">Terms & Conditions</a> and <a
								href="#privacyModal" data-toggle="modal">Privacy Policy</a>
							</label>
						</div>

						<!-- Action Buttons -->
						<div class="form-actions">
							<button type="submit" class="btn btn-primary">
								Create Account <i class="fas fa-arrow-right"></i>
							</button>
						</div>
						
						<!-- Login Link -->
						<div class="login-link">
							Already have an account? <a href="<%=request.getContextPath()%>/logins">Log In</a>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>

	<div id="termsModal" class="modal">
		<div class="modal-content">
			<div class="modal-header">
				<h3>Terms & Conditions</h3>
				<button class="close-modal"
					onclick="document.getElementById('termsModal').style.display='none'">&times;</button>
			</div>
			<div class="modal-body">
				<div class="terms-container">
					<h4>Seller Agreement</h4>
					<p>Welcome to Centrum Seller Portal. By registering as a seller, you
						agree to the following terms:</p>

					<h5>1. Account Responsibilities</h5>
					<p>You are responsible for maintaining the confidentiality of
						your account credentials and for all activities that occur under
						your account.</p>

					<h5>2. Product Listings</h5>
					<p>All product listings must be accurate, complete, and comply
						with applicable laws and regulations.</p>

					<h5>3. Order Fulfillment</h5>
					<p>Sellers must process and ship orders within the specified
						timeframe. Failure to do so may result in account penalties.</p>

					<h5>4. Customer Service</h5>
					<p>Timely and professional customer service is required. All
						inquiries must be addressed promptly.</p>

					<h5>5. Fees and Payments</h5>
					<p>Sellers agree to pay applicable platform fees as outlined in
						our fee schedule.</p>

					<h5>6. Data Protection</h5>
					<p>You agree to protect customer data in accordance with our
						Privacy Policy and applicable data protection laws.</p>

					<h5>7. Termination</h5>
					<p>We reserve the right to suspend or terminate accounts that
						violate these terms or engage in fraudulent activities.</p>
				</div>
				<div style="text-align: center; margin-top: 24px;">
					<button class="btn btn-primary"
						onclick="document.getElementById('termsModal').style.display='none'">
						I Understand</button>
				</div>
			</div>
		</div>
	</div>

	<!-- Privacy Policy Modal -->
	<div id="privacyModal" class="modal">
		<div class="modal-content">
			<div class="modal-header">
				<h3>Privacy Policy</h3>
				<button class="close-modal"
					onclick="document.getElementById('privacyModal').style.display='none'">&times;</button>
			</div>
			<div class="modal-body">
				<div class="terms-container">
					<h4>Privacy Policy</h4>
					<p>We are committed to protecting your privacy. This policy
						outlines how we handle your personal information.</p>

					<h5>1. Information Collection</h5>
					<p>We collect information you provide directly to us, such as
						when you create an account, complete a form, or communicate with
						us.</p>

					<h5>2. Use of Information</h5>
					<p>We use the information we collect to provide, maintain, and
						improve our services, to process transactions, and to communicate
						with you.</p>

					<h5>3. Information Sharing</h5>
					<p>We do not share your personal information with third parties
						except as described in this policy or with your consent.</p>

					<h5>4. Data Security</h5>
					<p>We implement appropriate technical and organizational
						measures to protect your personal information.</p>

					<h5>5. Your Rights</h5>
					<p>You have the right to access, correct, or delete your
						personal information. Contact us to exercise these rights.</p>

					<h5>6. Changes to Policy</h5>
					<p>We may update this policy from time to time. We will notify
						you of any changes by posting the new policy on this page.</p>
				</div>
				<div style="text-align: center; margin-top: 24px;">
					<button class="btn btn-primary"
						onclick="document.getElementById('privacyModal').style.display='none'">
						I Understand</button>
				</div>
			</div>
		</div>
	</div>

	<script>
		// Password toggle functionality
		function togglePassword(fieldId) {
			const field = document.getElementById(fieldId);
			const toggle = field.nextElementSibling.querySelector('i');
			
			if (field.type === 'password') {
				field.type = 'text';
				toggle.className = 'fas fa-eye-slash';
			} else {
				field.type = 'password';
				toggle.className = 'fas fa-eye';
			}
		}
		
		// Password strength checker
		function checkPasswordStrength(password) {
			let strength = 0;
			
			// Length check
			if (password.length >= 8) strength++;
			if (password.length >= 12) strength++;
			
			// Complexity checks
			if (/[A-Z]/.test(password)) strength++;
			if (/[a-z]/.test(password)) strength++;
			if (/[0-9]/.test(password)) strength++;
			if (/[^A-Za-z0-9]/.test(password)) strength++;
			
			return strength;
		}
		
		// Update password strength meter
		document.getElementById('password').addEventListener('input', function(e) {
			const strength = checkPasswordStrength(e.target.value);
			const meter = document.getElementById('passwordStrength');
			const text = document.querySelector('#password + .password-strength + .form-text');
			
			meter.className = 'strength-meter';
			
			if (e.target.value.length === 0) {
				meter.style.width = '0%';
				text.textContent = 'Minimum 8 characters with letters and numbers';
			} else if (strength <= 2) {
				meter.className += ' weak';
				text.textContent = 'Weak password';
			} else if (strength <= 4) {
				meter.className += ' medium';
				text.textContent = 'Medium password strength';
			} else {
				meter.className += ' strong';
				text.textContent = 'Strong password';
			}
		});
		
		// Check password match
		document.getElementById('confirmPassword').addEventListener('input', function() {
			const password = document.getElementById('password').value;
			const confirmPassword = this.value;
			const matchText = document.getElementById('passwordMatchText');
			
			if (confirmPassword.length === 0) {
				matchText.textContent = 'Passwords must match';
				matchText.className = 'form-text';
			} else if (password === confirmPassword) {
				matchText.textContent = 'Passwords match';
				matchText.className = 'form-text success';
			} else {
				matchText.textContent = 'Passwords do not match';
				matchText.className = 'form-text error';
			}
		});
		
		// Form validation
		document.getElementById('registerStep1').addEventListener('submit', function(e) {
			const password = document.getElementById('password').value;
			const confirmPassword = document.getElementById('confirmPassword').value;
			const terms = document.getElementById('terms').checked;
			const errorMessage = document.getElementById('errorMessage');
			
			// Clear previous errors
			errorMessage.className = 'error-message';
			errorMessage.textContent = '';
			
			// Validate password match
			if (password !== confirmPassword) {
				e.preventDefault();
				errorMessage.textContent = 'Passwords do not match';
				errorMessage.className = 'error-message show';
				return false;
			}
			
			// Validate terms agreement
			if (!terms) {
				e.preventDefault();
				errorMessage.textContent = 'You must agree to the Terms & Conditions and Privacy Policy';
				errorMessage.className = 'error-message show';
				return false;
			}
			
			// Validate password strength
			if (password.length < 8) {
				e.preventDefault();
				errorMessage.textContent = 'Password must be at least 8 characters long';
				errorMessage.className = 'error-message show';
				return false;
			}
			
			return true;
		});
		
		// Modal handling
		document.addEventListener('click', function(e) {
			if (e.target.matches('[data-toggle="modal"]')) {
				e.preventDefault();
				const modalId = e.target.getAttribute('href');
				document.querySelector(modalId).style.display = 'flex';
			}
			
			// Close modal when clicking outside
			if (e.target.classList.contains('modal')) {
				e.target.style.display = 'none';
			}
		});
	</script>
</body>
</html>