<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="sqloperate.Occupantlist,beans.Occupant,sqloperate.Stafflist, beans.Staff, beans.Operating, beans.StrDateTime, beans.Goods, java.util.ArrayList, java.util.Iterator"%>

<%
//ログインのセッションスコープを取得

//get scope

%>

<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>備品入出庫確認画面</title>
<meta name="viewport" content="width=device-width initial-scale=1">
<link rel = "stylesheet" href="/stockmanagementtest/css/styles.css">
</head>
<body>

<%-- header --%>
<jsp:include page="/view/template/header.jsp"></jsp:include>

<main>
<h3>備品入出庫確認画面</h3>
<%
	 Operating operation = (Operating)session.getAttribute("operation");
	 StrDateTime strDateTime = (StrDateTime)session.getAttribute("strDateTime");
	 Staff reStaff = (Staff)session.getAttribute("reStaff");
	 Goods reGoods = (Goods)session.getAttribute("reGoods");
	 Occupant reOccupant = (Occupant)session.getAttribute("reOccupant");


%>
<p id= "recordDate">日時：
<%= strDateTime.getYear() %>年
<%= strDateTime.getMonth()%>月
<%= strDateTime.getDay()%>日
<%= strDateTime.getHour()%>時
<%= strDateTime.getMinute()%>分
</p>

<p id = "staff">担当者：<%= reStaff.getStaffName() %>


<p>入庫 or 出庫：<%= operation.getStrSheds() %></p>
<p>備品名：<%= reGoods.getGoodsName() %></p>
<p>入庫数：<%= operation.getCountIn() %></p>
<p>出庫数：<%= operation.getCountOut() %></p>
<p>使用場所：<%= operation.getStrSpace() %>
<p>居室ならだれの居室？：<%= reOccupant.getRoomNumber() %>  <%= reOccupant.getOccupantName() %></p>

<p>こちらの内容で登録してもよろしいですか？</p>
<p><a href = "/stockmanagementtest/OperateController?value=complete"><button>登録</button></a></p>
<p><a href = "/stockmanagementtest/OperateComtroller?value=backFromComfirm"><button>入力画面へ戻る</button></a></p>
</main>



</body>
</html>