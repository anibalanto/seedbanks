'use strict';

angular.module('seedbanksApp')
    .factory('Harvest', function ($resource) {
        return $resource('api/harvests/:id/:verb', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.date = new Date(data.date);
                    return data;
                }
            }
        });
    });
