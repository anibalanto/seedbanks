'use strict';

angular.module('seedbanksApp')
    .controller('VarietyController', function ($scope, Variety, Harvest) {
        $scope.varietys = [];
        $scope.harvests = Harvest.query();
        $scope.loadAll = function() {
            Variety.query(function(result) {
               $scope.varietys = result;
            });
        };
        $scope.loadAll();

        $scope.create = function () {
            Variety.save($scope.variety,
                function () {
                    $scope.loadAll();
                    $('#saveVarietyModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            Variety.get({id: id}, function(result) {
                $scope.variety = result;
                $('#saveVarietyModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            Variety.get({id: id}, function(result) {
                $scope.variety = result;
                $('#deleteVarietyConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Variety.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteVarietyConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.variety = {name: null, id: null};
        };

    });
