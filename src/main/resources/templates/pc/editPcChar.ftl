<html>
<head>
    <title>Lol</title>
</head>
<body>
<form action="/pc/characts/${pcChars.pc.id}/save" method="post">
    <input type="text" name="name" value="${pcChars.name}">
    <input type="text" name="value" value="${pcChars.value}">
    <input type="text" name="id" value="${pcChars.id}">
    <input type="text" name="pc_id" value="${pcChars.pc.id}">
    <input type="hidden" name="_csrf" value="${_csrf.token}">
    <button type="submit">Save</button>
</form>
<div>
    <form  action="/charact/delete/${pcChars.id}">
        <input type="text" name="id" value="${pcChars.id}">
        <button type="submit">Delete</button>
    </form>
</div>
</body>
</html>