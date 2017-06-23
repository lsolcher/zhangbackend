<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html ng-app="zhang-app">
  <head>
    <meta charset="utf-8">
    <title>Lehrplan Ergebnis</title>

    <!-- styles -->
    <spring:url var ="bootstrapcss" value="/resources/css/bootstrap.css" />
    <link rel="stylesheet" href="${bootstrapcss}"/>
    <spring:url var ="style" value="/resources/css/style3.css" />
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
  	<spring:url var ="resultsjs" value="/resources/js/results.js" />
  	<script type="text/javascript" src="${resultsjs}"></script>


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
					<tr class="new-day">
						<td>Montag</td>
					</tr>
					<tr>
						<td>08:00-09:30</td>
						<td id="table-cell-0"></td>
						<td id="table-cell-35"></td>
						<td id="table-cell-70"></td>
						<td id="table-cell-105"></td>
						<td id="table-cell-140"></td>
						<td id="table-cell-175"></td>
						<td id="table-cell-210"></td>
						<td id="table-cell-245"></td>
						<td id="table-cell-279"></td>
						<td id="table-cell-314"></td>
					</tr>
					<tr>
						<td>09:45-11:15</td>
						<td id="table-cell-1"></td>
						<td id="table-cell-36"></td>
						<td id="table-cell-71"></td>
						<td id="table-cell-106"></td>
						<td id="table-cell-141"></td>
						<td id="table-cell-176"></td>
						<td id="table-cell-211"></td>
						<td id="table-cell-246"></td>
						<td id="table-cell-280"></td>
						<td id="table-cell-315"></td>
					</tr>
					<tr>
						<td>12:15-13:45</td>
						<td id="table-cell-2"></td>
						<td id="table-cell-37"></td>
						<td id="table-cell-72"></td>
						<td id="table-cell-107"></td>
						<td id="table-cell-142"></td>
						<td id="table-cell-177"></td>
						<td id="table-cell-212"></td>
						<td id="table-cell-247"></td>
						<td id="table-cell-281"></td>
						<td id="table-cell-316"></td>
					</tr>
					<tr>
						<td>14:00-15:30</td>
						<td id="table-cell-3"></td>
						<td id="table-cell-38"></td>
						<td id="table-cell-73"></td>
						<td id="table-cell-108"></td>
						<td id="table-cell-143"></td>
						<td id="table-cell-178"></td>
						<td id="table-cell-213"></td>
						<td id="table-cell-248"></td>
						<td id="table-cell-282"></td>
						<td id="table-cell-317"></td>
					</tr>
					<tr>
						<td>15:45-17:15</td>
						<td id="table-cell-4"></td>
						<td id="table-cell-39"></td>
						<td id="table-cell-74"></td>
						<td id="table-cell-109"></td>
						<td id="table-cell-144"></td>
						<td id="table-cell-179"></td>
						<td id="table-cell-214"></td>
						<td id="table-cell-249"></td>
						<td id="table-cell-283"></td>
						<td id="table-cell-318"></td>
					</tr>
					<tr>
						<td>17:30-19:00</td>
						<td id="table-cell-5"></td>
						<td id="table-cell-40"></td>
						<td id="table-cell-75"></td>
						<td id="table-cell-110"></td>
						<td id="table-cell-145"></td>
						<td id="table-cell-180"></td>
						<td id="table-cell-215"></td>
						<td id="table-cell-250"></td>
						<td id="table-cell-284"></td>
						<td id="table-cell-319"></td>
					</tr>
					<tr>
						<td>19:15-20:45</td>
						<td id="table-cell-6"></td>
						<td id="table-cell-41"></td>
						<td id="table-cell-76"></td>
						<td id="table-cell-111"></td>
						<td id="table-cell-146"></td>
						<td id="table-cell-181"></td>
						<td id="table-cell-216"></td>
						<td id="table-cell-251"></td>
						<td id="table-cell-285"></td>
						<td id="table-cell-320"></td>
					</tr>
					
					<tr class="new-day">
						<td>Dienstag</td>
					</tr>
					<tr>
						<td>08:00-09:30</td>
						<td id="table-cell-7"></td>
						<td id="table-cell-42"></td>
						<td id="table-cell-77"></td>
						<td id="table-cell-112"></td>
						<td id="table-cell-147"></td>
						<td id="table-cell-182"></td>
						<td id="table-cell-217"></td>
						<td id="table-cell-252"></td>
						<td id="table-cell-286"></td>
						<td id="table-cell-321"></td>
					</tr>
					<tr>
						<td>09:45-11:15</td>
						<td id="table-cell-8"></td>
						<td id="table-cell-43"></td>
						<td id="table-cell-78"></td>
						<td id="table-cell-113"></td>
						<td id="table-cell-148"></td>
						<td id="table-cell-183"></td>
						<td id="table-cell-218"></td>
						<td id="table-cell-253"></td>
						<td id="table-cell-287"></td>
						<td id="table-cell-322"></td>
					</tr>
					<tr>
						<td>12:15-13:45</td>
						<td id="table-cell-9"></td>
						<td id="table-cell-44"></td>
						<td id="table-cell-79"></td>
						<td id="table-cell-114"></td>
						<td id="table-cell-149"></td>
						<td id="table-cell-184"></td>
						<td id="table-cell-219"></td>
						<td id="table-cell-254"></td>
						<td id="table-cell-288"></td>
						<td id="table-cell-323"></td>
					</tr>
					<tr>
						<td>14:00-15:30</td>
						<td id="table-cell-10"></td>
						<td id="table-cell-45"></td>
						<td id="table-cell-80"></td>
						<td id="table-cell-115"></td>
						<td id="table-cell-150"></td>
						<td id="table-cell-185"></td>
						<td id="table-cell-220"></td>
						<td id="table-cell-255"></td>
						<td id="table-cell-289"></td>
						<td id="table-cell-324"></td>
					</tr>
					<tr>
						<td>15:45-17:15</td>
						<td id="table-cell-11"></td>
						<td id="table-cell-46"></td>
						<td id="table-cell-81"></td>
						<td id="table-cell-116"></td>
						<td id="table-cell-151"></td>
						<td id="table-cell-186"></td>
						<td id="table-cell-221"></td>
						<td id="table-cell-256"></td>
						<td id="table-cell-290"></td>
						<td id="table-cell-325"></td>
					</tr>
					<tr>
						<td>17:30-19:00</td>
						<td id="table-cell-12"></td>
						<td id="table-cell-47"></td>
						<td id="table-cell-82"></td>
						<td id="table-cell-117"></td>
						<td id="table-cell-152"></td>
						<td id="table-cell-187"></td>
						<td id="table-cell-222"></td>
						<td id="table-cell-257"></td>
						<td id="table-cell-291"></td>
						<td id="table-cell-326"></td>
					</tr>
					<tr>
						<td>19:15-20:45</td>
						<td id="table-cell-13"></td>
						<td id="table-cell-48"></td>
						<td id="table-cell-83"></td>
						<td id="table-cell-118"></td>
						<td id="table-cell-153"></td>
						<td id="table-cell-188"></td>
						<td id="table-cell-223"></td>
						<td id="table-cell-258"></td>
						<td id="table-cell-292"></td>
						<td id="table-cell-327"></td>
					</tr>
					
					<tr class="new-day">
						<td>Mittwoch</td>
					</tr>
					<tr>
						<td>08:00-09:30</td>
						<td id="table-cell-14"></td>
						<td id="table-cell-49"></td>
						<td id="table-cell-84"></td>
						<td id="table-cell-119"></td>
						<td id="table-cell-154"></td>
						<td id="table-cell-189"></td>
						<td id="table-cell-224"></td>
						<td id="table-cell-259"></td>
						<td id="table-cell-293"></td>
						<td id="table-cell-328"></td>
					</tr>
					<tr>
						<td>09:45-11:15</td>
						<td id="table-cell-15"></td>
						<td id="table-cell-50"></td>
						<td id="table-cell-85"></td>
						<td id="table-cell-120"></td>
						<td id="table-cell-155"></td>
						<td id="table-cell-190"></td>
						<td id="table-cell-225"></td>
						<td id="table-cell-260"></td>
						<td id="table-cell-294"></td>
						<td id="table-cell-330"></td>
					</tr>
					<tr>
						<td>12:15-13:45</td>
						<td id="table-cell-16"></td>
						<td id="table-cell-51"></td>
						<td id="table-cell-86"></td>
						<td id="table-cell-121"></td>
						<td id="table-cell-156"></td>
						<td id="table-cell-191"></td>
						<td id="table-cell-226"></td>
						<td id="table-cell-261"></td>
						<td id="table-cell-295"></td>
						<td id="table-cell-331"></td>
					</tr>
					<tr>
						<td>14:00-15:30</td>
						<td id="table-cell-17"></td>
						<td id="table-cell-52"></td>
						<td id="table-cell-87"></td>
						<td id="table-cell-122"></td>
						<td id="table-cell-157"></td>
						<td id="table-cell-192"></td>
						<td id="table-cell-227"></td>
						<td id="table-cell-262"></td>
						<td id="table-cell-296"></td>
						<td id="table-cell-332"></td>
					</tr>
					<tr>
						<td>15:45-17:15</td>
						<td id="table-cell-18"></td>
						<td id="table-cell-53"></td>
						<td id="table-cell-88"></td>
						<td id="table-cell-123"></td>
						<td id="table-cell-158"></td>
						<td id="table-cell-193"></td>
						<td id="table-cell-228"></td>
						<td id="table-cell-263"></td>
						<td id="table-cell-297"></td>
						<td id="table-cell-333"></td>
					</tr>
					<tr>
						<td>17:30-19:00</td>
						<td id="table-cell-19"></td>
						<td id="table-cell-54"></td>
						<td id="table-cell-89"></td>
						<td id="table-cell-124"></td>
						<td id="table-cell-159"></td>
						<td id="table-cell-194"></td>
						<td id="table-cell-229"></td>
						<td id="table-cell-264"></td>
						<td id="table-cell-298"></td>
						<td id="table-cell-334"></td>
					</tr>
					<tr>
						<td>19:15-20:45</td>
						<td id="table-cell-20"></td>
						<td id="table-cell-55"></td>
						<td id="table-cell-90"></td>
						<td id="table-cell-125"></td>
						<td id="table-cell-160"></td>
						<td id="table-cell-195"></td>
						<td id="table-cell-230"></td>
						<td id="table-cell-265"></td>
						<td id="table-cell-299"></td>
						<td id="table-cell-335"></td>
					</tr>
					
					<tr class="new-day">
						<td>Donnerstag</td>
					</tr>
					<tr>
						<td>08:00-09:30</td>
						<td id="table-cell-21"></td>
						<td id="table-cell-56"></td>
						<td id="table-cell-91"></td>
						<td id="table-cell-126"></td>
						<td id="table-cell-161"></td>
						<td id="table-cell-196"></td>
						<td id="table-cell-231"></td>
						<td id="table-cell-266"></td>
						<td id="table-cell-300"></td>
						<td id="table-cell-336"></td>
					</tr>
					<tr>
						<td>09:45-11:15</td>
						<td id="table-cell-22"></td>
						<td id="table-cell-57"></td>
						<td id="table-cell-92"></td>
						<td id="table-cell-127"></td>
						<td id="table-cell-162"></td>
						<td id="table-cell-197"></td>
						<td id="table-cell-232"></td>
						<td id="table-cell-267"></td>
						<td id="table-cell-301"></td>
						<td id="table-cell-337"></td>
					</tr>
					<tr>
						<td>12:15-13:45</td>
						<td id="table-cell-23"></td>
						<td id="table-cell-58"></td>
						<td id="table-cell-93"></td>
						<td id="table-cell-128"></td>
						<td id="table-cell-163"></td>
						<td id="table-cell-198"></td>
						<td id="table-cell-233"></td>
						<td id="table-cell-268"></td>
						<td id="table-cell-302"></td>
						<td id="table-cell-338"></td>
					</tr>
					<tr>
						<td>14:00-15:30</td>
						<td id="table-cell-24"></td>
						<td id="table-cell-59"></td>
						<td id="table-cell-94"></td>
						<td id="table-cell-129"></td>
						<td id="table-cell-164"></td>
						<td id="table-cell-199"></td>
						<td id="table-cell-234"></td>
						<td id="table-cell-269"></td>
						<td id="table-cell-303"></td>
						<td id="table-cell-339"></td>
					</tr>
					<tr>
						<td>15:45-17:15</td>
						<td id="table-cell-25"></td>
						<td id="table-cell-60"></td>
						<td id="table-cell-95"></td>
						<td id="table-cell-130"></td>
						<td id="table-cell-165"></td>
						<td id="table-cell-200"></td>
						<td id="table-cell-235"></td>
						<td id="table-cell-270"></td>
						<td id="table-cell-304"></td>
						<td id="table-cell-340"></td>
					</tr>
					<tr>
						<td>17:30-19:00</td>
						<td id="table-cell-26"></td>
						<td id="table-cell-61"></td>
						<td id="table-cell-96"></td>
						<td id="table-cell-131"></td>
						<td id="table-cell-166"></td>
						<td id="table-cell-201"></td>
						<td id="table-cell-236"></td>
						<td id="table-cell-271"></td>
						<td id="table-cell-305"></td>
						<td id="table-cell-341"></td>
					</tr>
					<tr>
						<td>19:15-20:45</td>
						<td id="table-cell-27"></td>
						<td id="table-cell-62"></td>
						<td id="table-cell-97"></td>
						<td id="table-cell-132"></td>
						<td id="table-cell-167"></td>
						<td id="table-cell-202"></td>
						<td id="table-cell-237"></td>
						<td id="table-cell-272"></td>
						<td id="table-cell-306"></td>
						<td id="table-cell-342"></td>
					</tr>
					
					<tr class="new-day">
						<td>Freitag</td>
					</tr>
					<tr>
						<td>08:00-09:30</td>
						<td id="table-cell-28"></td>
						<td id="table-cell-63"></td>
						<td id="table-cell-98"></td>
						<td id="table-cell-133"></td>
						<td id="table-cell-168"></td>
						<td id="table-cell-203"></td>
						<td id="table-cell-238"></td>
						<td id="table-cell-273"></td>
						<td id="table-cell-307"></td>
						<td id="table-cell-343"></td>
					</tr>
					<tr>
						<td>09:45-11:15</td>
						<td id="table-cell-29"></td>
						<td id="table-cell-64"></td>
						<td id="table-cell-99"></td>
						<td id="table-cell-134"></td>
						<td id="table-cell-169"></td>
						<td id="table-cell-204"></td>
						<td id="table-cell-239"></td>
						<td id="table-cell-273"></td>
						<td id="table-cell-308"></td>
						<td id="table-cell-344"></td>
					</tr>
					<tr>
						<td>12:15-13:45</td>
						<td id="table-cell-30"></td>
						<td id="table-cell-65"></td>
						<td id="table-cell-100"></td>
						<td id="table-cell-135"></td>
						<td id="table-cell-170"></td>
						<td id="table-cell-205"></td>
						<td id="table-cell-240"></td>
						<td id="table-cell-274"></td>
						<td id="table-cell-309"></td>
						<td id="table-cell-345"></td>
					</tr>
					<tr>
						<td>14:00-15:30</td>
						<td id="table-cell-31"></td>
						<td id="table-cell-66"></td>
						<td id="table-cell-101"></td>
						<td id="table-cell-136"></td>
						<td id="table-cell-171"></td>
						<td id="table-cell-206"></td>
						<td id="table-cell-241"></td>
						<td id="table-cell-275"></td>
						<td id="table-cell-310"></td>
						<td id="table-cell-346"></td>
					</tr>
					<tr>
						<td>15:45-17:15</td>
						<td id="table-cell-32"></td>
						<td id="table-cell-67"></td>
						<td id="table-cell-102"></td>
						<td id="table-cell-137"></td>
						<td id="table-cell-172"></td>
						<td id="table-cell-207"></td>
						<td id="table-cell-242"></td>
						<td id="table-cell-276"></td>
						<td id="table-cell-311"></td>
						<td id="table-cell-347"></td>
					</tr>
					<tr>
						<td>17:30-19:00</td>
						<td id="table-cell-33"></td>
						<td id="table-cell-68"></td>
						<td id="table-cell-103"></td>
						<td id="table-cell-138"></td>
						<td id="table-cell-173"></td>
						<td id="table-cell-208"></td>
						<td id="table-cell-243"></td>
						<td id="table-cell-277"></td>
						<td id="table-cell-312"></td>
						<td id="table-cell-348"></td>
					</tr>
					<tr>
						<td>19:15-20:45</td>
						<td id="table-cell-34"></td>
						<td id="table-cell-69"></td>
						<td id="table-cell-104"></td>
						<td id="table-cell-139"></td>
						<td id="table-cell-174"></td>
						<td id="table-cell-209"></td>
						<td id="table-cell-244"></td>
						<td id="table-cell-278"></td>
						<td id="table-cell-313"></td>
						<td id="table-cell-349"></td>
					</tr>
				</tbody>
			</table>
			
			
			
			
			
			
			<!-- div id='calendar'></div-->
			
			
			
			
			
			
			
			
			
			
			
			
			
			
		</div>
    </div>
  </body>
</html>
