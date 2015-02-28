'use strict';

angular.module('seedbanksApp')
    .controller('VarietyDetailController', function ($scope, $stateParams, Variety, Harvest) {
        $scope.variety = {};
        $scope.load = function (id) {
            Variety.get({id: id}, function(result) {
              $scope.variety = result;
            });
        };
        $scope.load($stateParams.id);
    });
