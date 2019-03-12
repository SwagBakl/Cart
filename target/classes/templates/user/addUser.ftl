<#import "../parts/common.ftl" as c>

<@c.page>
<center>
<form action="/addUser" method="post" class="col-6 edit-form shadow-lg" >
    <div class="form-group display">
        <h3>Добавление пользователя</h3>
    </div>
    <div class="form-group">
        <input type="text" value="<#if user??>${user.username}</#if>" name="username"
               class="shadow-lg form-control ${(usernameError??)?string('is-invalid', '')}" placeholder="Введите имя пользователя"/>
        <#if usernameError??>
        <div class="invalid-feedback">
            ${usernameError}
        </div>
        </#if>
    </div>
    <div class="form-group">
        <input type="password" name="password"
               class="shadow-lg form-control ${(passwordError??)?string('is-invalid', '')}" placeholder="Введите пароль"/>
        <#if passwordError??>
        <div class="invalid-feedback">
            ${passwordError}
        </div>
        </#if>
    </div>
    <div class="form-group">
        <input type="password" name="verPassword"
               class="shadow-lg form-control ${(verPasswordError??)?string('is-invalid', '')}" placeholder="Подтвердите пароль"/>
        <#if verPasswordError??>
        <div class="invalid-feedback">
            ${verPasswordError}
        </div>
        </#if>
    </div>
    <input type="hidden" name="_csrf" value="${_csrf.token}">
    <div><input type="submit" value="Добавить пользователя" class="btn btn-warning shadow-lg "/></div>
</form>
</center>
</@c.page>