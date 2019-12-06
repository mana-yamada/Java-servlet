<%@ page contentType="text/html; charset=UTF-8"%>

<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>ログイン画面</title>
<meta name="viewport" content="width=device-width initial-scale=1">
<link rel = "stylesheet" href="/css/styles.css">
</head>

<body>

<main>
<h3>ログイン画面</h3>
<form action ="/stockmanagement/LoginController" method="post">
<p>ユーザーID(5ケタの数字)<input type ="text" name ="userId" minlength = "5" maxlength = "5"></p>
<p>パスワード(8文字以上15字以内)<input type ="password" name = "password" minlength ="8" maxlength="15"></p>
<input type ="submit" value="ログイン">
</form>
</main>

</body>
</html>