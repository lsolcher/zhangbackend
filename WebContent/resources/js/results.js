var singleElements;

//(function() { //
$(document).ready(function(){
	
	$.ajax({
		type: "GET",
		url: "Prof 0.csv",
		dataType: "text",
		success: function(data) {processData(data);}
	});
	
//	var semester = 0;
	var kurs = "xxx";
	var dozent = "yyy";
	var raum = "000";
	
	for (var semester = 0; semester <= 9; semester++) {
	// TODO: html-tabelle befüllen: document.getElementById('table-cell-' + i + semester).innerHTML = arrayInhalt... ;
		for (var i = 0; i < 35; i++) {
		//for (var i = 0; i < singleElements.length; i++) {	
		
//			if (singleElements[i] != 0 ) {
				
//				kurs = ;
//				dozent = ;
//				raum = ;
				
				document.getElementById('table-cell-' + i + semester).innerHTML = "Kurs: " + kurs + ", Dozent: " + dozent + ", Raum: " + raum;
//			}
		}
	}
	
	
	
	
});

function processData(allText) {
	
	alert("-> " + allText);
	
	var allTextLines = allText.split(/\r\n|\n/);
	singleElements = allText.split("\n|','");
	
	alert("-> " + allTextLines[0]);
	
	
	for (var i = 0; i < singleElements.length; i++) {
		alert("entry: " + singleElements[i]);
	}
	
	
	/*var singleElements = [];
	
	for (var i = 1; i < allTextLines.length; i++) {
		singleElements.push(allTextLines[i].split(','));
		alert((allTextLines[i].split(',')));
	}*/
}
	
	
//})()
