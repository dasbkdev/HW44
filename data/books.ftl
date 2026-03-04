<html>
<head>
    <title>Books</title>
    <link rel="stylesheet" href="/css/freemarker.css">
</head>
<body>

<h1>Books</h1>

<table>
    <tr>
        <th>ID</th>
        <th>Image</th>
        <th>Title</th>
        <th>Author</th>
        <th>Description</th>
        <th>Status</th>
        <th>Issued to</th>
    </tr>

    <#list books as book>
        <tr>
            <td>${book.id}</td>

            <td>
                <img src="/${book.image}" width="80">
            </td>

            <td>
                <a href="/book?id=${book.id}">
                    ${book.title}
                </a>
            </td>

            <td>${book.author}</td>

            <td>
                ${book.description}
            </td>

            <td>${book.status}</td>

            <td>
                <#if book.issuedToEmployeeId??>
                    ${library.getEmployeeNameById(book.issuedToEmployeeId)}
                <#else>
                    -
                </#if>
            </td>

        </tr>
    </#list>

</table>

</body>
</html>