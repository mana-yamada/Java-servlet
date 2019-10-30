<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import = "model.Health, model.HealthCheckLogic" %>
<%
	//getAttribute HealthCheckで保存したインスタンスを呼び出す
	Health health = (Health) request.getAttribute("health");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset = "UTF-8">
<title>スッキリ健診結果</title>
</head>
<body>
<h1>スッキリ健診結果</h1>
<p>身長(cm)：<%= health.getHeight() %></p>
<p>体重(kg)：<%= health.getWeight() %></p>
<p>BMI：<%= health.getBmi() %></p>
<p>判定：<%= health.getBodyType() %></p>
</body>
</html>