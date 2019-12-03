<%@ page contentType="text/html; charset=UTF-8"%>
<%-- ログインのセッションスコープを取得 --%>
<%@ page import = "beans.Staff" %>
<%
Staff loginUser = (Staff)session.getAttribute("loginUser");
%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>入居者情報変更完了</title>
<meta name="viewport" content="width=device-width initial-scale=1">
<link rel = "stylesheet" href="/css/styles.css">
</head>

<body>

<%-- header --%>
<jsp:include page="/view/template/header.jsp"></jsp:include>

<main>

<%-- 入居者情報変更完了 --%>
<h3>入居者情報 変更完了</h3>
<p>入居者情報の変更が完了しました</p>
<a href="/OccupantEdit"><button>入居者情報編集を続ける</button></a>
<a href="/MenuController"><button>メニューへ戻る</button></a>
</main>


</body>
</html>