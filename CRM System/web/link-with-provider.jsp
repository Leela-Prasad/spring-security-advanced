<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<h1>Do you want to link your ${provider} account with the CRM System?</h1>

<c:set var="email" value="${email}"/>
<c:choose>
  <c:when test="${email != null}">
	<p>You will be creating a new user account for the user with email ${email}</p>
  </c:when>
  <c:otherwise>
	<p>The provider didn't send your email (of course, as a coder we could make other calls to try to get eg their handle or profile picture)</p> 
  </c:otherwise>
</c:choose>

<form:form>
   <input type="submit" value="Link my account to ${provider}"/>
</form:form>