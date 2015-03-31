'use strict';

angular.module('seedbanksApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('variety', {
                parent: 'entity',
                url: '/variety',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'seedbanksApp.variety.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/variety/varietys.html',
                        controller: 'VarietyController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('variety');
                        return $translate.refresh();
                    }]
                }
            })
            .state('varietyDetail', {
                parent: 'entity',
                url: '/variety/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'seedbanksApp.variety.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/variety/variety-detail.html',
                        controller: 'VarietyDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('variety');
                        $translatePartialLoader.addPart('harvest');
                        return $translate.refresh();
                    }]
                }
            });
    });
