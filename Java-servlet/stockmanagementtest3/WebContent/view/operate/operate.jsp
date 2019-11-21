<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="sqloperate.Occupantlist,beans.Occupant,sqloperate.Stafflist,beans.Staff, sqloperate.Goodslist, beans.Goods, java.util.ArrayList, java.util.Iterator"%>

<%
//ログインのセッションスコープを取得

//get scope


%>

<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>備品入出庫入力画面</title>
<meta name="viewport" content="width=device-width initial-scale=1">
<link rel = "stylesheet" href="/stockmanagementtest/css/styles.css">
</head>
<body>

<%-- header --%>
<jsp:include page="/view/template/header.jsp"></jsp:include>

	<main>
	<h3>備品入出庫</h3>

	<form action ="/stockmanagementtest/OperateController" method = "get">


		<div id = "recordDate">
		<p><label>日時
			<select name = "year">
			<% for(int year = 2019; year < 2025 ; year++) {%>
			<option value = "<%= year %>"><%= year %></option>
			<% } %>
			</select>
			年
			<select name ="month">
			<% for(int month = 0; month < 12 ; month++) {%>
			<option value ="<%= month + 1 %>"><%= month + 1 %></option>
			<% } %>
			</select>
			月
			<select name = "day">
			<% for(int day = 0; day < 31 ; day++) {%>
			<option value = "<%= day + 1 %>"><%= day + 1 %></option>
			<% } %>
			</select>
			日
			<select name = "hour">
			<% for(int hour = -1; hour < 23 ; hour++) {%>
			<option value = "<%= hour + 1 %>"><%= hour + 1 %></option>
			<% } %>
			</select>
			時
			<select name = "minute">
			<% for(int minute = 0; minute < 12 ; minute++) {%>
			<option value = "<%= minute * 5 %>"><%= minute * 5 %></option>
			<% } %>
			</select>
			分
		</label></p>
		</div>

		<div id = "staff">
		<p><label>担当者
			<select name = "staff">
			<%ArrayList<Staff> staffList = new ArrayList<Staff>();
		      Stafflist operate = new Stafflist();
		      operate.get(staffList); %>
			<% for(Staff target : staffList){%>
				<option value = "<%= target.getStaffId()%>"><%= target.getStaffName() %></option>
			<% } %>
			</select>
		</label></p>
		</div>

		<div id = "bihin">
		<p>備品名
		<select name = "goods">
		<%	ArrayList<Goods> goodsList = new ArrayList<Goods>();
	    	Goodslist displayGoods = new Goodslist();
			displayGoods.get(goodsList);    %>
		<%for(Goods target : goodsList){%>
			<option value = "<%= target.getGoodsId()%>"><%= target.getGoodsName() %> 残数：<%= target.getStock() %></option>
		<%}%>
		</select>
		</p>
		</div>

		<div id = "stockoperate">
		<p>入出庫
		<input type="radio" name = "sheds" value = "insheds"  onclick="inStock();" >入庫
		<input type="radio" name = "sheds" value = "outsheds" onclick="outStock();"  >出庫
		<input type="radio" name = "sheds" value = "nonsheds" style = "display:none" checked = "checked">
		</p>
		</div>


		<div id = "caseIn">
		<p>入庫数
		<select name="countIn">
		<% for(int x = 0; x < 50; x++){ %>
		<option value = "<%= x + 1 %>"><%= x + 1 %></option>
		<% } %>
		</select>
		</p>
		</div>

		<div id = "caseOut">
		<p>出庫数
		<select name="countOut" >
		<% for(int x = 0; x < 10; x++){ %>
		<option value = "<%= x + 1 %>"><%= x + 1 %></option>
		<% } %>
		</select>
		</p>
		</div>


		<!-- 使用場所 -->
		<div id = "outInfo">
			<!-- 使用場所詳細① 共用部 or 居室  ボタン(出庫時のみ表示) -->
			<div id ="space">
			<p>使用場所

			<input type = "radio" name = "space" value ="shareSpace" onclick="share();">共用部
			<input type = "radio" name = "space" value ="possSpace" onclick="poss();">利用者居室
			<input type = "radio" name = "space" value = "nonSpace" style = "display:none;" checked = "checked">
			</p>
			</div>

			<!-- 使用場所詳細② どこのフロア -->
			<div id="where">
			<p>対象者</p>
			<input type = "radio" name= "floor" value = "1" onclick ="floor1()">1F
			<input type = "radio" name= "floor" value = "2" onclick ="floor2()">2F
			<input type = "radio" name= "floor" value = "3" onclick ="floor3()">3F
			<input type = "radio" name= "floor" value = "nonFloor" style = "display:none;" checked = "checked" >
			</div>

			<% ArrayList <Occupant> occupantList = new ArrayList<Occupant>();
			   Occupantlist displayOccupant = new Occupantlist();
			   displayOccupant.get(occupantList);%>

			<div id ="client">
				<!-- 利用者プルダウン① -->
				<div id ="select1">
				<select name = "occupant1">
				<% for(Occupant target: occupantList) { %>
				<%   if(target.getFloorId() == 1 ) { %>
				<option value="<%= target.getOccupantId()%>">
				 <%= target.getRoomNumber() %>
				 <%= target.getOccupantName() %>
				 </option>
				<%   } %>
				<% } %>
				</select>
				</div>


				<!-- 利用者プルダウン② -->
				<div id="select2">
				<select name = "occupant2">
				<% for(Occupant target: occupantList) { %>
				<%   if(target.getFloorId() == 2 ) { %>
				 <option value="<%= target.getOccupantId()%>">
				 <%= target.getRoomNumber() %>
				 <%= target.getOccupantName() %>
				 </option>
				<%   } %>
				<% } %>
				</select>
				</div>

				<!-- 利用者プルダウン③ -->
				<div id="select3">
				<select name = "occupant3">
				<% for(Occupant target: occupantList) { %>
				<%   if(target.getFloorId() == 3 ) { %>
				 <option value = "<%= target.getOccupantId()%>">
				 <%= target.getRoomNumber() %>
				 <%= target.getOccupantName() %>
				 </option>
				<%   } %>
				<% } %>
				</select>
				</div>
			</div>
		</div>


	<button>入力内容確認</button>
	</form>

    <p><a href="#"><button>メニューへ戻る</button></a></p>
	</main>

	<script src="/stockmanagementtest/js/operate.js">
	</script>

</body>
</html>

<%-- include file="/view/operate/date.jsp" --%>
<%-- include file="/view/operate/staff.jsp" --%>
<%-- include file="/view/operate/goods.jsp" --%>