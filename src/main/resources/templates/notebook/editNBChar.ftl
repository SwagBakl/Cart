<#import "../parts/common.ftl" as c>
<@c.page>
<center>
    <div class="edit-form col-7 shadow-lg">
        <h3 class="display">Редактор характеристик ноутбука</h3>
        <form action="/notebook/characts/${nbChars.noteBook.id}/save" method="post">
            <div class="form-group">
                <select name="name" class="form-control shadow-lg">
            <#list elcharsName as elect>
                <option>${elect.charName}</option>
            </#list>
                </select>
            </div>
            <div class="form-group">
                <select name="value" class="form-control shadow-lg">
            <#list elcharsValue as elect>
                <option>${elect.charValue}</option>
            </#list>
                </select>
            </div>
            <input type="hidden" name="id" value="${nbChars.id}">
            <input type="hidden" name="tab_id" value="${nbChars.noteBook.id}">
            <input type="hidden" name="_csrf" value="${_csrf.token}">
            <button type="submit" class="btn btn-warning mt-2 shadow-lg d-inline-flex">
                Сохранить
                <i class="material-icons ml-2">
                    save
                </i>
            </button>
        </form>
    </div>
</center>
</@c.page>