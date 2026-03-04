<html>
<head>
<title>Book</title>
<link rel="stylesheet" href="/css/freemarker.css">
</head>
<body>

<h1>${book.title}</h1>

<h3>Author: ${book.author}</h3>

<img src="${book.image}" width="200"/>

<p>${book.description}</p>

<#if book.issuedToEmployeeId??>
<p>Issued</p>
<#else>
<p>Available</p>
</#if>

<p>
<a href="/issue?id=${book.id}">Take book</a>
</p>

<p>
<a href="/return?id=${book.id}">Return book</a>
</p>

<p>
<a href="/books">Back</a>
</p>

</body>
</html>