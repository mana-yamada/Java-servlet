<%@ page language = "java" contentType = "text/html; charset = UTF-8" pageEncoding= "UTF-8" %>

<%-- import --%>
<%@ page import = "java.util.Date" %>
<%@ page import = "java.text.SimpleDateFormat" %>

<%-- javaの実装部分 --%>
<%
	//getTime
	Date date= new Date();
	SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日HH時mm分");
	String strDate = sdf.format(date);

%>
<%-- HTMLを出力  --%>
<!DOCTYPE html>
<html lang = "ja">
<head>
<meta charset="UTF-8">
<title>現在時刻</title>
<meta name="viewport" content="width=device-width initial-scale=1">
</head>
<body>
<p>只今の時刻：<%= strDate %></p>
</body>
</html>
