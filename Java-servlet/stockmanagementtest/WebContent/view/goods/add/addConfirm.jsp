<%@ page contentType="text/html; charset=UTF-8"%>

<%@ page import = "goodscontroller.GoodsAdd" %>
<%@ page import = "beans.Goods" %>

<%
//login scope

// add.jspで保存したインスタンス取得
Goods goods = (Goods) session.getAttribute("goods");

// errorMsgインスタンス取得
String errorMsg = (String) request.getAttribute("errorMsg");

%>

<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>備品情報登録確認</title>
<meta name="viewport" content="width=device-width initial-scale=1">
<link rel = "stylesheet" href="/stockmanagementtest/css/styles.css">
</head>

<body>

<%-- header --%>
<jsp:include page ="/view/template/header.jsp"></jsp:include>


<main>
<%--エラーがあったら条件分岐でエラー文を出したい --%>
<%if(errorMsg != null){ %>
	<h3>備品情報 入力エラー</h3>
	<p><%= errorMsg %></p>
	<a href="/stockmanagementtest/GoodsAdd"><button>入力画面へ戻る</button></a>
<% } else { %>
<%-- エラーがなければ備品情報 登録確認 --%>
	<h3>備品情報 登録確認</h3>

	<p>品目</p>
	<p>備品名：<%= goods.getGoodsName() %></p>
	<p>備品の単価：<%= goods.getGoodsPrice() %>円</p>
	<p>上記の内容で備品情報を登録してもよろしいですか？</p>
	<p><a href ="/stockmanagementtest/GoodsAdd?value=addgoods"><button>登録</button></a></p>
	<p><a href="/stockmanagementtest/GoodsAdd?value=reinput"><button>入力画面へ戻る</button></a></p>
<% } %>
<p><a href="#?value=fromaddConfirm"><button>メニューへ戻る</button></a></p>
</main>

</body>
</html>