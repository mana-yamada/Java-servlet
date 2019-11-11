<%@ page contentType="text/html; charset=UTF-8"%>
<%-- ログインのセッションスコープを取得 --%>

<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>備品情報新規登録</title>
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

<%-- 備品情報新規登録 --%>
<main>
<h3>備品情報新規登録</h3>
<form action ="/stockmanagementtest/GoodsOperate?value=addconfirm" method = "post">
<p>品目</p>
<p>備品名：<input type= "text" name="goodsname"  minlength="1" maxlength = "30"></p>
<p>備品の単価：<input type="text" name="goodsprice" minlength="1" maxlength = "7"></p>
<button>登録内容確認</button>
</form>
<a href="#"><button>メニューへ戻る</button></a>
</main>


</body>
</html>