<%@ page contentType="text/html; charset=UTF-8"%>
<%-- ログインのセッションスコープを取得 --%>

<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>入居者情報削除完了</title>
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

<%-- 入居者情報削除完了 --%>
<h3>入居者情報 削除完了</h3>
<p>入居者情報の削除が完了しました</p>
<p><a href="/stockmanagementtest/OccupantEdit"><button>入居者情報編集を続ける</button></a></p>
<p><a href="/stockmanagementtest/#"><button>メニューへ戻る</button></a></p>
</main>


</body>
</html>