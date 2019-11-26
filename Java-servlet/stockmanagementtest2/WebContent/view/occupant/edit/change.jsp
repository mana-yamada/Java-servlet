<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import = "occupantcontroller.OccupantEdit" %>
<%@ page import = "beans.Occupant" %>
<%-- ログインのセッションスコープを取得 --%>
<%@ page import = "beans.Staff" %>
<%
Staff loginUser = (Staff)session.getAttribute("loginUser");
%>
<%
	//変更前の入居者データ1行を保存したインスタンスを取得
	Occupant editOccupant = (Occupant)session.getAttribute("editOccupant");
%>


<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>入居者情報変更</title>
<meta name="viewport" content="width=device-width initial-scale=1">
<link rel = "stylesheet" href="/stockmanagementtest/css/styles.css">
</head>

<body>

<%-- header --%>
<jsp:include page="/view/template/header.jsp"></jsp:include>

<%-- 入居者情報変更 --%>

<main>
<h3>入居者情報変更</h3>
<p>変更前の入居者名：<%= editOccupant.getOccupantName() %></p>
<p>変更前の入居フロア：<%= editOccupant.getFloorId() %></p>
<p>変更前の居室番号：<%= editOccupant.getRoomNumber() %></p>



<form action ="/stockmanagementtest/OccupantChange?value=changeConfirm" method ="post">

<%--入居者名 --%>
<p>入居者名</p>
<p><input type="radio" name="occupantNameChange" value="unchange" onclick="nameChange2();" >変更なし</p>
<p><input type="radio" name="occupantNameChange" value="change"   onclick="nameChange2();" checked = "checked">変更あり</p>
<p id ="nameForm"><input type= "text" name="occupantName" value="<%= editOccupant.getOccupantName() %>"  maxlength = "30"></p><!-- scriptタグで表示設定 -->

<%--入居フロア  --%>
<p>入居フロア</p>
<p><input type="radio" name="occupantFloorChange" value="unchange" onclick="floorChange();">変更なし</p>
<p><input type="radio" name="occupantFloorChange" value="change"   onclick="floorChange();" checked = "checked">変更あり</p>
<p id ="floorForm" ><input  type="text" name="occupantFloor" value="<%= editOccupant.getFloorId() %>"  maxlength = "7"></p><!-- scriptタグで表示設定 -->

<%--居室番号  --%>
<p>居室番号</p>
<p><input type="radio" name="roomNumberChange" value="unchange" onclick="roomChange();">変更なし</p>
<p><input type="radio" name="roomNumberChange" value="change"   onclick="roomChange();" checked = "checked">変更あり</p>
<p id ="roomNumberForm" ><input  type="text" name="roomNumber" value="<%= editOccupant.getRoomNumber() %>"  maxlength = "7"></p><!-- scriptタグで表示設定 -->


<%-- 確認ボタン --%>
<button>変更内容確認</button>
</form>

<p><a href="/stockmanagementtest/OccupantEdit?value=backFromChange"><button>編集画面に戻る</button></a></p>
<p><a href="/stockmanagementtest/MenuController?value=fromOccupantEditing"><button>メニューへ戻る</button></a></p>
</main>

<script type="text/javascript" src="/stockmanagementtest/js/occupant.js"></script>

</body>
</html>