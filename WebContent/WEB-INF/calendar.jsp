<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html ng-app="zhang-app">
<head>
<meta charset="utf-8">
<title>Kalender</title>

<!-- styles -->
<spring:url value="/resources/css/style.css" var="style" />
<link href="${style}" rel="stylesheet" />
<!-- end of styles -->


<!-- libs -->
<script src="http://code.jquery.com/jquery-1.10.2.js"></script>
<script src="http://code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
<script
	src="http://ajax.googleapis.com/ajax/libs/angularjs/1.3.15/angular.js"></script>

<script
	src="http://ajax.googleapis.com/ajax/libs/angularjs/1.2.0rc1/angular-route.min.js"></script>
<spring:url var ="courses" value="/resources/js/courses.js" />
<script src="${courses}"></script>
<spring:url var ="priorities" value="/resources/js/priorities.js" />
<script src="${priorities}"></script>
<spring:url var ="calendar" value="/resources/js/calendar.js" />
<script src="${calendar}"></script>
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
					<a href="/" class="navbar-brand">StundenplanBaukasten</a>
				</div>
				<nav class="collapse navbar-collapse" role="navigation">
					<ul class="nav navbar-nav pull-right">
						<li>
							<a href="#sec">Wunschkonzert</a>
						</li>
						<li>
							<a href="#sec">Puzzle Bar</a>
						</li>
						<li>
							<a href="#sec">Kleinster Gemeinsamer Nenner</a>
						</li>
					</ul>
				</nav>
			</div>
	</header>
	<div class="main group">
		<div class="calendar group">
			<h2>Bevorzugte Belegungszeiten</h2>
			<div class="option-choice group">
				<a href="#" class="preferred" prio="3">Bevorzugte Wahl</a>
				<a href="#" class="alternative" prio="2">Alternative Wahl</a>
        <a href="#" class="impossible" prio="1">wenns sein muss</a>
				<a href="#" class="no-pref-choice" prio="0">nicht möglich</a>
			</div>
			<div class="calendar-body group">
				<div class="first-calendar-row">
					<span class="week-day">MO</span>
					<span class="week-day">DI</span>
					<span class="week-day">MI</span>
					<span class="week-day">DO</span>
					<span class="week-day">FR</span>
				</div>
				<div class="calendar-row">
					<span class="lecture-time">08:00 - 09:30</span>
					<div class="calendar-input" prio="1"></div>
					<div class="calendar-input" prio="1"></div>
			        <div class="calendar-input" prio="1"></div>
			        <div class="calendar-input" prio="1"></div>
			        <div class="calendar-input" prio="1"></div>
				</div>
				<div class="calendar-row">
					<span class="lecture-time">09:45 - 11:15</span>
					<div class="calendar-input" prio="1"></div>
			        <div class="calendar-input" prio="1"></div>
			        <div class="calendar-input" prio="1"></div>
			        <div class="calendar-input" prio="1"></div>
			        <div class="calendar-input" prio="1"></div>
				</div>
				<div class="calendar-row">
					<span class="lecture-time">12:15 - 13:45</span>
			        <div class="calendar-input" prio="1"></div>
			        <div class="calendar-input" prio="1"></div>
			        <div class="calendar-input" prio="1"></div>
			        <div class="calendar-input" prio="1"></div>
			        <div class="calendar-input" prio="1"></div>
				</div>
				<div class="calendar-row">
					<span class="lecture-time">14:00 - 15:30</span>
			        <div class="calendar-input" prio="1"></div>
			        <div class="calendar-input" prio="1"></div>
			        <div class="calendar-input" prio="1"></div>
			        <div class="calendar-input" prio="1"></div>
			        <div class="calendar-input" prio="1"></div>
				</div>
				<div class="calendar-row">
					<span class="lecture-time">15:45 - 17:15</span>
			        <div class="calendar-input" prio="1"></div>
			        <div class="calendar-input" prio="1"></div>
			        <div class="calendar-input" prio="1"></div>
			        <div class="calendar-input" prio="1"></div>
			        <div class="calendar-input" prio="1"></div>
				</div>
				<div class="calendar-row">
					<span class="lecture-time">17:30 - 19:00</span>
			        <div class="calendar-input" prio="1"></div>
			        <div class="calendar-input" prio="1"></div>
			        <div class="calendar-input" prio="1"></div>
			        <div class="calendar-input" prio="1"></div>
			        <div class="calendar-input" prio="1"></div>
				</div>
			</div>
		</div>
		<div class="wishes-section group"></div>
  </div>
	</body>
</html>
