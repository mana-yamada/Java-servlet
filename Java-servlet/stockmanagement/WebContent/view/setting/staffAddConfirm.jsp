<%@ page contentType="text/html; charset=UTF-8"%>

<%@ page import = " beans.Staff" %>
<%@ page import = "beans.Goods" %>
<%@ page import = "beans.Staff" %>
<%

%>
<%
//login scope
// errorMsgインスタンス取得
String errorMsg = (String) session.getAttribute("errorMsg");

// add.jspで保存したインスタンス取得
Staff registerStaff = (Staff)session.getAttribute("registerStaff");

%>

<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>初期設定</title>
<meta name="viewport" content="width=device-width initial-scale=1">
<link rel = "stylesheet" href="/stockmanagement/css/styles.css">
</head>

<body>
<main>
<%--エラーがあったら条件分岐でエラー文を出したい --%>
<%if(errorMsg != null){ %>
	<h3>職員情報 入力エラー</h3>
	<p><%= errorMsg %></p>
	<p><a href="/stockmanagement/SettingController?value=backFromAddError"><button>入力画面へ戻る</button></a></p>
<% } else { %>
<%-- エラーがなければ職員情報 登録確認    floorId, roomNumber ,staffName--%>
	<h1>初期設定</h1>
	<h3>職員情報 登録確認</h3>
	<p>職員名：<%= registerStaff.getStaffName() %></p>
	<p>パスワード：＊＊＊＊＊＊＊＊＊＊＊＊</p>

	<% if(registerStaff.getAuthority().equals("YES")){ %>
			<p>管理者権限：有</p>
	<% }%>

	<p><a href="/stockmanagement/SettingController?value=addAction"><button>登録</button></a></p>
	<p><a href="/stockmanagement/SettingController?value=backFromAdd"><button>入力画面へ戻る</button></a></p>
<% } %>

</main>

</body>
</html>