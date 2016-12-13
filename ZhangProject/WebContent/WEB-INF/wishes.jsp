<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html ng-app="zhang-app">
  <head>
    <meta charset="utf-8">
    <title>Sonderwünsche</title>

    <!-- Styles -->
    <spring:url var="stylesheet" value="/resources/css/style.css" />
    <link href="${stylesheet}" rel="stylesheet" />
    <!-- end of Styles -->

    <!-- JS -->
    <script src="http://code.jquery.com/jquery-1.10.2.js"></script>
  	<script src="http://code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
  	<script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.3.15/angular.js"></script>
  	<script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.2.0rc1/angular-route.min.js"></script>
    <spring:url var ="priorities" value="/resources/js/priorities.js" />
	<script src="${priorities}"></script>
    <!-- end of JS -->

  </head>
  <body>
    <header class="group">
      <div class="wrapper group">
        <span class="welcome-message">Guten Tag, Prof. Dr. Käsekuchen!</span>
        <nav class="menu group">
          <ul class="menu-list">
            <li><a href="calendar.html">Kalender</a></li>
            <li>Wünsche</li>
            <li class="profil"><a href="#">Abmelden</a></li>
          </ul>
        </nav>
        <a href="#" class="menu-trigger"> </a>
        <!--a href="#" class="menu-close-btn"> </a-->
      </div>
    </header>
    <div class="calendar group"></div>
    <div class="wishes-section group" ng-controller="prioController">
      <h2>Sonderwünsche</h2>
      <span>Bitte wählen Sie einen oder mehrere Sonderwünsche aus.</span>
      <div class="priority-select-list">
        <div class="priority-select-list-entry" ng-repeat="option in possiblePriorities track by option.id" data-id="{{option.id}}" ng-click="selectPriority($event)">
          <div class="title">
            {{option.label}}
          </div>
        </div>
        <input id="priorities-submit-button" type="button" name="name" value="maxim's submit button">
      </div>

      <div class="selected-priorities">
        <ul id="priority-list">

        </ul>
      </div>
    </div>


    <div id="templates" style="display: none;">
      <div id="template1">
        <select name="day">
            <option>Ersten Tag wählen</option>
            <option value="montag">Montag</option>
            <option value="dienstag">Dienstag</option>
            <option value="mittwoch">Mittwoch</option>
            <option value="donnerstag">Donnerstag</option>
            <option value="freitag">Freitag</option>
        </select>
        <select name="time">
            <option>Zweiten Tag wählen</option>
            <option value="montag">Montag</option>
            <option value="dienstag">Dienstag</option>
            <option value="mittwoch">Mittwoch</option>
            <option value="donnerstag">Donnerstag</option>
            <option value="freitag">Freitag</option>
        </select>
      </div>
    </div>

  </body>
</html>
