<%--jspファイルの設定スニペット --%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%
  //使用型 変数名 = (使用する型) session.getAttribute("インスタンス名");
%>
<%--indexフォルダ --%>

<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>ログイン画面</title>
<meta name="viewport" content="width=device-width initial-scale=1">
</head>
<body>
<h1>ログイン</h1>
<form action ="/usersystem3/Login" method="post">
<p>ユーザーID<input type ="text" name ="userId" minlength ="8" maxlength="8"></p>
<p>パスワード<input type ="password" name = "password" minlength ="8" maxlength="20"></p>
<input type ="submit" value="ログイン">
</form>
<p>初めての方は新規登録を行ってください</p>
<h5><a href = "/usersystem3/Add">新規登録</a></h5>
</body>
</html>


