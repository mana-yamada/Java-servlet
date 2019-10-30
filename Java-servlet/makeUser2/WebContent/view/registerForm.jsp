<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import = "model.User, model.RegisterLogic"%>

<!DOCTYPE html>
<html>
<head>
<meta charset = "UTF-8">
<title>新規ユーザー登録</title>
</head>
<body>
<p></p>
<form action="/makeUser2/RegisterController" method = "post">
<p>ユーザーID：<input type = "text" name ="userId"></p>
<p>パスワード：<input type ="password" name = "password"></p>
<p>名前：<input type = "text" name= "name" ></p>
<input type ="submit" value="確認画面へ">
</form>
</body>


</html>