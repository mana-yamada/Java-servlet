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
<title>備品情報編集</title>
<meta name="viewport" content="width=device-width initial-scale=1">
<link rel = "stylesheet" href="/stockmanagementtest/css/styles.css">
</head>
<body>
<div id="contents">
<%-- header --%>
	<header>
		<a href="#"><button id="menu">ラックん</button></a>
		<h3>ログインユーザー：〇〇〇〇</h3>
		<a href ="#"><button id ="logout">ログアウト</button></a>
	</header>
	<main>
		<h3>備品情報編集</h3>
		<table border="1">
		<tr>
		<th>ArrayList Id</th>
		<th>Id</th>
		<th>入居フロア</th>
		<th>居室番号</th>
		<th>氏名</th>
		<th>表示設定</th>
		<th>変更</th>
		<th>削除</th>

		</tr>

		<%
		ArrayList<Occupant> occupantList = new ArrayList<Occupant>();
	    Occupantlist displayOccupant = new Occupantlist();
		displayOccupant.get(occupantList);

		for(Occupant target : occupantList){
			//ArrayList 内でのインデックス番号
			int listNumber = occupantList.indexOf(target);

			//DBに格納した1行の各列を取得
			int occupantId = target.getOccupantId();
			int floorId = target.getFloorId();
			int roomNumber = target.getRoomNumber();
			String occupantName = target.getOccupantName();
			String display = target.getDisplay();
		%>
		<tr>
		    <td><%= listNumber  %></td>
			<td><%= target.getOccupantId() %></td>
			<td><%= target.getFloorId() %>
			<td><%= target.getRoomNumber() %></td>
		 	<td><%= target.getOccupantName() %></td>
		 	<td><%= target.getDisplay() %></td>

		 	<td>
		 	<form action="/stockmanagementtest/OccupantChange" method="get">
			 	<input type="hidden" name="occupantId" value="<%= target.getOccupantId() %>">
		 		<input type="hidden" name="floorId" value="<%= target.getFloorId() %>">
			 	<input type="hidden" name="roomNumber" value="<%= target.getRoomNumber() %>">
			 	<input type="hidden" name="occupantName" value="<%= target.getOccupantName() %>">
			 	<button id="#">変更</button>
		 	</form>
		 	</td>

		    <td>
		    <form action="/stockmanagementtest/OccupantUndisplay" method="get">
			 	<input type="hidden" name="occupantId" value="<%= target.getOccupantId() %>">
		 		<input type="hidden" name="floorId" value="<%= target.getFloorId() %>">
			 	<input type="hidden" name="roomNumber" value="<%= target.getRoomNumber() %>">
			 	<input type="hidden" name="occupantName" value="<%= target.getOccupantName() %>">
			 	<button id="#">削除</button>
		 	</form>

		    </td>
		<%}%>
	    </tr>

		</table>
	</main>
</div>


</body>
</html>


<%-- section
<section>
</section>
--%>


<%-- footer
<footer>
		<p>@rakkun kaigo_se Inc.</p>
	</footer>
 --%>


