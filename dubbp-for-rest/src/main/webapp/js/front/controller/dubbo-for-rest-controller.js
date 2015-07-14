dubboForRestApp.controller("dubbo-for-rest-controller",function($scope,$http,$filter) {


    $scope.pomStr;
    $scope.interfaceList = [];
    $scope.selectInterface = {
    };
    $scope.isOk=true;
    $scope.isSend = false;

    /**
     * 导入pom
     */
    $scope.submitPom = function () {
        var pomStr =$scope.pomStr;
        var params = {pomStr : pomStr};
        $http.post("/dubbo/pom" , params).success(function (data) {
            console.log(data);
            if(data.resultList){
                $scope.interfaceList = data.resultList;
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
            paramsAry.push(item.value);
        });
        var params = {};
        params.valueAry = paramsAry;
        params.interfaceName = $scope.selectInterface.clazz;
        params.methodParamsClassAry = $scope.selectInterface.method.parameterTypes;
        params.method = $scope.selectInterface.method.name;
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