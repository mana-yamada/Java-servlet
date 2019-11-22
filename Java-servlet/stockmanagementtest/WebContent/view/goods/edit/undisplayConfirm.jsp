<%@ page contentType="text/html; charset=UTF-8"%>

<%@ page import = "goodscontroller.GoodsAdd" %>
<%@ page import = "beans.Goods" %>

<%
//login scope


%>

<%
	//削除しようとしている備品データ1行を保存したインスタンスを取得
	Goods editGoods = (Goods)session.getAttribute("editGoods");
%>

<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>備品情報削除確認</title>
<meta name="viewport" content="width=device-width initial-scale=1">
<link rel = "stylesheet" href="/stockmanagementtest/css/styles.css">
</head>

<body>
<%-- header --%>
<jsp:include page="/view/template/header.jsp"></jsp:include>

<main>

<h3>備品情報 削除確認</h3>
<p>削除する備品名：<%=  editGoods.getGoodsName() %></p>
<p>単価：<%= editGoods.getGoodsPrice() %></p>

<p>上記の内容で備品情報を削除してもよろしいですか？</p>
<p><a href ="/stockmanagementtest/GoodsUndisplay?value=undisplayComplete"><button>削除</button></a></p>

<p><a href="/stockmanagementtest/GoodsEdit?value=backFromUndisplay"><button>編集画面へ戻る</button></a></p>
<p><a href="/stockmanagementtest/MenuController?-value=fromGoodsEditing"><button>メニューへ戻る</button></a></p>
</main>

</body>
</html>