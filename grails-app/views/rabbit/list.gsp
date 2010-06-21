
<%@ page import="groovymag.jobs.Rabbit" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'rabbit.label', default: 'Rabbit')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}">Home</a></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                            <g:sortableColumn property="id" title="${message(code: 'rabbit.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="message" title="${message(code: 'rabbit.message.label', default: 'Message')}" />
                        
                            <g:sortableColumn property="errorMessage" title="${message(code: 'rabbit.errorMessage.label', default: 'Error Message')}" />
                        
                            <g:sortableColumn property="lastUpdated" title="${message(code: 'rabbit.lastUpdated.label', default: 'Last Updated')}" />
                        
                            <g:sortableColumn property="dateCreated" title="${message(code: 'rabbit.dateCreated.label', default: 'Date Created')}" />
                        
                            <g:sortableColumn property="completed" title="${message(code: 'rabbit.completed.label', default: 'Completed')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${rabbitInstanceList}" status="i" var="rabbitInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${rabbitInstance.id}">${fieldValue(bean: rabbitInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: rabbitInstance, field: "message")}</td>
                        
                            <td>${fieldValue(bean: rabbitInstance, field: "errorMessage")}</td>
                        
                            <td><g:formatDate date="${rabbitInstance.lastUpdated}" /></td>
                        
                            <td><g:formatDate date="${rabbitInstance.dateCreated}" /></td>
                        
                            <td><g:formatBoolean boolean="${rabbitInstance.completed}" /></td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${rabbitInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
