<%@ page contentType="text/html; charset=UTF-8"%>

<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>ユーザーID検索</title>
<meta name="viewport" content="width=device-width initial-scale=1">
</head>
<body>

<%--入力フォームの作成 --%>
<form action = "/usersystem2/SearchUserCheck" method = "post">
<p>ユーザーID：<input type ="text" name ="userId"><p>
<%--//検索内容を確認するページへ移動(SearchUserCheck.java) --%>
<input type = "submit" value ="検索結果画面へ">
</form>
<%--//TOP画面へ戻る --%>
<a href ="/usersystem2/index/index.jsp"><input type = "submit" value = "TOPへ戻る"></a>
</body>
</html>



