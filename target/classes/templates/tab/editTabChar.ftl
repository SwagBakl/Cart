<#import "../parts/common.ftl" as c>
<@c.page>
<center>
    <div class="edit-form col-7 shadow-lg">
        <h3 class="display">Редактор характеристик планшета</h3>
<form action="/tab/characts/${tabChars.tab.id}/save" method="post">
    <div class="form-group">
        <select name="name" class="form-control shadow-lg">
            <#list elNameList as elect>
                <option>${elect.charName}</option>
            </#list>
        </select>
    </div>
    <div class="form-group">
        <select name="value" class="form-control shadow-lg">
            <#list elValueList as elect>
                <option>${elect.charValue}</option>
            </#list>
        </select>
    </div>
    <input type="hidden" name="id" value="${tabChars.id}">
    <input type="hidden" name="tab_id" value="${tabChars.tab.id}">
    <input type="hidden" name="_csrf" value="${_csrf.token}">
    <div class="d-inline-flex">
    <button type="submit" class="btn btn-warning mt-2 shadow-lg d-inline-flex">
        Сохранить
        <i class="material-icons ml-2">
            save
        </i>
    </button>
        <a class="btn btn-warning shadow-lg ml-1 mt-2 d-inline-flex" href="/tab/${tabChars.tab.id}/characts">Отмена
            <i class="material-icons ml-1">
                cancel
            </i>
        </a>
    </div>
</form>
</div>
</center>
</@c.page>