<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import = "beans.Staff" %>
<%  //login scope  %>
<%
Staff loginUser = (Staff)session.getAttribute("loginUser");
%>
<%   String errorMsg = (String)session.getAttribute("errorMsg"); //change %>
<%
	//変更前の職員データ1行を保存したインスタンスを取得
	Staff editStaff = (Staff)session.getAttribute("editStaff");
	/*変更後に表示しようとしている職員名、単価を保存したインスタンスを取得*/
	Staff changeStaff = (Staff)session.getAttribute("changeStaff"); //change
	int staffId = editStaff.getStaffId();
	String strStaffId = String.format("%05d", staffId);
%>


<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>職員情報変更確認</title>
<meta name="viewport" content="width=device-width initial-scale=1">
<link rel = "stylesheet" href="/css/styles.css">
</head>

<body>
<%-- header --%>
<jsp:include page="/view/template/header.jsp"></jsp:include>


<main>
<%   if(errorMsg != null) { %>
	<%--エラーがあった場合 --%>
	<h3>職員情報入力エラー</h3>
	<p><%= errorMsg %></p>

	<a href="/StaffChange?value=reChangeInputByError"><button>入力画面へ戻る</button></a>
	<a href="/StaffEdit?value=backFromConfirmByError"><button>職員情報編集画面へ戻る</button></a>
<% } else { %>
	<%-- エラーがなく問題ない場合 --%>
	<h3>職員情報 変更確認</h3>

<p>ユーザーID：<%= strStaffId %></p>
<p>変更前の職員名：<%= editStaff.getStaffName() %></p>
<p>変更前の管理者権限：
	<% if(editStaff.getAuthority().equals("YES")){ %>
			有
	<% }else { %>
		    無
	<% }%>
</p>
<p>変更後の職員名：<%= changeStaff.getStaffName() %></p>
<p>変更後の管理者権限：
	<% if(changeStaff.getAuthority().equals("YES")){ %>
			有
	<% }else { %>
		    無
	<% }%>
</p>

<p>上記の内容で職員情報を変更してもよろしいですか？</p>

<p><a href="/StaffChange?value=changeAction"><button>変更</button></a></p>

<p><a href="/StaffChange?value=reChangeInput"><button>入力画面へ戻る</button></a></p>
<p><a href="/StaffEdit?value=backFromConfirm"><button>職員情報編集画面へ戻る</button></a></p>
<% } %>
<p><a href="/MenuController?value=fromStaffChangeConfirm"><button>メニューへ戻る</button></a></p>
</main>

</body>
</html>