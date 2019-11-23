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
<title>入居者情報新規登録</title>
<meta name="viewport" content="width=device-width initial-scale=1">
<link rel = "stylesheet" href="/stockmanagementtest/css/styles.css">
</head>

<body>

<%-- header --%>
<jsp:include page="/view/template/header.jsp"></jsp:include>

<%-- 入居者情報新規登録 --%>
<main>
<h3>入居者情報新規登録</h3>
<form action ="/stockmanagementtest/OccupantAdd?value=addConfirm" method = "post">
<p>登録する入居者</p>
<p>入居者名：(30字以内)<input type= "text" name="occupantName"  minlength="1" maxlength = "30"></p>
<p>入居フロア(整数)<input type ="text" name = "floorId"></p>
<p>居室番号(3ケタから4ケタの整数)：<input type="text" name="roomNumber"></p>
<button>登録内容確認</button>
</form>
<a href="/stockmanagementtest/MenuController"><button>メニューへ戻る</button></a>
</main>


</body>
</html>