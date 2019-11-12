<%@ page contentType="text/html; charset=UTF-8"%>

<%@ page import = "goodscontroller.AddController" %>
<%@ page import = "beans.Goods" %>

<%
//login scope

// add.jspで保存したインスタンス取得
Goods goods = (Goods) session.getAttribute("goods");

/*// errorMsgインスタンス取得
String errorMsg = (String) request.getAttribute("errorMsg");*/

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
<header>
<a href="#"><button id="menu">ラックん</button></a>
<h3>ログインユーザー：〇〇〇〇</h3>
<a href ="#"><button id ="logout">ログアウト</button></a>
</header>


<main>
<%--エラーがあったら条件分岐でエラー文を出したい --%>

<%-- エラーがなければ備品情報 登録確認 --%>
<h3>備品情報 登録確認</h3>

<p>品目</p>
<p>備品名：<%= goods.getGoodsName() %></p>
<p>備品の単価：<%= goods.getGoodsPrice() %>円</p>
<p>上記の内容で備品情報を登録してもよろしいですか？</p>
<a href ="/stockmanagementtest/AddCoontroller?value=addgoods"><button>登録</button></a>


<a href="/stockmanagementtest/AddCoontroller?value=reinput"><button>入力画面へ戻る</button></a>
<a href="#?value=fromaddConfirm"><button>メニューへ戻る</button></a>
</main>

</body>
</html>