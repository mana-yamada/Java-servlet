<%@ page contentType = "text/html;charset=UTF-8"%>

<%@ page import = "java.util.Map" %>
<%@ page import = "java.util.LinkedHashMap" %>

<%
	//LinkedHashMapを作成⇒ポケモンの名前・体力を格納する
	Map<String, Integer> pokemons = new LinkedHashMap<String, Integer>();
	pokemons.put("グラードン", 250);
	pokemons.put("カイオーガ", 250);
	pokemons.put("レックウザ", 280);

%>

<%-- HTML出力 --%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>伝説のポケモン</title>
<meta name="viewport" content="width=device-width initial-scale=1">
</head>
<body>
<%-- LinkedHashMapに格納したポケモンを全て出力 --%>
<% for(String key: pokemons.keySet()){ %>
	<% Integer value = pokemons.get(key); %>
    <p><%= key %>：<%= value %></p>
<%} %>
</body>
</html>

