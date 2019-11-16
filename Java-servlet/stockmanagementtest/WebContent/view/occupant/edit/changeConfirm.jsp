<%@ page contentType="text/html; charset=UTF-8"%>

<%@ page import = "occupantcontroller.OccupantAdd" %>
<%@ page import = "beans.Occupant" %>
<%  //login scope  %>

<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>入居者情報変更確認</title>
<meta name="viewport" content="width=device-width initial-scale=1">
<link rel = "stylesheet" href="/stockmanagementtest/css/styles.css">
</head>

<body>
<%-- header --%>
<header>
<a href="#"><button id="menu">ラックん</button></a>
<h3>ログインユーザー：〇〇〇〇</h3>
<a href ="#"><button id ="logout">ログアウト</button></a>
</header>

<main>

<%   String errorMsg = (String)session.getAttribute("errorMsg"); %>
<%   if(errorMsg != null) { %>
	<%--エラーがあった場合 --%>
	<h3>入居者情報入力エラー</h3>
	<p><%= errorMsg %></p>

	<a href="/stockmanagementtest/OccupantChange?value=reChangeInputByError"><button>入力画面へ戻る</button></a>
	<a href="/stockmanagementtest/OccupantEdit?value=backFromConfirmByError"><button>入居者情報編集画面へ戻る</button></a>
<% } else { %>
	<%-- エラーがなく問題ない場合 --%>
	<h3>入居者情報 変更確認</h3>

<%
	//変更前の入居者データ1行を保存したインスタンスを取得
	Occupant editOccupant = (Occupant)session.getAttribute("editOccupant");
	/*変更後に表示しようとしている入居者名、単価を保存したインスタンスを取得*/
	Occupant changeOccupant = (Occupant)session.getAttribute("changeOccupant");
%>
<p>入居者ID：<%= editOccupant.getOccupantId() %></p>
<p>変更前の入居者名：<%= editOccupant.getOccupantName() %></p>
<p>変更前の入居フロア：<%= editOccupant.getFloorId() %></p>
<p>変更前の居室番号<%= editOccupant.getRoomNumber() %></p>

<p>変更後の入居者名：<%= changeOccupant.getOccupantName() %></p>
<p>変更前の入居フロア：<%= changeOccupant.getFloorId() %></p>
<p>変更前の居室番号<%= changeOccupant.getRoomNumber() %></p>

<p>上記の内容で入居者情報を変更してもよろしいですか？</p>

<p><a href="/stockmanagementtest/OccupantChange?value=changeAction"><button>変更</button></a></p>

<p><a href="/stockmanagementtest/OccupantChange?value=reChangeInput"><button>入力画面へ戻る</button></a></p>
<p><a href="/stockmanagementtest/OccupantEdit?value=backFromConfirm"><button>入居者情報編集画面へ戻る</button></a></p>
<% } %>

</main>

</body>
</html>