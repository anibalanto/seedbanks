'use strict';

angular.module('seedbanksApp')
    .controller('InterchangeController', function ($scope, Interchange, User, Harvest) {
        $scope.interchanges = [];
        $scope.users = User.query();
        $scope.harvests = Harvest.query();
        $scope.loadAll = function() {
            Interchange.query(function(result) {
               $scope.interchanges = result;
            });
        };
        $scope.loadAll();

        $scope.create = function () {
            Interchange.save($scope.interchange,
                function () {
                    $scope.loadAll();
                    $('#saveInterchangeModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            Interchange.get({id: id}, function(result) {
                $scope.interchange = result;
                $('#saveInterchangeModal').modal('show');
            });
        };

        $scope.calificate = function (id) {
            Interchange.get({id: id}, function(result) {
                $scope.interchange = result;
                $('#saveCalificateInterchangeModal').modal('show');
            });
        };

        $scope.updateCalificate = function () {
            Interchange.save($scope.interchange,
                function () {
                    $scope.loadAll();
                    $('#saveCalificateInterchangeModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.delete = function (id) {
            Interchange.get({id: id}, function(result) {
                $scope.interchange = result;
                $('#deleteInterchangeConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Interchange.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteInterchangeConfirmation').modal('hide');
                    $scope.clear();
                });
        };
        $scope.clear = function (harvestId) {
            $scope.interchange = {score: null, state: null, id: null, harvest: harvestId, farmerReciever: 1};
        };
    });


