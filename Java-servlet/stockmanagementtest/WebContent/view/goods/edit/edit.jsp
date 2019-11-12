<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="sqloperate.Goodslist,beans.Goods, goodscontroller.EditController, java.util.ArrayList, java.util.Iterator"%>

<%
//ログインのセッションスコープを取得

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
<div id="contents">
<%-- header --%>
	<header>
		<a href="#"><button id="menu">ラックん</button></a>
		<h3>ログインユーザー：〇〇〇〇</h3>
		<a href ="#"><button id ="logout">ログアウト</button></a>
	</header>
	<main>
		<h3>備品情報編集</h3>
		<%
		  ArrayList<Goods> goodsList = new ArrayList<Goods>();
		  Goodslist displayGoods = new Goodslist();
		  displayGoods.get(goodsList);
		%>
		<table border="1" width="500" cellspacing="0" cellpadding="5" bordercolor="#333333">
		<tr>
		<th>備品名</th>
		<th>変更</th>
		<th>削除</th>
		</tr>
		<%  //Goodslistクラス getTableメソッド内でArrayListに格納した備品名を表示
		Iterator<Goods> it = goodsList.iterator(); %>
		<% while(it.hasNext()){ %>
		<% Goods content = it.next(); %>
		<tr>
		<%
		//DBに格納した1行の各列を取得
		int goodsId = content.getGoodsId();
		String goodsName = content.getGoodsName();
		int goodsPrice = content.getGoodsPrice();
		String display = content.getDisplay();
		//取得した1行データをインスタンス化、スコープに保存
	    Goods editGoods = new Goods(goodsId, goodsName, goodsPrice, display);
		request.setAttribute("editGoods", editGoods);
		%>
	 	<td> <%= goodsName %></td>
	 	<td><a href="/stockmanagement/EditController?value=change"><button id="#">変更</button></a></td>
	    <td><a href="/stockmanagement/EditController?value=undisplay"><button id="#">削除</button></a></td>
	    </tr>
		<%} %>
		</table>
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


