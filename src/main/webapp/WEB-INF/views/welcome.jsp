<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="mdl-layout mdl-js-layout login-layout">
    <div class="login-form-card mdl-card mdl-shadow--2dp">
        <div class="mdl-card__title">
            <div>
                <div class="login-form-company">
                    <img class="login-form-company-logo" alt="logo"
                         src="<c:url value="/assets/images/abstergo-industries-logo.png"/>"/>
                    <h2 class="mdl-card__title-text login-form-company-name">Abstergo</h2>
                </div>
                <h2 class="mdl-card__title-text login-form-title">Welcome,</h2>
                <h3 class="mdl-card__subtitle-text"><c:out value="${principal.name}" /></h3>
            </div>
        </div>
    </div>
</div>