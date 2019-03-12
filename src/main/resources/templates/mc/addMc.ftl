<#include "../parts/security.ftl">
<#import "../parts/common.ftl" as c>
<#import "../parts/pager.ftl" as p>
<@c.page>
<center>
    <div class="col-sm-8 add-section shadow-lg mt-5">
        <div>
            <h3 class="display ">Добавление музыкального центра</h3>
            <div>
                <form method="post" action="/mc/mcList" enctype="multipart/form-data" class="col-12" id="add_form">
                    <div class="form-group">
                        <input type="text" name="model" placeholder="Введите модель" class="shadow-lg form-control ${(modelError??)?string('is-invalid', '')}">
            <#if modelError??>
        <div class="invalid-feedback">
            ${modelError}
        </div>
            </#if>
                    </div>
                    <div class="form-group">
                        <input type="number" name="price" placeholder="Введите цену" class="shadow-lg form-control ${(priceError??)?string('is-invalid', '')}">
            <#if priceError??>
        <div class="invalid-feedback">
            ${priceError}
        </div>
            </#if>
                    </div>
                    <div class="form-group">
                        <input  name="quantity" placeholder="Введите количество" class="shadow-lg form-control ${(quantityError??)?string('is-invalid', '')}" >
            <#if quantityError??>
        <div class="invalid-feedback">
            ${quantityError}
        </div>
            </#if>
                    </div>
                    <div class="form-group">
                        <textarea type="text" name="description" placeholder="Введите описание" class="shadow-lg form-control ${(descriptionError??)?string('is-invalid', '')}"></textarea>
            <#if descriptionError??>
        <div class="invalid-feedback">
            ${descriptionError}
        </div>
            </#if>
                    </div>

                    <div class="form-group add-file-button">
        <span class="btn btn-default btn-file shadow-lg">
        Выберите файл <input type="file" name="file" id="file" onchange="return fileValidation()">
        </span>
                    </div>
                     <#if error??>
                    <div class="alert alert-danger col-5">
                        ${error}
                    </div>
                     </#if>
                    <input type="hidden" name="_csrf" value="${_csrf.token}">
                    <button type="submit" class="btn btn-warning shadow-lg">Добавить</button>
                </form>
            </div>
        </div>
    </div>
</center>
</@c.page>