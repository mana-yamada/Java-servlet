<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="controller.Add" %>
<%@ page import="bean.UserBeans" %>

<%
 UserBeans person = (UserBeans)session.getAttribute("person");
%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>登録内容確認</title>
<meta name="viewport" content="width=device-width initial-scale=1">
</head>
<body>
<h3>登録内容確認</h3>
<p>ユーザーID(8文字の半角数字のみ)：<%= person.getUserId() %></p>
<p>パスワード(8文字以上20文字以内)：***************</p>
<p>名前(2文字以上50文字以内)：<%= person.getName() %></p>
<p>メールアドレス(15文字以上)50文字以内)：<%= person.getMail() %></p>
<p>電話番号(8文字以上15文字以内)：<%= person.getTel() %></p>
<p>登録してもよろしいですか？</p>
<h5><a href = "/usersystem3/Add?value=ok">登録</a></h5>
<h5><a href = "/usersystem3/Add?value=ng">入力画面へ戻る</a></h5>
<h5><a href ="/usersystem3/Login?value=person">他のユーザーでログインする</a></h5>
</body>
</html>



