<%@ page contentType="text/html; charset=UTF-8"%>
<%-- ログインのセッションスコープを取得 --%>
<%@ page import = "beans.Staff" %>
<%
 Staff registerStaff = (Staff)session.getAttribute("registerStaff");
 int staffId = registerStaff.getStaffId();
 String strStaffId = String.format("%05d",staffId );
%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>職員情報登録完了</title>
<meta name="viewport" content="width=device-width initial-scale=1">
<link rel = "stylesheet" href="/stockmanagementtest/css/styles.css">
</head>

<body>


<main>

<%-- 職員情報登録完了 --%>
<h3>職員情報 登録完了</h3>
<p>職員情報の新規登録が完了しました</p>
<p>下記のユーザーIDはログイン時に必要なので、必ずメモを取っておいてください。</p>
<p>ユーザーID ：<%= strStaffId  %></p>
<p>職員名：<%= registerStaff.getStaffName() %></p>
	<p>パスワード：＊＊＊＊＊＊＊＊＊＊＊＊</p>

	<% if(registerStaff.getAuthority().equals("YES")){ %>
			<p>管理者権限：有</p>
	<% }else { %>
		    <p>管理者権限：無</p>
	<% }%>


<p><a href="/stockmanagementtest/SettingController"><button>登録を続ける</button></a></p>

</main>

</body>
</html>