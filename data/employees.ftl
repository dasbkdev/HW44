<html>
<head>
    <title>Employees</title>
    <link rel="stylesheet" href="/css/freemarker.css">
</head>
<body>

<h1>Employees</h1>

<ul>
    <#list employees as employee>
        <li>
            <a href="/employee?id=${employee.id}">
                ${employee.fullName}
            </a>
        </li>
    </#list>
</ul>

</body>
</html>