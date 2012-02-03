<%@ page import="org.weceem.content.WcmSpace; cms.Profile" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="weceemadmin"/>
    <g:set var="entityName" value="${message(code: 'profile.label', default: 'Profile')}"/>
    <title><g:message code="default.edit.label" args="[entityName]"/></title>
</head>

<body>
<div class="nav">
    <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>
    </span>
    <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label"
                                                                           args="[entityName]"/></g:link></span>
    <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label"
                                                                               args="[entityName]"/></g:link></span>
</div>

<div class="body">
    <g:if test="${flash.message}">
        <div class="message">${flash.message}</div>
    </g:if>
    <g:hasErrors bean="${profileInstance}">
        <div class="errors">
            <g:renderErrors bean="${profileInstance}" as="list"/>
        </div>
    </g:hasErrors>
    <g:form method="post">
        <g:hiddenField name="id" value="${profileInstance?.id}"/>
        <g:hiddenField name="version" value="${profileInstance?.version}"/>
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
                        <label for="picture"><g:message code="profile.picture.label" default="Picture"/></label>
                    </td>
                    <td valign="top" class="value ${hasErrors(bean: profileInstance, field: 'picture', 'errors')}">
                        <g:textField name="picture" value="${profileInstance?.picture}"/>
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
                        <label for="logins"><g:message code="profile.logins.label" default="Logins"/></label>
                    </td>
                    <td valign="top" class="value ${hasErrors(bean: profileInstance, field: 'logins', 'errors')}">

                        <ul>
                            <g:each in="${profileInstance?.logins?}" var="l">
                                <li><g:link controller="logon" action="show"
                                            id="${l.id}">${l?.encodeAsHTML()}</g:link></li>
                            </g:each>
                        </ul>
                        <g:link controller="logon" action="create"
                                params="['profile.id': profileInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'logon.label', default: 'Logon')])}</g:link>

                    </td>
                </tr>

                <tr class="prop">
                    <td valign="top" class="name">
                        <label for="roles"><g:message code="profile.roles.label" default="Roles"/></label>
                    </td>
                    <td valign="top" class="value ${hasErrors(bean: profileInstance, field: 'roles', 'errors')}">
                        <g:select name="roles" from="${cms.Role.list()}" multiple="yes" optionKey="id" size="5"
                                  value="${profileInstance?.roles*.id}"/>
                    </td>
                </tr>

                <tr class="prop">
                    <td valign="top" class="name">
                        <label for="permittedSpaces"><g:message code="profile.roles.label" default="Spaces"/></label>
                    </td>
                    <td valign="top" class="value ${hasErrors(bean: profileInstance, field: 'permittedSpaces', 'errors')}">
                        <g:select name="permittedSpaces" from="${WcmSpace.list()}" multiple="yes" optionKey="id" optionValue="name" size="5"
                                  value="${profileInstance?.permittedSpaces*.id}"/>
                    </td>
                </tr>

                </tbody>
            </table>
        </div>

        <div class="buttons">
            <span class="button"><g:actionSubmit class="save" action="update"
                                                 value="${message(code: 'default.button.update.label', default: 'Update')}"/></span>
            <span class="button"><g:actionSubmit class="delete" action="delete"
                                                 value="${message(code: 'default.button.delete.label', default: 'Delete')}"
                                                 onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');"/></span>
        </div>
    </g:form>
</div>
</body>
</html>
