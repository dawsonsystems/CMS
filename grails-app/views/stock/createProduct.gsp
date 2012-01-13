<%@ page import="uk.co.devooght.stock.Product" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'product.label', default: 'Product')}"/>
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
    <g:hasErrors bean="${productInstance}">
        <div class="errors">
            <g:renderErrors bean="${productInstance}" as="list"/>
        </div>
    </g:hasErrors>
    <g:form action="saveProduct">
        <div class="dialog">
            <table>
                <tbody>

                <tr class="prop">
                    <td valign="top" class="name">
                        <label for="name"><g:message code="product.name.label" default="Name"/></label>
                    </td>
                    <td valign="top" class="value ${hasErrors(bean: productInstance, field: 'name', 'errors')}">
                        <g:textField name="name" value="${productInstance?.name}"/>
                    </td>
                </tr>
                <tr class="prop">
                    <td valign="top" class="name">
                        <label for="costPrice"><g:message code="product.costPrice.label" default="Description"/></label>
                    </td>
                    <td valign="top" class="value ${hasErrors(bean: productInstance, field: 'description', 'errors')}">
                        <g:textArea name="description" cols="30" rows="5" value="${fieldValue(bean: productInstance, field: 'description')}"></g:textArea>
                    </td>
                </tr>
                <tr class="prop">
                    <td valign="top" class="name">
                        <label for="costPrice"><g:message code="product.costPrice.label" default="Cost Price"/></label>
                    </td>
                    <td valign="top" class="value ${hasErrors(bean: productInstance, field: 'costPrice', 'errors')}">
                        <g:textField name="costPrice" value="${fieldValue(bean: productInstance, field: 'costPrice')}"/>
                    </td>
                </tr>
                <tr class="prop">
                    <td valign="top" class="name">
                        <label for="name"><g:message code="product.name.label" default="Quantity Available"/></label>
                    </td>
                    <td valign="top" class="value ${hasErrors(bean: productInstance, field: 'quantity', 'errors')}">
                        <g:textField name="quantity" value="${productInstance?.quantity}"/>
                    </td>
                </tr>
                <tr class="prop">
                    <td valign="top" class="name">
                        <label for="name"><g:message code="product.name.label" default="Product Code"/></label>
                    </td>
                    <td valign="top" class="value ${hasErrors(bean: productInstance, field: 'productCode', 'errors')}">
                        <g:textField name="productCode" value="${productInstance?.productCode}"/>
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
