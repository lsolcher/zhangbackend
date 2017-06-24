<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html ng-app="zhang-app">
  <head>
    <meta charset="utf-8">
    <title>LEP-Tool</title>

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
  	<spring:url var ="courses" value="/resources/js/courses.js" />
  	<script type="text/javascript" src="${courses}"></script>
  	<spring:url var ="priorities" value="/resources/js/priorities.js" />
    <script type="text/javascript" src="${priorities}"></script>
    <spring:url var ="calendar" value="/resources/js/calendar.js" />
  	<script type="text/javascript" src="${calendar}"></script>
  	<spring:url var ="bootstrap" value="/resources/js/bootstrap.js" />
  	<script type="text/javascript" src="${bootstrap}"></script>
	   <!-- libs end -->

  </head>
  <body>

    <div class="course-selector-wrapper">
      <div class="course-selector" ng-controller="courseController">
        <span>Bitte ihre Lehrveranstaltungen auswählen</span>
        <script>
        	var initCourses = '<% String veranstaltungen = (String) request.getAttribute("veranstaltungen"); out.print(veranstaltungen); %>';
        	try {
        		initCourses = JSON.parse(initCourses);
        	} catch(e) {
        		//console.log('NOOO', initCourses);
        		console.log(e.stack);
        	}
        </script>
        <div class="course-list">
          <input type="search" id="course-list-search" ng-model="search" placeholder="Durchsuche Kurse">
          <div class="course-scroll">
            <div class="course" ng-repeat="course in list | filter:search">
              <input type="checkbox" ng-model="course.selected" ng-change="selectCourse($event)">
              <span>{{course.id}}</span> - {{course.kurzname}}
            </div>
          </div>
        </div>

        <div class="selected-courses">
          <div class="selected-course" ng-repeat="course in list | filter: {selected: 'true'}">
            <span>{{course.id}}  </span><span ng-click="selectVal($event)">{{course.kurzname}}</span>
          </div>
        </div>

        <button type="button" name="button" id="course-list-next" class="btn btn-default">Weiter</button>

      </div>
    </div>

    <header>
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
            <li><a href="#" id="add-course">Kurse bearbeiten</a></li>
						<li><a href="#" id="logout">Logout</a></li>
					</ul>
				</nav>
			</div>
    </header>
    <div class="container">
      <div class="row">

        <div class="col-md-6">
          <h2>Kalenderauswahl</h2>
          <div class="calendar-legend">
    				<a href="#" class="preferred" prio="3">Bevorzugte Wahl</a>
    				<a href="#" class="alternative" prio="2">Alternative Wahl</a>
            <a href="#" class="impossible" prio="1">Grundsätzlich möglich</a>
            <a href="#" class="no-pref-choice" prio="0">Nicht möglich</a>
          </div>
          <div class="calendar-content" ng-controller="prioController">
            <div class="week-days">
              <div class="week-day">MO</div>
              <div class="week-day">DI</div>
              <div class="week-day">MI</div>
              <div class="week-day">DO</div>
              <div class="week-day">FR</div>
              <div class="week-day">SA</div>
            </div>
            <div class="week-times">
              <div class="week-time">08:00 - 09:30</div>
              <div class="week-time">09:45 - 11:15</div>
              <div class="week-time">12:15 - 13:45</div>
              <div class="week-time">14:00 - 15:30</div>
              <div class="week-time">15:45 - 17:15</div>
              <div class="week-time">17:30 - 19:00</div>
              <div class="week-time">19:15 - 20:45</div>
            </div>
            <div class="calendar-body group" ng-init="calendar">
              <div class="calendar-input" ng-repeat="time in calendar track by $index" ng-click="updateCalendar(calendar, $index);" value="{{$index}}" data-prio="{{time}}"></div>
            </div>
          </div>
        </div>

        <div class="col-md-6">
          <h2>Sonderwünsche</h2>
          <div class="wishes-section" ng-controller="prioController">

            <p>Bitte geben Sie hier gegebenenfalls spezielle Einschränkungen oder Sonderwünsche hinsichtlich Ihrer Verfügbarkeiten an.
            Ihre Auswahl im Belegungsplan wird entsprechend dieser Vorgaben angepasst. Um eine Einschränkung oder einen Sonderwunsch wieder zu entfernen,
            können Sie diese über den x-Button löschen. Als Grundlage zum Erstellen des Stundenplanes wird der Belegungsplan, sowie ggf. die hier definierten
            Einschränkungen und Sonderwünsche herangezogen.</p>

            <div class="priority-select-list">
              <div class="priority-select-list-entry" ng-repeat="option in possiblePriorities" ng-click="selectPrio($index, option)">
                <div class="title">
                  {{option.title}}
                </div>
              </div>
              <button type="button" class="btn btn-default" id="priorities-submit-button" ng-click="save()">Speichern</button>
            </div>

            <h4>Ausgewählte Sonderwünsche:</h4>
            <div class="selected-priorities">
              <ul id="priority-list" ng-repeat="prio in $root.selectedPriorities track by $index" priority>
                <li class="priority-entry">
                  <div class="priority-label" ng-click="prio.hideContent ? prio.hideContent=false : prio.hideContent=true">{{prio.title}}</div>
                  <div class="priority-delete" ng-click="removeEntry($index, prio)">x</div>
                  <div class="priority-container" ng-hide="prio.hideContent">
                    <div ng-if="prio.type == 'SingleChoicePrio'" class="priority-content">
                      <span class="priotext">{{prio.text[0]}}</span>
                        <select ng-model="prio.singlechoice" ng-change="change(prio.singlechoice)" required>
                          <option ng-repeat="option in prio.options" value="{{$index}}">{{option}}</option>
                        </select>
                        <span ng-show="myForm.prio.singlechoice.$touched && myForm.prio.singlechoice.$invalid">The name is required.</span>
                      <span class="priotext">{{prio.text[1]}}</span>
                    </div>
                    <div ng-if="prio.type == 'SimplePrio'" class="priority-content">
                      <span class="priotext">{{prio.text}}</span>
                    </div>
                    <div ng-if="prio.type == 'ExcludeDayCombinationPrio' && prio.text.length <= 3" class="priority-content">
                      <span class="priotext">{{prio.text[0]}}</span>
                      <select name="day" ng-change="change(prio.dayOne[0])" ng-model="prio.dayOne[0]" required>
                          <option value="Ersten Tag wählen">Ersten Tag wählen</option>
                          <option value="0">Montag</option>
                          <option value="1">Dienstag</option>
                          <option value="2">Mittwoch</option>
                          <option value="3">Donnerstag</option>
                          <option value="4">Freitag</option>
                      </select>
                      <span class="priotext">{{prio.text[1]}}</span>
                      <select name="day" ng-change="change(prio.dayTwo)" ng-model="prio.dayTwo" required>
                          <option value="Zweiten Tag wählen">Zweiten Tag wählen</option>
                          <option value="0">Montag</option>
                          <option value="1">Dienstag</option>
                          <option value="2">Mittwoch</option>
                          <option value="3">Donnerstag</option>
                          <option value="4">Freitag</option>
                      </select>
                      <span class="priotext">{{prio.text[2]}}</span>
                    </div>
                    <div ng-if="prio.type == 'ExcludeDayCombinationPrio' && prio.text.length > 3" class="priority-content">
                      <span class="priotext">{{prio.text[0]}}</span>
                      <select ng-change="change(prio.dayOne)" ng-model="prio.dayOne" required>
                          <option value="Ersten Tag wählen">Ersten Tag wählen</option>
                          <option value="0">Montag</option>
                          <option value="1">Dienstag</option>
                          <option value="2">Mittwoch</option>
                          <option value="3">Donnerstag</option>
                          <option value="4">Freitag</option>
                      </select>
                      <span class="priotext">{{prio.text[1]}}</span>
                      <select ng-change="change(prio.dayOne[1])" ng-model="prio.dayOne[1]" required>
                          <option value="Uhrzeit wählen">Uhrzeit wählen</option>
                          <option value="0">08:00-09:30</option>
                          <option value="1">09:45-11:15</option>
                          <option value="2">12:15-13:45</option>
                          <option value="3">14:00-15:30</option>
                          <option value="4">15:45-17:15</option>
                          <option value="5">17:30-19:00</option>
                          <option value="6">19:15-20:45</option>
                      </select>
                      <span class="priotext">{{prio.text[2]}}</span>
                      <select ng-change="change(prio.dayTwo)" ng-model="prio.dayTwo" required>
                          <option value="Zweiten Tag wählen">Zweiten Tag wählen</option>
                          <option value="0">Montag</option>
                          <option value="1">Dienstag</option>
                          <option value="2">Mittwoch</option>
                          <option value="3">Donnerstag</option>
                          <option value="4">Freitag</option>
                      </select>
                      <span class="priotext">{{prio.text[3]}}</span>
                      <select ng-change="change(prio.timeTwo)" class="time second-time" ng-model="prio.timeTwo" required>
                          <option value="Uhrzeit wählen">Uhrzeit wählen</option>
                          <option value="0">08:00-09:30</option>
                          <option value="1">09:45-11:15</option>
                          <option value="2">12:15-13:45</option>
                          <option value="3">14:00-15:30</option>
                          <option value="4">15:45-17:15</option>
                          <option value="5">17:30-19:00</option>
                          <option value="6">19:15-20:45</option>
                      </select>
                      <span class="priotext">{{prio.text[4]}}</span>
                    </div>
                    <div ng-if="prio.type == 'FreeTextInput'" class="priority-content">
                      <span class="priotext">{{prio.text}}</span>
                      <textarea id="freeTextWish" name="freeTextWish" cols="35" rows="4" ng-change="change($event)" ng-model="prio.freetext"></textarea>
                    </div>

                    <div class="prio-course-select" ng-show="prio.showCourses">
                      <select ng-change="changeCourse(prio.course)" class="select-course" ng-model="prio.course" required>
                        <option value="Alle Kurse">Alle Kurse</option>
                        <option ng-repeat="course in $root.courseList" value="{{course.id}}">{{course.kurzname}}</option>
                      </select>
                    </div>
                  </div>

                </li>
              </ul>
            </div>
          </div>
        </div>

      </div>
    </div>

  </body>
</html>
