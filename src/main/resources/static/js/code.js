document.getElementById('code-area').onkeydown = function (event) {
	if ((event.ctrlKey || event.metaKey) && (event.key === 'Enter')) {
		document.getElementById('code-form').submit();
	}
	if ((event.key === 'l')) {
		document.querySelectorAll('.code-table-code').forEach(line => line.style.whiteSpace = 'pre-wrap')
	}
	if ((event.key === 'r')) {
		document.querySelectorAll('.code-table-code').forEach(line => line.style.whiteSpace = 'pre')
	}
}
