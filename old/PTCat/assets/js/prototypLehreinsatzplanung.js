var counter = 0;

function moreFields() {
	counter++;
	var newFields = document.getElementById('readrootFromTimePerDay').cloneNode(true);
	newFields.id = '';
	newFields.style.display = 'block';
	var newField = newFields.childNodes;
	for (var i=0;i<newField.length;i++) {
		var theName = newField[i].name
		if (theName)
			newField[i].name = theName + counter;
	}
	var insertHere = document.getElementById('writerootFromTimePerDay');
	insertHere.parentNode.insertBefore(newFields,insertHere);
}

function moreFieldsDayCombination() {
	counter++;
	var newFields = document.getElementById('readrootDayCombination').cloneNode(true);
	newFields.id = '';
	newFields.style.display = 'block';
	var newField = newFields.childNodes;
	for (var i=0;i<newField.length;i++) {
		var theName = newField[i].name
		if (theName)
			newField[i].name = theName + counter;
	}
	var insertHere = document.getElementById('writerootDayCombination');
	insertHere.parentNode.insertBefore(newFields,insertHere);
}
window.onload = moreFields;
window.onload = moreFieldsDayCombination;