<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import = "beans.Staff" %>
<%
Staff loginUser = (Staff)session.getAttribute("loginUser");
%>

<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>備品情報削除完了</title>
<meta name="viewport" content="width=device-width initial-scale=1">
<link rel = "stylesheet" href="/stockmanagementtest/css/styles.css">
</head>

<body>

<%-- header --%>
<jsp:include page="/view/template/header.jsp"></jsp:include>

<main>

<%-- 備品情報削除完了 --%>
<h3>備品情報 削除完了</h3>
<p>備品情報の削除が完了しました</p>
<p><a href="/stockmanagementtest/GoodsEdit"><button>備品情報編集を続ける</button></a></p>
<p><a href="/stockmanagementtest/MenuController"><button>メニューへ戻る</button></a></p>
</main>


</body>
</html>