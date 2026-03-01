<html>
<head>
    <title>Return</title>
    <link rel="stylesheet" href="/css/freemarker.css">
</head>
<body>

<#if success>
    <h1>Book returned successfully</h1>
<#else>
    <h1>Book return failed</h1>
    <p>This book is not issued to you or invalid book.</p>
</#if>

<#if book??>
    <p>${book.title} — ${book.author}</p>
    <p><a href="/book?id=${book.id}">Open book</a></p>
</#if>

<p><a href="/books">Back to books</a></p>
<p><a href="/profile">Profile</a></p>

</body>
</html>