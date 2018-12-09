<#import "parts/common.ftl" as c>

<@c.page>
<!--<div>
<form method="post" action="/main" enctype="multipart/form-data">
    <input type="text" name="name" placeholder="Enter name">
    <input type="text" name="desc" placeholder="Enter description">
    <input type="file" name="file">
    <input type="hidden" name="_csrf" value="${_csrf.token}">
    <button type="submit">Add new item</button>
</form>
</div>-->
<div class="card-columns">
    <div class="card my-3">
        <div>
                <img src="/images/tab.jpeg" class="card-img-top">
        </div>
        <div class="m-2">
            <i>PC</i><br>
            <i>Great PC</i>
        </div>
        <div class="card-footer text-muted">
            <a href="/pcList" class="btn btn-primary">Show list</a>
        </div>
    </div>
</div>
</@c.page>