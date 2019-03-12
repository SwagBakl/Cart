<#include "../parts/security.ftl">
<#import "../parts/common.ftl" as c>
<#import "../parts/pager.ftl" as p>
<@c.page>
<center>
<div class="col-sm-8 add-section shadow-lg mt-5">
    <div>
        <h3 class="display ">Добавление планшета</h3>
        <div>
            <form method="post" action="/tab/tabList" enctype="multipart/form-data" class="col-12" id="add_form">
                <div class="form-group">
                    <input type="text" name="model" placeholder="Введите модель" class="shadow-lg form-control ${(modelError??)?string('is-invalid', '')}">
            <#if modelError??>
        <div class="invalid-feedback">
            ${modelError}
        </div>
            </#if>
                </div>
                <div class="form-group">
                    <input type="text" name="price" placeholder="Введите цену" class="shadow-lg form-control ${(priceError??)?string('is-invalid', '')}">
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
        <span class="btn btn-default btn-file shadow-lg ">
        Выберите файл <input type="file" id="file" onchange="return fileValidation()" name="file">
        </span>
                </div>
                 <#if error??>
                    <div class="alert alert-danger col-5">
                        ${error}
                    </div>
                 </#if>
                <div class="form-group" id="imagePreview">

                </div>
                <input type="hidden" name="_csrf" value="${_csrf.token}">
                <div class="d-inline-flex">
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
            </form>
        </div>
    </div>
</div>
</center>
    </@c.page>