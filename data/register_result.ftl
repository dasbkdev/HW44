<html>
<head>
    <title>Register result</title>
    <link rel="stylesheet" href="/css/freemarker.css">
</head>
<body>

<#if success>
    <h1>Registration success</h1>
    <p>${fullName} (${identifier})</p>
    <p><a href="/register">Register another</a></p>
<#else>
    <h1>Registration failed</h1>
    <p>User already exists or invalid data</p>
    <p><a href="/register">Try again</a></p>
</#if>

</body>
</html>