'use strict';

angular.module('seedbanksApp')
    .controller('UserDetailController', function ($scope, $stateParams, User, Interchange, Harvest) {
        $scope.user = {};
        $scope.load = function (id) {
            User.get({id: id}, function(result) {
              $scope.user = result;
            });
        };
        $scope.load($stateParams.id);
    });
