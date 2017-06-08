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
  
    <link rel='stylesheet' href='https://cdnjs.cloudflare.com/ajax/libs/fullcalendar/2.6.0/fullcalendar.css' />
    <link rel='stylesheet' href='https://cdnjs.cloudflare.com/ajax/libs/fullcalendar/3.4.0/fullcalendar.min.css' />
	<style>

		.main {
			margin: 40px 10px;
			padding: 0;
			font-family: "Lucida Grande",Helvetica,Arial,Verdana,sans-serif;
			font-size: 14px;
		}
	
		#calendar {
			max-width: 900px;
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


	<!-- spring:url var ="jQuery" value="/resources/js/jquery.min.js" />
  	<script type="text/javascript" src="${jQuery}"></script>
	<spring:url var ="moment" value="/resources/js/moment.min.js" />
  	<script type="text/javascript" src="${moment}"></script>
	<spring:url var ="fullcalendar-min" value="/resources/js/fullcalendar.min.js" />
  	<script type="text/javascript" src="${fullcalendar-min}"></script-->

	<script src='http://code.jquery.com/jquery-1.11.3.min.js'></script>
	<script src='https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.11.1/moment.min.js'></script>
	<script src='https://cdnjs.cloudflare.com/ajax/libs/fullcalendar/2.6.0/fullcalendar.min.js'></script>

	<script>
	
		$(document).ready(function() {
			
			$('#calendar').fullCalendar({
				header: {
					left: 'prev,next today',
					center: 'title',
					right: 'month,basicWeek,basicDay'
				},
				defaultDate: '2017-05-12',
				navLinks: true, // can click day/week names to navigate views
				editable: true,
				eventLimit: true, // allow "more" link when too many events
				events: [
					{
						title: 'All Day Event',
						start: '2017-05-01'
					},
					{
						id: 999,
						title: 'Repeating Event',
						start: '2017-05-09T16:00:00'
					},
					{
						title: 'Meeting',
						start: '2017-05-12T10:30:00',
						end: '2017-05-12T12:30:00'
					},
					{
						title: 'Lunch',
						start: '2017-05-12T12:00:00'
					}
				]
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
			<div id='calendar'></div>
		</div>
    </div>
  </body>
</html>
