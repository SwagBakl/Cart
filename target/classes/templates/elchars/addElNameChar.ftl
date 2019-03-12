<#include "../parts/security.ftl">
<#import "../parts/common.ftl" as c>

<@c.page>

<div class="col-sm-8 add-section">
    <div>
        <div>
            <h3 class="display orange">Добавление наименования характеристики</h3>
            <form method="post" action="/elchars/elCharNameList" enctype="multipart/form-data" class="col-12" id="add_form">
                <div class="form-group">
                    <input type="text" name="charName" placeholder="Введите наименование характеристики" class="shadow-lg form-control ${(charNameError??)?string('is-invalid', '')}">
            <#if charNameError??>
        <div class="invalid-feedback">
            ${charNameError}
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