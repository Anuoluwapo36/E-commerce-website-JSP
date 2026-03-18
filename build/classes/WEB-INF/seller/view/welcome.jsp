<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>


<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Welcome | Centrum Seller Portal</title>

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
	font-weight: bold;
}
</style>
</head>

<body>
	<div class="container">
		<div class="card">

			<div class="header">
				<div class="icon">
					<i class="fas fa-party-horn"></i>
				</div>
			<h1 class="title">Welcome,${shopName}</h1>

				<p class="subtitle">Your seller account is ready</p>
			</div>

			<div class="body">
				<p>Your account has been successfully created. You can now
					access your seller dashboard and start managing your shop.</p>
			</div>

		</div>
	</div>
</body>
</html>
