<%@ page import="cms.Profile" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="weceemadmin"/>
    <g:set var="entityName" value="${message(code: 'profile.label', default: 'Profile')}"/>
    <title><g:message code="default.create.label" args="[entityName]"/></title>
</head>

<body>
<div class="nav">
    <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>
    </span>
    <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label"
                                                                           args="[entityName]"/></g:link></span>
</div>

<div class="body">
    <h1><g:message code="default.create.label" args="[entityName]"/></h1>
    <g:if test="${flash.message}">
        <div class="message">${flash.message}</div>
    </g:if>
    <g:hasErrors bean="${profileInstance}">
        <div class="errors">
            <g:renderErrors bean="${profileInstance}" as="list"/>
        </div>
    </g:hasErrors>
    <g:form action="save">
        <div class="dialog">
            <table>
                <tbody>

                <tr class="prop">
                    <td valign="top" class="name">
                        <label for="displayName"><g:message code="profile.displayName.label"
                                                            default="Display Name"/></label>
                    </td>
                    <td valign="top" class="value ${hasErrors(bean: profileInstance, field: 'displayName', 'errors')}">
                        <g:textField name="displayName" value="${profileInstance?.displayName}"/>
                    </td>
                </tr>
                <tr class="prop">
                    <td valign="top" class="name">
                        <label for="email"><g:message code="profile.email.label" default="Email"/></label>
                    </td>
                    <td valign="top" class="value ${hasErrors(bean: profileInstance, field: 'email', 'errors')}">
                        <g:textField name="email" value="${profileInstance?.email}"/>
                    </td>
                </tr>
                <tr class="prop">
                    <td valign="top" class="name">
                        <label for="password"><g:message code="profile.password.label" default="Password"/></label>
                    </td>
                    <td valign="top" class="value">
                        <g:textField name="password"/>
                    </td>
                </tr>

                </tbody>
            </table>
        </div>

        <div class="buttons">
            <span class="button"><g:submitButton name="create" class="save"
                                                 value="${message(code: 'default.button.create.label', default: 'Create')}"/></span>
        </div>
    </g:form>
</div>
</body>
</html>
