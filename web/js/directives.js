var appDirective = angular.module('appDirective', ['ngMessages']);

appDirective.directive("compareTo", function() {
    return {
        require: "ngModel",
        scope: {
            otherModelValue: "=compareTo"
        },
        link: function(scope, element, attributes, ngModel) {

            ngModel.$validators.compareTo = function(modelValue) {
                return modelValue == scope.otherModelValue;
            };

            scope.$watch("otherModelValue", function() {
                ngModel.$validate();
            });
        }
    };
});

app.directive('ngLoading', function() {
  return {
    restrict: 'A',
    link: function(scope, element, attrs) {
        var res = JSON.parse(attrs.ngLoading);
        for(var key in res){
            console.log(key);
            element.attr(key,res.key);
        }
    }
      
    };
});



appDirective.directive("myForm", function(){
    return {
        restrict: 'E',
        transclude: true,
        templateUrl: 'templates/dform.html'
    };
});

appDirective.directive("fGroup", function(){
    return {
        restrict: 'E',
        transclude: true,
        scope: {
            label: '@'
        },
        link: function(scope, element, attrs) {
            scope.var = attrs.nome;
        },
        templateUrl: 'templates/dgrupo.html'
    };
});

appDirective.directive("fNmsg", function() {
    return {
        restrict: 'E',
        templateUrl: 'templates/dmessage.html'
    };
});

appDirective.directive("fInput", function() {
    return {
        restrict: 'E',
        scope: {tipo: '='},
        templateUrl: 'templates/dinput.html'
    };
});

appDirective.directive("fButton", function() {
    return {
        restrict: 'E',
        link: function(scope, element, attrs) {
            scope.nbt = attrs.nbt;
        },
        templateUrl: 'templates/dbutton.html'
    };
});

appDirective.directive("listCervejas", function($http) {
    return {
        restrict: 'E',
        scope:{
            model: '='
        },
        link: function(scope, element, attrs) {
            scope.data = [];
            $http.get("getCerveja").success(function(resultado){
                for(var it in resultado){
                    scope.data.push(resultado[it].nome);
                }
            }); 
        },
        template: '<autocomplete attr-placeholder="Selecione sua cerveja" ng-model="model" data="data"></autocomplete>'
    }
});
