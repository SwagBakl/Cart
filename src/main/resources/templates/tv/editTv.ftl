<#import "../parts/common.ftl" as c>

<@c.page>
<center>
    <div class="edit-form col-7 shadow-lg">
        <h3 class="display">Редактор телевизора</h3>
        <form action="/tv/save" method="post" enctype="multipart/form-data">
            <div class="form-group">
                <input type="text" name="name" value="${tv.model}" class="form-control shadow-lg">
            </div>
            <div class="form-group">
                <input type="text" name="price" value="${tv.price}" class="form-control shadow-lg">
            </div>
            <div class="form-group">
                <input type="text" name="quantity" value="${tv.quantity}" class="form-control shadow-lg">
            </div>
            <div class="form-group">
                <textarea  name="description"  class="form-control shadow-lg">${tv.description}</textarea>
            </div>
            <div class="form-group add-file-button">
        <span class="btn btn-default btn-file shadow-lg">
        Выберите файл <input type="file" name="file" id="file" onchange="return fileValidation()">
        </span>
            </div>
            <img src="/img/${tv.filename}" class="card-img-top shadow-lg">
            <input type="hidden" name="id" value="${tv.id}">
            <input type="hidden" name="_csrf" value="${_csrf.token}">
            <div class="d-inline-flex">
                <button type="submit" class="btn btn-warning mt-2 shadow-lg d-inline-flex">
                    Сохранить
                    <i class="material-icons ml-2">
                        save
                    </i>
                </button>
                <a class="btn btn-warning shadow-lg ml-1 mt-2 d-inline-flex" href="/tv/tvList">Отмена
                    <i class="material-icons ml-1">
                        cancel
                    </i>
                </a>
            </div>
        </form>
    </div>
</center>
</@c.page>