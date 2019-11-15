<%@ page contentType="text/html; charset=UTF-8"%>
<%-- ログインのセッションスコープを取得 --%>

<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>職員情報新規登録</title>
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

<%-- 職員情報新規登録 --%>
<main>
<h3>職員情報新規登録</h3>
<form action ="/stockmanagementtest/StaffAdd?value=addConfirm" method = "post">

<p>職員名：(30字以内)<input type= "text" name="staffName"  minlength="1" maxlength = "30"></p>

<p>パスワード：8字以上20字以内：<input type = "password" name="password" minlength = "8" maxlength = "20"></p>

<p>管理者権限</p>
<p><input type = "radio" name ="authority" value="YES"> 有 <input type = "radio" name = "authority" value="NO"> 無</p>

<button>登録内容確認</button>

</form>
<a href="#"><button>メニューへ戻る</button></a>
</main>


</body>
</html>