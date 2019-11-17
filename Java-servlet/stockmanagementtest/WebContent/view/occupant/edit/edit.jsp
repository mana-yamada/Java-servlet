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
<jsp:include page="/view/template/header.jsp"></jsp:include>

	<main>
		<h3>備品情報編集</h3>

		<table border ="0">
		<tr>
		<th>表示フロア</th>
		<td>
			<select id="changeSelect" name="selectFloor" onchange="entryChange2();">
				<option value="select1">1F</option>
				<option value="select2">2F</option>
				<option value="select3">3F</option>
			</select>
		</td>
		</tr>
		</table>


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

		//ArrayListをSQL内で作ってくれるようにする
		ArrayList<Occupant> occupantList = new ArrayList<Occupant>();
	    Occupantlist displayOccupant = new Occupantlist();
		displayOccupant.get(occupantList);

		%>

		<% for(Occupant target : occupantList){ %>

			<% if(target.getFloorId() == 1){ %>
		    	<tr id ="floor1">
				    <td><%=  occupantList.indexOf(target)  %></td>
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
		   	    </tr>
		  	<% } %>
			<% if(target.getFloorId() == 2){ %>
				<tr id ="floor2">
				    <td><%=  occupantList.indexOf(target)  %></td>
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
		   	    </tr>
		 	<% } %>
			<% if(target.getFloorId() == 3){ %>
				<tr id ="floor3">
				    <td><%=  occupantList.indexOf(target)  %></td>
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
		   	    </tr>
		   <% }%>
	<%}%>
		</table>
	</main>
</div>

<script type="text/javascript" src="/stockmanagementtest/js/occupant.js"></script>
</body>
</html>


