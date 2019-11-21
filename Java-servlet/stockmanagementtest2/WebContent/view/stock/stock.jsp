<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="java.util.ArrayList, java.util.Iterator , beans.Stock, sqloperate.Nowstocklist"%>

<%
//ログインのセッションスコープを取得

//get scope


%>
<%
	ArrayList<Stock> stockList = new ArrayList<Stock>();

    Nowstocklist getTable = new Nowstocklist();
    getTable.display(stockList);
%>

<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>備品入出庫入力画面</title>
<meta name="viewport" content="width=device-width initial-scale=1">
<link rel = "stylesheet" href="/stockmanagementtest/css/styles.css">
</head>
<body>

<%-- header --%>
<jsp:include page="/view/template/header.jsp"></jsp:include>

<main>
<div id = "nowstock">
	<table>
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
<p><a href="#"><button>メニューへ戻る</button></a></p>
</main>


</body>
</html>