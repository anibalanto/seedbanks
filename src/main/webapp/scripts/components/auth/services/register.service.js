'use strict';

angular.module('seedbanksApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


