'use strict';

angular.module('seedbanksApp')
    .controller('HarvestDetailController', function ($scope, $stateParams, Harvest, User, Interchange, Variety) {
        $scope.harvest = {};
        $scope.load = function (id) {
            Harvest.get({id: id}, function(result) {
                $scope.harvest = result;
            });
            Harvest.query({id:id, verb:'interchanges'}, function(result) {
                $scope.interchanges = result;
            });
        }
        $scope.load($stateParams.id);
    });
