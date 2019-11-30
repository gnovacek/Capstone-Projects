<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%@ include file="header.jspf" %>

	<h2>National Park Geek Daily Survey</h2>
	
	<p>Thank you so much for visiting our website. We'd love to hear
		from you. Please complete our survey to let us know your favorite
		park!</p>

	<c:url var="surveyURL" value="/survey" />
	<form:form method="POST" action="${surveyURL }"
		modelAttribute="survey">

		<label for="parkCode">Favorite National Park</label>
		<select name="parkCode">
			<option value="CVNP">Cuyahoga Valley National Park</option>
			<option value="ENP">Everglades National Park</option>
			<option value="GCNP">Grand Canyon National Park</option>
			<option value="GNP">Glacier National Park</option>
			<option value="GSMNP">Great Smoky Mountains National Park</option>
			<option value="GTNP">Grand Teton National Park</option>
			<option value="MRNP">Mount Rainier National Park</option>
			<option value="RMNP">Rocky Mountain National Park</option>
			<option value="YNP">Yellowstone National Park</option>
			<option value="YNP2">Yosemite National Park</option>
		</select>
	
		<div>
			<form:label path="emailAddress">Your Email</form:label>
			<form:input path="emailAddress" placeholder="enter Email" />
			<form:errors path="emailAddress" cssClass="error" />
			<span style="color: red;">*</span>
		</div>
		<br>

		<label for="state">State of residence</label>
		<select name="state">
			<option value="AL">Alabama</option>
			<option value="AK">Alaska</option>
			<option value="AZ">Arizona</option>
			<option value="AR">Arkansas</option>
			<option value="CA">California</option>
			<option value="CO">Colorado</option>
			<option value="CT">Connecticut</option>
			<option value="DE">Delaware</option>
			<option value="DC">District Of Columbia</option>
			<option value="FL">Florida</option>
			<option value="GA">Georgia</option>
			<option value="HI">Hawaii</option>
			<option value="ID">Idaho</option>
			<option value="IL">Illinois</option>
			<option value="IN">Indiana</option>
			<option value="IA">Iowa</option>
			<option value="KS">Kansas</option>
			<option value="KY">Kentucky</option>
			<option value="LA">Louisiana</option>
			<option value="ME">Maine</option>
			<option value="MD">Maryland</option>
			<option value="MA">Massachusetts</option>
			<option value="MI">Michigan</option>
			<option value="MN">Minnesota</option>
			<option value="MS">Mississippi</option>
			<option value="MO">Missouri</option>
			<option value="MT">Montana</option>
			<option value="NE">Nebraska</option>
			<option value="NV">Nevada</option>
			<option value="NH">New Hampshire</option>
			<option value="NJ">New Jersey</option>
			<option value="NM">New Mexico</option>
			<option value="NY">New York</option>
			<option value="NC">North Carolina</option>
			<option value="ND">North Dakota</option>
			<option value="OH">Ohio</option>
			<option value="OK">Oklahoma</option>
			<option value="OR">Oregon</option>
			<option value="PA">Pennsylvania</option>
			<option value="RI">Rhode Island</option>
			<option value="SC">South Carolina</option>
			<option value="SD">South Dakota</option>
			<option value="TN">Tennessee</option>
			<option value="TX">Texas</option>
			<option value="UT">Utah</option>
			<option value="VT">Vermont</option>
			<option value="VA">Virginia</option>
			<option value="WA">Washington</option>
			<option value="WV">West Virginia</option>
			<option value="WI">Wisconsin</option>
			<option value="WY">Wyoming</option>
			<option value="AS">American Samoa</option>
			<option value="GU">Guam</option>
			<option value="MP">Northern Mariana Islands</option>
			<option value="PR">Puerto Rico</option>
			<option value="UM">United States Minor Outlying Islands</option>
			<option value="VI">Virgin Islands</option>
		</select>

	
		<div>
			<input class="form-check-input" type="radio" name="activityLevel"
				id="activityLevel" value="inactive"> <label
				class="form-check-label" for="activityLevel">Inactive</label>
		</div>

		<div>
			<input class="form-check-input" type="radio" name="activityLevel"
				id="activityLevel" value="sedentary"> <label
				class="form-check-label" for="activityLevel">Sedentary</label>
		</div>

		<div>
			<input class="form-check-input" type="radio" name="activityLevel"
				id="activityLevel" value="active"> <label
				class="form-check-label" for="activityLevel">Active</label>
		</div>

		<div>
			<input class="form-check-input" type="radio" name="activityLevel"
				id="activityLevel" value="extremelyActive"> <label
				class="form-check-label" for="activityLevel">Extremely
				Active</label>
		</div>


		<div>
			<input class="btn btn-primary" type="submit" value="Submit" />
		</div>
	</form:form>


<%@include file="footer.jspf" %> 
