<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="controller.Search" %>
<%@ page import="bean.UserBeans" %>

<%
 UserBeans target = (UserBeans)session.getAttribute("target");
%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>検索結果</title>
<meta name="viewport" content="width=device-width initial-scale=1">
</head>
<body>
<h3>検索結果</h3>
<p>ユーザーID(8文字の半角数字のみ)：<%= target.getUserId() %></p>
<p>パスワード(8文字以上20文字以内)：***************<%-- target.getPassword() --%><%--←パスワードがきちんと復号化されていることを確認 --%></p>
<p>名前(2文字以上50文字以内)：<%= target.getName() %></p>
<p>メールアドレス(15文字以上)50文字以内)：<%= target.getMail() %></p>
<p>電話番号(8文字以上15文字以内)：<%= target.getTel() %></p>
<h5><a href = "/usersystem3/Search?value=back">検索画面へ戻る</a></h5>
</body>
</html>



