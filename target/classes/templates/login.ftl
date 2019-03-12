<#import "parts/common.ftl" as c>

<@c.page>
<h1 class="display orange mt-5">ВХОД В СИСТЕМУ</h1>
<div class="modal-dialog text-center">
    <div class="col-sm-8 main-section">
    <div class="modal-content">
<form action="/login" method="post" class="col-12">
    <div class="col-12 user-img">
        <img src="/images/user.png">
    </div>
    <div class="form-group">
    <#if Session?? && Session.SPRING_SECURITY_LAST_EXCEPTION??>
<div class="alert alert-danger" role="alert">
    Ошибка заполнения формы.
</div>
    </#if>
    </div>
    <div class="form-group">
             <input type="text" name="username" class="form-control shadow-lg" placeholder = "Введите логин"/>
    </div>
    <div class="form-group">
            <input type="password" name="password" class="form-control shadow-lg" placeholder="Введите пароль"/>
    </div>
    <input type="hidden" name="_csrf" value="${_csrf.token}">
    <button type="submit" class="btn btn-warning shadow-lg">Вход</button>
</form>
    </div>
    </div>
</div>
</@c.page>