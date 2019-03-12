<#import "../parts/common.ftl" as c>

<@c.page>
    Pc editor
    <form action="/pc/save" method="post" enctype="multipart/form-data">
        <input type="text" name="name" value="${pc.model}">
        <input type="number" name="price" value="${pc.price}">
        <input type="number" name="quantity" value="${pc.quantity}">
        <input type="file" name="file">
        <img src="/img/${pc.filename}" class="card-img-top">
        <input type="hidden" name="id" value="${pc.id}">
        <input type="hidden" name="_csrf" value="${_csrf.token}">
        <button type="submit">Save</button>
    </form>
</@c.page>