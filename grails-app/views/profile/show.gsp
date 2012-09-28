<%@ page import="cms.Profile" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="weceemadmin"/>
    <g:set var="entityName" value="${message(code: 'profile.label', default: 'Profile')}"/>
    <title><g:message code="default.show.label" args="[entityName]"/></title>
</head>

<body>

<div class="body">
    <g:if test="${flash.message}">
        <div class="message">${flash.message}</div>
    </g:if>
    <div class="dialog">
        <table>
            <tbody>

            <tr class="prop">
                <td valign="top" class="name"><g:message code="profile.displayName.label" default="Display Name"/></td>

                <td valign="top" class="value">${fieldValue(bean: profileInstance, field: "displayName")}</td>

            </tr>

            <%--<tr class="prop">
                <td valign="top" class="name"><g:message code="profile.picture.label" default="Picture"/></td>

                <td valign="top" class="value">${fieldValue(bean: profileInstance, field: "picture")}</td>

            </tr>--%>

            <tr class="prop">
                <td valign="top" class="name"><g:message code="profile.email.label" default="Email"/></td>
                <td valign="top" class="value">${fieldValue(bean: profileInstance, field: "email")}</td>
            </tr>

            <%--<tr class="prop">
                <td valign="top" class="name"><g:message code="profile.logins.label" default="Logins"/></td>
                <td valign="top" style="text-align: left;" class="value">
                    <ul>
                        <g:each in="${profileInstance.logins}" var="l">
                            <li><g:link controller="logon" action="show" id="${l.id}">${l?.encodeAsHTML()}</g:link></li>
                        </g:each>
                    </ul>
                </td>

            </tr>--%>

            <tr class="prop">
                <td valign="top" class="name"><g:message code="profile.roles.label" default="Roles"/></td>
                <td valign="top" style="text-align: left;" class="value">
                    <ul>
                        <g:each in="${profileInstance.roles}" var="r">
                            <li>${r?.name?.encodeAsHTML()}</li>
                        </g:each>
                    </ul>
                </td>
            </tr>
            <tr class="prop">
                <td valign="top" class="name"><g:message code="profile.roles.label" default="Authorised spaces"/></td>
                <td valign="top" style="text-align: left;" class="value">
                    <ul>
                        <g:each in="${profileInstance.permittedSpaces}" var="r">
                            <li>${r?.name?.encodeAsHTML()} (http://${r.aliasURI})</li>
                        </g:each>
                    </ul>
                </td>
            </tr>

            </tbody>
        </table>
    </div>
    <shiro:hasPermission permission="user:manage">
        <div class="buttons">
            <g:form>
                <g:hiddenField name="id" value="${profileInstance?.id}"/>
                <span class="button"><g:actionSubmit class="edit" action="edit"
                                                     value="${message(code: 'default.button.edit.label', default: 'Edit')}"/></span>
                <%--<span class="button"><g:actionSubmit class="delete" action="delete"
                                                     value="${message(code: 'default.button.delete.label', default: 'Delete')}"
                                                     onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');"/></span>--%>
            </g:form>
        </div>
        <div class="buttons" style="clear:left; margin-top:10px; border-top:2px solid black;">
            <h3>Reset Password</h3>
            <g:form action="updatePassword">
                <g:hiddenField name="id" value="${profileInstance?.id}"/>
                Password : <g:textField name="password"/><br/>
                <span class="button"><input type="submit" class="save"
                                                     value="Update Password"/></span>
            <%--<span class="button"><g:actionSubmit class="delete" action="delete"
      value="${message(code: 'default.button.delete.label', default: 'Delete')}"
      onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');"/></span>--%>
            </g:form>
        </div>
    </shiro:hasPermission>
</div>
</body>
</html>
