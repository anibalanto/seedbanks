'use strict';

angular.module('seedbanksApp')
    .controller('HarvestController', function ($scope, Harvest, User, Interchange, Variety) {
        $scope.harvests = [];
        $scope.users = User.query();
        $scope.interchanges = Interchange.query();
        $scope.varietys = Variety.query();
        $scope.loadAll = function() {
            Harvest.query(function(result) {
               $scope.harvests = result;
            });
        };
        $scope.loadAll();

        $scope.create = function () {
            Harvest.save($scope.harvest,
                function () {
                    $scope.loadAll();
                    $('#saveHarvestModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            Harvest.get({id: id}, function(result) {
                $scope.harvest = result;
                $('#saveHarvestModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            Harvest.get({id: id}, function(result) {
                $scope.harvest = result;
                $('#deleteHarvestConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Harvest.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteHarvestConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.harvest = {codeValidator: null, date: null, shared: null, id: null};
        };
    });
