<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN" ng-app="dubbo-for-rest-app">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Bootstrap 101 Template</title>


    <!-- Bootstrap -->
    <link href="css/bootstrap.min.css" rel="stylesheet">

</head>
<body>
<div class="container theme-showcase" style="padding-bottom: 100px;" role="main" ng-controller="dubbo-for-rest-controller">

    <!-- Main jumbotron for a primary marketing message or call to action -->
    <div class="jumbotron">
        <h1>DUBBO-FOR-REST</h1>

        <p>DUBBO提供rest的服务，仅作为测试环境方便测试</p>
    </div>

    <div class="container">


        <div class="clearfix">
            <h2><b>导入新的pom文件</b></h2>
            <div class="bs-example pull-left" style="width: 50%;">
                <form>
                    <textarea class="form-control" style="max-width: 570px;min-width: 570px;" ng-model="pomStr" rows="6" placeholder="输入pom文件中的dependency"></textarea>
                </form>
            </div>

            <div class="pull-right" style="width: 48%">
                <p>范例如下：</p>
                <code class="language-html" data-lang="html">
                    <small>&lt;dependency&gt;<br/>
                        &lt;groupId&gt;com.howbuy &lt;/groupId&gt;<br/>
                        &lt;artifactId&gt;crm-label-api2Login-model &lt;/artifactId&gt;<br/>
                        &lt;version&gt;1.0 &lt;/version&gt;<br/>
                        &lt;/dependency&gt;<br/>
                    </small>
                </code>
            </div>
        </div>
        <button type="button" ng-click="submitPom();" class="btn btn-primary btn-lg" style="margin-top: 10px;">导入pom</button>


        <div ng-show="interfaceList.length > 0" style="padding-top: 25px;">
            <h2><b>pom中的接口服务</b></h2>
            <div>
                <table class="table" style="word-wrap: break-word;">
                    <thead>
                        <tr>
                            <th width="5%">#</th>
                            <th width="40%">接口类</th>
                            <th width="30%">方法名称</th>
                            <th width="15%;">操作</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr ng-repeat="_interface in interfaceList">
                            <th scope="row">{{$index}}</th>
                            <td style="word-break:break-all; word-wrap:break-word;">{{_interface.clazz}}</td>
                            <td style="word-break:break-all; word-wrap:break-word;">{{_interface.fullMethodName}}</td>
                            <td><input class="btn btn-default" ng-click="selectInterfaceFn(_interface);" value="选择接口发送请求"></td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>

        <div style="padding-top: 25px;" ng-show="selectInterface.clazz">
            <h2><b>发送请求</b></h2>
            <h4>选择的接口</h4>
            <h4><mark>{{selectInterface.clazz}}.{{selectInterface.fullMethodName}}</mark></h4>
            <div>
                <table class="table" style="word-wrap: break-word;">
                    <thead>
                    <tr>
                        <th width="5%">#</th>
                        <th width="20%">参数名</th>
                        <th width="30%">参数类型</th>
                        <th width="45%;">输入参数值</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr ng-repeat="params in selectInterface.methodParamsInfoAry">
                        <th scope="row">{{$index}}</th>
                        <td style="word-break:break-all; word-wrap:break-word;">{{params.paramsName || '-'}}</td>
                        <td style="word-break:break-all; word-wrap:break-word;">{{params.paramsClass}}</td>
                        <td>
                            <textarea class="form-control" rows="2" ng-model="params.value" style="max-width: 500px;min-width: 500px;"></textarea>
                            <%--<input type="text" class="form-control" placeholder="参数值" >--%>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <button type="button" ng-click="excute();" ng-disabled="isSend" class="btn btn-primary btn-lg" style="margin-top: 10px;">确认发送</button>


            <h5>返回数据</h5>
            <p class="wBreak" ng-class="{true:'text-info' , false : 'text-danger' }[isOk]">
               {{responseMessage}}
            </p>
        </div>




    </div>


</div>


</body>
<script src="js/angular.js"></script>
<script src="js/jquery-1.11.3.js"></script>
<script src="js/front/app.js"></script>
<script src="js/front/controller/dubbo-for-rest-controller.js"></script>

</html>