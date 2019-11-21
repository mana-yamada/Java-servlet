<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="sqloperate.Goodslist, sqloperate.Occupantlist, beans.Goods, beans.Occupant, java.util.ArrayList, java.util.Iterator"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>備品入出庫出力画面</title>
<meta name="viewport" content="width=device-width initial-scale=1">
<link rel = "stylesheet" href="/stockmanagementtest/css/styles.css">
</head>
<body>

<%----%>


<form action = "" method="get">


		<div id = "bihin">
		<p>備品名
		<select name = "goods">
		<%	ArrayList<Goods> goodsList = new ArrayList<Goods>();
	    	Goodslist displayGoods = new Goodslist();
			displayGoods.get(goodsList);    %>
		<%for(Goods target : goodsList){%>
			<option value = "<%= target.getGoodsId()%>"><%= target.getGoodsName() %></option>
		<%}%>
		</select>
		</p>
		</div>

		<div id = "stockoperate">
		<p>入出庫
		<input type="radio" name = "sheds" value = "insheds"  onclick="inStock();" >入庫
		<input type="radio" name = "sheds" value = "outsheds" onclick="outStock();" >出庫
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
			</p>
			</div>

			<!-- 使用場所詳細② どこのフロア -->
			<div id="where">
			<p>対象者</p>
			<input type = "radio" name="floor" value = "1" onclick ="floor1()">1F
			<input type = "radio" name="floor" value = "2" onclick ="floor2()">2F
			<input type = "radio" name="floor" value = "3" onclick ="floor3()">3F
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


<button>確認</button>
</form>

		<script src="/stockmanagementtest/js/operate.js">
		</script>


		</body>
		</html>