<%@ page contentType ="text/html; charset=UTF-8"%>
<%@ page import = "model.RegisterLogic, model.User"%>


<!DOCTYPE html>
<html>
<head>
<meta charset = "UTF=8">
<title>ユーザー登録</title>
</head>
<body>
<h1>ユーザー登録</h1>
<form action = "/makeUser/RegisterUser" method = "post">
<p>ユーザーID(5ケタ)：<input type = "text" name ="userId" minlength ="5" maxlength ="5"></p>
<p>パスワード(8文字以上20字以内)<input type = "password" name ="password" minlength ="8" maxlength = "20")></p>
<p>名前：(2文字以上、50字以内)<input type = "text" name ="name" minlength = "2" maxlength = "50"></p>
<input type = "submit" value = "確認画面へ">
</form>
</body>


</html>