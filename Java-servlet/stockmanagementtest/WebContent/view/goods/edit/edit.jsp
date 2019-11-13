<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="sqloperate.Goodslist,beans.Goods, goodscontroller.EditController, java.util.ArrayList, java.util.Iterator"%>

<%
//ログインのセッションスコープを取得

//get scope


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
		<table border="1">
		<tr>
		<th>ArrayListのID</th>
		<th>goodslistのID</th>
		<th>備品名</th>
		<th>備品の単価</th>
		<th>表示or非表示</th>
		<th>☆変更☆</th>
		<th>■削除■</th>
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

			//取得した1行データをインスタンス化、スコープに保存
			Goods targetGoods = new Goods(goodsId, goodsName, goodsPrice, display, listNumber);
			//String targetInstance = "targetGoods" + listNumber;
			session.setAttribute("targetInstance" , targetGoods);
		%>
		<tr>
	    <td><%= target.getListNumber()  %></td>
		<td><%= target.getGoodsId() %></td>
	 	<td> <%= target.getGoodsName() %></td>
	 	<td><%= target.getGoodsPrice()%></td>
	 	<td><%= target.getDisplay() %></td>
	 	<td><a href="/stockmanagementtest/ChangeController?value=change"><button id="#">変更</button></a></td>
	    <td><a href="/stockmanagementtest/UndisplayController?value=undisplay"><button id="#">削除</button></a></td>
		<%}%>



	    </tr>

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


