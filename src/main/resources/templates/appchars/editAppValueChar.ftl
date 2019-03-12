<#import "../parts/common.ftl" as c>

<@c.page>
<center>
    <h3 class="display">Редактор характеристики</h3>
    <div class="edit-form col-8 shadow-lg">
        <form action="/appCharValue/save" method="post">
            <div class="form-group">
                <input type="text" value="${appCharValue.charValue}" name="charValue" placeholder="Введите наименование характеристики" class="shadow-lg form-control ${(charValueError??)?string('is-invalid', '')}">
            <#if charValueError??>
        <div class="invalid-feedback">
            ${charValueError}
        </div>
            </#if>
            </div>
    </div>
    <input type="hidden" name="id" value="${appCharValue.id}">
    <input type="hidden" name="_csrf" value="${_csrf.token}">
    <button type="submit" class="btn btn-warning shadow-lg d-inline-flex">Сохранить
        <i class="material-icons ml-2">
            save
        </i>
    </button>
    </form>
    </div>
</center>
</@c.page>