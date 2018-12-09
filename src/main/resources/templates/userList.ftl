<#import "parts/common.ftl" as c>

<@c.page>
<div>
    <form method="">
        <input type="text" name="filter">
        <button type="submit">Filter</button>
    </form>
</div>
<div>
    <table class="table">
        <tr>
            <th>Id</th>
            <th>Name</th>
            <th>Password</th>
            <th>Role</th>
            <th>Edit</th>
        </tr>
        <#list userList as user>
            <tr>
                <td>${user.id}</td>
                <td>${user.username}</td>
                <td>${user.password}</td>
                <#list user.roles as role>
                <td>${role}</td>
                </#list>
                <td><a href="/user/${user.id}">Edit</a></td>
            </tr>
        </#list>
    </table>
</div>
<a href="/addUser">Add user</a>
</@c.page>