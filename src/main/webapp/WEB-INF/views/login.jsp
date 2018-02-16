<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<div class="mdl-layout mdl-js-layout login-layout">
    <div class="login-form-card mdl-card mdl-shadow--2dp">
        <div class="mdl-card__title">
            <div>
                <div class="login-form-company">
                    <img class="login-form-company-logo" alt="logo"
                         src="<c:url value="/assets/images/abstergo-industries-logo.png"/>"/>
                    <h2 class="mdl-card__title-text login-form-company-name">Abstergo</h2>
                </div>
                <h2 class="mdl-card__title-text login-form-title">Sign in</h2>
            </div>
        </div>
        <form action="login" method="POST" novalidate>
            <div class="mdl-card__supporting-text">
                <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label is-invalid">
                    <label for="username" class="mdl-textfield__label">User ID</label>
                    <input type="text" id="username" name="username" class="mdl-textfield__input"/>
                    <span class="mdl-textfield__error">Enter a valid User ID</span>
                </div>
                <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label is-invalid">
                    <label for="password" class="mdl-textfield__label">Password</label>
                    <input type="password" id="password" name="password" class="mdl-textfield__input"/>
                    <span class="mdl-textfield__error">Password is required</span>
                </div>
            </div>
            <div class="mdl-card__actions login-form-button">
                <button type="subtmit" class="mdl-button mdl-js-button mdl-js-ripple-effect">Cancel</button>
                <button type="subtmit"
                        class="mdl-button mdl-js-button mdl-button--colored mdl-button--raised mdl-js-ripple-effect">
                    Sign in
                </button>
            </div>
        </form>
    </div>
</div>