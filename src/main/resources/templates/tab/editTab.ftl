<#import "../parts/common.ftl" as c>

<@c.page>
<center>
    <div class="edit-form col-7 shadow-lg">
    <h3 class="display">Редактор планшета</h3>
    <form action="/tab/save" method="post" enctype="multipart/form-data">
        <div class="form-group">
        <input type="text" name="name" value="${tab.model}" class="form-control shadow-lg">
        </div>
        <div class="form-group">
        <input type="text" name="price" value="${tab.price}" class="form-control shadow-lg">
        </div>
        <div class="form-group">
        <input type="text" name="quantity" value="${tab.quantity}" class="form-control shadow-lg">
        </div>
        <div class="form-group">
        <textarea  name="description"  class="form-control shadow-lg">${tab.description}</textarea>
        </div>
        <div class="form-group add-file-button">
        <span class="btn btn-default btn-file shadow-lg">
        Выберите файл <input type="file" name="file" id="file" onchange="return fileValidation()">
        </span>
        </div>
        <img src="/img/${tab.filename}" class="card-img-top shadow-lg">
        <input type="hidden" name="id" value="${tab.id}">
        <input type="hidden" name="_csrf" value="${_csrf.token}">
        <div class="d-inline-flex">
        <button type="submit" class="btn btn-warning mt-2 shadow-lg d-inline-flex">
            Сохранить
            <i class="material-icons ml-2">
                save
            </i>
        </button>
            <a class="btn btn-warning shadow-lg ml-1 mt-2 d-inline-flex" href="/tab/tabList">Отмена
                <i class="material-icons ml-1">
                    cancel
                </i>
            </a>
        </div>
    </form>
    </div>
</center>
</@c.page>