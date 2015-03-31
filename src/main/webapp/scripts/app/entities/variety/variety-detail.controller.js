'use strict';

angular.module('seedbanksApp')
    .controller('VarietyDetailController', function ($scope, $stateParams, Variety, Harvest) {
        $scope.variety = {};
        $scope.load = function (id) {
	    Variety.get({id: id}, function(result) {
              $scope.variety = result;
            });
	    Variety.query({id:id, verb:'harvests'}, function(result) {
               $scope.harvests = result;
            });
        };

        $scope.load($stateParams.id);
    });
