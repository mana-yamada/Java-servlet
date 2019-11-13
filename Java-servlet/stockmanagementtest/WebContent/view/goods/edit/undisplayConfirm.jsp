<%@ page contentType="text/html; charset=UTF-8"%>

<%@ page import = "goodscontroller.AddController" %>
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
<header>
<a href="#"><button id="menu">ラックん</button></a>
<h3>ログインユーザー：〇〇〇〇</h3>
<a href ="#"><button id ="logout">ログアウト</button></a>
</header>

<main>

<h3>備品情報 削除確認</h3>
<p>削除する備品名：<%= editGoods.getGoodsName() %></p>
<p>単価：<%= editGoods.getGoodsPrice() %></p>

<p>上記の内容で備品情報を変更してもよろしいですか？</p>
<a href ="/stockmanagementtest/UndisplayController?value=undisplayComplete"><button>削除</button></a>

<a href="/stockmanagementtest/EditController?value=backFromUndisplay"><button>備品情報編集画面へ戻る</button></a>
<a href="#"><button>メニューへ戻る</button></a>
</main>

</body>
</html>