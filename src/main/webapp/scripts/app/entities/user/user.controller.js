'use strict';

angular.module('seedbanksApp')
    .controller('UserController', function ($scope, User, Interchange, Harvest) {
        $scope.users = [];
        $scope.interchanges = Interchange.query();
        $scope.harvests = Harvest.query();
        $scope.loadAll = function() {
            User.query(function(result) {
               $scope.users = result;
            });
        };
        $scope.loadAll();

        $scope.create = function () {
            User.save($scope.user,
                function () {
                    $scope.loadAll();
                    $('#saveUserModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            User.get({id: id}, function(result) {
                $scope.user = result;
                $('#saveUserModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            User.get({id: id}, function(result) {
                $scope.user = result;
                $('#deleteUserConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            User.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteUserConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.user = {reliability: null, id: null};
        };
    });
