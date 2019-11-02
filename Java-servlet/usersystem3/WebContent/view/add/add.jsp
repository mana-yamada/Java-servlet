<%@ page contentType="text/html; charset=UTF-8"%>
<%-- addUserパッケージ --%>

<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">

<title>ユーザー新規登録</title>
<meta name="viewport" content="width=device-width initial-scale=1">
</head>
<body>

<%--入力フォームの作成 --%>
<form action = "/usersystem3/Add" method = "post">
<p>ユーザーID(8文字の半角数字のみ)：<input type ="text" name ="userId" minlength="8" maxlength="8"><p>
<p>パスワード(8文字以上20文字以内)：<input type = "password" minlength="8" maxlength="20" name = "password"></p>
<p>名前(2文字以上50文字以内)：<input type = "text" name = "name" minlength="2" maxlength="50"></p>
<p>メールアドレス(15文字以上)50文字以内)：<input type ="text" name = "mail" minlength="15" maxlength="50"></p>
<p>電話番号(8文字以上15文字以内)：<input type = "text" name = "tel" minlength = "8" maxlength="15"></p>
<input type = "submit" value="新規登録">
</form>

<h5><a href ="/usersystem3/">ログイン画面へ戻る</a></h5>
</body>
</html>



