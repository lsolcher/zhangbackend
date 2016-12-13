<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<body>
	<h1>${headerMessage}</h1>
	<h3>STUDENT ADMISSION FORM FOR ENGINEERING COURSES</h3>


	<form action="/ZhangProject/submitAdmissionForm.html"
		method="post">
		<table>
			<tr>
				<td>Student's Name :</td>
				<td><input type="text" name="studentName" /></td>
			</tr>
			<tr>
				<td>Student's Hobby :</td>
				<td><input type="text" name="studentHobby" /></td>
			</tr>
			<tr>
				<td>Student's Mobile :</td>
				<td><input type="text" name="studentMobile" /></td>
			</tr>
			<tr>
				<td>Student's DOB :</td>
				<td><input type="text" name="studentDOB" /></td>
			</tr>

			<tr>
				<td>Student's Skills set :</td>
				<td><select name="studentSkills" multiple>
						<option value="Java Core">Java Core</option>
						<option value="Spring Core">Spring Core</option>
						<option value="Spring MVC">Spring MVC</option>
				</select>
				<td>
			</tr>
		</table>
		<table>
			<tr>
				<td>Student's Address :</td>
			</tr>
			<tr>
				<td>country: <input type="text" name="studentAddress.country" /></td>
				<td>city: <input type="text" name="studentAddress.city" /></td>
				<td>street: <input type="text" name="studentAddress.street" /></td>
				<td>pincode:<input type="text" name="studentAddress.pincode" /></td>
			</tr>

			<tr>
				<td><input type="submit"
					value="Submit this form by clicking here" /></td>
			</tr>
		</table>

	</form>

</body>
</html>

