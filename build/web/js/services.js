'use strict';

/* Services */

var appServices = angular.module('appServices', []);

appServices.service(
    "estService",
    function( $http, $q ) {
        // Return public API.
        return({
            seLogado: seLogado,
            save: save,
            getUser: getUser,
            entrar: entrar,
            update: update,
            alterarSenha: alterarSenha,
            deletar: deletar,
            sair: sair
        });
        // ---
        // PUBLIC METHODS.
        // ---
        // I add a friend with the given name to the remote collection.
        
        function deletar(){
            var request = $http({
                method: "get",
                url: "deleteEstabelecimento"               
            }).then(
                function(response){
                    var data = response.data;
                    if(!data.status)
                        data = {status: false};
                    return data;
                });

            return request;
        };
        
        function sair(){
            var request = $http({
                method: "get",
                url: "logout"               
            }).then(
                function(response){
                    var data = response.data;
                    if(!data.status)
                        data = {status: false};
                    return data;
                });

            return request;
        };
        
        function alterarSenha( user ){
            var request = $http({
                method: "post",
                url: "alterarSenha",
                params: {
                    senha: user.senha
                }
            }).then(
                function(response){
                    var data = response.data;
                    if(!data)
                        data = {status: false, mensagem: "Erro desconhecido"};
                    return data;
                });

            return request;
        };
        
        function update( user ){
            var request = $http({
                method: "post",
                url: "editarEstabelecimento",
                params: {
                    email: user.email,
                    nomeoficial: user.nomeOficial,
                    nomecomercial: user.nomeComercial,
                    cnpj: user.cnpj
                }
            }).then(
                function(response){
                    var data = response.data;
                    if(!data)
                        data = {status: false, mensagem: "Erro desconhecido"};
                    return data;
                });

            return request;
        };
        
        function save( user ) {
            var request = $http({
                method: "post",
                url: "criarEstabelecimento",
                params: {
                    email: user.email,
                    senha: user.senha,
                    nomeoficial: user.nomeOficial,
                    nomecomercial: user.nomeComercial,
                    cnpj: user.cnpj,
                    latitude: user.py,
                    longitude: user.px
                }
            }).then(
                function(response){
                    var data = response.data;
                    if(!data)
                        data = {status: false, mensagem: "Erro desconhecido"};
                    return data;
                });

            return request;
        }
        
        function entrar( user ){
            var request = $http({
                method: "post",
                url: "login",
                params: {
                    email: user.email,
                    senha: user.senha
                }
            }).then(
                function(response){
                    var data = response.data;
                    if(!data)
                        data = {status: false, mensagem: "Erro desconhecido"};
                    return data;
                });

            return request;
        }

        function seLogado(){
            var request = $http({
                method: "get",
                url: "selogado"
            }).then(
                function(response){
                    var data = response.data;
                    if(!data)
                        data = {logado: false};
                    return data.logado;
                });
                
            return request;
        }
        
        function getUser(){
            var request = $http({
                method: "get",
                url: "getEstabelecimento"
            }).then(
                function(response){
                    var data = response.data;
                    if(!data)
                        data = {logado: false};
                    return data;
                });
            return request;
        }

        function handleError( response ) {
            if (
                ! angular.isObject( response.data ) ||
                ! response.data.message
                ) {
                return( $q.reject( "An unknown error occurred." ) );
            }
            return( $q.reject( response.data.message ) );
        }
        function handleSuccess( response ) {
            return( response.data );
        }
    }
);