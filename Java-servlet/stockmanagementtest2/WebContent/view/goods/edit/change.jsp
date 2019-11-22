<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import = "goodscontroller.GoodsEdit" %>
<%@ page import = "beans.Goods" %>
<%-- ログインのセッションスコープを取得 --%>

<%
	//変更前の備品データ1行を保存したインスタンスを取得
	Goods editGoods = (Goods)session.getAttribute("editGoods");
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
<jsp:include page="/view/template/header.jsp"></jsp:include>

<%-- 備品情報変更 --%>

<main>
<h3>備品情報変更</h3>
<p>変更前の備品名：<%= editGoods.getGoodsName() %></p>
<p>変更前の単価：<%= editGoods.getGoodsPrice() %>円</p>

<form action ="/stockmanagementtest/GoodsChange?value=changeConfirm" method ="post">

<%--備品名 --%>
<p>備品名</p>
<p><input type="radio" name="goodsNameChange" value="unchange" onclick="nameChange()" >変更なし</p>
<p><input type="radio" name="goodsNameChange" value="change"   onclick="nameChange()" checked = "checked">変更あり</p>
<p id ="nameForm"><input type= "text" name="goodsName" value="<%= editGoods.getGoodsName() %>"  maxlength = "30"></p><!-- scriptタグで表示設定 -->

<%--備品単価  --%>
<p>単価</p>
<p><input type="radio" name="goodsPriceChange" value="unchange" onclick="priceChange()">変更なし</p>
<p><input type="radio" name="goodsPriceChange" value="change"   onclick="priceChange()" checked = "checked">変更あり</p>
<p id ="priceForm" ><input  type="text" name="goodsPrice" value="<%= editGoods.getGoodsPrice() %>"  maxlength = "7">円</p><!-- scriptタグで表示設定 -->

<%-- 確認ボタン --%>
<button>変更内容確認</button>
</form>

<p><a href="/stockmanagementtest/GoodsEdit?value=backFromChange"><button>編集画面に戻る</button></a></p>
<p><a href="/stockmanagementtest/MenuController?-value=fromGoodsEditing"><button>メニューへ戻る</button></a></p>
</main>

<script type="text/javascript" src="/stockmanagementtest/js/goods.js">
</script>

</body>
</html>