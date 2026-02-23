<html>
<head>
    <title>Book</title>
    <link rel="stylesheet" href="/css/freemarker.css">
</head>
<body>

<#if book??>
    <h1>${book.title}</h1>
    <p>Author: ${book.author}</p>
    <p>Status: ${book.status}</p>

    <#if book.issuedToEmployeeId??>
        <p>Issued to: ${library.getEmployeeNameById(book.issuedToEmployeeId)}</p>
    </#if>

    <img src="/${book.image}" width="200">
<#else>
    <h1>Book not found</h1>
</#if>

<p><a href="/books">Back</a></p>

</body>
</html>