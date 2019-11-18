<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="sqloperate.Occupantlist,beans.Occupant, occupantcontroller.OccupantEdit, java.util.ArrayList, java.util.Iterator"%>

<%
//ログインのセッションスコープを取得

//get scope


%>

<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>備品入出庫出力画面</title>
<meta name="viewport" content="width=device-width initial-scale=1">
<link rel = "stylesheet" href="/stockmanagementtest/css/styles.css">
</head>
<body>
<div id="contents">
<%-- header --%>
<jsp:include page="/view/template/header.jsp"></jsp:include>

	<main>
		<h3>備品入出庫</h3>

		<%

		//ArrayListをSQL内で作ってくれるようにする
		ArrayList<Occupant> occupantList = new ArrayList<Occupant>();
	    Occupantlist displayOccupant = new Occupantlist();
		displayOccupant.get(occupantList);

		%>


<script>
document.getElementById('floor1').style.display = "none";
document.getElementById('floor2').style.display = "none";
document.getElementById('floor3').style.display = "none";
</script>
<script type="text/javascript" src="/stockmanagementtest/js/occupant.js"></script>
</body>
</html>