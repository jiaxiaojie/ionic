angular.module('qianduan.controllers', [])

.controller('HomeCtrl', function($scope) {})

// ListCtrl

.controller('ListCtrl', ['$scope','$http','$state',function($scope,$http,$state) {

  $scope.list = [];

  $http.get("templates/css3/detail.json").then(function(res){
      $scope.list = res.data;
  });

  $scope.listDetails = function(css3) {
      $state.go("css3Detail", {
          "css3Id": css3
      });
  };

}])

.controller('ListDetailCtrl', function($scope, $stateParams,$http,$sce) {

  $http.get("templates/css3/detail.json").then(function(res){
      $scope.entity = res.data[$stateParams.css3Id];
      $http.get($scope.entity.url).success(function(res){
         $scope.html = $sce.trustAsHtml(res.replace(/</g,'&lt;').replace(/>/g,'&gt;'));
      });
  });

})

// JavascriptCtrl
.controller('JavascriptCtrl', ['$scope','$http','$state',function($scope,$http,$state) {

  $scope.list = [];

  $http.get("templates/javascript/detail.json").then(function(res){
      $scope.list = res.data;
  });

  $scope.javascriptDetails = function(javascript) {
      $state.go("javascriptDetail", {
          "javascriptId": javascript
      });
  };

}])

.controller('JavascriptDetailCtrl', function($scope, $stateParams,$http,$sce) {

  $http.get("templates/javascript/detail.json").then(function(res){
      $scope.entity = res.data[$stateParams.javascriptId];
      $http.get($scope.entity.url).success(function(res){
         $scope.html = $sce.trustAsHtml(res.replace(/</g,'&lt;').replace(/>/g,'&gt;'));
      });
  });

})

// Html5Ctrl
.controller('Html5Ctrl', function($scope) {});
