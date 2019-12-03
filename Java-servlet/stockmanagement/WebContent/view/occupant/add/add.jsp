<%@ page contentType="text/html; charset=UTF-8"%>
<%-- ログインのセッションスコープを取得 --%>
<%@ page import = "beans.Staff" %>
<%
Staff loginUser = (Staff)session.getAttribute("loginUser");
%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>入居者情報新規登録</title>
<meta name="viewport" content="width=device-width initial-scale=1">
<link rel = "stylesheet" href="/css/styles.css">
</head>

<body>

<%-- header --%>
<jsp:include page="/view/template/header.jsp"></jsp:include>

<%-- 入居者情報新規登録 --%>
<main>
<h3>入居者情報新規登録</h3>
<form action ="/OccupantAdd?value=addConfirm" method = "post">
<p>登録する入居者</p>
<p>入居者名：(30字以内)<input type= "text" name="occupantName"  minlength="1" maxlength = "30"></p>
<p>
入居フロア
<input type ="radio" value= "1" name = "floorId" onclick = "addFloor();" >1F
<input type ="radio" value= "2" name = "floorId" onclick = "addFloor();">2F
<input type ="radio" value= "3" name = "floorId" onclick = "addFloor();">3F
<input type ="radio" value= "nonFloor" style="display:none;" checked="checked" >
</p>

	<div id ="client">
	<p>居室番号</p>
				<div id="select1">
				<select name = "roomNumber1">
				<% for(int x = 101; x < 161 ; x++) { %>
				 <% if( (x-4) % 10 == 0 || (x-9) % 10 == 0) {
					 continue;
				 	}
				 %>
				 <option value="<%= x %>">
					<%= x  %>
				 </option>
				<% } %>
				</select>
				</div>

				<div id="select2">
				<select name = "roomNumber2">
				<% for(int x = 201; x < 261 ; x++) { %>
				 <% if( (x-4) % 10 == 0 || (x-9) % 10 == 0) {
					 continue;
				 	}
				 %>
				 <option value="<%= x %>">
					<%= x  %>
				 </option>
				<% } %>
				</select>
				</div>

				<div id="select3">
				<select name = "roomNumber3">
				<% for(int x = 301; x < 361 ; x++) { %>
				 <% if( (x-4) % 10 == 0 || (x-9) % 10 == 0) {
					 continue;
				 	}
				 %>
				 <option value="<%= x %>">
					<%= x  %>
				 </option>
				<% } %>
				</select>
				</div>

	 </div>

<button>登録内容確認</button>
</form>
<a href="/MenuController"><button>メニューへ戻る</button></a>
</main>

<script>
document.getElementById('select1').style.display = "none";
document.getElementById('select2').style.display = "none";
document.getElementById('select3').style.display = "none";

function addFloor(){
	   const name = document.getElementsByName('floorId');
	   if(name[0].checked){
		   document.getElementById('select1').style.display = "block";
		   document.getElementById('select2').style.display = "none";
		   document.getElementById('select3').style.display = "none";
	   }else if(name[1].checked){
		   document.getElementById('select1').style.display = "none";
		   document.getElementById('select2').style.display = "block";
		   document.getElementById('select3').style.display = "none";
	   }else if(name[2].checked){
		   document.getElementById('select1').style.display = "none";
		   document.getElementById('select2').style.display = "none";
		   document.getElementById('select3').style.display = "block";
   	   }
}

</script>
<script src="/js/occupant.js"></script>
</body>
</html>