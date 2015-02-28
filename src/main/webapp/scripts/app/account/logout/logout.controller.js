'use strict';

angular.module('seedbanksApp')
    .controller('LogoutController', function (Auth) {
        Auth.logout();
    });
