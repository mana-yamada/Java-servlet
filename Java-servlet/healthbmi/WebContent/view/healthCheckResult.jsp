<%--jspファイルの設定スニペット --%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="model.Health" %>
<%
//リクエストスコープに保存されたHealthインスタンスを取得
Health health = (Health)request.getAttribute("health");
%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>すっきり健診結果</title>
<meta name="viewport" content="width=device-width initial-scale=1">
<link rel = "stylesheet" href="/healthbmi/css/style.css">
</head>
<body>
<h1>すっきり健診結果</h1>
<p>身長：<%= health.getHeight() %></p>
<p>体重：<%=health.getWeight() %></p>
<p>BMI：<%= health.getBmi() %></p>
<p>健診判定：<%= health.getBodyType() %></p>
</body>
</html>


<%
//h1 文字の大きさ 優先度低め
//input type = "submit(送信ボタン)"⇒id「btn」を付与してbtnでスタイリング
//p 文字の大きさ  優先度中

%>