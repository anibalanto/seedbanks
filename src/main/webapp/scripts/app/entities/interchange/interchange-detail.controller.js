'use strict';

angular.module('seedbanksApp')
    .controller('InterchangeDetailController', function ($scope, $stateParams, Interchange, User, Harvest) {
        $scope.interchange = {};
        $scope.load = function (id) {
            Interchange.get({id: id}, function(result) {
              $scope.interchange = result;
            });
        };
        $scope.load($stateParams.id);
    });
