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
<jsp:include page ="/view/template/header.jsp"></jsp:include>

<%-- 備品情報新規登録 --%>
<main>
<h3>備品情報新規登録</h3>
<form action ="/stockmanagementtest/GoodsAdd?value=addconfirm" method = "post">
<p>品目</p>
<p>備品名：(30字以内)<input type= "text" name="goodsname"  minlength="1" maxlength = "30"></p>
<p>備品の単価：<input type="text" name="goodsprice" minlength="1" maxlength = "7">円</p>
<p><button>登録内容確認</button></p>
</form>
<p><a href="#"><button>メニューへ戻る</button></a></p>
</main>


</body>
</html>