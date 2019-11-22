<%@ page contentType="text/html; charset=UTF-8"%>
<%-- ログインのセッションスコープを取得 --%>

<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>職員情報登録完了</title>
<meta name="viewport" content="width=device-width initial-scale=1">
<link rel = "stylesheet" href="/stockmanagementtest/css/styles.css">
</head>

<body>

<%-- header --%>
<jsp:include page="/view/template/header.jsp"></jsp:include>


<main>

<%-- 職員情報登録完了 --%>
<h3>職員情報 登録完了</h3>
<p>職員情報の新規登録が完了しました</p>
<p><a href="/stockmanagementtest/StaffAdd"><button>登録を続ける</button></a></p>
<p><a href="/stockmanagementtest/MenuController"><button>メニューへ戻る</button></a></p>
</main>

</body>
</html>