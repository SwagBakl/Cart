<#include "../parts/security.ftl">
<#import "../parts/common.ftl" as c>
<#import "../parts/pager.ftl" as p>
<@c.page>

        <div class="card-col-sum mb-4 shadow-lg">

            <div class="card-columns shadow-lg">

         <div class="card  mr-4 shadow-lg border border-warning rounded">
             <div>
                 <img src="/images/tab.jpeg" class="card-img-top img-fluid p-1 rounded">
             </div>
             <div class="card-body">
                 <h3 class="card-title"><i>Домашние кинотеатры</i></h3>
             </div>

             <div class="card-footer text-muted">
                <a href="/hometheater/htList" class="btn btn-warning form-control">Открыть каталог</a>
             </div>

         </div>
                <div class="card  mr-4 shadow-lg border border-warning rounded">
                    <div>
                        <img src="/images/TV.jpg" class="card-img-top img-fluid p-1 rounded">
                    </div>
                    <div class="card-body">
                        <h3 class="card-title"><i>Mp3-плееры</i></h3>
                    </div>

                    <div class="card-footer text-muted">
                        <a href="/player/playerList" class="btn btn-warning form-control">Открыть каталог</a>
                    </div>

                </div>
                <div class="card  mr-4 shadow-lg border border-warning rounded">
                    <div>
                        <img src="/images/TV.jpg" class="card-img-top img-fluid p-1 rounded">
                    </div>
                    <div class="card-body">
                        <h3 class="card-title"><i>Музыкальные центры</i></h3>
                    </div>

                    <div class="card-footer text-muted">
                        <a href="/mc/mcList" class="btn btn-warning form-control">Открыть каталог</a>
                    </div>

                </div>
            </div>
        </div>

</@c.page>
