'use strict';

angular.module('seedbanksApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('harvest', {
                parent: 'entity',
                url: '/harvest',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'seedbanksApp.harvest.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/harvest/harvests.html',
                        controller: 'HarvestController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('harvest');
                        return $translate.refresh();
                    }]
                }
            })
            .state('harvestDetail', {
                parent: 'entity',
                url: '/harvest/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'seedbanksApp.harvest.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/harvest/harvest-detail.html',
                        controller: 'HarvestDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('harvest');
                         $translatePartialLoader.addPart('interchange');
                        return $translate.refresh();
                    }]
                }
            });
    });
