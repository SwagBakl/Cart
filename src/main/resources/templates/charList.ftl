<html>
<head>
    <title>Col</title>
</head>
<body>
<h3>${model}</h3>
<div>
    <form method="post" action="/pc/characts/${id}/">
        <input type="text" name="name">
        <input type="text" name="value">
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
                <td>${char.pc.model}</td>
                <td>${char.id}</td>
                <td>${char.name}</td>
                <td>${char.value}</td>
                <td><a href="/charact/${char.id}">Edit</a></td>
            </tr>
            </#list>
    </table>
</div>
</body>
</html>