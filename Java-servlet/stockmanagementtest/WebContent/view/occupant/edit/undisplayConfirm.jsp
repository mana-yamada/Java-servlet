<%@ page contentType="text/html; charset=UTF-8"%>

<
<%@ page import = "beans.Occupant" %>

<%
//login scope


%>

<%
	//削除しようとしている入居者データ1行を保存したインスタンスを取得
	Occupant editOccupant = (Occupant)session.getAttribute("editOccupant");
%>

<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>入居者情報削除確認</title>
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

<h3>入居者情報 削除確認</h3>
<p>氏名：<%=  editOccupant.getOccupantName() %></p>
<p>入居フロア：<%= editOccupant.getFloorId() %></p>
<p>居室番号：<%= editOccupant.getRoomNumber() %></p>

<p>上記の内容で入居者情報を削除してもよろしいですか？</p>
<p><a href ="/stockmanagementtest/OccupantUndisplay?value=undisplayComplete"><button>削除</button></a></p>

<p><a href="/stockmanagementtest/OccupantEdit?value=backFromUndisplay"><button>編集画面へ戻る</button></a></p>
<p><a href="#"><button id ="#">メニューへ戻る</button></a></p>
</main>

</body>
</html>