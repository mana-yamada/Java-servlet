<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import = "goodscontroller.GoodsEdit" %>
<%@ page import = "beans.Goods" %>
<%-- ログインのセッションスコープを取得 --%>

<%
	//変更前の備品データ1行を保存したインスタンスを取得
	Goods targetGoods = (Goods)session.getAttribute("targetInstance");
%>


<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>備品情報変更</title>
<meta name="viewport" content="width=device-width initial-scale=1">
<link rel = "stylesheet" href="/stockmanagementtest/css/styles.css">
</head>

<body>

<%-- header --%>
<header>
<a href="#"><button id="menu">ラックん</button></a>
<h3>ログインユーザー：〇〇〇〇</h3>
<a href ="#"><button id ="logout">ログアウト</button></a>
</header>

<%-- 備品情報変更 --%>

<main>
<h3>備品情報変更</h3>

<p>変更前の備品名：<%= targetGoods.getGoodsName() %></p>
<p>変更前の単価：<%= targetGoods.getGoodsPrice() %>円</p>
<form action ="/stockmanagementtest/GoodsChange?value=changeConfirm" method ="post">
<p>変更後の備品名：<input type= "text" name="goodsName"  minlength="1" maxlength = "30"></p>
<p>変更後の単価：<input type="text" name="goodsPrice" minlength="1" maxlength = "7">円</p>
<button>変更内容確認</button>
</form>
<a href="/stockmanagementtest/GoodsEdit?value=backFromChange"><button>編集画面に戻る</button></a>
<a href="#"><button>メニューへ戻る</button></a>
</main>

</body>
</html>