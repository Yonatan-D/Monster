document.addEventListener('deviceready', onDeviceReady);

function onDeviceReady() {
    console.log("ready...");

    upload();
    uploadlogo();
    showServerFile();
}

/**
 * 上传选择文件
 */
function upload() {
    $('#upBtn').on('click', function () {

        var fileName = $('#myfile')[0].files[0].name;

        // 路径转换
        var reader = new FileReader();
        reader.onload = function (e) {
            var localFile = e.target.result;
            uploadToServer(localFile, fileName);
        }
        reader.onerror = function () { console.error("readAsDataURL error!"); }

        reader.readAsDataURL(myfile.files[0]); //转换成base64
    });

}

/**
 * 上传指定文件 (logo.png)
 */
function uploadlogo() {
    $('#uplogoBtn').on('click', function () {
        var localFile = cordova.file.applicationDirectory + "www/img/logo.png";
        var fileName = "logo.png";

        uploadToServer(localFile, fileName);
    });
}

/**
 * 上传至服务器
 */
function uploadToServer(localFile, fileName) {
    var server = "http://192.168.1.189:8080/UploadServlet3/Upload"; //服务器端
    var options = { fileKey: "myfile", fileName: fileName };

    var trans = new FileTransfer();
    trans.upload(localFile, encodeURI(server), onsuccess, onerror, options); //文件上传

    function onsuccess() {
        alert("上传成功:" + fileName);
        $('#progress').text("progress");
    };
    function onerror(err) { console.log("上传失败:" + JSON.stringify(err)); };

    trans.onprogress = function (progressEvent) { // 传输进度
        if (progressEvent.lengthComputable) {
            var progress = Math.round((progressEvent.loaded / progressEvent.total) * 100);
            $('#progress').text("progress: " + progress + "%");
        } else {
            $('#progress').text("progress");
        }
    }
}

/**
 * 下载自服务器
 */
function downloadFromServer() {
    var source = "http://192.168.1.189:8080/UploadServlet3/uploadFile/list.dat";
    var target = cordova.file.externalApplicationStorageDirectory+"list.dat";

    var trans = new FileTransfer();
    trans.download(encodeURI(source), target, onsuccess, onerror);

    function onsuccess(entry) { console.log("下载成功" + entry.toURL()); }
    function onerror(err) { console.log("下载失败" + JSON.stringify(err)); };//下载失败生成一个空list.dat。
}

/**
 * 读取文件内容
 */
function onReadFile()
{
    resolveLocalFileSystemURL(cordova.file.externalApplicationStorageDirectory, function(entry) {

        entry.getFile("list.dat", { create:false, exclusive:false }, function(fileEntry){

            fileEntry.file(function(file){

                var textView = document.getElementById("textView");

                var reader = new FileReader();
                reader.onload = function(e) { //测试，待精简
                    var files = e.target.result;
                    files = files.split(","); //截取到4个，第4个为空
                    var listtv = "";
                    for(var i=0; i<files.length-1; i++){ //files.length-1减去最后一个空白的项
                        listtv = listtv + ((i+1)+"> "+files[i]+"<br>");
                    }
                    textView.innerHTML = listtv;
                 }
                reader.onerror = function() { alert("读取文件出错"); }

                reader.readAsText(file, 'utf-8');

            }, errorCB);

        }, errorCB);

    }, errorCB);
}

function errorCB(err)
{
    JSON.stringify(err);
}

/**
 * 查看服务器文件
 */
function showServerFile() {
    $('#checkBtn').on('click', function () {

        var url = "http://192.168.1.189:8080/UploadServlet3/ReadCloud";
        $.ajax({
            url: url,
            type: "post",
            async: true,
            success: function(){ console.log("ajax请求成功！"); }
        });

        downloadFromServer();
        onReadFile();
        
    });
}