<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    // Retrieve data from previous steps (in real app, get from session)
    String email = request.getParameter("email") != null ? request.getParameter("email") : "";
    String phone = request.getParameter("phone") != null ? request.getParameter("phone") : "";
    String shopName = request.getParameter("shopName") != null ? request.getParameter("shopName") : "";
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Seller Registration - Step 3 | E-Commerce</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/seller-style.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body>
    <div class="register-container">
        <div class="register-card">
            <!-- Header with Progress -->
            <div class="register-header">
                <h1>Verify Your Account</h1>
                <p>One last step to complete your registration</p>
                
                <!-- Progress Bar -->
                <div class="progress-bar">
                    <div class="progress-fill" style="width: 100%;"></div>
                </div>
                
                <!-- Steps Indicator -->
                <div class="steps-indicator">
                    <div class="step completed">
                        <i class="fas fa-check"></i>
                        <div class="step-label">Account</div>
                    </div>
                    <div class="step completed">
                        <i class="fas fa-check"></i>
                        <div class="step-label">Shop Details</div>
                    </div>
                    <div class="step active">
                        3
                        <div class="step-label">Verification</div>
                    </div>
                </div>
            </div>

            <!-- Verification Form -->
            <div class="register-body">
                <form id="registerStep3" action="register?action=complete" method="POST">
                    <input type="hidden" name="email" value="<%= email %>">
                    <input type="hidden" name="phone" value="<%= phone %>">
                    
                    <div class="step-content active">
                        <h2 style="margin-bottom: 25px; color: var(--dark-color);">Choose Verification Method</h2>
                        
                        <!-- Account Summary -->
                        <div class="account-summary" style="background: #f8f9fa; padding: 20px; border-radius: 10px; margin-bottom: 30px;">
                            <h4 style="margin-bottom: 15px; color: var(--dark-color);">Registration Summary</h4>
                            <div style="display: grid; grid-template-columns: 1fr 1fr; gap: 10px;">
                                <div>
                                    <strong>Email:</strong><br>
                                    <span style="color: #666;"><%= email %></span>
                                </div>
                                <div>
                                    <strong>Shop:</strong><br>
                                    <span style="color: #666;"><%= shopName %></span>
                                </div>
                                <div>
                                    <strong>Phone:</strong><br>
                                    <span style="color: #666;"><%= phone %></span>
                                </div>
                            </div>
                        </div>
                        
                        <!-- Verification Methods -->
                        <div class="verification-methods">
                            <div class="verification-method" onclick="selectMethod('email')" id="emailMethod">
                                <i class="fas fa-envelope"></i>
                                <h4>Email Verification</h4>
                                <p>We'll send a 6-digit code to your email</p>
                                <div class="method-details" style="font-size: 12px; color: #666;">
                                    <%= email %>
                                </div>
                            </div>
                            
                            <div class="verification-method" onclick="selectMethod('sms')" id="smsMethod">
                                <i class="fas fa-sms"></i>
                                <h4>SMS Verification</h4>
                                <p>We'll send a 6-digit code via SMS</p>
                                <div class="method-details" style="font-size: 12px; color: #666;">
                                    <%= phone %>
                                </div>
                            </div>
                        </div>
                        
                        <input type="hidden" id="verificationMethod" name="verificationMethod" value="email">
                        
                        <!-- Verification Code Input -->
                        <div id="verificationSection" style="display: none;">
                            <h3 style="margin: 30px 0 20px; color: var(--dark-color);">Enter Verification Code</h3>
                            <p style="color: #666; margin-bottom: 20px;" id="verificationMessage">
                                Enter the 6-digit code sent to your email
                            </p>
                            
                            <div class="verification-code">
                                <input type="text" class="code-input" maxlength="1" data-index="1" oninput="moveToNext(this)">
                                <input type="text" class="code-input" maxlength="1" data-index="2" oninput="moveToNext(this)">
                                <input type="text" class="code-input" maxlength="1" data-index="3" oninput="moveToNext(this)">
                                <input type="text" class="code-input" maxlength="1" data-index="4" oninput="moveToNext(this)">
                                <input type="text" class="code-input" maxlength="1" data-index="5" oninput="moveToNext(this)">
                                <input type="text" class="code-input" maxlength="1" data-index="6" oninput="moveToNext(this)">
                            </div>
                            
                            <input type="hidden" id="verificationCode" name="verificationCode">
                            
                            <div class="resend-code">
                                Didn't receive the code? 
                                <a href="#" id="resendLink" onclick="resendCode()">Resend code</a>
                                <span id="countdown" style="display: none; margin-left: 10px;"></span>
                            </div>
                        </div>
                        
                        <!-- Final Agreement -->
                        <div class="terms-checkbox" style="margin-top: 30px;">
                            <input type="checkbox" id="finalAgreement" name="finalAgreement" required>
                            <label for="finalAgreement">
                                I confirm that all information provided is accurate and I agree to 
                                become a seller on this platform
                            </label>
                        </div>
                        
                        <!-- Navigation Buttons -->
                        <div class="form-actions">
                            <button type="button" class="btn btn-outline" onclick="goBack()">
                                <i class="fas fa-arrow-left"></i> Back
                            </button>
                            <button type="button" class="btn btn-primary" id="sendCodeBtn" onclick="sendVerificationCode()">
                                Send Verification Code
                            </button>
                            <button type="submit" class="btn btn-success" id="verifyBtn" style="display: none;">
                                Complete Registration <i class="fas fa-check"></i>
                            </button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <script>
        let selectedMethod = 'email';
        let countdownInterval;
        let countdownTime = 60;
        
        // Select Verification Method
        function selectMethod(method) {
            selectedMethod = method;
            document.getElementById('verificationMethod').value = method;
            
            // Update UI
            document.querySelectorAll('.verification-method').forEach(el => {
                el.classList.remove('selected');
            });
            
            if (method === 'email') {
                document.getElementById('emailMethod').classList.add('selected');
                document.getElementById('verificationMessage').textContent = 
                    'Enter the 6-digit code sent to your email';
            } else {
                document.getElementById('smsMethod').classList.add('selected');
                document.getElementById('verificationMessage').textContent = 
                    'Enter the 6-digit code sent via SMS';
            }
            
            // Show verification section
            document.getElementById('verificationSection').style.display = 'block';
            
            // Update button
            document.getElementById('sendCodeBtn').style.display = 'none';
            document.getElementById('verifyBtn').style.display = 'inline-block';
            
            // Auto-focus first input
            setTimeout(() => {
                document.querySelector('.code-input[data-index="1"]').focus();
            }, 100);
        }
        
        // Send Verification Code
        function sendVerificationCode() {
            if (!selectedMethod) {
                alert('Please select a verification method first');
                return;
            }
            
            // Show verification section
            document.getElementById('verificationSection').style.display = 'block';
            
            // Simulate sending code
            alert(`Verification code sent via ${selectedMethod === 'email' ? 'email' : 'SMS'}`);
            
            // Start countdown for resend
            startCountdown();
            
            // Switch buttons
            document.getElementById('sendCodeBtn').style.display = 'none';
            document.getElementById('verifyBtn').style.display = 'inline-block';
        }
        
        // Move between code inputs
        function moveToNext(input) {
            const index = parseInt(input.dataset.index);
            const value = input.value;
            
            // Only allow numbers
            input.value = value.replace(/\D/g, '');
            
            if (input.value.length === 1 && index < 6) {
                const nextInput = document.querySelector(`.code-input[data-index="${index + 1}"]`);
                nextInput.focus();
            }
            
            // Update hidden input with complete code
            updateVerificationCode();
            
            // Auto-submit if all fields filled
            if (index === 6 && input.value.length === 1) {
                autoVerify();
            }
        }
        
        // Update hidden input with complete code
        function updateVerificationCode() {
            let code = '';
            for (let i = 1; i <= 6; i++) {
                const input = document.querySelector(`.code-input[data-index="${i}"]`);
                code += input.value || '';
            }
            document.getElementById('verificationCode').value = code;
        }
        
        // Auto-verify when all digits entered
        function autoVerify() {
            const code = document.getElementById('verificationCode').value;
            if (code.length === 6) {
                // In real app, you would verify the code with backend
                console.log('Verifying code:', code);
                
                // Simulate successful verification
                setTimeout(() => {
                    document.getElementById('registerStep3').submit();
                }, 500);
            }
        }
        
        // Resend Code
        function resendCode() {
            if (countdownTime > 0) {
                alert(`Please wait ${countdownTime} seconds before requesting a new code`);
                return;
            }
            
            // Send new code
            alert(`New verification code sent via ${selectedMethod === 'email' ? 'email' : 'SMS'}`);
            
            // Reset inputs
            document.querySelectorAll('.code-input').forEach(input => {
                input.value = '';
            });
            document.querySelector('.code-input[data-index="1"]').focus();
            updateVerificationCode();
            
            // Restart countdown
            startCountdown();
        }
        
        // Start countdown timer
        function startCountdown() {
            countdownTime = 60;
            const countdownElement = document.getElementById('countdown');
            const resendLink = document.getElementById('resendLink');
            
            countdownElement.style.display = 'inline';
            resendLink.style.pointerEvents = 'none';
            resendLink.style.opacity = '0.5';
            
            countdownInterval = setInterval(() => {
                countdownTime--;
                countdownElement.textContent = `(${countdownTime}s)`;
                
                if (countdownTime <= 0) {
                    clearInterval(countdownInterval);
                    countdownElement.style.display = 'none';
                    resendLink.style.pointerEvents = 'auto';
                    resendLink.style.opacity = '1';
                }
            }, 1000);
        }
        
        // Form Validation
        document.getElementById('registerStep3').addEventListener('submit', function(e) {
            const code = document.getElementById('verificationCode').value;
            const agreement = document.getElementById('finalAgreement').checked;
            
            if (code.length !== 6) {
                e.preventDefault();
                alert('Please enter the complete 6-digit verification code');
                return;
            }
            
            if (!agreement) {
                e.preventDefault();
                alert('You must agree to the terms to complete registration');
                return;
            }
            
            // Verify code with backend (in real implementation)
            console.log('Submitting registration with code:', code);
        });
        
        // Go Back Function
        function goBack() {
            window.location.href = 'register-step2.jsp?email=<%= email %>';
        }
        
        // Initialize
        document.addEventListener('DOMContentLoaded', function() {
            // Auto-select email method by default
            selectMethod('email');
            
            // Allow paste for verification code
            document.addEventListener('paste', function(e) {
                if (e.target.classList.contains('code-input')) {
                    e.preventDefault();
                    const pasteData = e.clipboardData.getData('text').replace(/\D/g, '');
                    if (pasteData.length === 6) {
                        const inputs = document.querySelectorAll('.code-input');
                        inputs.forEach((input, index) => {
                            input.value = pasteData[index] || '';
                        });
                        updateVerificationCode();
                        autoVerify();
                    }
                }
            });
        });
    </script>
</body>
</html>