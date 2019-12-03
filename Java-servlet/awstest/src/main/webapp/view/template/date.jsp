<%@ page contentType="text/html; charset=UTF-8"%>

	<p>日時</p>
	<div id = "recordDate">
		<!-- 年-->
		<select>
		<% for(int x = 2019; x < 2025 ; x++) {%>
		<option><%= x %></option>
		<% } %>
		</select>
		年

		<!-- 月 -->
		<select>
		<% for(int x = 0; x < 12 ; x++) {%>
		<option><%= x + 1 %></option>
		<% } %>
		</select>
		月

		<!-- 日 -->
		<select>
		<% for(int x = 0; x < 31 ; x++) {%>
		<option><%= x + 1 %></option>
		<% } %>
		</select>
		日


		<!-- 時-->
		<select>
		<% for(int x = 0; x < 12 ; x++) {%>
		<option><%= x + 1 %></option>
		<% } %>
		</select>
		時

		<!-- 分-->
		<select>
		<% for(int x = 0; x < 12 ; x++) {%>
		<option><%= x * 5 %></option>
		<% } %>
		</select>
		分

	</div>