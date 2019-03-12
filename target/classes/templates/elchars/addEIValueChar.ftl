<#include "../parts/security.ftl">
<#import "../parts/common.ftl" as c>

<@c.page>

<div class="col-sm-8 add-section">
    <div>
        <h3 class="display orange">Добавление наименования характеристики</h3>
        <div>
            <form method="post" action="/elchars/elCharValueList" enctype="multipart/form-data" class="col-12" id="add_form">
                <div class="form-group">
                    <input type="text" name="charValue" placeholder="Введите наименование характеристики" class="shadow-lg form-control ${(charValueError??)?string('is-invalid', '')}">
            <#if charValueError??>
        <div class="invalid-feedback">
            ${charValueError}
        </div>
            </#if>
                </div>
        </div>
        <input type="hidden" name="_csrf" value="${_csrf.token}">
        <button type="submit" class="btn btn-warning shadow-lg">Добавить</button>
        </form>
    </div>
</div>
</@c.page>