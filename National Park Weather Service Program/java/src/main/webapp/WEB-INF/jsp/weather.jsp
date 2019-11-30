<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="header.jspf"%>
<c:import
	url="module-3/12-Capstone/java/src/main/webapp/WEB-INF/jsp/footer.jspf" />
<!DOCTYPE html>
<head>
<meta charset="UTF-8">
<title>Weather Page -- Will update this with real header</title>
</head>


<c:url var="parkimageurl" value="/img/parks/${park.parkCode.toLowerCase() }.jpg"/>
<a class="park-image-detail"> <img
	src="${parkimageurl}"
	style="width: 400px; height: 250px;" /></a>

<div class="parkinfo" style="width: 400px; padding-left: 10px;">
	<p>
		<c:out value="${park.parkName }"></c:out>
	</p>

	<p>
		<c:out value="${park.parkDescription }"></c:out>
	</p>

</div>

<h3>Please pick which unit you prefer:</h3>
<c:url var="conversionSubmit"
	value="/parkDetail/${park.parkCode}/weather" />
<form method="post" action="${conversionSubmit }">
	<input type="radio" name="convert" value="C">Celsius <input
		type="radio" name="convert" value="F">Fahrenheit <input
		type="submit">
</form>

<h2>5-Day Forecast</h2>
<table>
<c:url var="weatherprefix" value="/img/weather/"/>
<tr id="weather tr">
	<c:forEach items="${weather}" var="dailyForecast">
	<td id="days">
	<img class="weather" src="${weatherprefix}${dailyForecast.forecastImg}" />
	
		<c:choose>
	
	<c:when test="${weatherScale == 'C' }" >
	<div class="high">High: <c:out value="${dailyForecast.highC}" /> C</div>	

				<div class="low">Low:  <c:out value="${dailyForecast.lowC}" /> C</div>
	</c:when>
	<c:otherwise>
	<div class="high">High: <c:out value="${dailyForecast.highF}" /> F</div>	

				<div class="low">Low:  <c:out value="${dailyForecast.lowF}" /> F</div>
	</c:otherwise>
				
			</c:choose>
			
			<c:forEach items="${dailyForecast.conditions }" var="item">


			<c:out value="${item }" />
		</c:forEach>
	</c:forEach>
</tr>
</table>
<br>

<%@include file="footer.jspf"%>
