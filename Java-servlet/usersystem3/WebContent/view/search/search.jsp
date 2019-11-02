<%--jspファイルの設定スニペット --%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="bean.UserBeans" %>
<%
  //使用型 変数名 = (使用する型) session.getAttribute("インスタンス名");
UserBeans loginUser = (UserBeans)session.getAttribute("loginUser");
%>
<%--indexフォルダ --%>

<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>ユーザー検索</title>
<meta name="viewport" content="width=device-width initial-scale=1">
</head>
<body>
<h1>ユーザー検索</h1>
<form action = "/usersystem3/Search" method = "post">
<p>ユーザーID：<input type ="text" name ="userId" minlength="8" maxlength="8"><p>
<p>パスワード：<input type = "password" name = "password" minlength="8" maxlength="20"></p>
<%--//検索内容を確認するページへ移動(SearchUserCheck.java) --%>
<input type = "submit" value ="検索結果画面へ">
</form>
<h5><a href = "/usersystem3/Menu">メニュー</a></h5>
<h5><a href ="/usersystem3/Logout">ログアウト</a></h5>
</body>
</html>


