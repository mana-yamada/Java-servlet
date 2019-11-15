<%@ page contentType="text/html; charset=UTF-8"%>
<%-- ログインのセッションスコープを取得 --%>

<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>備品情報変更完了</title>
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

<%-- 備品情報変更完了 --%>
<h3>備品情報 変更完了</h3>
<p>備品情報の変更が完了しました</p>
<a href="/stockmanagementtest/GoodsEdit"><button>備品情報編集を続ける</button></a>
<a href="/stockmanagementtest/#?value=fromAddComlete"><button>メニューへ戻る</button></a>
</main>


</body>
</html>