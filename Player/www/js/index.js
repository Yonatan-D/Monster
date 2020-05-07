document.addEventListener('deviceready', onDeviceReady);

function onDeviceReady()
{
    console.log("ready...");

    $('.footer-btnPause').hide();
    $('.main-btnPause').hide();
    StatusBar.backgroundColorByHexString("#429BCF"); // 状态栏颜色
    StatusBar.styleLightContent(); // 状态栏文本浅色
    addLocalAudio(); // 添加本地音频
    addArrayAudio(); // 添加数组音频
    touchSelectItem(); // 获取音乐列表的点击
    pauseAudio(); // 暂停播放
    continueAudio(); // 继续播放
    stopAudio(); // 停止播放
    offVolume(); // 静音
    switchAudio(); // 切换上下曲
    sliderTouchListener(); // 监听进度条事件
 
    // swipePage();

    clickBtnRecStart();
    playRecord();
}

// 浏览器调试
// $(document).ready(function() {

//     $('#main').bind('swiperight', function() {
//         $.mobile.changePage('#page2', {transition:'slide'});
//     });
//     $('#main').bind('swipeleft', function() {
//         $.mobile.changePage('#page3', {transition:'slide'});
//     });

// });
 

var my_media; // 媒体对象
var my_audioGroup = ["リフレイン Piano Version - Famishin", "Kyle Xian - ミカヅキ", "if（Cover：丁可） - 顾西奇", "theme of SSS -Piano Arrange Ver"]; // 本地音乐列表
var currentAudioKey; // 当前播放音频-key
var allTime; // 歌曲总时长
var currentTimer; // 当前播放进度
var jumpTime; // 进度条跳转
var isStopVolume = false; // 静音


/** 添加本地音频
 * 
 */
function addLocalAudio()
{
    var inputAudioFile = document.getElementById('inputAudioFile');
    inputAudioFile.addEventListener('change', function(e){

        var files = e.currentTarget.files;

        let s = "";

        for(let i=my_audioGroup.length-1; i<files.length; i++) { // my_audioGroup.length-1 避免覆盖原有音频

            my_audioGroup[i] = files[i];
            //选中音频的文件名
            let name = my_audioGroup[i].name;
            let index = name.lastIndexOf(".");
            name = name.substring(0, index); // 去掉文件名后缀格式
            //选中音频的文件大小
            let size = (my_audioGroup[i].size/Math.pow(2,20)).toFixed(2); // 单位MB，四舍五入两位小数
            //选中音频的文件类型
            let type = my_audioGroup[i].type;
            s = s + "<li> <a><h1>" + name + "</h1><p>" + size + "MB/" + type + "</p> </a>  <a class='ui-icon-grid'></a> </li>";

        };

        $('#audioGroup').append(s);
        $("#page2").page();
        $('#audioGroup').listview("refresh");

    });
}

/** 添加数组音频
 * 
 */
function addArrayAudio()
{
    let s = "";

    for(let i=0; i<my_audioGroup.length; i++) {

        let name = my_audioGroup[i];
        let size = "";
        let type = "";
        s = s + "<li> <a><h1>" + name + "</h1><p>" + size + "MB/" + type + "</p> </a>  <a class='ui-icon-grid'></a> </li>";

    };

    $('#audioGroup').append(s);
    $("#page2").page();
    $('#audioGroup').listview("refresh");
}

/** 获取音乐列表的点击
 * 
 */
function touchSelectItem()
{
    var mp3URL;
 
    $('#audioGroup').on('click', 'li a:nth-of-type(1)', function(e) {

        mp3URL = cordova.file.applicationDirectory + "www/music/"; // 音频文件路径-默认内置
        // mp3URL = cordova.file.externalRootDirectory + "Android/data/edu.yonatan.player/files/"; // 音频文件路径-本地上传

        let name = $(this).children("h1").text();
        mp3URL = mp3URL + name + ".mp3";
        console.log(mp3URL);

        playAudio(mp3URL); // 播放音乐

        currentAudioKey = my_audioGroup.indexOf(name); // 获得当前歌曲在数组中的位置

    });

}

/** 播放音乐
 * 
 */
function playAudio(src) 
{
    if(my_media) { // 释放资源
        my_media.release();
        console.log("release Media");
    }

    my_media = new Media(src, successCallback, errorCallback, statusCallback);

    my_media.play(); // 播放

    readAllTime(); // 获取歌曲总时长
    getCurrentTime(); // 获取播放进度
}

/** 实例化Media成功回调函数
 *  
 */ 
function successCallback()
{
    console.log("create Media success");
}


/** 实例化Media失败回调函数
 *  
 */ 
function errorCallback()
{
    console.log("实例化Media失败");
    JSON.stringify(err);
}


/** 实例化Media状态回调函数
 *  
 */ 
function statusCallback(status)
{
    switch (status) { // 播放状态
        case Media.MEDIA_NONE: console.log("NONE"); // 0
            break;
        case Media.MEDIA_STARTING: console.log("STARTING"); // 1
            break;
        case Media.MEDIA_RUNNING: console.log("RUNNING"); // 2

            // 播放键切换暂停键
            $('.footer-btnPlay').hide(); //底栏
            $('.footer-btnPause').show();
            $('.main-btnPlay').hide(); //播放页
            $('.main-btnPause').show();
            // 显示歌名
            $('.musicName').text(my_audioGroup[currentAudioKey]); 
            if(isPlayRec) {
                $('.musicName').text("录音"); 
            }
            // 封面旋转
            $('#musicCover').css("animation-play-state", "running");
            $('#musicCover').removeClass(); // 移除全部class
            $('#musicCover').addClass("covera"); // 添加旋转动画

            if(isPlayRec) {
                $('#pic-recAnime').css("animation-play-state", "running");
                $('#pic-recAnime').removeClass(); // 移除全部class
                $('#pic-recAnime').addClass("covera"); // 添加旋转动画
            }

            break;
        case Media.MEDIA_PAUSED: console.log("PAUSED"); // 3

            // 暂停键切换播放键
            $('.footer-btnPlay').show();
            $('.footer-btnPause').hide();
            $('.main-btnPlay').show();
            $('.main-btnPause').hide();
            // 封面停止旋转
            $('#musicCover').css("animation-play-state", "paused");

            if(isPlayRec) {
                $('#pic-recAnime').css("animation-play-state", "paused");
            }

            break;
        case Media.MEDIA_STOPPED: console.log("STOPPED"); // 4

            // 暂停键切换播放键
            $('.footer-btnPlay').show();
            $('.footer-btnPause').hide();
            $('.main-btnPlay').show();
            $('.main-btnPause').hide();
            // 封面复位
            $('#musicCover').addClass("coverb"); // 复位

            if(isPlayRec) {
                $('#pic-recAnime').addClass("coverb"); // 复位
            }
            
            // BUG未修复
            // clearInterval(currentTimer); // 清除定时器
            // currentTimer=null;

            isPlayRec = false; // // 关闭显示：播放录音时显示倒计时
            $('#pic-recNum').text("0");

            break;
        default:
            break;
    }
}

/** 暂停播放
 * 
 */
function pauseAudio()
{
    $('.footer-btnPause, .main-btnPause').on('click', function() { 
        if(my_media) {
            my_media.pause();
        }
    })
}

/** 继续播放
 * 
 */
function continueAudio()
{
    $('.footer-btnPlay, .main-btnPlay').on('click', function() {
        if(my_media) {
            my_media.play();
        }
    })
}

/** 停止播放
 * 
 */
function stopAudio()
{
    $('.footer-btnStop, .main-btnStop').on('click', function() {
        if(my_media) {
            my_media.stop();
        }
    })
}

/** 静音
 * 
 */
function offVolume()
{
    $('.main-btnVolume')[0].addEventListener('click', function() {
        if(isStopVolume) {
            if(my_media) { 
                my_media.setVolume(1);
                isStopVolume = false; //设置布尔值
                $('.main-btnVolume').css("background-image", "url('img/icon_volume.png')"); // 切换到音量图标
            }
            
        }else {
            if(my_media) {
                my_media.setVolume(0);
                isStopVolume = true; //设置布尔值
                $('.main-btnVolume').css("background-image", "url('img/icon_stopvolume.png')"); // 切换到静音图标
            }
        }
    });
}

/** 获取歌曲总时长
 * 
 */
function readAllTime()
{
    setTimeout(function() { 
        allTime = parseInt(my_media.getDuration());

        // 60分钟制
        let minute = parseInt(allTime/60);
        if(minute < 10) {
            minute = "0" + minute;
        }
        let second = parseInt(allTime%60);
        if(second < 10) {
            second = "0" + second;
        }

        $('.all_time').html(minute+":"+second); //输出到页面

    }, 100);
} 

/** 获取播放进度
 * 
 */
function getCurrentTime()
{
    if(currentTimer == null) {
        currentTimer = setInterval(function() {

            my_media.getCurrentPosition(
                // 获取进度成功
                function(position) {

                    if(position > -1) {
                        // 60分钟制
                        let minute = parseInt(position/60);
                        if(minute < 10) {
                            minute = "0" + minute;
                        }
                        let second = parseInt(position%60);
                        if(second < 10) {
                            second = "0" + second;
                        }

                        $('.current_time').html(minute+":"+second+" / ");
                        if(isPlayRec==true) { $('#pic-recNum').text(allTime-parseInt(position)); } // 播放录音时显示倒计时
                        // $('#pic-recNum').text(allTime-parseInt(position));

                        // 更新进度条的播放进度
                        setTimeout(timeUpdate(position), 950); // 1秒前进一格
                    }

                },
                //获取进度失败
                function(err) {
                    JSON.stringify(err);
                }
            )

        }, 50);
    }
}

/** 监听进度条事件
 * 
 */
function sliderTouchListener() {
    $('.touchRange')[0].addEventListener('touchstart', sliderTouch);
    $('.touchRange')[0].addEventListener('touchmove', sliderTouch);
    $('.touchRange')[0].addEventListener('touchend', jumpTimePlay); // touchend获得不到pageX, e.changedTouches[0].pageX。或者用move的坐标计算
}

/** 进度条操作
 * 
 */
function sliderTouch(event)
{
    console.log(event.type);
    let e = event.touches[0];

    let controllerwidth = $('.controller')[0].offsetWidth; // 控制点的宽度
    let sliderstart = $('.slider')[0].offsetParent.offsetLeft; // 进度条起始位置
    let sliderwidth = $('.slider')[0].offsetWidth; // 进度条宽度
    let sliderend = sliderwidth - controllerwidth; // 进度条结束位置
    let controllerLeft = e.pageX - sliderstart; // 控制点左边长度

    // 越界判断
    if(controllerLeft < 0) {
        controllerLeft = 0;
    }
    else if(controllerLeft > sliderend) {
        controllerLeft = sliderend;
    }

    let progress = controllerLeft + controllerwidth;

    $('.controller').css("left", controllerLeft + "px"); // 控制点位置
    $('.progress').css("width", progress + "px"); // 当前进度

    // 同步回音频进度
    jumpTime = allTime*1000*(controllerLeft/sliderwidth); // 应该用controllerLeft
}

/** 松手时跳转播放
 * 
 */
function jumpTimePlay()
{
    my_media.seekTo(jumpTime);
}


/** 更新进度条的播放进度
 * 
 */
function timeUpdate(position)
{
    let sliderwidth= $('.slider')[0].offsetWidth;
    let controllerwidth = $('.controller')[0].offsetWidth;

    let controllerLeft = (sliderwidth-controllerwidth)*(parseInt(position)/parseInt(allTime)); // 播放进度百分比   减去控制点的宽度
    let progress = controllerLeft + controllerwidth;

    $('.controller').css("left", controllerLeft + "px"); // 控制点位置
    $('.progress').css("width", progress + "px"); // 进度条颜色
}

/** 切换上下曲
 * 
 */
function switchAudio()
{
    // 上一曲
    $('.main-btnPre').on('click', function() {

        let key = currentAudioKey; //当前播放音频的key值
        key = key - 1;
        console.log(key);
        if(key >= 0) {
            let mp3URL = cordova.file.applicationDirectory + "www/music/" + my_audioGroup[key] + ".mp3";console.log(mp3URL);
            playAudio(mp3URL);
            currentAudioKey = key;
        }else {
            window.plugins.toast.show('已经到尽头了', '2000', 'bottom');
        }
        console.log(currentAudioKey); //修改全局key
    });

    // 下一曲
    $('.main-btnNext').on('click', function() {

        let key = currentAudioKey; //当前播放音频的key值
        key = key + 1;
        console.log(key);
        if(key <= my_audioGroup.length-1) {
            let mp3URL = cordova.file.applicationDirectory + "www/music/" + my_audioGroup[key] + ".mp3";console.log(mp3URL);
            playAudio(mp3URL);
            currentAudioKey = key;
        }else {
            window.plugins.toast.show('已经到尽头了', '2000', 'bottom');
        }
        console.log(currentAudioKey); //修改全局key
    });
}


 
// 取消page转场效果
$("div[data-role=page]").bind("pagebeforeshow", function (e, data) {  
    $.mobile.changePage.defaults.transition = 'none';  // 默认是fade, 还有slide slideup...
});  

// // 屏幕滑动切换page
// function swipePage()
// {
//     var nav = ["navTab", "navPly", "navRec"];
//     var flag;

//     $('.navTab').click(function() { flag = nav[0]; });
//     $('.navPly').click(function() { flag = nav[1]; });
//     $('.navRec').click(function() { flag = nav[2]; });

//     $('.page').on("swipeleft",function(){
//         console.log("You swiped left!");
//     });
    
//     $('.page').on("swiperight",function(){
//         console.log("You swiped right!");
//     });
// }



/**
 * 录音功能页
 */
var mediaRec=null; // 录音实例
var tid=null; // 计时器
let num = 0;
let isRecBtn = true; // "录音"状态
let isPlayRec = false; // "播放"状态

function clickBtnRecStart() 
{
    $('#btnRecStart').on('click', function() {
        
        if(isRecBtn) {
            startRecord();
            isRecBtn = false;
        }
        else {
            stopRecord();
            isRecBtn = true;
        }

    });
}

function startRecord()
{
    if(mediaRec==null)
    {
        //
        mediaRec=new Media(cordova.file.externalRootDirectory+"player_rec_test.wav"  ,function(){
                    console.log("rec end...");
        }  ,function(err){
            console.log("rec err:"+JSON.stringify(err));

        } ,function(code){
                switch(code)
                {
                    case Media.MEDIA_STARTING:console.log("start...");break;
                    case Media.MEDIA_RUNNING:afterStartRecord();break;
                    case Media.MEDIA_STOPPED:afterStopRecord();break;
                    case Media.MEDIA_PAUSED:break;
                }
        }  ) ;
    }

    mediaRec.startRecord(); // 开始录音
}

function stopRecord()
{
    if(mediaRec) mediaRec.stopRecord();
    if(tid) clearInterval(tid);
}



function afterStartRecord()
{
    $("#btnRecStart").text("停止");
    $("#btnRecStart").css('background-color', '#e74c3c');


    tid=setInterval(function(){

        num++;
        $('#pic-recNum').text(num);
            
    },1000);
}


function afterStopRecord()
{
    if(tid) clearInterval(tid);

    ///改变状态
    $("#btnRecStart").text("录音");
    $("#btnRecStart").css('background-color', '#429BCF');
    num = 0;
    $('#pic-recNum').text("0");
}

function playRecord()
{
    $('#btnRecPlay').on('click', function() {

        let recURL = cordova.file.externalRootDirectory+"player_rec_test.wav"; // 录音文件路径

        playAudio(recURL);

        isPlayRec = true;
    })
}