dubboForRestApp.controller("dubbo-for-rest-controller",function($scope,$http,$filter) {


    $scope.pomStr;
    $scope.interfaceList = [];
    $scope.selectInterface = {
    };
    $scope.isOk=true;
    $scope.isSend = false;
    $scope.pom = {
    }
    $scope.isDubbo = false;
    $scope.commonParams = {};

    /**
     * 导入pom
     */
    $scope.submitPom = function (isDubbo) {
        var pomStr =$scope.pomStr;
        $scope.isDubbo = isDubbo;
        var params = {pomStr : pomStr , isDubbo : isDubbo};
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
        $scope.commonParams = {};

        if(selectInterface.clazz){
            $scope.showClassFieldAry = [];
            var i = 0 ;
            selectInterface.methodParamsInfoAry.forEach(function(e){
                if(e.fieldList && e.fieldList.length > 0){
                    $scope.showClassFieldAry.push(i);
                }
                i++;
            });
        }
    }


    /**
     * 执行接口
     */
    $scope.excute = function() {
        $scope.isSend = true;
        var paramsAry = [];
        var i = 0 ;
        $scope.selectInterface.methodParamsInfoAry.forEach(function (item) {
            if($scope.showClassField(i)){
                var obj = {};
                item.fieldList.forEach(function (field) {
                    obj[field.name] = field.fieldValue;
                });
                paramsAry.push(encodeURIComponent(angular.toJson(obj)));
            } else {
                paramsAry.push(encodeURIComponent(item.value));
            }
            i++;
        });

        var params = {};
        for (var i in $scope.commonParams) {
            params[i] = $scope.commonParams[i];
        }
        ;
        params.isDubbo = $scope.isDubbo;
        params.valueAry = paramsAry;
        params.interfaceName = $scope.selectInterface.clazz;
        if ($scope.selectInterface.method) {
            params.methodParamsClassAry = $scope.selectInterface.method.parameterTypes;
            params.methodName = $scope.selectInterface.method.name;
        }
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
            $scope.isSend = false;
            $scope.responseMessage = data.message;
        });
    }


    $scope.showClassFieldAry = [];
    $scope.changeField = function(e){
        if($scope.showClassField(e)){
            var index = $scope.showClassFieldAry.indexOf(e);
            if (index > -1) {
                $scope.showClassFieldAry.splice(index, 1);
            }
        }else {
            $scope.showClassFieldAry.push(e);
        }
    }

    $scope.showClassField = function(index){
        var has = false;
        $scope.showClassFieldAry.forEach(function(e){
            if(e == index){
                has = true;
                return;
            }
        });
        return has;
    }

});