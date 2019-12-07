<%@ page contentType="text/html; charset=UTF-8"%>

<%@ page import = "beans.Staff" %>
<%

%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>初期設定</title>
<meta name="viewport" content="width=device-width initial-scale=1">
<link rel = "stylesheet" href="/stockmanagement/css/styles.css">
</head>

<body>



<%-- 初期設定 --%>
<main>
<p>こちらのシステムを初めて施設で使う際には、この画面から管理者権限の有る職員のユーザーを登録しログインしていただきます。</p>
<h1>初期設定</h1>
<h3>職員情報新規登録</h3>
<form action ="/stockmanagement/SettingController?value=addConfirm" method = "post">

<p>職員名：(30字以内)</p>
<p><input type= "text" name="staffName"  minlength="1" maxlength = "30"></p>

<p>パスワード：8字以上15字以内</p>
<p><input type = "password" name="password" minlength = "8" maxlength = "15"></p>

<p>管理者権限</p>
<p><input type = "radio" name ="authority" value="YES" checked="checked"> 有</p>

<p><button>登録内容確認</button></p>

</form>
</main>

</body>
</html>