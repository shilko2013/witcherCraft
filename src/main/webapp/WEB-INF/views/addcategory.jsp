<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Log in with your account</title>

    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/common.css" rel="stylesheet">

    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>

</head>
<%--@RequestParam("name") String name,
@RequestParam("price") int price,
@RequestParam("weight") double weight,
@RequestParam("description") String description,
@RequestParam("categoryId") long categoryId,
@RequestParam("isAlchemy") boolean isAlchemy,
@RequestParam("image") MultipartFile imageFile--%>
<body>

<div class="container">

    <form action="${contextPath}/api/component/addcategory" method="post">
        <label for="name"></label>
        <input type="text" id="name" name="name" placeholder="Enter name" required>
        <br/>
        <label for="information"></label>
        <input type="text" id="information" name="information" placeholder="Enter information" required>
        <input type="submit" value="Add category">
    </form>

</div>
<!-- /container -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="${contextPath}/resources/js/bootstrap.min.js"></script>
</body>
</html>