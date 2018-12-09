<#include "security.ftl">

<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" href="/">SHOP</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item">
                <a class="nav-link" href="/">Home</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/main">Electronic</a>
            </li>
            <#if isAdmin>
            <li class="nav-item">
                <a class="nav-link" href="/userList">UserList</a>
            </li>
            </#if>
        </ul>
        <div class="navbar-text mr-3">${name}</div>
        <form action="/logout" method="post">
            <input type="hidden" name="_csrf" value="${_csrf.token}">
            <button type="submit" class="btn btn-primary">Sign out</button>
        </form>
    </div>
</nav>