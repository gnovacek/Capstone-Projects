<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ include file="header.jspf" %>


<h1>Favorite Parks</h1>

<h3>Thanks for filling out our survey!</h3>

<p>Here what others had to say:</p>
	
	
	
	<c:forEach items="${allPosts}" var="post">
		<c:out value="${post.parkName}" /> 
	
		<c:out value="${post.count}" />
		<br>
		<hr>
	</c:forEach>

<%@include file="footer.jspf"%>
