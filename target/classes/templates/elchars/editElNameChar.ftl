<#import "../parts/common.ftl" as c>

<@c.page>
<center>
    <h3 class="display orange">Редактор характеристки</h3>
    <div class="edit-form col-8 shadow-lg">
        <form action="/elChar/save" method="post">
            <div class="form-group">
                <input type="text" value="${elCharName.charName}" name="charName" placeholder="Введите наименование характеристики" class="shadow-lg form-control ${(charNameError??)?string('is-invalid', '')}">
            <#if charNameError??>
        <div class="invalid-feedback">
            ${charNameError}
        </div>
            </#if>
            </div>
    </div>
            <input type="hidden" name="id" value="${elCharName.id}">
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