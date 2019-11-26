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
<title>職員情報新規登録</title>
<meta name="viewport" content="width=device-width initial-scale=1">
<link rel = "stylesheet" href="/stockmanagementtest/css/styles.css">
</head>

<body>

<%-- header --%>
<jsp:include page="/view/template/header.jsp"></jsp:include>


<%-- 職員情報新規登録 --%>
<main>
<h3>職員情報新規登録</h3>
<form action ="/stockmanagementtest/StaffAdd?value=addConfirm" method = "post">

<p>職員名：(30字以内)</p>
<p><input type= "text" name="staffName"  minlength="1" maxlength = "30"></p>

<p>パスワード：8字以上15字以内</p>
<p><input type = "password" name="password" minlength = "8" maxlength = "15"></p>

<p>管理者権限</p>
<p><input type = "radio" name ="authority" value="YES"> 有 <input type = "radio" name = "authority" value="NO" checked="checked" > 無</p>

<p><button>登録内容確認</button></p>

</form>
<p><a href="/stockmanagementtest/MenuController"><button>メニューへ戻る</button></a></p>
</main>


</body>
</html>