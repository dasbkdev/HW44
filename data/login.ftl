<html>
<head>
    <title>Login</title>
    <link rel="stylesheet" href="/css/freemarker.css">
</head>
<body>

<h1>Login</h1>

<#if error?has_content>
    <p>${error}</p>
</#if>

<form method="post" action="/login">
    <p>Identifier: <input name="identifier" type="text"></p>
    <p>Password: <input name="password" type="password"></p>
    <button type="submit">Login</button>
</form>

<p><a href="/register">Register</a></p>

</body>
</html>