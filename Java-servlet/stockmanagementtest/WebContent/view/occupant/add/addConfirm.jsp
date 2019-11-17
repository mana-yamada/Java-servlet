<%@ page contentType="text/html; charset=UTF-8"%>

<%@ page import = "occupantcontroller.OccupantAdd, beans.Occupant" %>
<%@ page import = "beans.Goods" %>

<%
//login scope


//errorMsgインスタンス取得
String errorMsg = (String) session.getAttribute("errorMsg");


// add.jspで保存したインスタンス取得
Occupant registerOccupant = (Occupant)session.getAttribute("registerOccupant");

%>

<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>入居者情報登録確認</title>
<meta name="viewport" content="width=device-width initial-scale=1">
<link rel = "stylesheet" href="/stockmanagementtest/css/styles.css">
</head>

<body>

<%-- header --%>
<jsp:include page="/view/template/header.jsp"></jsp:include>


<main>
<%--エラーがあったら条件分岐でエラー文を出したい --%>
<% if(errorMsg != null){ %>
	<h3>入居者情報 入力エラー</h3>
	<p><%= errorMsg %></p>
	<a href="/stockmanagementtest/OccupantAdd?value=backFromAddError"><button>入力画面へ戻る</button></a>
<% } else { %>
<%-- エラーがなければ入居者情報 登録確認    floorId, roomNumber ,occupantName--%>
	<h3>入居者情報 登録確認</h3>
	<p>入居フロア：<%= registerOccupant.getFloorId() %></p>
	<p>居室番号：<%= registerOccupant.getRoomNumber() %></p>
	<p>入居者名：<%= registerOccupant.getOccupantName() %></p>
	<a href ="/stockmanagementtest/OccupantAdd?value=addAction"><button>登録</button></a>
	<a href="/stockmanagementtest/OccupantAdd?value=backFromAdd"><button>入力画面へ戻る</button></a>
<% } %>
<a href="#?value=fromaddConfirm"><button>メニューへ戻る</button></a>
</main>

</body>
</html>