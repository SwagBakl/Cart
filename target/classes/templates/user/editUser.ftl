<#import "../parts/common.ftl" as c>

<@c.page>
    <h3 class="display ">Редактирование пользователя</h3>
    <form action="/user" method="post" class="col-8 shadow-lg pt-2 pb-2">
        <input type="text" name="username" value="${user.username}" class="form-control shadow-lg col-6">
        <input type="text" name="password" value="${user.password}" class="form-control shadow-lg col-6">
        <#list roles as role>
            <div class=" d-inline-flex">
                <label><input type="checkbox" class="form-control shadow-lg" name="${role}" ${user.roles?seq_contains(role)?string("checked", "")}>${role}</label>
            </div>
        </#list>
        <input type="hidden" name="id" value="${user.id}">
        <input type="hidden" name="_csrf" value="${_csrf.token}"><br>
        <button type="submit" class="btn btn-warning shadow-lg">Сохранить</button>
    </form>
</@c.page>