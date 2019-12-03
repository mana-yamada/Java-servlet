<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="sqloperate.Stafflist, beans.Staff,java.util.ArrayList, java.util.Iterator"%>

<%
Staff loginUser = (Staff)session.getAttribute("loginUser");

%>

<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>職員情報編集</title>
<meta name="viewport" content="width=device-width initial-scale=1">
<link rel = "stylesheet" href="/css/styles.css">
</head>
<body>
<div id="contents">
<%-- header --%>
	<jsp:include page="/view/template/header.jsp"></jsp:include>

	<main>
		<h3>職員情報編集</h3>
		<table border="1">
		<tr>
		<th>職員ID</th>
		<th>職員名</th>
		<th>管理者権限</th>
		<th>変更</th>
		<th>削除</th>
		</tr>
		<%
		ArrayList<Staff> staffList = new ArrayList<Staff>();
	    Stafflist operate = new Stafflist();
	    operate.get(staffList);

		for(Staff target : staffList){
			//ArrayList 内でのインデックス番号
			int listNumber = staffList.indexOf(target);
			int staffId = target.getStaffId();
			String strStaffId = String.format("%05d", staffId);
			String kengenn = "";
			if(target.getAuthority().equals("YES")){
				 kengenn = "有";
			}else if(target.getAuthority().equals("NO")){
				 kengenn = "無";
			}

			//DBに格納した1行の各列を取得

		%>
		<tr>
		    <td><%= strStaffId %></td>
		    <td><%= target.getStaffName() %></td>
			<td><%= kengenn %></td>
			<td>
		 	<form action="/StaffChange" method="get">
			 	<input type="hidden" name="staffId" value="<%= strStaffId %>">
			 	<input type="hidden" name="staffName" value="<%= target.getStaffName() %>">
			 	<input type="hidden" name="authority" value="<%= target.getAuthority() %>">
			 	<button id="#">変更</button>
		 	</form>
		 	</td>

		    <td>
		    <form action="/StaffUndisplay" method="get">
				<input type="hidden" name="staffId" value="<%= strStaffId %>">
			 	<input type="hidden" name="staffName" value="<%= target.getStaffName() %>">
			 	<input type="hidden" name="authority" value="<%= target.getAuthority() %>">
			 	<button id="#">削除</button>
		 	</form>

		    </td>
		<%}%>
	    </tr>

		</table>
		<p><a href="/MenuController"><button>メニューへ戻る</button></a></p>
	</main>
</div>


</body>
</html>


