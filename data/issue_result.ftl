<html>
<head>
    <title>Issue</title>
    <link rel="stylesheet" href="/css/freemarker.css">
</head>
<body>

<#if success>
    <h1>Book issued successfully</h1>
<#else>
    <h1>Book issue failed</h1>
    <#if limitReached>
        <p>Limit reached (max 2 books).</p>
    <#else>
        <p>Book is not available or invalid book.</p>
    </#if>
</#if>

<#if book??>
    <p>${book.title} — ${book.author}</p>
    <p><a href="/book?id=${book.id}">Open book</a></p>
</#if>

<p><a href="/books">Back to books</a></p>
<p><a href="/profile">Profile</a></p>

</body>
</html>