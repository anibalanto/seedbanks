'use strict';

angular.module('seedbanksApp')
    .controller('HarvestDetailController', function ($scope, $stateParams, Harvest, User, Interchange, Variety) {
        $scope.harvest = {};
        $scope.load = function (id) {
            Harvest.get({id: id}, function(result) {
              $scope.harvest = result;
            });
        };
        $scope.load($stateParams.id);
    });
