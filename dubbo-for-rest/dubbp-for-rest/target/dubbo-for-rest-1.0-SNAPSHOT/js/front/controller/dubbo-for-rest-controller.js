dubboForRestApp.controller("dubbo-for-rest-controller",function($scope,$http,$filter) {


    $scope.pomStr;
    $scope.interfaceList = [];
    $scope.selectInterface = {
    };
    $scope.isOk=true;
    $scope.isSend = false;
    $scope.pom = {
    }

    /**
     * 导入pom
     */
    $scope.submitPom = function () {
        var pomStr =$scope.pomStr;
        var params = {pomStr : pomStr};
        $http.post("/dubbo/pom" , params).success(function (data) {
            console.log(data);
            if(data.status == 'ok'){
                $scope.interfaceList = data.resultList;
                $scope.pom.artifactId = data.artifactId;
                $scope.pom.groupId = data.groupId;
            }else {
                alert("导入pom失败")
            }
        }).error(function(data){
            alert("导入pom失败")
            console.log(data);
        });

    };


    /**
     * 选择接口
     * @param selectInterface
     */
    $scope.selectInterfaceFn = function(selectInterface){
        $scope.selectInterface = selectInterface;
    }


    /**
     * 执行接口
     */
    $scope.excute = function(){
        $scope.isSend = true;
        var paramsAry = [];
        $scope.selectInterface.methodParamsInfoAry.forEach(function(item){
            paramsAry.push(encodeURIComponent(item.value));
        });
        var params = {};

        params.valueAry = paramsAry;
        params.interfaceName = $scope.selectInterface.clazz;
        params.methodParamsClassAry = $scope.selectInterface.method.parameterTypes;
        params.methodName = $scope.selectInterface.method.name;
        params.fullJarPath = $scope.selectInterface.fullJarPath;
        $http.post("/dubbo/excute" , params).success(function (data) {
            $scope.responseMessage = data.message;
            if (data.status == 'ok') {
                $scope.isOk = true;
            } else {
                $scope.isOk = false;
            }
            $scope.isSend = false;
        }).error(function(data){
            $scope.isOk=false;
            $scope.responseMessage = data.message;
            $scope.isSend = false;
        });
    }

});