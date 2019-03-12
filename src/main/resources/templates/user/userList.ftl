<#import "../parts/common.ftl" as c>

<@c.page>
<div class="m-1">
    <a href="/addUser" class="btn btn-warning shadow-lg d-inline-flex">Добавить пользователя
        <i class="material-icons ml-2">
            person_add
        </i>
    </a>
</div>

<div class="col-8 card-col-sum">
    <table class="table shadow-lg ">
        <tr>
            <th>Логин</th>
            <th>Пароль</th>
            <th>Роль</th>
            <th>Редактировать</th>
            <th>Удалить</th>
        </tr>
        <#list userList as user>
            <tr>
                <td>${user.username}</td>
                <td>${user.password}</td>
                <#list user.roles as role>
                <td>${role}</td>
                </#list>
                <td><a href="/user/${user.id}" class="btn btn-warning  shadow-lg d-inline-flex">Редактировать
                    <i class="material-icons ml-2">
                        edit
                    </i>
                </a></td>
                <td><a href="/user/delete/${user.id}" class="btn btn-warning shadow-lg d-inline-flex">Удалить
                    <i class="material-icons ml-2">
                        delete_forever
                    </i>
                </a></td>
            </tr>
        </#list>
    </table>
</div>
</@c.page>