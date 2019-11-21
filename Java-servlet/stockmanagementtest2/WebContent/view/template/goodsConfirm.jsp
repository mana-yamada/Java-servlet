<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="sqloperate.Goodslist, sqloperate.Occupantlist, beans.Goods, beans.Occupant,beans.Operating, java.util.ArrayList, java.util.Iterator"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>備品入出庫出力画面</title>
<meta name="viewport" content="width=device-width initial-scale=1">
<link rel = "stylesheet" href="/stockmanagementtest/css/styles.css">
</head>
<body>

<%
	Operating operation = (Operating)session.getAttribute("operation");
	Goods reGoods = (Goods)session.getAttribute("reGoods");
	Occupant reOccupant = (Occupant)session.getAttribute("reOccupant");



%>
<p>入庫 or 出庫：<%= operation.getStrSheds() %></p>
<p>備品名：<%= reGoods.getGoodsName() %></p>
<p>入庫数：<%= operation.getCountIn() %></p>
<p>出庫数：<%= operation.getCountOut() %></p>
<p>使用場所：<%= operation.getStrSpace() %>
<p>居室ならだれの居室？：<%= reOccupant.getRoomNumber() %>  <%= reOccupant.getOccupantName() %></p>



<script src="/stockmanagementtest/js/operate.js">
		</script>


		</body>
		</html>