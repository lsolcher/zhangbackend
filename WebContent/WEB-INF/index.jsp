<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html ng-app="zhang-app">
  <head>
    <meta charset="utf-8">
    <title>Test</title>

    <!-- styles -->
    <spring:url var ="bootstrapcss" value="/resources/css/bootstrap.css" />
    <link rel="stylesheet" href="${bootstrapcss}"/>
    <spring:url var ="style" value="/resources/css/style2.css" />
    <link rel="stylesheet" href="${style}">
    <!-- end of styles -->




    <!-- libs -->
  	<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
	<script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
	<script src="//ajax.googleapis.com/ajax/libs/angularjs/1.3.15/angular.js"></script>
	<script src="//ajax.googleapis.com/ajax/libs/angularjs/1.2.0rc1/angular-route.min.js"></script>
	<spring:url var ="bootstrap" value="/resources/js/bootstrap.js" />
  	<script type="text/javascript" src="${bootstrap}"></script>

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
							<a href="calendar.html">zur Eingabe der Lehrkraftwünsche</a>
						</li>
					</ul>
				</nav>
			</div>
	</header>
  	<div class="row">
		<div class="main group">
			<h2>Willkommen beim Tool zur automatischen Lehreinsatzplanung!</h1>
			
			<p>Sie haben hier die Möglichkeit dieses und solches und jenes zu tun. ... Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.</p>
			
			<button>Administratorzugriff</button>
			<button>Eingabe der Wünsche der Lehrkräfte</button>
		</div>
    </div>
  </body>
</html>
