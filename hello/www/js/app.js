// Ionic Starter App

// angular.module is a global place for creating, registering and retrieving Angular modules
// 'starter' is the name of this angular module example (also set in a <body> attribute in index.html)
// the 2nd parameter is an array of 'requires'
angular.module('starter', ['ionic'])

.run(function($ionicPlatform) {
  $ionicPlatform.ready(function() {
    if(window.cordova && window.cordova.plugins.Keyboard) {
      // Hide the accessory bar by default (remove this to show the accessory bar above the keyboard
      // for form inputs)
      cordova.plugins.Keyboard.hideKeyboardAccessoryBar(true);

      // Don't remove this line unless you know what you are doing. It stops the viewport
      // from snapping when text inputs are focused. Ionic handles this internally for
      // a much nicer keyboard experience.
      cordova.plugins.Keyboard.disableScroll(true);
    }
    if(window.StatusBar) {
      StatusBar.styleDefault();
    }
  });
})

.factory('Tasks', function($http) {
  var base = "http://localhost:3000";
  return {
    all: function() {
      return $http.get(base + "/task");
    },
    save: function(task) {
      return $http.post(base + "/task", {title: task.title});
    },
    update: function(task) {
      return $http.patch(base + "/task", {conditions:{title: task.title}, updates: {$set:{checked: task.checked}}});
    }
  }
})

.controller('TodoCtrl', function($scope, $ionicModal, Tasks) {
  Tasks.all()
    .success(function(tasks){
      $scope.tasks = tasks;
      console.log($scope.tasks);
    })
    .error(function(){
      $scope.tasks = [];
    })
  // Called when the form is submitted
  $scope.createTask = function(task) {
    $scope.tasks.push({
      title: task.title,
      checked: false
    });
    Tasks.save(task)
      .success(function(task){
        console.log(task);
      })
      .error(function(){
        console.log("request error");
      });
  };
  $scope.checkedListener = function(task) {
    Tasks.update(task);
  }
  $scope.delLine = function(task) {
    if (task.checked) {
      return {"text-decoration": "line-through"};
    }
    else {
      return {};
    }
  }
})


