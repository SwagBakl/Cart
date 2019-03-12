<#import "../parts/common.ftl" as c>
<#include "../parts/security.ftl">
<@c.page>


<div class="col-3" id="add_form shadow-lg">
    <h3 class="display">Добавление характеристик планшета<br> <b>${model}</b></h3>
    <#if isAdmin>
    <form method="post" action="/tab/${id}/characts">
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
        <div class="d-inline-flex">
            <div class="d-inline-flex mb-2">
                <button type="submit" class="btn btn-warning shadow-lg d-inline-flex">Добавить
                    <i class="material-icons ml-1">
                        add_box
                    </i>
                </button>
                <a class="btn btn-warning shadow-lg ml-1 d-inline-flex" href="/tab/tabList">Отмена
                    <i class="material-icons ml-1">
                        cancel
                    </i>
                </a>
            </div>
        </div>
    </form>
    </#if>
</div>

   
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
            <#list tabChars as char>
            <tr>
                <td><b>${char.name}</b></td>
                <td><b>${char.value}</b></td>
                <#if isAdmin>
                <td><a href="/tabCharact/${char.id}" class="btn btn-warning  shadow-lg">
                    <span class="d-inline-flex">Редактировать
                    <i class="material-icons ml-2">
                        edit
                    </i></span>
                </a></td>
                <td><a href="/tab/characts/${char.id}/delete" class="btn btn-warning  shadow-lg">
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