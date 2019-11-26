<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="sqloperate.Goodslist, beans.Stock, java.util.ArrayList, java.util.Iterator , beans.Stock"%>
<%@ page import = "beans.Staff" %>
<%
Staff loginUser = (Staff)session.getAttribute("loginUser");
%>
<%
	ArrayList<Stock> stockList = new ArrayList<Stock>();

   	Goodslist getTable = new Goodslist();
    getTable.making(stockList);
%>

<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>現在の備品残数</title>
<meta name="viewport" content="width=device-width initial-scale=1">
<link rel = "stylesheet" href="/stockmanagementtest/css/styles.css">
</head>
<body>

<%-- header --%>
<jsp:include page="/view/template/header.jsp"></jsp:include>

<main>
<h3>現在の備品残数</h3>
<div id = "nowstock">
	<table  border = "1">
	<tr>
	<th>備品名</th>
	<th>現在の残数</th>
	</tr>
   <% for(Stock parts: stockList){ %>
	<tr>
	<td><%= parts.getGoodsName() %></td>
	<td><%= parts.getStock() %></td>
	</tr>
   <% } %>
	</table>
</div>
<p><a href="/stockmanagementtest/MenuController"><button>メニューへ戻る</button></a></p>
</main>


</body>
</html>