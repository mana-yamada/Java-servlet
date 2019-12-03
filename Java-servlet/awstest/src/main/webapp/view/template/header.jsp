<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="beans.Staff" %>
<% Staff loginUser = (Staff) session.getAttribute("loginUser"); %>
<%-- header --%>
<header>
<a href="/stockmanagementtest/MenuController"><button id="menu">ラックん</button></a>
<h3>ログインユーザー：<%= loginUser.getStaffName() %></h3>
<a href ="/stockmanagementtest/LogoutController"><button id ="logout">ログアウト</button></a>
</header>
<!-- /stockmanagementtest/MenuController -->