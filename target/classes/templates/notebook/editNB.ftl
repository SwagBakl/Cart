<#import "../parts/common.ftl" as c>

<@c.page>
<center>
    <div class="edit-form col-7 shadow-lg">
        <h3 class="display">Редактор ноутбука</h3>
        <form action="/nb/save" method="post" enctype="multipart/form-data">
            <div class="form-group">
                <input type="text" name="name" value="${noteBook.model}" class="form-control shadow-lg">
            </div>
            <div class="form-group">
                <input type="text" name="price" value="${noteBook.price}" class="form-control shadow-lg">
            </div>
            <div class="form-group">
                <input type="text" name="quantity" value="${noteBook.quantity}" class="form-control shadow-lg">
            </div>
            <div class="form-group">
                <textarea  name="description"  class="form-control shadow-lg">${noteBook.description}</textarea>
            </div>
            <div class="form-group add-file-button">
        <span class="btn btn-default btn-file shadow-lg">
        Выберите файл <input type="file" name="file" id="file" onchange="return fileValidation()">
        </span>
            </div>
            <img src="/img/${noteBook.filename}" class="card-img-top shadow-lg">
            <input type="hidden" name="id" value="${noteBook.id}">
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