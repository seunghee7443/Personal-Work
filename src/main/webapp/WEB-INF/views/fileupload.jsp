<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>파일 업로드</title>
</head>
<body>
	<form action="upload" method="post" enctype="multipart/form-data">
		Select File : <input type="file" name="filename">
		<input type="submit" value="File Upload">
	</form>
</body>
</html>