<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import = "beans.Staff" %>
<%
	//ログインエラー取得
	String errorMsg = (String)session.getAttribute("errorMsg");
	//ログインスコープ取得
	Staff loginUser = (Staff)session.getAttribute("loginUser");
%>

<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>メニュー画面</title>
<meta name="viewport" content="width=device-width initial-scale=1">
<link rel = "stylesheet" href="/stockmanagementtest/css/styles.css">
</head>

<body>
<%-- if login error { --%>
<% if(errorMsg != null) { %>
	<p><%= errorMsg %></p>
	<p><a href = "/stockmanagementtest/LoginController?value=backFromMenuError"></a></p>

<% } else { %>
	<%-- header --%>
	<jsp:include page="/view/template/header.jsp"></jsp:include>
	<main>
	<h3>メニュー画面</h3>
	<%-- ログインユーザー全員に見せる--%>
	<p><a href="/stockmanagementtest/view/operate/operate.jsp"><button id="">在庫入出庫画面</button></a></p>
	<p><a href="/stockmanagementtest/view/stock/stock.jsp"><button id="">現在の備品残数</button></a></p>

	<% if (loginUser.getAuthority().equals("YES") ) { %>
	<p>職員（担当者）情報管理</p>
	<p><a href="/stockmanagementtest/StaffEdit"><button id="">職員情報編集</button></a>  <a href="/stockmanagementtest/StaffAdd"><button id="">新規登録</button></a></p>
	<p>入居者情報管理</p>
	<p><a href="/stockmanagementtest/OccupantEdit"><button id="">入居者情報編集</button></a>  <a href="/stockmanagementtest/OccupantAdd"><button id="">新規登録</button></a></p>
	<p>備品情報管理</p>
	<p><a href="/stockmanagementtest/GoodsEdit"><button id="">備品情報編集</button></a>  <a href="/stockmanagementtest/GoodsAdd"><button id="">新規登録</button></a></p>
	<% } %>
	</main>
<% } %>
</body>
</html>



<%-- section
<section>
</section>
--%>


<%-- footer
<footer>
<p>@rakkun kaigo_se Inc.</p>
</footer>
 --%>


