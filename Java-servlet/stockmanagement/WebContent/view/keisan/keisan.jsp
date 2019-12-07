<%@ page contentType="text/html; charset=UTF-8"%>


<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>テンプレート</title>
<meta name="viewport" content="width=device-width initial-scale=1">
<link rel = "stylesheet" href="/stockmanagement/css/styles.css">
</head>

<body>

<%-- header   <jsp:include page="/view/template/header.jsp"></jsp:include>--%>



<main>
<h3>個数計算開始画面</h3>
<form action="/KeisanController" method = "get">
		<!-- 年-->
		<select name = "year">
		<% for(int x = 2019; x < 2025 ; x++) {%>
		<option><%= x %></option>
		<% } %>
		</select>
		年

		<!-- 月 -->
		<select name="month">
		<% for(int x = 0; x < 12 ; x++) {%>
		<option><%= x + 1 %></option>
		<% } %>
		</select>
		月
		<button>計算開始</button>
</form>
<p><a href="/MenuController"><button>メニューへ戻る</button></a></p>
</main>

</body>
</html>