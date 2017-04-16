<html>
<head />
<body>
	<h1>Notebook</h1>
	<h2>Add new note</h2>
	<form method="post"">
		<p>Author: <input type="text" name="author" /></p>
		<p>Text: <input type="text" name="text" /></p>
		<p><input type="submit" value="Add Note"></p>
	</form>
	<h2>All notes</h2>
	<#list notesSorted as note>
	<p>${note.id?html} (${note.author?html}) - ${note.text?html}</p>
	</#list>
</body>
</html>