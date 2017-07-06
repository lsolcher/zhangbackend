
$(document).ready(function(){
	
	var allSemestersText = "";
	
	var allTimeslots = [];
	var singleElements = [];
	
	var semesterID = 0;
	var timeID = 0;
	var fullTableID = "";
	var fullTableIDFollower = "";
	
	var mongoTemplate;

	
	$.ajax({
		type: "GET",
		url: "/ZhangProjectBackend/resources/BachelorIMI1.csv",
		dataType: "text",
		contentType:"text/plain",
		success: function(data) {saveFirstData(data);},
	});
	$.ajax({
		type: "GET",
		url: "/ZhangProjectBackend/resources/BachelorIMI2.csv",
		dataType: "text",
		contentType:"text/plain",
		success: function(data) {saveData(data);},
	});
	$.ajax({
		type: "GET",
		url: "/ZhangProjectBackend/resources/BachelorIMI3.csv",
		dataType: "text",
		contentType:"text/plain",
		success: function(data) {saveData(data);},
	});
	$.ajax({
		type: "GET",
		url: "/ZhangProjectBackend/resources/BachelorIMI4.csv",
		dataType: "text",
		contentType:"text/plain",
		success: function(data) {saveData(data);},
	});
	$.ajax({
		type: "GET",
		url: "/ZhangProjectBackend/resources/BachelorIMI5.csv",
		dataType: "text",
		contentType:"text/plain",
		success: function(data) {saveData(data);},
	});
	$.ajax({
		type: "GET",
		url: "/ZhangProjectBackend/resources/BachelorIMI6.csv",
		dataType: "text",
		contentType:"text/plain",
		success: function(data) {saveData(data);},
	});
	$.ajax({
		type: "GET",
		url: "/ZhangProjectBackend/resources/MasterIMI1.csv",
		dataType: "text",
		contentType:"text/plain",
		success: function(data) {saveData(data);},
	});
	$.ajax({
		type: "GET",
		url: "/ZhangProjectBackend/resources/MasterIMI2.csv",
		dataType: "text",
		contentType:"text/plain",
		success: function(data) {saveData(data);},
	});
	$.ajax({
		type: "GET",
		url: "/ZhangProjectBackend/resources/MasterIMI3.csv",
		dataType: "text",
		contentType:"text/plain",
		success: function(data) {saveData(data);},
	});
	$.ajax({
		type: "GET",
		url: "/ZhangProjectBackend/resources/MasterIMI4.csv",
		dataType: "text",
		contentType:"text/plain",
		success: function(data) {saveData(data); processData(data);},
	});
		
	
	
	function saveFirstData (allTextOfOneSemester) {
		allSemestersText = allSemestersText + allTextOfOneSemester;
	}
	
	function saveData (allTextOfOneSemester) {
		allSemestersText = allSemestersText + " ; " + allTextOfOneSemester;
	}
	
	function processData() {
		
		allTimeslots = allSemestersText.split(/[\n;]/);
		
		for (var i = 0; i < allTimeslots.length; i++) {	
			
			fullTableID = "table-cell-" + i;
			fullTableIDFollower = "table-cell-" + (i+1);
						
			if (!((allTimeslots[i] == "")||(allTimeslots[i] == "-")||(allTimeslots[i] == " ")||(allTimeslots[i] == " -")||(allTimeslots[i] == "- "))) {
				
				singleElements = allTimeslots[i].split(',');
				
				if (!((singleElements[0] == undefined) || (singleElements[1] == undefined) || (singleElements[2] == undefined))) {
					document.getElementById(fullTableID).innerHTML = allTimeslots[i];
					document.getElementById(fullTableID).innerHTML = singleElements[0] + singleElements[1] + singleElements[2];
				}
				
				if (singleElements[3] == " 2 Doppelstunden") {
					document.getElementById(fullTableIDFollower).innerHTML = singleElements[0] + singleElements[1] + singleElements[2];
				}
			}
		}
		
		mongoTemplate.createCollection("schedules");
		
		var schedulesDB = mongoTemplate.getCollection("teachers");
		var cursor = schedulesDB.find();
		while (cursor.hasNext()) {

            var object = cursor.next();

            console.log(object);

        }
	}
	
//	private void setTeachers() {
//        DBCollection teachersDB = mongoTemplate.getCollection("teachers");
//        DBCursor cursor = teachersDB.find();
//        while (cursor.hasNext()) {
//            DBObject obj = cursor.next();
//            Teacher t = mongoTemplate.getConverter().read(Teacher.class, obj);
//            allTeachers.add(t);
//        }    }
});


