<html>
<head>
    <title>Employee</title>
    <link rel="stylesheet" href="/css/freemarker.css">
</head>
<body>

<#if employee??>
    <h1>${employee.fullName}</h1>

    <h2>Current books</h2>
    <#if employee.currentBooks?size == 0>
        <p>-</p>
    <#else>
        <ul>
            <#list employee.currentBooks as bookId>
                <#assign book = library.getBookById(bookId)>
                <li>
                    <a href="/book?id=${book.id}">${book.title}</a>
                </li>
            </#list>
        </ul>
    </#if>

    <h2>Past books</h2>
    <#if employee.pastBooks?size == 0>
        <p>-</p>
    <#else>
        <ul>
            <#list employee.pastBooks as bookId>
                <#assign book = library.getBookById(bookId)>
                <li>
                    <a href="/book?id=${book.id}">${book.title}</a>
                </li>
            </#list>
        </ul>
    </#if>
<#else>
    <h1>Employee not found</h1>
</#if>

<p><a href="/employees">Back</a></p>

</body>
</html>