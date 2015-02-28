'use strict';

angular.module('seedbanksApp')
    .factory('Variety', function ($resource) {
        return $resource('api/varietys/:id', {}, {
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
