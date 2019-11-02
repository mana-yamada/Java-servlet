<%--jspファイルの設定スニペット --%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="bean.UserBeans" %>
<%
  //使用型 変数名 = (使用する型) session.getAttribute("インスタンス名");
	UserBeans loginUser = (UserBeans)session.getAttribute("loginUser");

%>
<%--indexフォルダ --%>

<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>メニュー画面</title>
<meta name="viewport" content="width=device-width initial-scale=1">
</head>
<body>
<h1>メニュー</h1>
<h3>ユーザーID:<%= loginUser.getUserId() %> </h3>
<h5><a href = "/usersystem3/Logout">ログアウト</a></h5>
<h5><a href = "/usersystem3/Search">ユーザー検索</a></h5>
<h5><a href = "/usersystem3/Remove">ユーザー削除</a></h5>
</body>
</html>


