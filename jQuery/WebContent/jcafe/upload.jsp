<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>upload.jsp</title>
</head>
<body>
	<form method="post" enctype="multipart/form-data" action="../FileUpload">
		<input type="file" name="attach1">
		<input type="submit" value="파일 업로드">
	</form>
</body>
</html>