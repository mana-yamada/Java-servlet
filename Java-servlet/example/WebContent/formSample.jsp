<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="ja">
<head>

<meta charset="UTF-8">

<title>JSPで作った登録フォームもどき</title>

<%-- スマホやタブレットでも文字が見やすいようにするコード --%>
<meta name="viewport" content="width=device-width initial-scale=1">

</head>
<body>
<%-- 入力フォーム --%>
<form action = "/example/FormSample" method ="post">
<%-- 名前を入力 --%>
<p>名前：<input type ="text" name ="name"></p>
<%-- 性別を入力 --%>
<p>性別：男<input type ="radio" name="gender" value="g1">
		 女<input type ="radio" name="gender" value="g2"></p>
<input type="submit" value="送信">
</form>
</body>
</html>
