<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html>
<head>
    <c:set var="url">${pageContext.request.requestURL}</c:set>
    <base href="${fn:substring(url, 0, fn:length(url) - fn:length(pageContext.request.requestURI))}${pageContext.request.contextPath}/"/>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="author" content="Hendra Tommy Wijaya">
    <meta name="company" content="Java Studio Garage Project ID">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <meta name="description" content="spring-oauth2-jwt">
    <title>
        <tiles:insertAttribute name="title" ignore="true"/>
    </title>
    <link rel="shortcut icon" href="">
    <link rel="shortcut icon" type="image/x-icon" href="<c:url value="/assets/images/favicon.ico" />">
    <link rel="stylesheet" href="<c:url value="/assets/fonts/material-design-icons/material-icons.css" />">
    <link rel="stylesheet" href="<c:url value="/assets/vendor/material.min.css"/> ">
    <link rel="stylesheet" href="<c:url value="/assets/main/styles.css"/>">
</head>
<body>
<noscript>
    You need to enable JavaScript to run this app.
</noscript>
<tiles:insertAttribute name="page"/>
<script src="<c:url value="/assets/vendor/material.min.js"/>"></script>
</body>
</html>