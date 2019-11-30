<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ include file="header.jspf" %>
<c:import
	url="module-3/12-Capstone/java/src/main/webapp/WEB-INF/jsp/footer.jspf" />
<!DOCTYPE html>


<meta charset="UTF-8">
<!--  <title>National Parks Home Page</title>-->


	<div class="container">
		<c:forEach items="${parks }" var="park">
		
			<c:url value="/parkDetail" var="detailURL"/>		

			<a class="park-image" href="${detailURL}/${park.parkCode}"> <img
				src="img/parks/${park.parkCode.toLowerCase() }.jpg"
				style="width: 400px; height: 250px;" /></a>

			<div class="parkinfo" style="width: 400px; padding-left: 10px;">
				<p>
					<c:out value="${park.parkName }"></c:out>
				</p>

				<p>
					<c:out value="${park.parkDescription }"></c:out>
				</p>

			</div>
		</c:forEach>
	</div>


<%@ include file="footer.jspf" %>
