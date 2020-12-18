document.getElementById('code-area').onkeydown = function (event) {
	if ((event.ctrlKey || event.metaKey) && (event.key === 'Enter')) {
		document.getElementById('code-form').submit();
	}
}

var button_copy = document.getElementById('button-copy');
if (button_copy) {
	button_copy.onclick = function () {
		// document.querySelector('#code-area').focus();
		document.querySelector('#code-area').select();
		try {
			document.execCommand('copy');
			document.getElementById('copy-label').classList.remove('hide');
			setTimeout(function () {
				document.getElementById('copy-label').classList.add('hide');
			}, 3000); // 3 seconds.
		} catch (error) {
			console.error('Unable to copy content from ".code-area" class to clipboard: "' + error + '".');
		}
	}
}

var button_wide = document.getElementById('button-wide');
if (button_wide) {
	button_wide.onclick = function () {
		if (document.getElementById('panel').classList.toggle('hide')) {
			document.getElementById('code-form').style.marginLeft = '0';
			document.getElementById('button-wide').classList.add('img-button-toggle');
		} else {
			document.getElementById('code-form').style.marginLeft = '175px';
			document.getElementById('button-wide').classList.remove('img-button-toggle');
		}
	}
}

var button_wrap = document.getElementById('button-wrap');
if (button_wrap) {
	button_wrap.onclick = function () {
		if (document.getElementById('panel').classList.toggle('toggle')) {
			document.querySelectorAll('.code-line').forEach(line => line.style.whiteSpace = 'pre-wrap');
			document.getElementById('button-wrap').classList.add('img-button-toggle');
		} else {
			document.querySelectorAll('.code-line').forEach(line => line.style.whiteSpace = 'pre');
			document.getElementById('button-wrap').classList.remove('img-button-toggle');
		}
	}
}

var font = document.getElementById('font');
if (font) {
	font.onclick = function () {
		if (font.classList.toggle('toggle-font')) {
			document.getElementsByClassName('page')[0].style.font = '12px "Synergy Full Bold Condensed", "DejaVu Sans", sans-serif';
			document.getElementsByClassName('table')[0].style.fontSize = '12px'
			document.querySelectorAll('.radio-mark').forEach(radio => radio.style.left = '2px');
		} else {
			document.getElementsByClassName('page')[0].style.font = '12px "Nokia Standard Bold", "DejaVu Sans", sans-serif';
			document.getElementsByClassName('table')[0].style.fontSize = '11px'
			document.querySelectorAll('.radio-mark').forEach(radio => radio.style.left = '1px');
		}
	}
}
