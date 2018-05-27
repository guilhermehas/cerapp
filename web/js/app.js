'use strict';

/* App Module */

var app = angular.module('myApp', [
    'ngRoute',
    'ngResource',
    
    'uiGmapgoogle-maps',

    'appController',
    'appServices',
    'appDirective',
    
    'autocomplete',
    
    'ngMessages',
    'ngCpfCnpj',
    'ngMask',
    'ui.bootstrap'
]);

app.config(
  function($routeProvider) {
    $routeProvider.
    when('/cerapp', {
        templateUrl: 'partials/cerappindex.html',
        controller: 'CerIndexCtrl'
    }).
    when('/criarestabelecimento', {
        templateUrl: 'partials/criarEstabelecimento.html',
        controller: 'CEstController'
    }).
    when('/entrarestabelecimento', {
        templateUrl: 'partials/entrarEstabelecimento.html',
        controller: 'EnEstController'
    }).
    when('/editarestabelecimento', {
        templateUrl: 'partials/editarEstabelecimento.html',
        controller: 'EdEstController'
    }).
    when('/alterarsenha', {
        templateUrl: 'partials/alterarSenha.html',
        controller: 'ASEstController'
    }).
    when('/adicionarcerveja', {
        templateUrl: 'partials/adicionarCerveja.html',
        controller: 'ACEstController'
    }).
      otherwise({
        redirectTo: '/cerapp'
      });
  });


app.run(function($rootScope, $location, estService) {
    $rootScope.dic = {};
    
    var serv = estService.seLogado();
    
    serv.then(function(result) {
        $rootScope.logado = result;
    });
    
    $rootScope.setClass = function(pal){
        if($rootScope.dic[pal])
            return "active";
    };
    
    $rootScope.sair = function(){
        estService.sair().then( function(resultado){
            if(resultado.status)
                $rootScope.logado = false;
            $location.path("/");
        });
    };
});
