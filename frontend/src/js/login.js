define(['angular', 'kitt'],function(angular, kitt){
  return angular.module('login', ['kitt'])
  .controller('Login', function($scope, $http){
  $scope.admin={};
    $scope.Login=function(){
       service({url: '/login',
       data: $scope.admin,
       success: function(data){
       if(data.success){
         //goto("/");
           location.href="/";
       }
       else{
           $.each(data.errors,function(index,error){
                $("#errorInfo").text(error.defaultMessage).css("color","red");
           });
       }
       } //ajax请求
    });
  };
});
});
