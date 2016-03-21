angular.module('qianduan.routes', [])

.config(function($stateProvider, $urlRouterProvider) {

  // Ionic uses AngularUI Router which uses the concept of states
  // Learn more here: https://github.com/angular-ui/ui-router
  // Set up the various states which the app can be in.
  // Each state's controller can be found in controllers.js
  $stateProvider

  // setup an abstract state for the tabs directive
    .state('tab', {
    url: '/tab',
    abstract: true,
    templateUrl: 'templates/tabs.html'
  })

  // Each tab has its own nav history stack:

  .state('tab.home', {
    url: '/home',
    views: {
      'tab-home': {
        templateUrl: 'templates/tab-home.html',
        controller: 'ListCtrl'
      }
    }
  })
  .state('homeDetail', {
      url: '/homeDetail/:homeId',
      templateUrl: 'templates/list-detail.html',
      controller: 'ListDetailCtrl'
  })

  .state('tab.css3', {
    url: '/css3',
    views: {
      'tab-css3': {
        templateUrl: 'templates/tab-css3.html',
        controller: 'Css3Ctrl'
      }
    }
  })
  .state('css3Detail', {
      url: '/css3Detail/:css3Id',
      templateUrl: 'templates/css3-detail.html',
      controller: 'Css3DetailCtrl'
  })

  .state('tab.html5', {
      url: '/html5',
      views: {
        'tab-html5': {
          templateUrl: 'templates/tab-html5.html',
          controller: 'Html5Ctrl'
        }
      }
    })
    

  .state('tab.javascript', {
    url: '/javascript',
    views: {
      'tab-javascript': {
        templateUrl: 'templates/tab-javascript.html',
        controller: 'JavascriptCtrl'
      }
    }
  })
  .state('javascriptDetail', {
      url: '/javascriptDetail/:javascriptId',
      templateUrl: 'templates/javascript-detail.html',
      controller: 'JavascriptDetailCtrl'
  });

  // if none of the above states are matched, use this as the fallback
  $urlRouterProvider.otherwise('/tab/css3');
});