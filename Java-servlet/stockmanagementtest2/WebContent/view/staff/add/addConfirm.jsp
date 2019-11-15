<%@ page contentType="text/html; charset=UTF-8"%>

<%@ page import = "staffcontroller.StaffAdd, beans.Staff" %>
<%@ page import = "beans.Goods" %>

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
<title>職員情報登録確認</title>
<meta name="viewport" content="width=device-width initial-scale=1">
<link rel = "stylesheet" href="/stockmanagementtest/css/styles.css">
</head>

<body>

<%-- header --%>
<header>
<a href="#"><button id="menu">ラックん</button></a>
<h3>ログインユーザー：〇〇〇〇</h3>
<a href ="#"><button id ="logout">ログアウト</button></a>
</header>


<main>
<%--エラーがあったら条件分岐でエラー文を出したい --%>
<%if(errorMsg != null){ %>
	<h3>職員情報 入力エラー</h3>
	<p><%= errorMsg %></p>
	<a href="/stockmanagementtest/StaffAdd?value=backFromAddError"><button>入力画面へ戻る</button></a>
<% } else { %>
<%-- エラーがなければ職員情報 登録確認    floorId, roomNumber ,staffName--%>
	<h3>職員情報 登録確認</h3>
	<p>職員名<%= registerStaff.getStaffName() %></p>
	<p>パスワード＊＊＊＊＊＊＊＊＊＊＊＊</p>

	<% if(registerStaff.getAuthority().equals("YES")){ %>
			<p>管理者権限：有</p>
	<% }else { %>
		    <p>管理者権限：無</p>
	<% }%>

	<a href ="/stockmanagementtest/StaffAdd?value=addAction"><button>登録</button></a>
	<a href="/stockmanagementtest/StaffAdd?value=backFromAdd"><button>入力画面へ戻る</button></a>
<% } %>
<a href="#?value=fromaddConfirm"><button>メニューへ戻る</button></a>
</main>

</body>
</html>