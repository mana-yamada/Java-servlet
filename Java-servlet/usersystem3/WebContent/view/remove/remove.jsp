<%--jspファイルの設定スニペット --%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%

%>

<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>ユーザー削除</title>
<meta name="viewport" content="width=device-width initial-scale=1">
</head>
<body>
<h1>ユーザー削除</h1>
<form action = "/usersystem3/Remove" method ="post">
<p>ユーザーID<input type ="text" name = "userId" minlength = "8" maxlength = "8"></p>
<!--  -->
<input type="submit" value = "確認">
</form>
<h5><a href="/usersystem3/Menu">メニュー</a></h5>
<h5><a href = "/usersystem3/Logout">ログアウト</a></h5>

</body>
</html>


