<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="sqloperate.Stafflist, beans.Staff,java.util.ArrayList, java.util.Iterator"%>

		<p>担当者</p>
		<%ArrayList<Staff> staffList = new ArrayList<Staff>();
	      Stafflist operate = new Stafflist();
	      operate.get(staffList); %>

		<select>
		<% for(Staff target : staffList){
			//ArrayList 内でのインデックス番号
			//int listNumber = staffList.indexOf(target);
			%>
			<option><%= target.getStaffName() %></option>
		<% } %>
		</select>