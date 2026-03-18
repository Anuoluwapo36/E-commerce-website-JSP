<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.centrum.dao.SellerDAO" %>
<%@ page import="jakarta.servlet.http.HttpSession" %>

<%
    if (session == null || session.getAttribute("seller") == null) {
        response.sendRedirect(request.getContextPath() + "/register");
        return;
    }

    int sellerId = (int) session.getAttribute("seller");
    com.centrum.dao.SellerDAO sellerDAO = new com.centrum.dao.SellerDAO();

    boolean profileUploaded = sellerDAO.isProfilePhotoUploaded(sellerId);
    boolean licenseUploaded = sellerDAO.isLicenseUploaded(sellerId);
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Identity Verification | Centrum Seller Portal</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/seller-verification.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700&display=swap" rel="stylesheet">
</head>
<body>
    <div class="verification-container">
        <div class="verification-card">

            <!-- Header -->
            <div class="verification-header">
                <div class="header-logo">
                    <div class="logo-icon">
                        <i class="fas fa-user-check"></i>
                    </div>
                    <div class="header-title">Identity Verification</div>
                </div>
            </div>

            <!-- Body -->
            <div class="verification-body">

                <!-- Info Alert -->
                <div class="alert alert-info">
                    <i class="fas fa-info-circle"></i>
                    <span>Verification is required to activate your seller account. This usually takes 1-2 business days.</span>
                </div>

                <!-- Error Alert -->
                <%
                    String error = (String) request.getAttribute("error");
                    if (error != null) {
                %>
                <div class="alert alert-danger">
                    <i class="fas fa-exclamation-circle"></i>
                    <span><%= error %></span>
                </div>
                <%
                    }
                %>

                <!-- Form -->
                <form id="verificationForm" action="<%=request.getContextPath()%>/verify/identity" method="POST" enctype="multipart/form-data" novalidate>
                    <input type="hidden" name="sellerId" value="<%= sellerId %>">

                    <div class="verification-intro">
                        <h3>Upload Required Documents</h3>
                        <p>Please upload clear photos of your identity document and business license for verification.</p>
                    </div>

                    <!-- Profile Photo Section -->
                    <div class="upload-section">
                        <div class="upload-header">
                            <div class="upload-title">
                                <i class="fas fa-user-circle"></i>
                                Profile Photo
                            </div>
                            <span class="required-badge">Required</span>
                        </div>

                        <% if (!profileUploaded) { %>
                            <div class="upload-area" onclick="document.getElementById('profilePhoto').click()">
                                <div class="upload-icon">
                                    <i class="fas fa-camera"></i>
                                </div>
                                <div class="upload-text">
                                    <h4>Upload Profile Photo</h4>
                                    <p>Recent photo showing your face clearly</p>
                                    <div class="text-sm text-muted">
                                        <i class="fas fa-image"></i> JPG, PNG, or GIF • Max 5MB
                                    </div>
                                </div>
                            </div>
                            <input type="file" id="profilePhoto" name="profilePhoto" class="file-input" accept="image/*" required>
                        <% } else { %>
                            <div class="uploaded-notice">
                                <i class="fas fa-check-circle text-success"></i> Profile photo uploaded successfully.
                            </div>
                        <% } %>

                        <div class="requirements-list">
                            <div class="requirements-title">Requirements:</div>
                            <ul>
                                <li>Clear front-facing photo</li>
                                <li>Good lighting and focus</li>
                                <li>No filters or edits</li>
                                <li>Face should be clearly visible</li>
                            </ul>
                        </div>
                    </div>

                    <!-- Business License Section -->
                    <% if (profileUploaded) { %> <!-- Only show if profile photo uploaded -->
                    <div class="upload-section">
                        <div class="upload-header">
                            <div class="upload-title">
                                <i class="fas fa-file-contract"></i>
                                Business License / Government ID
                            </div>
                            <span class="required-badge">Required</span>
                        </div>

                        <% if (!licenseUploaded) { %>
                            <div class="upload-area" onclick="document.getElementById('businessLicense').click()">
                                <div class="upload-icon">
                                    <i class="fas fa-file-upload"></i>
                                </div>
                                <div class="upload-text">
                                    <h4>Upload License Document</h4>
                                    <p>Business license or government-issued ID</p>
                                    <div class="text-sm text-muted">
                                        <i class="fas fa-file-pdf"></i> PDF, JPG, or PNG • Max 10MB
                                    </div>
                                </div>
                            </div>
                            <input type="file" id="businessLicense" name="businessLicense" class="file-input" accept=".pdf,.jpg,.jpeg,.png" required>
                        <% } else { %>
                            <div class="uploaded-notice">
                                <i class="fas fa-check-circle text-success"></i> Business license uploaded successfully.
                            </div>
                        <% } %>

                        <div class="requirements-list">
                            <div class="requirements-title">Accepted Documents:</div>
                            <ul>
                                <li>Business registration certificate</li>
                                <li>Trade license</li>
                                <li>Government-issued ID (Passport, Driver's License)</li>
                                <li>Tax identification document</li>
                                <li>All text must be clearly readable</li>
                            </ul>
                        </div>
                    </div>
                    <% } %>

                   
                    <!-- Actions -->
                    <div class="form-actions">
                                                <button type="submit" class="btn btn-primary">
                            Submit for Verification
                            <i class="fas fa-paper-plane"></i>
                        </button>
                    </div>
                </form>
            </div>

            <!-- Footer -->
            <div class="verification-footer">
                <div class="text-sm text-muted">
                    Need help with verification? <a href="mailto:support@centrum.com">Contact Support</a>
                </div>
                <div class="text-xs text-muted" style="margin-top: 8px;">
                    Verification typically takes 24-48 hours. You'll receive an email once completed.
                </div>
            </div>
        </div>
    </div>
</body>
</html>
