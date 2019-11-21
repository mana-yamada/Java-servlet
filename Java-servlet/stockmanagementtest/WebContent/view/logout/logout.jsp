<%@ page contentType="text/html; charset=UTF-8"%>
<%-- ログインのセッションスコープを削除 --%>
<% session.removeAttribute("loginUser"); %>


<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>ログアウト</title>
<meta name="viewport" content="width=device-width initial-scale=1">
<link rel = "stylesheet" href="/stockmanagementtest/css/styles.css">
</head>

<body>

<%-- header --%>
<jsp:include page="/view/template/header.jsp"></jsp:include>
<main>
<p>ログアウトしました</p>
<p><a href = "/stockmanagementtest/LoginController">ログイン画面に戻る</a></p>
</main>


</body>
</html>