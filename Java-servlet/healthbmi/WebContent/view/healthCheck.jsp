<%@ page contentType = "text/html; charset=UTF-8"%>


<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>すっきり健診</title>
<meta name="viewport" content="width=device-width initial-scale=1">
<link rel = "stylesheet" href="/healthbmi/css/style.css">
</head>
<body>
<h1>すっきり健診</h1>
<form action = "/healthbmi/HealthCheck" method="post" >
<p>身長(cm)  <input type = "text" name = "height"></p>
<p>体重(kg)   <input type = "text" name = "weight"></p>
<button type = "submit">診断</button>
</form>
</body>
</html>


