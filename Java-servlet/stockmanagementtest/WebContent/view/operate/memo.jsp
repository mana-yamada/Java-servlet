<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="sqloperate.Goodslist, sqloperate.Occupantlist, beans.Goods, beans.Occupant, goodscontroller.GoodsEdit, java.util.ArrayList, java.util.Iterator"%>

<!-- 使用場所  入庫時は非表示-->

		<!-- 使用場所詳細① 共用部or居室  ボタン(出庫時のみ表示) -->
		<p>使用場所</p>
		<button value ="shareSpace" onclick="share()">共用部</button>
		<button value ="possSpace" onclick="poss()">利用者居室</button>


		<!-- 使用場所詳細② 居室フロア  ボタン「利用者居室」ボタン押されたときのみ表示  divタグでidつける-->
		<p>対象者</p>
		<button name="floor1">1F</button>
		<button name="floor2">2F</button>
		<button name="floor3">3F</button>


		<!-- 使用場所詳細③ 利用者名 プルダウン 「利用者居室」ボタン押されたときのみ表示-->
		<% ArrayList <Occupant> occupantList = new ArrayList<Occupant>();
		   Occupantlist displayOccupant = new Occupantlist();
		   displayOccupant.get(occupantList);%>


		<!-- 利用者プルダウン① -->

		<select id ="select1">
		<% for(Occupant target: occupantList) { %>
		<%   if(target.getFloorId() == 1 ) { %>
		 <option><%= target.getOccupantName() %></option>
		<%   } %>
		<% } %>

		</select>

		<!-- 利用者プルダウン② -->

		<select id="select2">
		<% for(Occupant target: occupantList) { %>
		<%   if(target.getFloorId() == 2 ) { %>
		 <option><%= target.getOccupantName() %></option>
		<%   } %>
		<% } %>
		</select>

		<!-- 利用者プルダウン③ -->
		<select id="select3">

		<% for(Occupant target: occupantList) { %>
		<%   if(target.getFloorId() == 3 ) { %>
		 <option><%= target.getOccupantName() %></option>
		<%   } %>
		<% } %>
		</select>

