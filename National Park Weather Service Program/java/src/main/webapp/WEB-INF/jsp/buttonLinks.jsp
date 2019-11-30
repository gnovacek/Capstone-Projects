<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<c:url value="/" var="homePage" />
<c:url value="/survey" var="surveyURL" />
<c:url value="/exit" var="exitURL" />

<ul>
	<li class="buttons"><a href="${homePage}">Home</a></li>
	<li class="buttons"><a href="${surveyURL}">Survey</a></li>
	<li class="buttons"><a href="${exitURL}">Exit</a></li>

</ul>




