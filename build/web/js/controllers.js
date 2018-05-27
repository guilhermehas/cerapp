/* global estService, $location */

'use strict';

/* Controllers */

var appController = angular.module('appController', []);

appController.controller('CerIndexCtrl', function($scope, $rootScope, $http) {
    AltDic($rootScope,'index');
    
    $scope.cerveja = {preco: 10, distancia: 10};
    $scope.cervs = {};
    
    navigator.geolocation.getCurrentPosition(function(position) {
        $scope.map = { center: { latitude: position.coords.latitude, longitude: position.coords.longitude }, zoom: 13 };

        $scope.cerveja.longitude = position.coords.longitude;
        $scope.cerveja.latitude = position.coords.latitude;
    });
    
    $scope.coords = false;
    $scope.submitForm = function(){
        if(!$scope.cerveja.latitude || !$scope.cerveja.longitude){
            console.log("Sem localização");
            return;
        }
        
        //console.log($scope.cerveja);
        
        var prov = $http({
            method: "get",
            url: "getCervejaBusca",
            params: $scope.cerveja
        });
        
        prov.then(function(resultado){
            //console.log("Cerveja encontrada com sucesso");
            $scope.cervs = resultado.data;

            var estCoords = {};

            for(var it in $scope.cervs){
                var ob = $scope.cervs[it];
                estCoords[ob.eid] = {latitude: ob.latitude, longitude: ob.longitude, id: it};
            }

            $scope.markers = []
            for(var it in estCoords){
                $scope.markers.push(estCoords[it]);
            }
            //console.log($scope.markers);

            $scope.coords = true;
            //console.log($scope.cervs);
        }, 
        function(erro){
            console.log("Ocorreu um erro!");
            console.log(erro);
        });
        
    }

  });

appController.controller('CEstController', function($scope, $rootScope, $location, estService) {
    AltDic($rootScope,'cEst');

    $scope.main = {};
    navigator.geolocation.getCurrentPosition(function(position) {
        $scope.main.px = position.coords.longitude;
        $scope.main.py = position.coords.latitude;
    });
    
    entrar($scope,$rootScope,$location,estService.save);
  });


appController.controller('EnEstController', function($scope, $rootScope, $location, estService) {
    AltDic($rootScope,'enEst');
    entrar($scope,$rootScope,$location,estService.entrar,true);
  });

appController.controller('BusCervController', function($scope, $rootScope) {
    AltDic($rootScope,'cerveja');
  });
  
appController.controller('EdEstController', function($scope, $rootScope, $location, estService) {
    AltDic($rootScope,'edEst');
    
    estService.getUser().then( function(resultado){
        if(resultado.logado)
            $scope.main = resultado.user;
        else{
            $rootScope.logado = false;
            $location.path("/");
        }
    });
    
    entrar($scope,$rootScope,$location,estService.editar);
    
    $scope.deletar = function(){
        estService.deletar().then( function(resultado){
            $scope.data =  resultado;
            if(!$scope.data.status)
                alert($scope.data.mensagem);
            else{
                $rootScope.logado = false;
                $location.path("/");
            }
        });
    }
  });

appController.controller('ASEstController', function($scope, $rootScope, $location, estService) {
    redirectSeNaoLogado($location,estService);
    AltDic($rootScope,'ASEst');
    entrar($scope,$rootScope,$location,estService.alterarSenha);
  });
  
 appController.controller('ACEstController', function($scope, $rootScope, $location, $resource, $http, estService) {
    redirectSeNaoLogado($location,estService);
    AltDic($rootScope,'ACEst');
    
    var provider = $resource('getCervejaEstabelecimento');
    
    provider.query(function(resultado){
        $scope.cervs = resultado;
    });
    
    $scope.cerveja = {preco: 1.00};
    
    $scope.submitForm = function(){
        var erro = false;
        for (var it in $scope.cervs){
            if(it.nome == $scope.cerveja.nome){
                erro = true;
                break;
            }
        }
        if(!erro){
            $scope.cervs.push($scope.cerveja);
            $http({
                method: "post",
                url: "adicionarCerveja",
                params: $scope.cerveja
            }).then(function(){
                console.log("Cerveja adicionada com sucesso");
            });
        }
    };
    
    $scope.remove = function(item){
        var index = $scope.cervs.indexOf(item);
        $http({
                method: "get",
                url: "deletarCerveja",
                params: item
            }).then(
                function(){
                    console.log("Cerveja deletada com suceso");
                    $scope.cervs.splice(index,1);
            });
    };
  });
  
 function redirectSeNaoLogado($location, estService){
    estService.seLogado().then( function(resultado){
        if(!resultado)
            $location.path("/");
    });
  }

function AltDic(rootScope,nome){
    rootScope.dic = {};
    rootScope.dic[nome] = true;
}

function entrar(scope,rootScope,location,servico, vx){
    scope.submitForm = function(){
        var serv = servico(scope.main);
        serv.then(function(result) {  
            scope.data =  result;
            if(!scope.data.status)
                alert(scope.data.mensagem);
            else{
                if(vx == null)
                    rootScope.logado = true;
                else
                    rootScope.logado = vx;
                location.path("/");
            }
        });
    };
};
