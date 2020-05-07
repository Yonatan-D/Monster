/**
 * 设置服务器的IP, 端口
 */
const IP = "192.168.1.247";
const port = "8080";
/**
 * 使用 Web SQL 数据库
 */
var webDB;
/**
 * 记录当前新闻列表加载到的最后一条
 * 初始值为启动时间
 */
var currTime = new CurrDate().toString();//自定义的时间类
var markTime = currTime;
/**
 * 处理滚动事件频繁请求ajax
 */
var isScroll = true;
/**
 * page转场效果
 */
$('div[data-role=page]').on('pagebeforeshow', function () {
    $.mobile.changePage.defaults.transition = 'none';
});
/**
 * cordova载入时调用
 */
$(document).on('deviceready', function () {

    console.log("ready...");
    try { // 设置状态栏及文本颜色   
        StatusBar.backgroundColorByHexString("#3498db");
        StatusBar.styleLightContent();
    } catch (error) { }

    openWebDB(); //打开本地数据库

    // 启动时断网检测
    var mode = navigator.connection.type;
    switch (mode) {
        case Connection.NONE: console.log("offline...");
            // 加载缓存的列表
            loadingLocalItems();
            // 加载缓存的轮播图
            loadingLocalBanNews();
            // 监控动态生成的a标签点击事件
            $('.slideShow, .newsItem').on('click', 'a', function () {
                loadingLocalArticle(this.id);
            });// 从本地加载缓存正文
            break;
        default: console.log("online...");
            loadingNewsItems(markTime); // 启动app时默认加载10条
            loadingBannerNews(); // 加载轮播新闻
            $(document).on('scroll', slideupLoading);// 监听列表滚动事件
            $('.slideShow, .newsItem').on('click', 'a', function () {
                loadingArticle(this.id);
            }); // 从服务器请求正文
            break;
    }

    // 响应实时网络状态
    $(document).on("online", function () {
        console.log("online article");
        window.location.reload(); // 联网自动刷新页面
    });
    $(document).on("offline", function () {
        console.log("offline article");
        window.plugins.toast.show('请检查网络状态', '2000', 'bottom');
        $('.slideShow, .newsItem').on('click', 'a', function () {
            loadingLocalArticle(this.id); // 断网加载本地已缓存的文章
        });
    });

    /**
     * 注册监听器
     */
    $('#main').on('pageshow', function () {
        $('.refreshBtn').show('slow'); // 首页显示刷新键
        $(document).on("backbutton", onBack); // 监听返回键按钮事件
        $(document).on('scroll', slideupLoading); // 监听列表滚动事件
    });
    $('#main').on('pagehide', function () {
        $('.refreshBtn').hide(); // 隐藏刷新键
        $(document).off("backbutton", onBack);
        $(document).off('scroll', slideupLoading); //移除事件
    });
    $('#detail').on('pagehide', function () {
        $('.title').empty();
        $('.subtime').empty(); //离开正文页时清空模板内容
        $('.author').empty();
        $('.container').empty();
    });
    $('.refreshBtn').on('click', onRefresh); // 刷新页面事件
    $('.delBtn').on('click', clearAllTables); //删除缓存事件
})
/**
 * 刷新按钮事件
 */
var rotateNum = -360;
function onRefresh() {
    $('html,body').animate({ scrollTop: 0 }, 1000);//滚动回顶部

    $('.refreshBtn').css('transform', 'rotate(' + rotateNum + 'deg)');
    rotateNum -= 360; //减去已经旋转的角度

    setTimeout(function () {
        currTime = new CurrDate().toString();
        markTime = currTime;
        window.location.reload();
    }, 1000);//等待滚动完毕执行
}
/**
 * 连续两次点击返回键退出
 */
var times = 0;
function onBack() {
    times++;
    if (times == 1)
        window.plugins.toast.show('再按一次退出', '2000', 'bottom');
    if (times == 2)
        navigator.app.exitApp();

    setTimeout(function () {
        times = 0;
    }, 2000);
}
/**
 * 下拉加载更多
 */
function slideupLoading() {
    var scrollTop = $(window).scrollTop();
    var scrollHeight = $(document).height();
    var windowHeight = $(window).height();
    if (scrollTop + windowHeight >= scrollHeight && isScroll) {
        isScroll = false; //进入判断将标记置为false
        console.log('加载中...');//加载中...
        try {
            window.plugins.toast.show('加载中', '500', 'bottom');
        } catch (error) { }
        loadingNewsItems(markTime);
    }
}
/**
 * 从服务器请求10条新闻
 */
function loadingNewsItems(time) {
    $.ajax({
        url: 'http://' + IP + ':' + port + '/NewsServer/GetNewsItems?time=' + time,
        type: 'get',
        dataType: 'jsonp',
        jsonp: 'jsonpCallback',
        success: function (data) {

            if (data == null || data.length == 0) { // 请求结果为空
                console.log("没有更多了");
                try {
                    window.plugins.toast.show('没有更多了', '2000', 'bottom');
                } catch (error) { }
            }

            for (var i in data) { //将请求的数据动态添加至列表页

                var icover = "http://" + IP + ":" + port + data[i].cover;
                var idate = formatDate(currTime, data[i].subtime);

                var item = '<a href="#detail" id="' + data[i].id + '" class="ui-item" data-role="none">'
                    + '<div class="ui-item-img"><img src="' + icover + '"></div>'
                    + '<div class="ui-item-msg">'
                    + '<h1>' + data[i].title + '</h1>'
                    + '<p><span>' + idate + '</span><span>' + data[i].author + '</span></p></div></a>';

                $('.newsItem').append(item);

                //更新标记时间
                markTime = data[i].subtime;
                //缓存新闻列表
                cachingNewsItem(data[i].id, data[i].title, data[i].author, data[i].subtime, data[i].cover);
            };

            imgOnError($('.ui-item img'));// 图片加载失败

        },
        error: function (err) {
            console.log("加载失败：" + JSON.stringify(err));
        }
    }).then(function () {
        isScroll = true; //等待请求回来再置回true
    })
}
/**
 * 从服务器请求轮播新闻
 */
function loadingBannerNews() {
    $.ajax({
        url: 'http://' + IP + ':' + port + '/NewsServer/GetBannerNews',
        type: 'get',
        dataType: 'jsonp',
        jsonp: 'jsonpCallback',
        success: function (data) {
            clearBanNewsID(); //清空id表的记录

            for (var i in data) { //将请求的数据动态添加至列表页

                var icover = "http://" + IP + ":" + port + data[i].cover;

                var item = '<a class="slideShow-container" id=' + data[i].id + ' href="#detail">'
                    + '<img class="sldShow-inside-img" src="' + icover + '">'
                    + '<div class="sldShow-inside-tit">' + data[i].title + '</div></a>';

                $('.slideShow').append(item);

                //记录轮播新闻的id
                recordBanNewsID(data[i].id);

            };
            imgOnError($('.sldShow-inside-img'));// 图片加载失败
        },
        error: function (err) {
            console.log("加载失败：" + JSON.stringify(err));
        }
    })
}
/**
 * 从服务器请求相应id值的新闻正文
 */
function loadingArticle(tid) {
    $.ajax({
        url: 'http://' + IP + ':' + port + '/NewsServer/GetNewsArticle?tid=' + tid,
        type: 'get',
        dataType: 'jsonp',
        jsonp: 'jsonpCallback',
        success: function (data) {

            let cover = "http://" + IP + ":" + port + data[0].cover;
            $('.title').text(data[0].title); //将请求的数据添加到显示页面
            $('.subtime').text(data[0].subtime.slice(0, 16));
            $('.author').text(data[0].author);
            $('.container').html("<img src=" + cover + ">" + data[0].content);
            imgOnError($('.container img'));// 图片加载失败
            cachingNewsArticle(data[0].id, data[0].content); //缓存已加载的正文

        },
        error: function (err) {
            console.log("加载失败：" + JSON.stringify(err));
        }
    })
}
/**
 * 打开 Web SQL 数据库
 * 创建数据库newsdb，表tb_article
 */
function openWebDB() {
    webDB = openDatabase('newsdb', '1.0', '缓存新闻信息', 2 * 1024 * 1024);
    webDB.transaction(function (tx) {
        tx.executeSql('CREATE TABLE IF NOT EXISTS tb_article (id primary key, title, author, subtime, cover, content)');
        tx.executeSql('CREATE TABLE IF NOT EXISTS tb_banner (id)');
    })
}
/**
 * 添加数据到tb_article表
 */
function cachingNewsItem(id, title, author, subtime, cover) {
    if (webDB)
        webDB.transaction(function (tx) {
            var sql = 'INSERT INTO tb_article (id, title, author, subtime, cover) VALUES (?, ?, ?, ?, ?)';
            tx.executeSql(sql, [id, title, author, subtime, cover]);
        })
}
/**
 * 记录轮播新闻的id
 */
function recordBanNewsID(id) {
    if (webDB)
        webDB.transaction(function (tx) {
            var sql = 'INSERT INTO tb_banner (id) VALUES (?)';
            tx.executeSql(sql, [id]);
        })
}
/**
 * 清空tb_banner表
 */
function clearBanNewsID() {
    if (webDB)
        webDB.transaction(function (tx) {
            var sql = 'DELETE FROM tb_banner';
            tx.executeSql(sql);
        })
}
/**
 * 清空tb_article表、tb_banner表
 */
function clearAllTables() {
    var c = confirm("确定要清除应用缓存吗？");
    if (c) {
        clearBanNewsID();
        if (webDB)
            webDB.transaction(function (tx) {
                var sql = 'DELETE FROM tb_article';
                tx.executeSql(sql);
            })
        window.plugins.toast.show('清除完毕', '500', 'center');
    }
}
/**
 * 修改指定id的新闻正文
 */
function cachingNewsArticle(id, content) {
    if (webDB)
        webDB.transaction(function (tx) {
            var sql = 'UPDATE tb_article SET content="' + content + '" WHERE id=?';
            tx.executeSql(sql, [id]);
        })
}
/**
 * 加载本地缓存列表
 */
function loadingLocalItems() {
    if (webDB)
        webDB.transaction(function (tx) {
            var sql = 'SELECT id, title, author, subtime, cover FROM tb_article';
            tx.executeSql(sql, [], function (tx, results) {
                var len = results.rows.length, i;

                for (i = 0; i < len; i++) {
                    var id = results.rows.item(i).id;
                    var title = results.rows.item(i).title;
                    var author = results.rows.item(i).author;
                    var subtime = results.rows.item(i).subtime;
                    subtime = formatDate(currTime, subtime);
                    var cover = results.rows.item(i).cover;

                    var item = '<a href="#detail" id="' + id + '" class="ui-item" data-role="none">'
                        + '<div class="ui-item-img"><img src="' + cover + '"></div>'
                        + '<div class="ui-item-msg">'
                        + '<h1>' + title + '</h1>'
                        + '<p><span>' + subtime + '</span><span>' + author + '</span></p></div></a>';

                    $('.newsItem').append(item);
                }
                imgOnError($('.ui-item-img img'));// 图片加载失败
            })
        })
}
/**
 * 加载本地缓存轮播新闻
 */
function loadingLocalBanNews() {
    if (webDB) 
        webDB.transaction(function (tx) {
            var sql = 'SELECT id, title, cover FROM tb_article a WHERE EXISTS (SELECT id FROM tb_banner b WHERE a.id=b.id)';
            tx.executeSql(sql, [], function (tx, results) {
                var len = results.rows.length, i;
                for (i = 0; i < len; i++) {
                    var id = results.rows.item(i).id;
                    var title = results.rows.item(i).title;
                    var cover = results.rows.item(i).cover;

                    var item = '<a class="slideShow-container" id=' + id + ' href="#detail">'
                        + '<img class="sldShow-inside-img" src="' + cover + '">'
                        + '<div class="sldShow-inside-tit">' + title + '</div></a>';

                    $('.slideShow').append(item);
                };
                imgOnError($('.sldShow-inside-img'));// 图片加载失败
            })

        })
}
/**
 * 加载本地缓存文章
 */
function loadingLocalArticle(tid) {
    if (webDB) 
        webDB.transaction(function (tx) {
            var sql = 'SELECT * FROM tb_article WHERE id=' + tid;
            tx.executeSql(sql, [], function (tx, results) {
                var title = results.rows.item(0).title;
                var author = results.rows.item(0).author;
                var subtime = results.rows.item(0).subtime;
                var cover = results.rows.item(0).cover;
                var content = results.rows.item(0).content;
                $('.title').text(title); //将请求的数据添加到显示页面
                $('.subtime').text(subtime.slice(0, 16));
                $('.author').text(author);
                $('.container').html('<img src="' + cover + '"/>' + content);
                imgOnError($('.container img'));// 图片加载失败
            })
        })
}
/**
 * 处理图片获取失效
 */
function imgOnError(imgObj) {
    imgObj.error(function () {
        let imgSrc = $(this).attr('src');
        imgSrc = imgSrc.replace("http://" + IP + ":" + port, "");//截取图片src信息
        if (imgSrc == "NoCover") { //无封面图
            $(this).hide();
        } else {
            $(this).attr('src', 'img/icon_img404.png'); //404
        }
    })
}
/**
 * 获取当前时间
 * (格式：YYYY-MM-DD hh:mm:ss)
 */
function CurrDate() {
    this.toString = function () {
        var date = new Date(+new Date() + 8 * 60 * 60 * 1000);//调整时区：东8区
        dateString = date.toISOString().slice(0, 19).replace('T', ' ');//转换字符串
        return dateString;
    }
}
/**
 * 将dateString字符串存储进DateObject对象
 */
function DateObject(dateString) {
    this.year = dateString.slice(0, 4);
    this.month = dateString.slice(5, 7);
    this.day = dateString.slice(8, 10);
    this.hh = dateString.slice(11, 13);
    this.mm = dateString.slice(14, 16);
    this.ss = dateString.slice(17, 19)
}
/**
 * 格式化为距离当前时间的时间间隔
 */
function formatDate(currentDateStr, targetDateStr) {
    let interval = ""; //时间间隔
    let dateArr = ["年前", "月前", "天前", "小时前", "分钟前", "秒前"];
    let currentDate = Object.values(new DateObject(currentDateStr));
    let targetDate = Object.values(new DateObject(targetDateStr));

    for (let i in currentDate) {
        if (currentDate[i] > targetDate[i]) {
            let number = currentDate[i] - targetDate[i];
            interval = number + dateArr[i];
            return interval;
        }
    }
}
