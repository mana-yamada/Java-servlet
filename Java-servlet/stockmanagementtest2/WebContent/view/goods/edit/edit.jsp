<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="sqloperate.Goodslist,beans.Goods, goodscontroller.GoodsEdit, java.util.ArrayList, java.util.Iterator"%>
<%@ page import = "beans.Staff" %>
<%
Staff loginUser = (Staff)session.getAttribute("loginUser");
%>

<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>備品情報編集</title>
<meta name="viewport" content="width=device-width initial-scale=1">
<link rel = "stylesheet" href="/stockmanagementtest/css/styles.css">
</head>
<body>

<%-- header --%>
<jsp:include page="/view/template/header.jsp"></jsp:include>

<div id="contents">
	<main>
		<h3>備品情報編集</h3>
		<table border="1">
		<tr>
		<th>備品名</th>
		<th>備品の単価</th>
		<th>変更</th>
		<th>削除</th>
		</tr>
		<%
		ArrayList<Goods> goodsList = new ArrayList<Goods>();
	    Goodslist displayGoods = new Goodslist();
		displayGoods.get(goodsList);

		for(Goods target : goodsList){
			//ArrayList 内でのインデックス番号
			int listNumber = goodsList.indexOf(target);

			//DBに格納した1行の各列を取得
			int goodsId = target.getGoodsId();
			String goodsName = target.getGoodsName();
			int goodsPrice = target.getGoodsPrice();
			String display = target.getDisplay();
		%>
		<tr>

		 	<td><%= target.getGoodsName() %></td>
		 	<td><%= target.getGoodsPrice()%></td>
		 	<td>
		 	<form action="/stockmanagementtest/GoodsChange" method="get">
			 	<input type="hidden" name="goodsId" value="<%= target.getGoodsId() %>">
			 	<input type="hidden" name="goodsName" value="<%= target.getGoodsName() %>">
			 	<input type="hidden" name="goodsPrice" value="<%= target.getGoodsPrice() %>">
			 	<button id="#">変更</button>
		 	</form>
		 	</td>

		    <td>
		    <form action="/stockmanagementtest/GoodsUndisplay" method="get">
			 	<input type="hidden" name="goodsId" value="<%= target.getGoodsId() %>">
			 	<input type="hidden" name="goodsName" value="<%= target.getGoodsName() %>">
			 	<input type="hidden" name="goodsPrice" value="<%= target.getGoodsPrice() %>">
			 	<button id="#">削除</button>
		 	</form>

		    </td>
		<%}%>
	    </tr>

		</table>
		<p><a href="/stockmanagementtest/MenuController"><button>メニューへ戻る</button></a></p>
	</main>
</div>


</body>
</html>


<%-- section
<section>
</section>
--%>


<%-- footer
<footer>
		<p>@rakkun kaigo_se Inc.</p>
	</footer>
 --%>


