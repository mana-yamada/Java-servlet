<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import = "model.User, model.RegisterLogic"%>
<% //get instance from session scope
	User newUser = (User)session.getAttribute("newUser");

%>
<!DOCTYPE html>
<html>
<head>
<meta charset = "UTF-8">
<title>確認画面</title>
</head>
<body>
<p>ユーザーID：<%= newUser.getUserId() %></p>
<p>名前：<%= newUser.getUserId() %></p>
<a href = "/makeUser2/RegisterController?afterConfirm=done">登録</a>
<a href = "/makeUser2/RegisterController">戻る</a>
</body>


</html>