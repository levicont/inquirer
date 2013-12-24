<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

	<div class="loginForm">
	<p class="error">
	 <jsp:include  page="modules/validMessage.jsp"/>
	 </p>
		<form action="<%=request.getContextPath()%>/login.php" method="post">
			<table class="formTable">				
				<tr>
					<td>
						Login:
					</td>
					<td><input title="Login:" type="text" name="username" /></td>
				</tr>
				<tr>
					<td>
						Password:
					</td>
					<td><input title="Password: " type="password" name="password" />
					</td>
				</tr>
				<tr>
				<td>Select role</td>
				<td><select name="role" id="role">
						<option value="1">Administrator</option>
						<option value="2">Advanced tutor</option>
						<option value="3">Tutor</option>
						<option value="4">Student</option>
				</select></td>
			</tr>
				<tr>
					<td><input type="submit" value="Login" /></td>
				</tr>
			</table>
		</form>
		<p>
		<a href="${CONTEXT }/register.php">Registration</a>
		<a href="${CONTEXT }/recover.php">Recover</a>
		</p>
	</div>
