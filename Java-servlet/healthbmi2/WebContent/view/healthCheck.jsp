<%@ page contentType="text/html; charset=UTF-8" %>

<!DOCTYPE html>
<html>
<head>
<meta charset = "UTF-8">
<title>スッキリ占い</title>
</head>
<body>
<h1>スッキリ占い</h1>
<%--身長、体重⇒HealthCheckへ送信！！  --%>
<form action ="/healthbmi2/HealthCheck" method="post">
<p>身長(cm)：<input type="text" name ="height"></p>
<p>体重(kg)<input type ="text" name = "weight"></p>
<input type ="submit" value ="送信">
</form>

</body>
</html>