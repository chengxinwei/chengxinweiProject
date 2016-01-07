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

    <div class="jumbotron">
        <h1>DUBBO-FOR-REST</h1>

        <p>提供DUBBO和HSB的HTTP服务，仅作为测试环境方便测试</p>
    </div>

    <div class="container">


        <div class="clearfix">
            <h2><b>导入新的pom文件</b></h2>
            <div class="bs-example pull-left" style="width: 50%;">
                <form>
                    <textarea style="width: 90%" class="form-control"  ng-model="pomStr" rows="6" placeholder="输入pom文件中的dependency"></textarea>
                </form>
            </div>

            <div class="pull-right" style="width: 25%">
                <p>DUBBO范例如下：</p>
                <code class="language-html" data-lang="html">
                    <small>&lt;dependency&gt;<br/>
                        &lt;groupId&gt;com.howbuy.cc&lt;/groupId&gt;<br/>
                        &lt;artifactId&gt;cim-web-client&lt;/artifactId&gt;<br/>
                        &lt;version&gt;0.0.2&lt;/version&gt;<br/>
                        &lt;/dependency&gt;<br/>
                    </small>
                </code>
            </div>

            <div class="pull-right" style="width: 25%">
                <p>RMI范例如下：</p>
                <code class="language-html" data-lang="html">
                    <small>&lt;dependency&gt;<br/>
                        &lt;groupId&gt;com.howbuy.test&lt;/groupId&gt;<br/>
                        &lt;artifactId&gt;howbuy-txio&lt;/artifactId&gt;<br/>
                        &lt;version&gt;1.0.0&lt;/version&gt;<br/>
                        &lt;/dependency&gt;<br/>
                    </small>
                </code>
            </div>
        </div>

        <button type="button" ng-click="submitPom(true);" class="btn btn-primary btn-lg" style="margin-top: 10px;">导入pom(DUBBO)</button>
        <button type="button" ng-click="submitPom(false);" class="btn btn-primary btn-lg" style="margin-top: 10px;">导入pom(RMI)</button>

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
            <h4>选择的接口 <mark>{{selectInterface.clazz}}.{{selectInterface.fullMethodName}}</mark></h4>


            <div style="padding-top: 10px;" ng-show="!isDubbo">
                <h4>RMI公共参数</h4>
                <table class="table" style="word-wrap: break-word;">
                    <thead>
                    <tr>
                        <th width="5%">#</th>
                        <th width="20%">参数名</th>
                        <th width="30%">参数说明</th>
                        <th width="45%;">输入参数值</th>
                    </tr>
                    </thead>
                    <tr>
                        <th scope="row">1</th>
                        <td style="word-break:break-all; word-wrap:break-word;">IP</td>
                        <td style="word-break:break-all; word-wrap:break-word;">代销联机,创新产品:192.168.221.28</td>
                        <td>
                            <input type="text" class="form-control" placeholder="192.168.221.28" ng-model="commonParams.ip">
                        </td>
                    </tr>
                    <tr>
                        <th scope="row">2</th>
                        <td style="word-break:break-all; word-wrap:break-word;">port</td>
                        <td style="word-break:break-all; word-wrap:break-word;">代销联机:13242;创新产品:12312</td>
                        <td>
                            <input type="text" class="form-control" placeholder="13242" ng-model="commonParams.port">
                        </td>
                    </tr>

                    <tr>
                        <th scope="row">3</th>
                        <td style="word-break:break-all; word-wrap:break-word;">communPort</td>
                        <td style="word-break:break-all; word-wrap:break-word;">代销联机:13244;创新产品:12314</td>
                        <td>
                            <input type="text" class="form-control" placeholder="13244" ng-model="commonParams.communPort">
                        </td>
                    </tr>
                    <tr>
                        <th scope="row">4</th>
                        <td style="word-break:break-all; word-wrap:break-word;">recognizers</td>
                        <td style="word-break:break-all; word-wrap:break-word;">代销联机:20;创新产品:26</td>
                        <td>
                            <input type="text" class="form-control" placeholder="20" ng-model="commonParams.recognizers">
                        </td>
                    </tr>
                </table>
            </div>

            <div style="padding-top: 10px;" ng-show="isDubbo">
                <h4>DUBBO公共参数</h4>
                <table class="table" style="word-wrap: break-word;">
                    <thead>
                    <tr>
                        <th width="5%">#</th>
                        <th width="20%">参数名</th>
                        <th width="30%">参数说明</th>
                        <th width="45%;">输入参数值</th>
                    </tr>
                    </thead>
                    <tr>
                        <th scope="row">1</th>
                        <td style="word-break:break-all; word-wrap:break-word;">zookeeper</td>
                        <td style="word-break:break-all; word-wrap:break-word;">zookeeper地址</td>
                        <td>
                            <input type="text" class="form-control" placeholder="zookeeper://192.168.220.220:2181" ng-model="commonParams.zookeeper">
                        </td>
                    </tr>
                </table>
            </div>

            <div>
                <h4>业务调用参数</h4>
                <table class="table" style="word-wrap: break-word;">
                    <thead>
                    <tr>
                        <th width="5%">#</th>
                        <th width="20%">参数名</th>
                        <th width="30%">参数类型</th>
                        <th width="45%;">输入参数值</th>
                    </tr>
                    </thead>
                    <tr ng-repeat="params in selectInterface.methodParamsInfoAry">
                        <th scope="row">{{$index}}</th>
                        <td style="word-break:break-all; word-wrap:break-word;">{{params.paramsName || '-'}}</td>
                        <td style="word-break:break-all; word-wrap:break-word;">{{params.paramsClass}}</td>
                        <td>
                            <%--params.fieldList && params.fieldList.length > 0--%>
                            <div class="input-group" ng-show="showClassField($parent.$index)" ng-repeat="field in params.fieldList">
                                <div class="input-group-addon">{{field.name}}</div>
                                <input type="text" class="form-control" placeholder="参数值" ng-model="field.fieldValue">
                            </div>
                                <%--params.fieldList && params.fieldList.length > 0--%>
                            <textarea ng-hide="showClassField($index)" class="form-control" rows="2" ng-model="params.value" style="max-width: 500px;min-width: 500px;"></textarea>
                        </td>
                        <td><button class="btn btn-default" ng-click="changeField($index)">切换</button></td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <button type="button" ng-click="excute();" ng-disabled="isSend" class="btn btn-primary btn-lg" style="margin-top: 10px;">确认发送</button>

            <h5>返回数据</h5>
            <div ng-class="{true:'text-info' , false : 'text-danger' }[isOk]">
                <p ng-repeat="ms in responseMessage track by $index ">
                   {{ms}}
                </p>
            </div>
        </div>
    </div>
</div>


</body>
<script src="js/angular.js"></script>
<script src="js/jquery-1.11.3.js"></script>
<script src="js/front/app.js"></script>
<script src="js/front/controller/dubbo-for-rest-controller.js"></script>

</html>