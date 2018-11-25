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
<%--
    @RequestParam("name") String name,
    @RequestParam("price") int price,
    @RequestParam("weight") double weight,
    @RequestParam("description") String description,
    @RequestParam("typeId") long typeId,
    @RequestParam("isAlchemy") boolean isAlchemy,
    @RequestParam("effects") List<String> effects,
    @RequestParam("effectsNames") List<String> effectsNames,
    @RequestParam("image") MultipartFile imageFile
--%>
<body>

<div class="container">

    <form action="${contextPath}/api/thing/add" method="post" enctype="multipart/form-data">
        <label for="name"></label>
        <input type="text" id="name" name="name" placeholder="Enter name" required>
        <br/>
        <label for="price"></label>
        <input type="text" id="price" name="price" placeholder="Enter price" required>
        <br/>
        <label for="weight"></label>
        <input type="text" id="weight" name="weight" placeholder="Enter weight" required>
        <br/>
        <label for="description"></label>
        <input type="text" id="description" name="description" placeholder="Enter description" required>
        <br/>
        <label for="typeId"></label>
        <input type="text" id="typeId" name="typeId" placeholder="Enter typeId" required>
        <br/>
        <label for="isAlchemy"></label>
        <input type="text" id="isAlchemy" name="isAlchemy" placeholder="Enter isAlchemy" required>
        <br/>
        <label for="image"></label>
        <input type="file" id="image" name="image" placeholder="Enter image">
        <br/>
        <label for="effectName1"></label>
        <input type="text" id="effectName1" name="effectsNames" placeholder="Enter effect1">
        <br/>
        <label for="effect1"></label>
        <input type="text" id="effect1" name="effects" placeholder="Enter effectName1">
        <br/>
        <label for="effectName2"></label>
        <input type="text" id="effectName2" name="effectsNames" placeholder="Enter effect2">
        <br/>
        <label for="effect2"></label>
        <input type="text" id="effect2" name="effects" placeholder="Enter effectName2">
        <br/>
        <label for="effectName3"></label>
        <input type="text" id="effectName3" name="effectsNames" placeholder="Enter effect3">
        <br/>
        <label for="effect3"></label>
        <input type="text" id="effect3" name="effects" placeholder="Enter effectName3">
        <br/>
        <input type="submit" value="Add thing">
    </form>

</div>
<!-- /container -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="${contextPath}/resources/js/bootstrap.min.js"></script>
</body>
</html>
