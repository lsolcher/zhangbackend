<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
  	<spring:url var ="additionalPriorities" value="/resources/js/additionalPriorities.js" />
    <script type="text/javascript" src="${additionalPriorities}"></script>
  	<spring:url var ="bootstrap" value="/resources/js/bootstrap.js" />
  	<script type="text/javascript" src="${bootstrap}"></script>
	   <!-- libs end -->

  </head>
  <body>

    <header>
      <div class="container">
				<div class="navbar-header">
					<a href="/" class="navbar-brand">HTW FB4 Lehreinsatzplanung</a>
				</div>
				<nav class="collapse navbar-collapse" role="navigation">
					<ul class="nav navbar-nav pull-right">
						<li><a href="#" id="logout">Logout</a></li>
					</ul>
				</nav>
			</div>
    </header>
    <div class="container">
      <div ng-controller="additionalPrioController">

        <h4>Zusätzliche Optionen:</h4>
        <div class="selected-additional-priorities">
          <ul id="additionalPriority-list" ng-repeat="prio in possibleAdditionalPriorities track by $index" priority>
            <li class="priority-entry">
              <div class="priority-container">
                <div class="priority-content">
                  <span class="priotext">{{prio.text}}</span>
                  <select ng-model="prio.additionalPrio" ng-change="change(prio.additionalPrio)" required>
                    <option ng-repeat="option in prio.options" value="{{$index}}">{{option}}</option>
                  </select>
                  <select ng-model="prio.additionalPrio2" ng-change="changePrio(prio.additionalPrio2)" required>
                    <option ng-repeat="option in prio.prio" value="{{$index}}">{{option}}</option>
                  </select>
                  Aktivieren: <input type="checkbox" name="" value="">
                </div>
              </div>
            </li>
          </ul>
          <button type="button" name="button" ng-click="save()">Speichern</button>
		  <form action="<c:url value="/algorithm.html" />" >
		            <button type="submit" name="action">Stundenplan erstellen</button>
		  </form>
        </div>

      </div>
    </div>

  </body>
</html>
