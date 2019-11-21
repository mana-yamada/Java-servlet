<%@ page contentType="text/html; charset=UTF-8"%>
<%-- ログインのセッションスコープを取得 --%>

<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>ログイン画面</title>
<meta name="viewport" content="width=device-width initial-scale=1">
<link rel = "stylesheet" href="/stockmanagementtest/css/styles.css">
</head>

<body>

<main>
<h3>ログイン画面</h3>
<form action ="/stockmanagementtest/LoginController" method="post">
<p>ユーザーID<input type ="text" name ="userId" minlength = "1" maxlength = "5"></p>
<p>パスワード(8文字以上20字以内)<input type ="password" name = "password" minlength ="8" maxlength="20"></p>
<input type ="submit" value="ログイン">
</form>
</main>

</body>
</html>