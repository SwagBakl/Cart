<#import "../parts/common.ftl" as c>

<@c.page>
<center>
    <div class="edit-form col-7 shadow-lg">
        <h3 class="display">Редактор телефона</h3>
        <form action="/cphone/save" method="post" enctype="multipart/form-data">
            <div class="form-group">
                <input type="text" name="name" value="${cellPhone.model}" class="form-control shadow-lg">
            </div>
            <div class="form-group">
                <input type="text" name="price" value="${cellPhone.price}" class="form-control shadow-lg">
            </div>
            <div class="form-group">
                <input type="text" name="quantity" value="${cellPhone.quantity}" class="form-control shadow-lg">
            </div>
            <div class="form-group">
                <textarea  name="description"  class="form-control shadow-lg">${cellPhone.description}</textarea>
            </div>
            <div class="form-group add-file-button">
        <span class="btn btn-default btn-file shadow-lg">
        Выберите файл <input type="file" name="file" id="file" onchange="return fileValidation()">
        </span>
            </div>
            <img src="/img/${cellPhone.filename}" class="card-img-top shadow-lg">
            <input type="hidden" name="id" value="${cellPhone.id}">
            <input type="hidden" name="_csrf" value="${_csrf.token}">
            <div class="d-inline-flex">
            <button type="submit" class="btn btn-warning mt-2 mr-1 shadow-lg d-inline-flex">
                Сохранить
                <i class="material-icons ml-2">
                    save
                </i>
            </button>
                <a class="btn btn-warning shadow-lg ml-1" href="/cphone/phoneList">Отмена</a>
            </div>
        </form>
    </div>
</center>
</@c.page>