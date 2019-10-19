<%@ page contentType="text/html; charset=UTF-8"%>

<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>ユーザー削除</title>
<meta name="viewport" content="width=device-width initial-scale=1">
</head>
<body>

<%--入力フォームの作成 --%>
<form action = "/usersystem2/RemoveUserCheck" method = "post">
<p>ユーザーID：<input type ="text" name ="userId"><p>
<%--//削除完了を確認するページへ移動(SearchUserCheck.java) --%>
<input type = "submit" value ="削除する">
</form>
<%--//TOP画面へ戻る --%>
<a href ="/usersystem2/index/index.jsp"><input type = "submit" value = "TOPへ戻る"></a>
</body>
</html>



