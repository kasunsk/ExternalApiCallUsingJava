var app = angular.module('app', ['ngRoute', 'ngResource', 'ngCookies']);
app.config(function ($routeProvider) {
    $routeProvider
        .when('/users', {
            templateUrl: '/views/users.html',
            controller: 'usersController'
        })
        .when('/login', {
            templateUrl: '/views/login.html',
            controller: 'loginController'
        })
        .when('/register', {
            templateUrl: '/views/register.html',
            controller: 'registerController'
        })
        .when('/airlineOffer', {
            templateUrl: '/views/airlineOffer.html',
            controller: 'airlineOfferController'
        })
        .when('/accountCreate', {
            templateUrl: '/views/accountCreate.html',
            controller: 'accountCreateController'
        })
        .when('/myTickets', {
            templateUrl: '/views/myTickets.html',
            controller: 'ticketController'
        })
        .when('/ticket', {
            templateUrl: '/views/ticket.html',
            controller: 'ticketsController'
        })
        .when('/usersTickets', {
            templateUrl: '/views/usersTickets.html',
            controller: 'usersTicketsController'
        })
        .when('/moneyExchange', {
            templateUrl: '/views/moneyExchange.html',
            controller: 'moneyExchangeController'
        })
        .otherwise(
        {redirectTo: '/'}
    );
});