<%@ page contentType="text/html; charset=UTF-8"%>

<%@ page import = "goodscontroller.AddController" %>
<%@ page import = "beans.Goods" %>

<%
//login scope



// errorMsgインスタンス取得
//String errorMsg = (String) request.getAttribute("errorMsg");

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
<header>
<a href="#"><button id="menu">ラックん</button></a>
<h3>ログインユーザー：〇〇〇〇</h3>
<a href ="#"><button id ="logout">ログアウト</button></a>
</header>


<main>
<%--エラー出力させるために後で条件分岐する --%>
<% /* String errorMsg = (String)session.getattribute("errorMsg");*/ %>
<%-- エラーがなければ備品情報 変更確認 --%>
<h3>備品情報 変更確認</h3>

<%
	//変更前の備品データ1行を保存したインスタンスを取得
	Goods editGoods = (Goods)session.getAttribute("editGoods");
	/*変更後に表示しようとしている備品名、単価を保存したインスタンスを取得*/
	Goods changeGoods = (Goods)session.getAttribute("changeGoods");
%>
<p>変更前の備品名：<%= editGoods.getGoodsName() %></p>
<p>変更後の備品名：<%= changeGoods.getGoodsName() %></p>
<p>変更前の単価：<%= editGoods.getGoodsPrice() %></p>
<p>変更後の単価：<%= changeGoods.getGoodsPrice() %></p>
<p>上記の内容で備品情報を変更してもよろしいですか？</p>

<a href="/stockmanagementtest/EditController?value=changegoods"><button>変更</button></a>

<a href="/stockmanagementtest/EditController?value=rechangeinput"><button>入力画面へ戻る</button></a>
<a href="/stockmanagementtest/EditController?value=backEditFromConfirm"><button>備品情報編集画面へ戻る</button></a>
</main>

</body>
</html>