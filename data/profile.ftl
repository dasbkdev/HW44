<html>
<head>
    <title>Profile</title>
    <link rel="stylesheet" href="/css/freemarker.css">
</head>
<body>

<h1>Profile</h1>

<p><b>Identifier:</b> ${user.identifier}</p>
<p><b>Full name:</b> ${user.fullName}</p>

<h2>Current books</h2>

<#if currentBooks?size == 0>
    <p>-</p>
<#else>
    <table>
        <tr>
            <th>ID</th>
            <th>Image</th>
            <th>Title</th>
            <th>Author</th>
            <th>Description</th>
            <th>Action</th>
        </tr>
        <#list currentBooks as book>
            <tr>
                <td>${book.id}</td>
                <td><img src="/${book.image}" width="80"></td>
                <td><a href="/book?id=${book.id}">${book.title}</a></td>
                <td>${book.author}</td>
                <td>${book.description}</td>
                <td><a href="/return?id=${book.id}">Return</a></td>
            </tr>
        </#list>
    </table>
</#if>

<h2>All books ever taken</h2>

<#if pastBooks?size == 0>
    <p>-</p>
<#else>
    <table>
        <tr>
            <th>ID</th>
            <th>Image</th>
            <th>Title</th>
            <th>Author</th>
            <th>Description</th>
        </tr>
        <#list pastBooks as book>
            <tr>
                <td>${book.id}</td>
                <td><img src="/${book.image}" width="80"></td>
                <td><a href="/book?id=${book.id}">${book.title}</a></td>
                <td>${book.author}</td>
                <td>${book.description}</td>
            </tr>
        </#list>
    </table>
</#if>

<p><a href="/books">Books</a></p>
<p><a href="/logout">Logout</a></p>

</body>
</html>