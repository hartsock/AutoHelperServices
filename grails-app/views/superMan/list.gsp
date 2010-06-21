
<%@ page import="groovymag.jobs.SuperMan" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'superMan.label', default: 'SuperMan')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'superMan.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="message" title="${message(code: 'superMan.message.label', default: 'Message')}" />
                        
                            <g:sortableColumn property="errorMessage" title="${message(code: 'superMan.errorMessage.label', default: 'Error Message')}" />
                        
                            <g:sortableColumn property="lastUpdated" title="${message(code: 'superMan.lastUpdated.label', default: 'Last Updated')}" />
                        
                            <g:sortableColumn property="dateCreated" title="${message(code: 'superMan.dateCreated.label', default: 'Date Created')}" />
                        
                            <g:sortableColumn property="completed" title="${message(code: 'superMan.completed.label', default: 'Completed')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${superManInstanceList}" status="i" var="superManInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${superManInstance.id}">${fieldValue(bean: superManInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: superManInstance, field: "message")}</td>
                        
                            <td>${fieldValue(bean: superManInstance, field: "errorMessage")}</td>
                        
                            <td><g:formatDate date="${superManInstance.lastUpdated}" /></td>
                        
                            <td><g:formatDate date="${superManInstance.dateCreated}" /></td>
                        
                            <td><g:formatBoolean boolean="${superManInstance.completed}" /></td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${superManInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
