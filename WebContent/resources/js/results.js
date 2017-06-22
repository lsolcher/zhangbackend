
$(document).ready(function(){
	
	var singleElements = [];
	var semesterID = "";
	var timeID = "";
	var fullTableID = "";
	
//	for (var semesterIMIba = 1; semesterIMIba < 6; semesterIMIba++) {
	
//		var fileUrl = "ZhangProjectBackend/resources/Bachelor IMI " + semesterIMIba + ".csv";
		var fileUrl = "ZhangProjectBackend/resources/Bachelor IMI 1.csv";
		
		$.ajax({
			type: "GET",
			url: "/ZhangProjectBackend/resources/Jung2.csv", //fileUrl,
			dataType: "text",
			contentType:"text/plain",
			success: function(data) {processData(data);},
//			error: function(a,b,c){console.log(a,b,c);console.log("!!!!!!!!!!!!!!!!!!!!!!!");}
		});
		
		
//		semesterID = "" + (semesterIMIba - 1 ); //TODO .toString
//			
//		for (var i = 0; i < 35; i++) {
//		//for (var i = 0; i < singleElements.length; i++) {	
//						
//			timeID = "";
//			fullTableID = "table-cell-" + timeID + semesterID;
//			
//			if (singleElements[i] != null ) {
//				
//				console.log("null");
////				document.getElementById(fullTableID).innerHTML = ;
//				
//				
//				
////				document.getElementById(fullTableID).innerHTML = "Kurs: " + kurs + ", Dozent: " + dozent + ", Raum: " + raum;
//			}
//		}
		
	
//	}
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
		
		console.log("HI!!! ");
		
		var allTextLines = allText.split(/\r\n|\n/);
//		singleElements = allText.split("\n|';'");
		
//		console.log("-> " + allTextLines[0]);
		
		
//		for (var i = 0; i < singleElements.length; i++) {
//			alert("entry: " + singleElements[i]);
//		}
		
//		for (var i = 1; i < allTextLines.length; i++) {
//			singleElements.push(allTextLines[i].split(','));
//			alert((allTextLines[i].split(',')));
//		}

		//function processData(allText) {
//		    var allTextLines = allText.split(/\r\n|\n/);
//		    var headers = allTextLines[0].split(';');
//		    var lines = [];
		//    
//		    console.log("in!");
		//
//		    for (var i=1; i<allTextLines.length; i++) {
//		        var data = allTextLines[i].split(';');
//		        if (data.length == headers.length) {
		//
//		            var tarr = [];
//		            for (var j=0; j<headers.length; j++) {
//		                tarr.push(headers[j]+":"+data[j]);
//		            }
//		            lines.push(tarr);
//		        }
//		    }
//		    // alert(lines);
		//}

	}
	
	
});


