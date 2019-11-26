<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import = "beans.Staff" %>
<%
Staff loginUser = (Staff)session.getAttribute("loginUser");
%>

<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>備品入出庫完了画面</title>
<meta name="viewport" content="width=device-width initial-scale=1">
<link rel = "stylesheet" href="/stockmanagementtest/css/styles.css">
</head>
<body>

<%-- header --%>
<jsp:include page="/view/template/header.jsp"></jsp:include>

<main>
<h3>備品入出庫完了</h3>
<p>備品入出庫の記録が登録されました。</p>

<p><a href= "/stockmanagementtest//view/operate/operate.jsp"><button>入出庫記録の登録を続ける</button></a>
<p><a href="/stockmanagementtest/MenuController"><button>メニューへ戻る</button></a></p>
</main>

</body>
</html>