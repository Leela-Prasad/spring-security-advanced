<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>

	<head>
		<title>Newsletter Management</title>
		<link href="<c:url value="/css/styles.css"/>" rel="Stylesheet" type="text/css"/>				
	</head>

	<body>
		<h1>Newsletter Management</h1>
		
		<em>Coming soon - you will be able to import contacts from the award winning "CRM System". 
						We will not need your CRM password to do this!</em>
		
		<form action="http://localhost:8080/crm/oauth/authorize">
			<p>Response type <input type="text" name="response_type" value="code"/></p>
			<p>Client Id <input type="text" name="client_id" value="mailmonkey"/></p>
			<p>Redirect URI <input type="text" name="redirect_uri" value="http://localhost:8080/mailmonkey/import.html"/></p>
			<p>Scope <input type="text" name="scope" value="read"/></p>
			
			<input type="submit"/>		
		</form>
									
									
		<jsp:include page="/footer.jsp"/>												 
	</body>
</html>