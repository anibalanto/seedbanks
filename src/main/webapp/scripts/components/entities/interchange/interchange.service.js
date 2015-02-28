'use strict';

angular.module('seedbanksApp')
    .factory('Interchange', function ($resource) {
        return $resource('api/interchanges/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            }
        });
    });
