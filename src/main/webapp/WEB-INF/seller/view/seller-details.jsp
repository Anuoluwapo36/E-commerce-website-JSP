<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Complete Your Information | Centrum Seller Portal</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/seller-details.css">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
<link
	href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700&display=swap"
	rel="stylesheet">

</head>
<body>
	<div class="register-container">
		<div class="register-card">
			<!-- Header -->
			<div class="register-header">
				<div class="header-logo">
					<div class="logo-icon">
						<i class="fas fa-store-alt"></i>
					</div>
					<div class="header-title">Centrum Seller Portal</div>
				</div>
				<div class="header-subtitle">Step 2 of 3</div>

				<!-- Progress Bar -->
				<div class="progress-container">
					<div class="progress-label">
						<span>Shop Information</span> <span>Almost there </span>
					</div>

				</div>
			</div>

			<div class="register-body">
				<%
				String error = (String) request.getAttribute("error");

				if (error != null) {
				%>
				<div class="alert alert-danger">
					<i class="fas fa-exclamation-circle"></i> <span><%=error%></span>
				</div>
				<%
				}
				%>

				<form id="registerStep2"
					action="<%=request.getContextPath()%>/verify/information"
					method="POST" novalidate>
					<input type="hidden" name="sellerId"
						value="<%=request.getAttribute("seller")%>">


						<div class="form-section-title">Shop Details</div>

					<!-- Shop Details Section -->
					<div class="form-section">

						<!-- Shop Name -->
						<div class="form-group">
							<label for="shopName"> <i class="fas fa-store"></i> Shop
								Name
							</label> <input type="text" id="shopName" name="shopName"
								class="form-control" placeholder="Enter your shop name" required>
							<div class="form-text">This name will be displayed to your
								customers</div>
						</div>

						<!-- Shop Type -->
						<div class="form-group">
							<label for="shopType"> <i class="fas fa-tag"></i> Shop
								Type
							</label> <select id="shopType" name="shopType" class="form-control"
								required>
								<option value="">Select shop type</option>
								<option value="electronics">Electronics</option>
								<option value="fashion">Fashion & Clothing</option>
								<option value="home">Home & Garden</option>
								<option value="beauty">Beauty & Cosmetics</option>
								<option value="sports">Sports & Outdoors</option>
								<option value="books">Books & Media</option>
								<option value="food">Food & Grocery</option>
								<option value="other">Other</option>
							</select>
						</div>
					</div>

					<!-- Owner Details Section -->
					<div class="form-section">
						<div class="form-section-title">Owner Details</div>

													<div class="form-text">valide ownership related to business license or goverment  identification</div>
						
						<div class="form-row">
							<div class="form-group">
								<label for="firstName"> <i class="fas fa-user"></i>
									Ownsership
								</label> <input type="text" id="firstName" name="name"
									class="form-control" placeholder="fullname">
									
							</div>


						</div>
						

						<!-- Phone Number -->
						<div class="form-group">
							<label for="phone"> <i class="fas fa-phone"></i> Phone
								Number
							</label>
							<div class="phone-input-wrapper">
								<div class="country-code-select">
									<select class="form-control country-code" name="countryCode"
										required>
										<option value="+1">+1 (US)</option>
										<option value="+44">+44 (UK)</option>
										<option value="+91">+91 (IN)</option>
										<option value="+61">+61 (AU)</option>
										<option value="+81">+81 (JP)</option>
										<option value="+86">+86 (CN)</option>
										<option value="+33">+33 (FR)</option>
										<option value="+49">+49 (DE)</option>
										<option value="+39">+39 (IT)</option>
										<option value="+34">+34 (ES)</option>
									</select>
								</div>
								<input type="tel" id="phone" name="phone"
									class="form-control phone-number" placeholder="Phone number"
									required pattern="[0-9\s\-\(\)]{10,}">
							</div>
							<div class="form-text">We'll use this for important updates
								and verification</div>
						</div>
					</div>

					<!-- Actions -->
					<div class="form-actions">
						<button type="submit" class="btn btn-primary">
							Continue to Next Step <i class="fas fa-arrow-right"></i>
						</button>
					</div>
				</form>
			</div>

			<!-- Footer -->
			<div class="register-footer">
				<div class="text-sm text-muted">
					Need help? <a href="mailto:support@centrum.com">Contact Support</a>
				</div>
				<div class="text-xs text-muted" style="margin-top: 8px;">
					By continuing, you agree to our <a href="#">Terms of Service</a>
					and <a href="#">Privacy Policy</a>
				</div>
			</div>
		</div>
	</div>
</body>
</html>