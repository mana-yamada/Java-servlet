<%@ page contentType ="text/html;charset=UTF-8"%>

<%@ page import = "java.util.Random" %>

<%
	//Randomコンストラクタ
	Random random = new Random();
	//1つ目の整数は100～1100の間でランダムに出力
	int r1 = random.nextInt(1000) + 100;
	//2つ目の整数は100～2100の間でランダムに出力
	int r2 = random.nextInt(2000) + 100;
	//3つ目の整数は100～30100の間でランダムに出力
	int r3 = random.nextInt(30000) + 100;

	//3つの整数の合計値を獲得したコインとする
	int sum = r1 + r2 + r3;
%>
<%-- htmlを出力 --%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>ボーナスコインを獲得！</title>
<meta name="viewport" content="width=device-width initial-scale=1">
</head>
<body>
<%-- 獲得したコインを出力 --%>
<p>あなたは<%= sum %>のコインを獲得しました</p>
</body>
</html>

