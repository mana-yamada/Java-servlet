<%@ page contentType="text/html; charset=UTF-8"%>

<%@ page import = "goodscontroller.GoodsAdd" %>
<%@ page import = "beans.Goods" %>
<%@ page import = "beans.Staff" %>
<%
Staff loginUser = (Staff)session.getAttribute("loginUser");
%>

<%   String errorMsg = (String)session.getAttribute("errorMsg"); %>
<% //change
	//変更前の備品データ1行を保存したインスタンスを取得
	Goods editGoods = (Goods)session.getAttribute("editGoods");
	/*変更後に表示しようとしている備品名、単価を保存したインスタンスを取得*/
	Goods changeGoods = (Goods)session.getAttribute("changeGoods");
%>

<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>備品情報変更確認</title>
<meta name="viewport" content="width=device-width initial-scale=1">
<link rel = "stylesheet" href="/stockmanagementtest/css/styles.css">
</head>

<body>
<%-- header --%>
<jsp:include page="/view/template/header.jsp"></jsp:include>

<main>
<%   if(errorMsg != null) { %>
	<%--エラーがあった場合 --%>
	<h3>備品情報入力エラー</h3>
	<p><%= errorMsg %></p>

	<a href="/stockmanagementtest/GoodsChange?value=reChangeInputByError"><button>入力画面へ戻る</button></a>
	<a href="/stockmanagementtest/GoodsEdit?value=backFromConfirmByError"><button>備品情報編集画面へ戻る</button></a>
<% } else { %>
	<%-- エラーがなく問題ない場合 --%>
	<h3>備品情報 変更確認</h3>
<p>変更前の備品名：<%= editGoods.getGoodsName() %></p>
<p>変更後の備品名：<%= changeGoods.getGoodsName() %></p>
<p>変更前の単価：<%= editGoods.getGoodsPrice() %></p>
<p>変更後の単価：<%= changeGoods.getGoodsPrice() %></p>
<p>上記の内容で備品情報を変更してもよろしいですか？</p>

<p><a href="/stockmanagementtest/GoodsChange?value=changeAction"><button>変更</button></a></p>

<p><a href="/stockmanagementtest/GoodsChange?value=reChangeInput"><button>入力画面へ戻る</button></a></p>
<p><a href="/stockmanagementtest/GoodsEdit?value=backFromConfirm"><button>備品情報編集画面へ戻る</button></a></p>
<% } %>
<p><a href="/stockmanagementtest/MenuController?value=fromGoodsChangeConfirm"><button>メニューへ戻る</button></a></p>
</main>

</body>
</html>