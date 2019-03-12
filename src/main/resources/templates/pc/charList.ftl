<html>
<head>
    <title>Col</title>
</head>
<body>
<h3>${model}</h3>
<div>

    <form method="post" action="/pc/${id}/characts">
        <select name="name">
            <#list elCharList as elect>
                <option>${elect.charName}</option>
            </#list>
        </select>
        <select name="value">
            <#list elCharValueList as elect>
                <option>${elect.charValue}</option>
            </#list>
        </select>

        <!-- <input type="text" name="name">
         <input type="text" name="value">-->
        <input type="text" name="id" value="${id}">
        <input type="hidden" name="_csrf" value="${_csrf.token}">
        <button type="submit">Add</button>
    </form>
</div>
<div>
    <table border="solid black">
        <tr>
            <th>Pc name</th>
            <th>Cat_id</th>
            <th>Name</th>
            <th>Value</th>
            <th>Edit</th>
        </tr>
            <#list pcChars as char>
            <tr>
                <td>${char.tv.model}</td>
                <td>${char.id}</td>
                <td>${char.name}</td>
                <td>${char.value}</td>
                <td><a href="/tv/charact/${char.id}">Edit</a></td>
            </tr>
            </#list>
    </table>
</div>
<a href="/electCharsList">Characteristics</a>
</body>
</html>