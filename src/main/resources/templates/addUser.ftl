<#import "parts/common.ftl" as c>

<@c.page>
<form action="/addUser" method="post">
    <div><label> User Name : <input type="text" name="username"/> </label></div>
    <div><label> Password: <input type="password" name="password"/> </label></div>
    <input type="hidden" name="_csrf" value="${_csrf.token}">
    <div><input type="submit" value="Add user"/></div>
</form>
</@c.page>