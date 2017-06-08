<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html ng-app="zhang-app">
  <head>
    <meta charset="utf-8">
    <title>Lehrplan Ergebnis</title>

    <!-- styles -->
    <spring:url var ="bootstrapcss" value="/resources/css/bootstrap.css" />
    <link rel="stylesheet" href="${bootstrapcss}"/>
    <spring:url var ="style" value="/resources/css/style2.css" />
    <link rel="stylesheet" href="${style}"/>
  
    <link rel='stylesheet' href='https://cdnjs.cloudflare.com/ajax/libs/fullcalendar/3.4.0/fullcalendar.css' />
    <link rel='stylesheet' href='https://cdnjs.cloudflare.com/ajax/libs/fullcalendar/3.4.0/fullcalendar.min.css' />
	<link rel='stylesheet' href='https://cdnjs.cloudflare.com/ajax/libs/fullcalendar/3.4.0/fullcalendar.print.min.css' media='print' />
	
	<style>

		.main {
			margin: 40px 10px;
			padding: 0;
			font-family: "Lucida Grande",Helvetica,Arial,Verdana,sans-serif;
			font-size: 14px;
		}
	
		#calendar {
			max-width: 1200px;
			margin: 0 auto;
		}
	
	</style>
    
    <!-- end of styles -->




    <!-- libs -->
  	<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
	<script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
	<script src="//ajax.googleapis.com/ajax/libs/angularjs/1.3.15/angular.js"></script>
	<script src="//ajax.googleapis.com/ajax/libs/angularjs/1.2.0rc1/angular-route.min.js"></script>
	<spring:url var ="bootstrap" value="/resources/js/bootstrap.js" />
  	<script type="text/javascript" src="${bootstrap}"></script>


	<!--spring:url var ="fullcalendar-min" value="/resources/js/fullcalendar.min.js" />
  	<script type="text/javascript" src="${fullcalendar-min}"></script-->
	<script src='http://code.jquery.com/jquery-1.11.3.min.js'></script>
	<script src='https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.11.1/moment.min.js'></script>
	<script src='https://cdnjs.cloudflare.com/ajax/libs/fullcalendar/3.4.0/fullcalendar.min.js'></script>

	<script>
	
		$(document).ready(function() {
			
			$('#calendar').fullCalendar({
				header: {
					left: 'prev,next today',
					center: 'title',
					right: 'month,agendaWeek,agendaDay,listWeek'	//basicWeek, basicDay, listDay
				},
				defaultDate: '2017-05-01',
				navLinks: true, // can click day/week names to navigate views
				
				//timeFormat: 'h:mm' ,
				columnFormat: 'dddd',	// or 'ddd M/D' -> like 'Mon 9/7', for week views
				
				weekNumbers: true,
				weekNumbersWithinDays: true,
				weekNumberCalculation: 'ISO',

				businessHours: true,
				businessHours: {
				    // days of week. an array of zero-based day of week integers (0=Sunday)
				    dow: [ 1, 2, 3, 4, 5 ], // Monday - Friday

				    start: '07:30', // a start time (10am in this example)
				    end: '21:00', // an end time (6pm in this example)
				},
				
				hiddenDays: [ 6, 0 ] ,
				minTime: '07:30',
				maxTime: '21:00',
				
				slotLabelFormat:'H:mm', 
				//slotDuration: '01:30:00',
				//slotLabelInterval: '00:45:00', 
				slotEventOverlap: false,
				
				defaultView: 'agendaWeek',
				editable: true,
				eventLimit: true, // allow "more" link when too many events
				events: [
					{
						title: 'Bildanalyse',
						start: '2017-05-01T09:45:00' ,
						end: '2017-05-01T11:15:00' ,
						allDay : false // will make the time show
					},
					{
						title: 'Mathe 2',
						start: '2017-05-01T09:45:00' ,
						end: '2017-05-01T11:15:00' ,
						allDay : false // will make the time show
					},
					{
						title: 'Informatik 1',
						start: '2017-05-03T12:15:00' ,
						end: '2017-05-03T13:45:00' ,
						allDay : false // will make the time show
					},
					{
						title: 'Mathe 1',
						start: '2017-05-05T14:00:00' ,
						end: '2017-05-05T15:30:00' ,
						allDay : false // will make the time show
					},
					{
						title: 'All Day Event',
						start: '2017-05-01'
					},
					/*{
						id: 999,
						title: 'Repeating Event',
						start: '2017-05-09T16:00:00'
					},
					{
						title: 'Lunch',
						start: '2017-05-12T12:00:00'
					}*/
				],
				timeFormat: 'H(:mm)' // uppercase H for 24-hour clock
			});
			
		});
	
	</script>
		
	<!-- libs end -->

  </script>
  </head>
  <body>

    <header class="group">
      <div class="container">
				<div class="navbar-header">
					<button class="navbar-toggle" type="button" data-toggle="collapse" data-target=".navbar-collapse">
						<span class="sr-only">Toggle navigation</span>
						<span class="icon-bar"></span>
						<span class="icon-bar"></span>
						<span class="icon-bar"></span>
					</button>
					<a href="/" class="navbar-brand">HTW FB4 Lehreinsatzplanung</a>
				</div>
				<nav class="collapse navbar-collapse" role="navigation">
					<ul class="nav navbar-nav pull-right">
            			<li>
							<a href="#"> </a>
						</li>
					</ul>
				</nav>
			</div>
	</header>
  	<div class="row">
		<div class="main group">
			
			<table class="result-table">
				<caption>Ergebnis-Lehrplan</caption>
				<thead>
					<th>Vorlesungszeit</th>
					<th>B 1.Sem.</th>
					<th>B 2.Sem.</th>
					<th>B 3.Sem.</th>
					<th>B 4.Sem.</th>
					<th>B 5.Sem.</th>
					<th>B 6.Sem.</th>
					<th>M 1.Sem.</th>
					<th>M 2.Sem.</th>
					<th>M 3.Sem.</th>
					<th>M 4.Sem.</th>
				</thead>
				<tbody>
					<tr>
						<td>Montag</td>
					</tr>
					<tr>
						<td>08:00-09:30</td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
					</tr>
					<tr>
						<td>09:45-11:15</td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
					</tr>
					<tr>
						<td>12:15-13:45</td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
					</tr>
					<tr>
						<td>14:00-15:30</td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
					</tr>
					<tr>
						<td>15:45-17:15</td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
					</tr>
					<tr>
						<td>17:30-19:00</td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
					</tr>
					<tr>
						<td>19:15-20:45</td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
					</tr>
					
					<tr>
						<td>Dienstag</td>
					</tr>
					<tr>
						<td>08:00-09:30</td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
					</tr>
					<tr>
						<td>09:45-11:15</td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
					</tr>
					<tr>
						<td>12:15-13:45</td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
					</tr>
					<tr>
						<td>14:00-15:30</td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
					</tr>
					<tr>
						<td>15:45-17:15</td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
					</tr>
					<tr>
						<td>17:30-19:00</td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
					</tr>
					<tr>
						<td>19:15-20:45</td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
					</tr>
					
					<tr>
						<td>Mittwoch</td>
					</tr>
					<tr>
						<td>08:00-09:30</td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
					</tr>
					<tr>
						<td>09:45-11:15</td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
					</tr>
					<tr>
						<td>12:15-13:45</td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
					</tr>
					<tr>
						<td>14:00-15:30</td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
					</tr>
					<tr>
						<td>15:45-17:15</td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
					</tr>
					<tr>
						<td>17:30-19:00</td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
					</tr>
					<tr>
						<td>19:15-20:45</td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
					</tr>
					
					<tr>
						<td>Donnerstag</td>
					</tr>
					<tr>
						<td>08:00-09:30</td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
					</tr>
					<tr>
						<td>09:45-11:15</td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
					</tr>
					<tr>
						<td>12:15-13:45</td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
					</tr>
					<tr>
						<td>14:00-15:30</td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
					</tr>
					<tr>
						<td>15:45-17:15</td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
					</tr>
					<tr>
						<td>17:30-19:00</td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
					</tr>
					<tr>
						<td>19:15-20:45</td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
					</tr>
					
					<tr>
						<td>Freitag</td>
					</tr>
					<tr>
						<td>08:00-09:30</td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
					</tr>
					<tr>
						<td>09:45-11:15</td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
					</tr>
					<tr>
						<td>12:15-13:45</td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
					</tr>
					<tr>
						<td>14:00-15:30</td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
					</tr>
					<tr>
						<td>15:45-17:15</td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
					</tr>
					<tr>
						<td>17:30-19:00</td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
					</tr>
					<tr>
						<td>19:15-20:45</td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
					</tr>
				</tbody>
			</table>
			
			
			
			
			
			
			<div id='calendar'></div>
			
			
			
			
			
			
			
			
			
			
			
			
			
			
		</div>
    </div>
  </body>
</html>
