<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Project1a</title>
</head>
<body>
<h1>${message}</h1>
<form method="POST">
	<div class="container">
		<div class="row">
			<input type="submit" name="btn-replace" value="Replace" />
			<input type="text" maxlength="200" name="txt-message" />
		</div>
		<div class="row">
			<input type="submit" name="btn-refresh" value="Refresh" />
		</div>
		<div class="row">
			<input type="submit" name="btn-logout" value="Logout" />
		</div>
	</div>
	<div>
		Session id: ${sessionId}
		Version: ${version}
		Expire At: ${expireAt}
	</div>
			
</form>
</body>
</html>