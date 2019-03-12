<#import "../parts/common.ftl" as c>
<#include "../parts/security.ftl">
<@c.page>
    <#if isAdmin>
<div class="col-3" id="add_form shadow-lg">
    <h3 class="display">Добавление характеристик телевизора<br> <b>${model}</b></h3>
    <form method="post" action="/fridge/${id}/characts">
        <div class="form-group">
            <select name="name" class="form-control shadow-lg">
            <#list elCharList as elect>
                <option>${elect.charName}</option>
            </#list>
            </select>
        </div>
        <div class="form-group">
            <select name="value" class="form-control shadow-lg">
            <#list elCharValueList as elect>
                <option>${elect.charValue}</option>
            </#list>
            </select>
        </div>
        <input type="hidden" name="id" value="${id}" class="form-control">

        <input type="hidden" name="_csrf" value="${_csrf.token}">
        <button type="submit" class="btn btn-warning form-control shadow-lg">Добавить</button>
    </form>
</div>
    </#if>
<div class="col-8 card-col-sum">
    <table class="table shadow-lg">
        <thead class="thead-dark">
        <tr>
            <th>*</th>
            <th>*</th>
            <#if isAdmin>
            <th>Редактировать</th>
            <th>Удалить</th>
            </#if>
        </tr>
        </thead>
            <#list fridgeChars as char>
            <tr>
                <td><b>${char.name}</b></td>
                <td><b>${char.value}</b></td>
                <#if isAdmin>
                <td><a href="/fridge/charact/${char.id}" class="btn btn-warning shadow-lg">
                    <span class="d-inline-flex">Редактировать
                    <i class="material-icons ml-2">
                        edit
                    </i></span>
                </a></td>
                <td><a href="/fridge/charact/delete/${char.id}" class="btn btn-warning shadow-lg">
                    <span class="d-inline-flex">Удалить
                    <i class="material-icons ml-2">
                        delete_forever
                    </i></span>
                </a></td>
                </#if>
            </tr>
            </#list>
    </table>
</div>
</@c.page>