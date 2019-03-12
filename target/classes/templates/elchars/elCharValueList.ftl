<#include "../parts/security.ftl">
<#import "../parts/common.ftl" as c>

<@c.page>
<h3 class="display orange">Характеристики электроники</h3>
    <#if isAdmin>
<a class="btn btn-warning shadow-lg d-inline-flex" href="/elchars/addElValueChar">
    Добавить значение характеристики
    <i class="material-icons ml-2">
        add_box
    </i>
</a>
    </#if>
<div class="div_center">
    <form method="get" action="/elchars/elCharValueList" class="form-inline">
        <input type="text" name="filter" value="${filter?ifExists}" class="filter-input form-control col-4" placeholder="Введите название характеристики">
        <button type="submit" class="btn btn-warning shadow-lg d-inline-flex">
            <i class="material-icons mr-2">
                search
            </i>
            Найти
        </button>
    </form>
</div>
<div class="card-col-sum mb-4 shadow-lg col-8">
    <table class="table">
        <thead class="thead-dark">
        <tr>
            <th>Наименование</th>
            <th>Удалить</th>
            <th>Редактировать</th>
        </tr>
        </thead>
        <#list elCharValue as elect>
        <tbody>
        <tr>
            <td><b>${elect.charValue}</b></td>
            <td><a href="/charsValue/${elect.id}" class="btn btn-warning shadow-lg d-inline-flex">Редактировать
                <i class="material-icons ml-2">
                    edit
                </i>
            </a></td>
            <td><a href="/elCharsValue/delete/${elect.id}" class="btn btn-warning shadow-lg d-inline-flex">Удалить
                <i class="material-icons ml-2 ">
                    delete_forever
                </i>
            </a></td>
        </tr>
        </tbody>
        </#list>
    </table>
</div>

</@c.page>