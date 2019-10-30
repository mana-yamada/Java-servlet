<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="model.Evaluation" %>
<%
	Evaluation evaluation = (Evaluation) application.getAttribute("evaluation");

%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>湊くんのサイト評価</title>
</head>
<body>
<h1>湊くんのページへようこそ</h1>
<p><a href ="/minatohyoka/MinatoClicked?value=good">よいね：</a><%= evaluation.getGoodE() %><a href="/minatohyoka/MinatoClicked?value=bad">よくないね：</a><%= evaluation.getBadE() %></p>
</body>
</html>