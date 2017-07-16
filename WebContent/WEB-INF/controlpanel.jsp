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
    <script src="//cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.js"></script>
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
					<button class="navbar-toggle" type="button" data-toggle="collapse" data-target=".navbar-collapse">
						<span class="sr-only">Toggle navigation</span>
						<span class="icon-bar"></span>
						<span class="icon-bar"></span>
						<span class="icon-bar"></span>
					</button>
					<a href="" class="navbar-brand"><img src="/ZhangProjectBackend/resources/img/logo.png" width="39" />Lehreinsatzplanung</a>
				</div>
				<nav class="collapse navbar-collapse" role="navigation">
					<ul class="nav navbar-nav pull-right">
                        <li><a href="#" th:inline='text'>Herzlich Willkommen,  ${user.lastName} </a></li>
						<li><a href="/ZhangProjectBackend/logout.html" id="logout" >Logout</a></li>
					</ul>
				</nav>
			</div>
    </header>
    <div class="container content-container">
        <div class="row">
            <div class="col-md-12 prop-wrapper">
                <div ng-controller="additionalPrioController">
                    <h5>Verfügbare Studiengänge: </h5>
                    <div class="priority-select-list">
                        <div class="priority-select-list-entry" ng-repeat="option in possibleAdditionalPriorities track by $index" ng-click="selectPrio($index, option)">
                            <div class="title" data-prio={{option.program}}>
                                {{option.program}}
                            </div>
                        </div>
                    </div>

                    <h5>Zusätzliche Optionen:</h5>
                    <div class="selected-additional-priorities">
                      <ul id="additionalPriority-list" ng-repeat="info in additionalPriorities track by $index" AddPriority>
                        <li class="priority-entry">
                          <div class="priority-container">
                            <div class="priority-content">
                                <span class="priotext">{{info.program}}</span>
                                <div ng-repeat="item in info.props track by $index">
                                    <span>{{item.text}}</span><br>
                                    <select ng-model="item.option" ng-change="changeOption(item.option)" required>
                                        <option ng-repeat="option in item.options">{{option}}</option>
                                    </select>
                                    <spasn>Priorität: </spasn>
                                    <select ng-model="item.prio" ng-change="changePrio(item.prio)" required>
                                        <option ng-repeat="option in item.prios">{{option}}</option>
                                    </select>
                                </div>
                            </div>
                          </div>
                        </li>
                      </ul>

                      <button type="button" class="btn btn-default" id="priorities-save-button" name="button" ng-click="save()">Speichern</button>
                      <form action="<c:url value="/algorithm.html" />" >
                        <button type="submit" class="btn btn-default" id="priorities-submit-button"  name="action">Stundenplan erstellen</button>
                      </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

  </body>
</html>
