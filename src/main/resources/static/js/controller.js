app.controller('usersController', ['$scope', '$http', '$window', '$cookies', function ($scope, $http, $window, $cookies) {

    $scope.headingTitle = "User List";
    $scope.clicked = false;

    $scope.userList = function () {
        $scope.submitting = true;
        $http({
            method: 'GET',
            url: '/user/list'
        }).success(function (data) {
            $scope.clicked = true;
            $scope.allUsers = data;

        }).error(function (data, status) {
            $scope.submitting = false;
        });
    };

    $scope.deleteUser = function (idx) {

        var user_to_delete = $scope.allUsers[idx];
        var userId = user_to_delete.userId;

        $scope.submitting = true;
        $http({
            method: 'GET',
            url: '/user/remove/' + userId
        }).success(function (data) {
            $scope.allUsers = $scope.userList();

        }).error(function (data, status) {
            $scope.submitting = false;
        });
    };

    $scope.searchUsers = function (userSearchCriteria) {

        var userId = $cookies.get('userId');
        var userSearchUrl = '/user/' + userId + '/search';

        $http({
            method: 'POST',
            url: userSearchUrl,
            data: userSearchCriteria
        }).success(function (data) {
            $scope.clicked = true;
            $scope.allUsers = data;

        }).error(function (data, status) {
            $scope.submitting = false;
        });
    };
}]);


app.controller('ticketController', ['$scope', '$http', '$cookies', '$window', function ($scope, $http, $cookies, $window) {
    $scope.headingTitle = "My Tickets";

    var userId = $cookies.get('userId');

    $scope.getMyTickets = function () {

        var myTicketUrl = '/' + userId + '/gammaairlines/tickets';

        $scope.submitting = true;
        $http({
            method: 'GET',
            url: myTicketUrl
        }).success(function (data) {
            $scope.myTickets = data;

        }).error(function (data, status) {
            $scope.submitting = false;
        });
    };

    $scope.viewTicket = function (idx) {

        if ($scope.myTickets != null) {
            $scope.userTicket = $scope.myTickets[idx];
        } else {
            $scope.userTicket = $scope.usersTickets[idx];
        }

        var jsonUserTicket = JSON.stringify($scope.userTicket);
        $cookies.put("selectedTicket", jsonUserTicket);

        $window.location.href = '#/ticket';
    };
}]);

app.controller('ticketsController', ['$scope', '$http', '$cookies', '$window', function ($scope, $http, $cookies, $window) {
    $scope.headingTitle = "My Tickets";
    $scope.submitting = false;

    $scope.init = function () {
        var userTicketObj = JSON.parse($cookies.get('selectedTicket'));
        $scope.userTicket = userTicketObj;
    };

    var userId = $cookies.get('userId');

    $scope.getMyTickets = function () {

        var myTicketUrl = '/' + userId + '/gammaairlines/tickets';

        $scope.submitting = true;
        $http({
            method: 'GET',
            url: myTicketUrl
        }).success(function (data) {
            $scope.myTickets = data;

        }).error(function (data, status) {
            $scope.submitting = false;
        });
    };

    $scope.viewTicket = function (idx) {

        if ($scope.myTickets != null) {
            $scope.userTicket = $scope.myTickets[idx];
        } else {
            $scope.userTicket = $scope.usersTickets[idx];
        }

        var jsonUserTicket = JSON.stringify($scope.userTicket);
        $cookies.put("selectedTicket", jsonUserTicket);

        $window.location.href = '#/ticket';
    };

    $scope.emailTicket = function (ticketId) {

        $scope.success = false;
        $scope.error = false;

        var emailTicketUrl = '/gammaairlines/userTicket/email/send/' + ticketId;

        $scope.submitting = true;
        $http({
            method: 'GET',
            url: emailTicketUrl
        }).success(function (data) {

            if (data) {
                $scope.success = "Email send success !"
            } else {
                $scope.error = "Email sending failed"
            }
        }).error(function (data, status) {
            $scope.error = data.message;
            $scope.success = false;
            $scope.submitting = false;
        });
    }
}]);

app.controller('loginController', ['$scope', '$http', '$cookies', '$window', function ($scope, $http, $cookies, $window) {

    $scope.headingTitle = "Login";

    $scope.loginRequest = {
        email: null,
        password: null
    };

    $scope.login = function () {
        $scope.submitting = true;
        $http({
            method: 'POST',
            url: '/user/login',
            data: $scope.loginRequest
        }).success(function (data) {
            $scope.submitting = false;
            $cookies.put('userId', data.userId);
            $cookies.put('applicantName', data.name);
            $cookies.put('applicantRole', data.role);
            $window.location.href = 'homepage.html#/airlineOffer';
            $scope.applicantName = data.name;

            if (data.role == 'ADMIN') {
                $scope.isAdmin = true;
            } else {
                $scope.isAdmin = false;
            }

        }).error(function (data, status) {
            $scope.submitting = false;
            $scope.error = data.message;
        });
    };

    $scope.logout = function () {
        $cookies.remove('userId');
        $cookies.remove('applicantName');
        $cookies.remove('applicantRole');
        $window.location.href = '/index.html';
    }

}]);


app.controller('registerController', ['$scope', '$http', '$window', function ($scope, $http, $window) {
    $scope.headingTitle = "Register";

    $scope.user = {
        name: null,
        email: null,
        password: null
    };

    $scope.saveUser = function () {
        $scope.submitting = true;
        $http({
            method: 'POST',
            url: '/user/save',
            data: $scope.user
        }).success(function (data) {
            $scope.submitting = false;
            $scope.allUsers = data;
            $window.location.href = '/index.html#/login';

        }).error(function (data, status) {
            $scope.submitting = false;
            $scope.error = data.message;
        });
    };
}]);

app.controller('accountCreateController', ['$scope', '$http', '$cookies', '$window', '$timeout', function ($scope, $http, $cookies, $window, $timeout) {
    $scope.headingTitle = "My Accounts";
    $scope.submitting = false;

    $scope.availableCurrencies = function () {
        $http({
            method: 'GET',
            url: '/account/availableCurrency'
        }).success(function (data) {
            $scope.currencies = data;

        }).error(function (data, status) {
            $scope.submitting = false;
        });
    };

    var userId = $cookies.get('userId');
    var accountCreateUrl = '/' + userId + '/paypallets/account';

    $scope.accountCreateRequest = {
        currency: null
    };


    $scope.createAccount = function () {
        $http({
            method: 'POST',
            url: accountCreateUrl,
            data: $scope.accountCreateRequest
        }).success(function (data) {
            $scope.bankAccount = data;
            $scope.accountList();
            $scope.error_create_account = false;
            $scope.success_create_account = "Account crated";
            $scope.submitting = true;

        }).error(function (data, status) {
            $scope.error_create_account = data.message;
            $scope. $scope.success_create_account = false;
            $scope.submitting = false;
        });
    };

    $scope.depositRequest = {
        accountId: null,
        monetaryAmount: null
    };

    $scope.deposit = function (account) {

        $scope.depositRequest.accountId = account.id;

        var depositUrl = '/' + userId + '/paypallets/account/deposit/';

        $http({
            method: 'POST',
            url: depositUrl,
            data: $scope.depositRequest
        }).success(function (data) {
            $scope.accountList();
            $scope.error_deposit = false;
            $scope.success_deposit = "Money deposited successfully!";
            $scope.displayDeposit = false;
            $scope.submitting = false;
        }).error(function (data, status) {
            $scope.error_deposit = data.message;
            $scope.success_deposit = false;
            $scope.submitting = false;
        });
    };

    $scope.showInput = function (idx) {
        var accountId = $scope.allAccounts[idx];
        var currency = $scope.allAccounts[idx].currency;

        $scope.displayDeposit = true;
        $scope.depositAccountId = accountId;
        $scope.accountCurrency = currency;

        $scope.error_deposit = false;
        $scope.success_deposit = false;
        $scope.error = false;
        $scope.error_create_account = false;
        $scope.success_create_account = false;

    };

    $scope.accountList = function () {
        var accountUrl = '/' + userId + '/paypallets/account/all';
        $http({
            method: 'GET',
            url: accountUrl
        }).success(function (data) {
            $scope.showViewAccounts = true;
            $scope.allAccounts = data;
        }).error(function (data, status) {
            $scope.error = data.message;
        });
    };

}]);

app.controller('airlineOfferController', ['$scope', '$http', '$cookies', '$window', function ($scope, $http, $cookies, $window) {
    $scope.headingTitle = "Available Offers";
    $scope.submitting = false;

    var applicantRole = $cookies.get('applicantRole');
    var userId = $cookies.get('userId');
    var requestUrl = '/' + userId + '/gammaairlines/offers';

    if (applicantRole == 'ADMIN') {
        $scope.isAdmin = true;
    } else {
        $scope.isAdmin = false;
    }

    $scope.availableAirlineOffers = function () {
        $scope.submitting = true;
        $http({
            method: 'GET',
            url: requestUrl
        }).success(function (data) {
            $scope.error = false;
            $scope.availableOffers = data;

        }).error(function (data, status) {
            $scope.error = data.message;
        });
    };

    $scope.init = function () {

        $http({
            method: 'GET',
            url: '/account/availableCurrency'
        }).success(function (data) {
            $scope.currencies = data;

        }).error(function (data, status) {
            $scope.submitting = false;
        });
    };

    $scope.select = function (idx) {

        $scope.offer_to_buy = $scope.availableOffers[idx];
        $scope.displayAccounts = true;
        $scope.error = false;
    };

    $scope.buyingRequest = {
        accountId: null,
        route: null,
        amount : null
    };


    $scope.buy = function (accountId) {

        var rout_to_buy = $scope.offer_to_buy.route;
        var userId = $cookies.get('userId');

        $scope.buyingRequest.accountId = accountId;
        $scope.buyingRequest.route = rout_to_buy;

        var buyingRequestUrl = '/' + userId + '/gammaairlines/offers/buy';

        $scope.submitting = true;
        $http({
            method: 'POST',
            url: buyingRequestUrl,
            data: $scope.buyingRequest
        }).success(function (data) {
            $scope.submitting = false;
            $scope.userTicket = data;

            var jsonUserTicket = JSON.stringify($scope.userTicket);
            $cookies.put("selectedTicket", jsonUserTicket);

            $window.location.href = '#/ticket';

        }).error(function (data, status) {
            $scope.submitting = false;
            $scope.error = data.message;
            $scope.displayAccounts = false;
        });
    };

}]);

app.controller('homeController', ['$scope', '$http', '$cookies', '$window', function ($scope, $http, $cookies, $window) {

    var applicantName = $cookies.get('applicantName');
    var applicantRole = $cookies.get('applicantRole');

    $scope.applicantName = applicantName;

    if (applicantRole == 'ADMIN') {
        $scope.isAdmin = true;
    } else {
        $scope.isAdmin = false;
    }
}]);

app.controller('usersTicketsController', ['$scope', '$http', '$cookies', '$window', function ($scope, $http, $cookies, $window) {
    $scope.headingTitle = "Users Tickets";
    $scope.submitting = false;

    var userId = $cookies.get('userId');
    var applicantRole = $cookies.get('applicantRole');

    if (applicantRole == 'ADMIN') {
        $scope.isAdmin = true;
    } else {
        $scope.isAdmin = false;
    }

    $scope.loadUserTickets = function (userId) {

        var userTicketsUrl = '/' + userId + 'gammaairlines/tickets';

        $scope.submitting = true;
        $http({
            method: 'GET',
            url: userTicketsUrl
        }).success(function (data) {
            $scope.submitting = false;
            $scope.usersTickets = data;

        }).error(function (data, status) {
            $scope.submitting = false;
        });
    };

    $scope.userTicketSearchCriteria  =  {
    };


    $scope.searchUsersTickets = function (userTicketSearchCriteria) {

        var emailTicketUrl = '/' + userId + '/gammaairlines/userTicket/search/';

        $scope.submitting = true;
        $http({
            method: 'POST',
            url: emailTicketUrl,
            data:userTicketSearchCriteria
        }).success(function (data) {
            $scope.usersTickets = data;
            $scope.submitting = false;
        }).error(function (data, status) {
            $scope.submitting = false;
        });
    }

}]);
