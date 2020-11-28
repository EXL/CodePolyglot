document.getElementById('code-area').onkeydown = function (event) {
	if ((event.ctrlKey || event.metaKey) && (event.key === 'Enter')) {
		document.getElementById('code-form').submit();
	}
}
