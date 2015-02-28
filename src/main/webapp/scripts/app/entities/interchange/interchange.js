'use strict';

angular.module('seedbanksApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('interchange', {
                parent: 'entity',
                url: '/interchange',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'seedbanksApp.interchange.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/interchange/interchanges.html',
                        controller: 'InterchangeController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('interchange');
                        return $translate.refresh();
                    }]
                }
            })
            .state('interchangeDetail', {
                parent: 'entity',
                url: '/interchange/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'seedbanksApp.interchange.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/interchange/interchange-detail.html',
                        controller: 'InterchangeDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('interchange');
                        return $translate.refresh();
                    }]
                }
            });
    });
