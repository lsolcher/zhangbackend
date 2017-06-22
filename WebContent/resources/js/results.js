
$(document).ready(function(){
	
	var allTimeslots = [];
	var singleElements = [];
	
	var semesterID = "";
	var timeID = "";
	var fullTableID = "";
	
	for (var semesterIMIba = 1; semesterIMIba < 6; semesterIMIba++) {
	
		var fileUrl = "/ZhangProjectBackend/resources/BachelorIMI" + semesterIMIba + ".csv";
//		var fileUrl = "/ZhangProjectBackend/resources/BachelorIMI1.csv";
		
		$.ajax({
			type: "GET",
//			url: fileUrl, //"/ZhangProjectBackend/resources/Jung2.csv",
			url: "/ZhangProjectBackend/resources/BachelorIMI1.csv",
			dataType: "text",
			contentType:"text/plain",
			success: function(data) {/*console.log(data);*/ processData(data);},
//			error: function(a,b,c){console.log(a,b,c);console.log("!!!!!!!!!!!!!!!!!!!!!!!");}
		});
		
		
		semesterID = "" + (semesterIMIba - 1 ); //TODO .toString
			
		for (var i = 0; i < allTimeslots.length; i++) {	
						
			timeID = i;
			fullTableID = "table-cell-" + timeID + semesterID;
			
			if (allTimeslots[i] != null ) {
				
				console.log("allTimeslots[i]");
				document.getElementById(fullTableID).innerHTML = allTimeslots[i];
				
				
				
//				document.getElementById(fullTableID).innerHTML = "Kurs: " + kurs + ", Dozent: " + dozent + ", Raum: " + raum;
			}
		}
		
	
	}
//	for (var semesterIMIma = 1; semesterIMIma < 4; semesterIMIma++) {
//		semesterID = "" + (semesterIMIma + 5 );  //  + 6 - 1 //TODO .toString
//		timeID = "";
//		fullTableID = "table-cell-" + timeID + semesterID;
//		
//		
//		
//		
//		
//		
//	}
	

	function processData(allText) {
		
		var allTextLines = allText.split(/\r\n|\n/);		// tODO: var allTextLines = allText.split(/\r\n|\n/|';');	???!!
//		singleElements = allText.split("\n|';'");
		
		
		for (var i = 0; i < allTextLines.length-1; i++) {
			console.log("-> " + allTextLines[i]);
		}
		
		
		for (var i = 0; i < allTextLines.length-1; i++) {
			allTimeslots.push(allTextLines[i].split(';'));
			
		}
		for (var i = 0; i < allTimeslots.length; i++) {
			console.log(allTimeslots[i]);
		}
		
		
//		for (var i = 0; i < allTimeslots.length; i++) {
//			singleElements.push(allTimeslots[i].split(','))
//		}
//		for (var i = 0; i < singleElements.length; i++) {
//			console.log("->> " + singleElements[i]);
//		}
		
	}
	
	
});


