document.addEventListener("deviceready", onDeviceReady);

function onDeviceReady() {
    console.log("ready...");

    init(); // 初始化显示当前位置
    checkIn(); //签到功能
    clickTrack(); // Track抽屉键
    startWatchPos(); // 实时定位
    stopWatchPos(); // 停止定位
    clickRange(); // 绘制多边形
    findAddress(); // 地址检索
    autoComplete(); // 关键字提示
    showBtndel(); // 文本框删除键
}


var map; // 地图对象
var cpoint; // 当前坐标
var test; // 签到有效区域


// 初始化显示当前位置
function init()
{
    map = new BMap.Map("map"); // 创建地图实例
    map.centerAndZoom(new BMap.Point(lng, lat), 18);

    // 注意：避免机房内获取不到GPS，无法演示模拟签到。暂时将此段代码注释。
    // navigator.geolocation.getCurrentPosition(function(pos) { // 获取当前坐标
    //     var lng = pos.coords.longitude;
    //     var lat = pos.coords.latitude;
    //     console.log("Current position");
    //     console.log("获取："+lng+","+lat);

    //     point = new BMap.Point(lng, lat);
    //     pointTranslate(point); 
    // },
    // function() { console.log("getPos error"); },
    // { maximumAge: 3000, timeout: 100000, enableHighAccuracy: true});
}

//签到功能
function checkIn()
{   
    // 添加签到区-信息楼
    test = new BMap.Polygon([ 
        new BMap.Point(lng1,lat1),
        new BMap.Point(lng2,lat2),
        new BMap.Point(lng3,lat3),
        new BMap.Point(lng4,lat4),
    ], {strokeColor:"orange", strokeWeight:2, strokeOpacity:0.5});

    //签到测试区-宿舍楼
        // test = new BMap.Polygon([
            // new BMap.Point(lng1,lat1),
            // new BMap.Point(lng2,lat2),
            // new BMap.Point(lng3,lat3),
            // new BMap.Point(lng4,lat4),
        // ], {strokeColor:"orange", strokeWeight:2, strokeOpacity:0.5});

    var testMarker = new BMap.Marker(new BMap.Point(lng, lat));
    var label = new BMap.Label("->签到区<-<br>（Check in<br>有效区域）", {offset:new BMap.Size(-5,-5)});
    testMarker.setLabel(label);
    map.addOverlay(testMarker);
    map.addOverlay(test);

    
    // 模拟签到
    var ckpoint;
    var ckpoints = [ //行走坐标
        new BMap.Point(lng1,lat1),
        new BMap.Point(lng2,lat2),
        new BMap.Point(lng3,lat3),
        new BMap.Point(lng4,lat4),
        new BMap.Point(lng5,lat5),
        new BMap.Point(lng6,lat6)
    ];
    var startPoint = new BMap.Marker(new BMap.Point(lng, lat));
    startPoint.setIcon(new BMap.Icon("img/startpoint.png", new BMap.Size(100, 100),{
        imageOffset: new BMap.Size(40, 20)
    }));
    var mpoint = new BMap.Marker(new BMap.Point(lng, lat)); // 用户移动
    map.addOverlay(startPoint);

    // 判断是否位于签到有效区域
    var btnCheckIn = document.getElementById("btnCheckIn");
    btnCheckIn.addEventListener("click", function() {
        
        var index = 0;

        // 每一秒移动一段路程
        tid = setInterval(function() {

            let movePoints = [];
            movePoints.push(ckpoints[index]);
            movePoints.push(ckpoints[index+1]);
            ckpoint = ckpoints[index+1]; // 当前模拟坐标

            var sym = new BMap.Symbol
            (
                BMap_Symbol_SHAPE_BACKWARD_OPEN_ARROW, // 箭头方向向下的非闭合箭头
                {
                    fillColor : 'white',
                    fillOpacity : 1, 
                    scale : 0.5, 
                    rotation : 90, 
                    strokeColor : '#FFF', 
                    strokeOpacity : 1, 
                    strokeWeight : 2
                }
            );
        
            var iconSequence = new BMap.IconSequence
                    (
                        sym,
                        '10%',
                        '100%' // 箭头在线上的距离
                    );

            var ckpolyline = new BMap.Polyline(movePoints, {
                icons : [iconSequence],
                strokeColor : '#2980b9', 
                strokeOpacity : 1,
                strokeWeight : 5
            });

            map.addOverlay(ckpolyline);

            // 移动标注
            map.removeOverlay(mpoint);
            mpoint = new BMap.Marker(ckpoints[index+1]);
            map.addOverlay(mpoint);

            //判断
            if(BMapLib.GeoUtils.isPointInPolygon(ckpoint, test)){ //ckpoint为模拟位置
                console.log("签到成功");
                window.plugins.toast.show('签到成功', '2000', 'center');
            }else{
                console.log("当前位置未在签到区");
            }


            if(index < ckpoints.length-2) {
                index++;
            }else{
                clearInterval(tid); // 清除
            }

        }, 500);



        // if(BMapLib.GeoUtils.isPointInPolygon(cpoint, test)){ //cpoint为设备实际位置
        //     // alert("签到成功");
        //     console.log("签到成功");
        // }else{
        //     // alert("当前位置未在签到区");
        //     console.log("当前位置未在签到区");
        // }

    });
}

// Track抽屉键
function clickTrack()
{
    var btnWatchPos = document.getElementById("btnWatchPos");
    btnWatchPos.addEventListener("click", function() {
        document.getElementById("btnon").style.right = "0";
        document.getElementById("btnoff").style.right = "0";
    });
}


// 实时定位
var watchID;
function startWatchPos()
{
    var btnon = document.getElementById("btnon");
    btnon.addEventListener("click", function() {

        document.getElementById("btnon").style.backgroundColor = "#9b59b6";

        watchID = navigator.geolocation.watchPosition(function(pos) {
            var lng = pos.coords.longitude;
            var lat = pos.coords.latitude;
            console.log("Watch position");
            console.log("获取："+lng+","+lat);

            var point = new BMap.Point(lng, lat);
            pointTranslate(point);    
        }, 
        function() { console.log("watchPos error"); },
        { maximumAge: 3000, timeout: 100000, enableHighAccuracy: true, frequency: 1000 });

    });
}

// 坐标转换
function pointTranslate(point)
{
    var convertor = new BMap.Convertor();
    var pointArr = new Array();
    pointArr.push(point); //第一种方法创建数组
    convertor.translate(pointArr, 1, 5, showMap);
}

// 显示地图
function showMap(data)
{
    inputBlurListener(map);

    if(data.status === 0) {
        var marker = new BMap.Marker(data.points[0]);

        var lng = data.points[0]['lng'];
        var lat = data.points[0]['lat'];
        var label = new BMap.Label("当前您所在的位置<br>经度：" + lng 
                                    + "<br>纬度：" + lat,
                                    {offset:new BMap.Size(20,-10)});
        marker.setLabel(label); // 添加文字标签
        console.log("转换后："+ lng +","+lat);

        cpoint = new BMap.Point(lng, lat);
        map.addOverlay(marker);
        map.centerAndZoom(data.points[0], 18);
    }
}

// 停止定位
function stopWatchPos()
{
    var btnoff = document.getElementById("btnoff");
    btnoff.addEventListener("click", function() {
        navigator.geolocation.clearWatch(watchID);
        
        document.getElementById("btnon").style.backgroundColor = "";
        document.getElementById("btnon").style.right = "-50px";
        document.getElementById("btnoff").style.right = "-50px";

        console.log("Stop watch position");
    });
}

// 绘制多边形
function clickRange()
{
    var btnRange = document.getElementById("btnRange");
    btnRange.addEventListener("click", function() {
        
        var map = new BMap.Map("map");
        inputBlurListener(map);
        map.centerAndZoom(new BMap.Point(lng, lat), 18);

        var polygon = new BMap.Polygon([
            new BMap.Point(lng1,lat1),
            new BMap.Point(lng2,lat2),
            new BMap.Point(lng3,lat3),
            new BMap.Point(lng4,lat4),
        ], {strokeColor:"orange", strokeWeight:2, strokeOpacity:0.5});

        var marker = new BMap.Marker(new BMap.Point(lng, lat));
        var label = new BMap.Label("<绘制多边形的范例><br><strong>Range模式</strong>支持四点绘图<br>"
                                    + "请尝试在地图任意位置点击", {offset:new BMap.Size(-5,-5)});
        marker.setLabel(label);

        map.addOverlay(marker);
        map.addOverlay(polygon);


        //手动绘制
        var pointNum = 0;
        var pointArr = []; //第二种方法创建数组
        var polygon;
        map.addEventListener("click", function(e) {
            
            if(pointNum <= 3){
                console.log(e.point.lng+","+e.point.lat);
                var marker = new BMap.Marker(e.point);
                map.addOverlay(marker);
                pointArr.push(e.point);
                pointNum++;

                console.log(pointArr);
                console.log(pointNum);

                if(pointNum == 4){
                    polygon = new BMap.Polygon(pointArr, 
                        {strokeColor:"orange", strokeWeight:2, strokeOpacity:0.5});
                    map.addOverlay(polygon);
                    pointNum = 0; //重置为零
                    pointArr = [];
                }
            }

        });

    });
}

// 地址检索
function findAddress()
{
    var search_btn = document.getElementById("search-btn");
    search_btn.addEventListener("click", function() {

        var address = document.getElementById("search-txt").value;

        // 创建地址解析器实例
        var myGeo = new BMap.Geocoder();
        myGeo.getPoint(address, function(point) {
            if(point) {
                var map = new BMap.Map("map");
                inputBlurListener(map);
                map.centerAndZoom(point, 16);
                var marker = new BMap.Marker(point);
                map.addOverlay(marker);

                var label = new BMap.Label("经度：" + point['lng'] 
                                        + "<br>纬度：" + point["lat"],
                                        {offset:new BMap.Size(20,-10)});

                console.log("查询定位："+ point['lng'] +","+point["lat"]);

                marker.setLabel(label); //添加label
            }
            else {
                alert("请您输入正确的地址！");
            }
        });

    });
}

// 关键字提示
function autoComplete()
{
    var ac = new BMap.Autocomplete(
        {
            "input" : "search-txt"
        });

    ac.addEventListener("onconfirm", function() {
        document.getElementById("search-btn").click();
    });
}

// 文本框删除键
function showBtndel()
{
    var search_txt = document.getElementById("search-txt");
    search_txt.addEventListener("input", function() {

        document.getElementById("del-btn").style.display = "inline-block";

    });

    search_txt.addEventListener("blur", function() {

        document.getElementById("del-btn").addEventListener("click", function() {
            document.getElementById("search-txt").value = " ";
        });
        setTimeout(function() {
            document.getElementById("del-btn").style.display = "none";
        }, 100);
        
    });
}

// 移除输入框焦点的监听器
function inputBlurListener(map)
{
    map.addEventListener("touchmove", function() {

        document.getElementById("search-txt").blur();

    });
}