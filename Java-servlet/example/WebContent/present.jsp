<%@ page contentType = "text/html; charset=UTF-8" %>
<%-- import --%>
<%@ page import = "java.util.Date" %>
<%@ page import = "java.text.SimpleDateFormat" %>
<%@ page import = "java.util.Random" %>

<%-- javaの実装部分 --%>

<%
	//random
	String[] presents = {"チョコボール", "ハイソフト", "エンゼルパイ" ,"小倉ソフト" , "じまんやき" , "いちごソフト", "カントリーマアム"};
	Random random = new Random();
	int randoms = random.nextInt(6) + 1;
	String present = presents[randoms];

%>
<%-- htmlを出力 --%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>7種から1種をプレゼント</title>
<meta name="viewport" content="width=device-width initial-scale=1">
</head>
<body>
<%-- プレゼントの中身を出力 --%>
<p><%= present %>をプレゼント</p>
</body>
</html>
