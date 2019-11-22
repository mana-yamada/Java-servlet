<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import = "beans.Staff" %>

<%-- ログインのセッションスコープを取得 --%>

<%
	//変更前の職員データ1行を保存したインスタンスを取得
	Staff editStaff = (Staff)session.getAttribute("editStaff");
%>


<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>職員情報変更</title>
<meta name="viewport" content="width=device-width initial-scale=1">
<link rel = "stylesheet" href="/stockmanagementtest/css/styles.css">
</head>

<body>

<%-- header --%>
<jsp:include page="/view/template/header.jsp"></jsp:include>


<%-- 職員情報変更 --%>

<main>
<h3>職員情報変更</h3>
<p>ユーザーID：<%= editStaff.getStaffId() %></p>
<p>変更前の職員名：<%= editStaff.getStaffName() %></p>
<p>変更前の管理者権限：
	<% if(editStaff.getAuthority().equals("YES")){ %>
			有
	<% }else { %>
		    無
	<% }%>
</p>

<form action ="/stockmanagementtest/StaffChange?value=changeConfirm" method ="post">

<%--職員名 --%>
<p>職員名</p>
<p><input type="radio" name="staffNameChange" value="unchange" onclick="nameChange();" >変更なし</p>
<p><input type="radio" name="staffNameChange" value="change"   onclick="nameChange();" checked = "checked">変更あり</p>
<p id ="nameForm"><input type= "text" name="staffName" value="<%= editStaff.getStaffName() %>"  maxlength = "30"></p><!-- scriptタグで表示設定 -->

<%--管理者権限 --%>
<p>変更後の管理者権限</p>
<p><input type = "radio" name ="authority" value="YES"> 有 <input type = "radio" name = "authority" value="NO" checked="checked" > 無</p>

<%-- 確認ボタン --%>
<button>変更内容確認</button>
</form>

<p><a href="/stockmanagementtest/StaffEdit?value=backFromChange"><button>編集画面に戻る</button></a></p>
<p><a href="/stockmanagementtest/MenuController?value=fromStaffEditing"><button>メニューへ戻る</button></a></p>
</main>

<script type="text/javascript" src="/stockmanagementtest/js/staff.js">
</script>

</body>
</html>