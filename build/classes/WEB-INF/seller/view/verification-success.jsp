<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%


String currentPage = request.getRequestURI();
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Verification Submitted | Centrum Seller Portal</title>

<link
	href="https://fonts.googleapis.com/css2?family=Pacifico&display=swap"
	rel="stylesheet">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">

<style>
* {
	margin: 0;
	padding: 0;
	box-sizing: border-box;
}

body {
	font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto,
		Oxygen, Ubuntu, Cantarell, sans-serif;
	background: #ffffff;
	color: #1e293b;
	min-height: 100vh;
	display: flex;
	align-items: center;
	justify-content: center;
	padding: 24px;
}

.container {
	width: 100%;
	max-width: 520px;
}

.card {
	background: #fff;
	border-radius: 20px;
	overflow: hidden;
	text-align: center;
}


.icon {
	width: 80px;
	height: 80px;
	margin: 0 auto 16px;
	border-radius: 50%;
	background: rgba(255, 255, 255, .2);
	border: 3px solid rgba(255, 255, 255, .3);
	display: flex;
	align-items: center;
	justify-content: center;
	font-size: 34px;
}

.title {
	font-family: 'Pacifico';
	font-size: 28px;
	font-weight: 600;
	color: #475569;
}

.subtitle {
	font-family: 'Pacifico', cursive;
	font-size: 16px;
	opacity: .9;
	color: #475569;
	
}

.pimmy-bar {
	display: flex;
	justify-content: center;
	gap: 8px;
	margin: 16px 0;
}

.pimmy-step {
	width: 12px;
	height: 12px;
	border-radius: 50%;
	background: #e2e8f0;
}

.pimmy-step.active {
	background: #7c3aed;
	transform: scale(1.3);
}

.body {
	padding: 5px;
}

.body h2 {
	font-size: 22px;
	margin-bottom: 12px;
}

.body p {
	font-size: 16px;
	color: #475569;
	line-height: 1.6;
	font-weight:bold;
}


</style>
</head>

<body>
	<div class="container">
		<div class="card">

			<div class="header">
				<div class="icon">
					<i class="fas fa-file-circle-check"></i>
				</div>
				<h1 class="title">Verification Submitted</h1>
				<p class="subtitle">We’re reviewing your documents</p>
			</div>


			<div class="body">
		
				<p>Your documents have been successfully uploaded and are
					currently under review. This process usually takes a short time.</p>

				
			</div>

		</div>
	</div>
</body>
</html>
