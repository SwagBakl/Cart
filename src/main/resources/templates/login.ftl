<#import "parts/common.ftl" as c>

<@c.page>
<form action="/login" method="post">
    <div class="form-group row">
        <label class="col-sm-2 col-form-label">User Name :
            <div class="col-sm-15">
             <input type="text" name="username" class="form-control"/>
            </div>
        </label>
    </div>
    <div class="form-group row">
        <label class="col-sm-2 col-form-label"> Password:
            <div class="col-sm-15">
            <input type="password" name="password" class="form-control"//>
            </div>
        </label>
    </div>
    <input type="hidden" name="_csrf" value="${_csrf.token}">
    <button type="submit" class="btn btn-primary">Sign in</button>
</form>
</@c.page>