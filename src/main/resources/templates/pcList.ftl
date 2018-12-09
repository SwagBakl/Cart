<#include "parts/security.ftl">
<#import "parts/common.ftl" as c>

<@c.page>
<#if isAdmin>
<a class="btn btn-primary" data-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false" aria-controls="collapseExample">
    Add new PC
</a>
<div class="collapse" id="collapseExample">
    <form method="post" action="/pcList" enctype="multipart/form-data">
        <input type="text" name="modelName" placeholder="Enter pc model">
        <input type="text" name="price" placeholder="Enter pc price">
        <input type="file" name="file">
        <input type="hidden" name="_csrf" value="${_csrf.token}">
        <button type="submit">Add new pc</button>
    </form>
</div>
</#if>
<div>
    <form method="get" action="/pcList">
        <input type="text" name="filter" value="${filter?ifExists}">
        <button type="submit">Filter</button>
    </form>
</div>
<div>
    <div class="card-columns">
     <#list pcList as list>
        <div class="card my-3">
            <div>
                <img src="/img/${list.filename}" class="card-img-top">
            </div>
            <div class="m-2">
                <i>${list.model}</i><br>
                <i>${list.price}</i>
            </div>
            <#if isAdmin>
            <div class="card-footer text-muted">
                <a href="/pc/${list.id}" class="btn btn-primary">Edit</a>
                <a href="/pc/delete/${list.id}" class="btn btn-primary">Delete</a>
            </div>
            </#if>
        </div>
     </#list>
    </div>
</div>
</@c.page>
