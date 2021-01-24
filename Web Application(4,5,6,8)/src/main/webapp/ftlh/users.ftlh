<html lang="en">
<head>
    <title>Title</title>
</head>
<body>
<h1 style="color:${color.value}">USERS</h1>
<form action="/users" method="post">
    <label>
        <select name="color">
            <option value="red">RED</option>
            <option value="green">GREEN</option>
            <option value="blue">BLUE</option>
        </select>
    </label>
    <input type="submit" value="OK">
</form>

</body>
</html>