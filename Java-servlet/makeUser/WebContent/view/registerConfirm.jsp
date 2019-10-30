<%@ page contentType ="text/html; charset=UTF-8"%>
<%@ page import = "model.RegisterLogic, model.User"%>
<%
//get instance (user)
	User user = (User)session.getAttribute("user");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset = "UTF=8">
<title>ユーザー登録</title>
</head>
<body>
<h1>ユーザー登録</h1>
<p>ユーザーID(5ケタ)：<%= user.getUserId() %></p>
<p>名前：<%= user.getName() %></p>
<a href = "/makeUser/RegisterUser">戻る</a>
<a href = "/makeUser/RegisterUser?action=done">登録</a>
</body>


</html>