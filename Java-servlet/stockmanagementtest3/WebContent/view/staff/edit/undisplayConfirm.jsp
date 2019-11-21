<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import = "beans.Staff" %>

<%
//login scope


%>

<%
	//削除しようとしている職員データ1行を保存したインスタンスを取得
	Staff editStaff = (Staff)session.getAttribute("editStaff");
%>

<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>職員情報削除確認</title>
<meta name="viewport" content="width=device-width initial-scale=1">
<link rel = "stylesheet" href="/stockmanagementtest/css/styles.css">
</head>

<body>
<%-- header --%>
<jsp:include page="/view/template/header.jsp"></jsp:include>


<main>

<h3>職員情報 削除確認</h3>
<p>削除する職員名：<%=  editStaff.getStaffName() %></p>
<p>ユーザーID：<%= editStaff.getStaffId() %></p>

<p>上記の内容で職員情報を削除してもよろしいですか？</p>
<p><a href ="/stockmanagementtest/StaffUndisplay?value=undisplayComplete"><button>削除</button></a></p>

<p><a href="/stockmanagementtest/StaffEdit?value=backFromUndisplay"><button>編集画面へ戻る</button></a></p>
<p><a href="#"><button id ="#">メニューへ戻る</button></a></p>
</main>

</body>
</html>