<#include "../parts/security.ftl">
<#import "../parts/common.ftl" as c>
<#import "../parts/pager.ftl" as p>
<@c.page>
<div class="d-flex justify-content-between">
    <#if isAdmin>
<div class="mt-2 ">
    <a href="/player/addPlayer" class="btn btn-warning add_button d-inline-flex shadow-lg" >Добавить плеер
        <i class="material-icons ml-2">
            add_box
        </i>
    </a>
</div>
    </#if>
    <div class="mt-1 ">
        <form method="get" action="/player/playerList" class="d-flex search ">
            <input type="text" name="filter" value="${filter?ifExists}" class="filter-input form-control col-9 shadow-lg" placeholder="Введите модель">
            <button type="submit" class="btn btn-warning form-control d-inline-flex shadow-lg">
                <i class="material-icons mr-2">
                    search
                </i>
                Найти
            </button>
        </form>
    </div>
</div>
    <@p.pager url playerList />

        <div class="card-col-sum mb-4 shadow-lg col-8">

            <div class="card-columns shadow-lg">

     <#list playerList.content as list>
         <div class="card  mr-4 shadow-lg border border-warning rounded">
             <div>
                 <img src="/img/${list.filename}" class="card-img-top img-fluid p-1 rounded">
             </div>
             <div class="card-body">
                 <h4 class="card-title"><i>${list.model}</i></h4><br>
                 <div class="card-subtitle text-truncate text-muted ">${list.description}</div>
                 <div class="card-text d-flex justify-content-between">
                     <span>Цена:</span>
                     <div class="position-relative price"><b>${list.price}</b> <span> руб.</span></div>
                 </div>
                 <div class="card-text d-flex justify-content-between">
                     <span>Кол-во в наличии:</span>
                     <div class="position-relative">${list.quantity} <span> шт.</span></div>
                 </div>
             </div>

             <div class="card-footer text-muted">
                <#if isAdmin>
                <a href="/player/${list.id}" class="btn btn-warning form-control">
                    <span class="d-inline-flex">Редактировать
                    <i class="material-icons ml-2">
                        edit
                    </i></span>
                </a>
                <a href="/player/delete/${list.id}" class="btn btn-warning form-control">
                    <span class="d-inline-flex">Удалить
                    <i class="material-icons ml-2">
                        delete_forever
                    </i></span>
                </a>
                </#if>
                 <a href="/player/${list.id}/characts" class="btn btn-warning form-control">
                     <span class="d-inline-flex">Характеристики
                     <i class="material-icons ml-2">
                         settings
                     </i>
                     </span>
                 </a>
             </div>

         </div>
     </#list>
            </div>
        </div>

</@c.page>
