<#include "security.ftl">

<nav class="navbar navbar-expand-lg navbar-dark   shadow-lg">
    <a class="navbar-brand" href="/">Магазин</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon">ШО ЗА</span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">

        <ul class="navbar-nav mr-auto">
            <div class="d-flex align-items-center mr-5 ml-5">
             <li class="nav-item dropdown">
            <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                <span><b>Телефоны</b></span><br>
                <span><b>Ноутбуки</b></span><br>
                <span><b>Планшеты</b></span><br>
            </a>
            <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                <a class="dropdown-item" href="/cphone/phoneList">Телефоны</a>
                <a class="dropdown-item" href="/notebook/nBList">Ноутбуки</a>
                <a class="dropdown-item" href="/tab/tabList">Планшеты</a>
            </div>
            </li>
            </div>
            <div class="d-flex align-items-center mr-5 ml-5">
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        <span><b>Телевизор</b></span><br>
                        <span><b>Аудио</b></span><br>
                        <span><b>Фото</b></span><br>
                    </a>
                    <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                        <a class="dropdown-item" href="/tv/tvList">Телевизоры</a>
                        <a class="dropdown-item" href="/audio/catalog">Аудио</a>
                        <a class="dropdown-item" href="/photo/photoList">Фото</a>
                    </div>
                </li>
            </div>
            <div class="d-flex align-items-center mr-5 ml-5">
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        <span><b>Бытовая техника</b></span><br>
                    </a>
                    <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                        <a class="dropdown-item" href="/fridge/fridgeList"><b>Холодильники</b></a>
                        <a class="dropdown-item" href="/washer/washerList"><b>Стиральные машины</b></a>
                        <a class="dropdown-item" href="/microwave/mwList"><b>Микроволновки</b></a>
                    </div>
                </li>
            </div>
            <#if isAdmin>
            <div class="d-flex align-items-center mr-5 ml-5">
            <li class="nav-item">
                <a class="nav-link" href="/user/userList">
                    <b>Пользователи</b>
                </a>
            </li>
             </#if>
            </div>
            <#if isAdmin>
            <div class="d-flex align-items-center mr-5 ml-5">
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        <span><b>Характеристики</b></span><br>
                    </a>
                    <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                        <a class="dropdown-item" href="/elchars/elCharNameList"><b>Характеристики электроники</b></a>
                        <a class="dropdown-item" href="/appchars/appCharNameList"><b>Характеристики бытовой техники</b></a>
                    </div>
                </li>
            </div>
            </#if>
        </ul>

        <form action="/logout" method="post" class="d-inline-flex">
            <input type="hidden" name="_csrf" value="${_csrf.token}">
            <div class="d-inline-flex">
                <div class="navbar-text mr-1"><#if user??>${name}<#else>Войдите</#if></div>
            <#if user??>
                <div id="timer" class="mt-2 mr-2 ml-1"></div>
                <button type="submit" class="btn btn-warning shadow-lg">Выйти</button>
            </#if>
            </div>
        </form>

    </div>
</nav>