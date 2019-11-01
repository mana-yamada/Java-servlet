<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="controller.Remove" %>
<%@ page import="bean.UserBeans" %>

<%
 UserBeans target = (UserBeans)session.getAttribute("target");
%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>削除確認</title>
<meta name="viewport" content="width=device-width initial-scale=1">
</head>
<body>
<h3>削除確認</h3>
<p>ユーザーID(8文字の半角数字のみ)：<%= target.getUserId() %></p>
<p>本当に削除してもよろしいですか？</p>
<h5><a href = "/usersystem3/Remove?value=ok">削除</a></h5>
<h5><a href = "/usersystem3/Remove">削除画面へ戻る</a></h5>
<h5><a href ="/usersystem3/Logout">ログアウト</a></h5>
</body>
</html>



