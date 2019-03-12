<#import "../parts/common.ftl" as c>
<@c.page>
<center>
    <div class="edit-form col-7 shadow-lg">
        <h3 class="display">Редактор характеристик фотоаппарата</h3>
        <form action="/photo/characts/${photoChars.photo.id}/save" method="post">
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
            <input type="hidden" name="id" value="${photoChars.id}">
            <input type="hidden" name="tab_id" value="${photoChars.photo.id}">
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