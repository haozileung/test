var app = angular.module('blogApp', [ 'ui.router', 'ui.bootstrap' ]);
// Route Config
app.config(function($stateProvider, $urlRouterProvider) {
	$stateProvider.state('greeting', {
		url : '/greeting',
		views : {
			'g' : {
				templateUrl : 'template/home.html',
				controller : 'homeCtrl'
			},
			't' : {
				templateUrl : 'template/test.html',
				controller : 'testCtrl'
			}
		}
	});
	//$urlRouterProvider.otherwise('#');
});
// Controllers
app.controller('homeCtrl', function($scope) {
	console.log('in g');
	$scope.greeting = "Hello World!";
}).controller('testCtrl', function($scope) {
	console.log('in t');
	$scope.test = "TEST";
});