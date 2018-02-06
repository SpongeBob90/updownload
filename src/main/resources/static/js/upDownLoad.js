$(function () {
    //绑定文件选择后加载文件名称事件
    $('#file').change(function(e){
        //e.currentTarget.files 是一个数组，如果支持多个文件，则需要遍历
        $('#uploadFile').html("<i class='fileIcon'></i>" + "<span>" + e.currentTarget.files[0].name + "</span>");
        $('#uploadFile span').attr("title", e.currentTarget.files[0].name);
    });
    //绑定文件上传事件
    $('#uploadBtn').bind("click",upLoad);
    //绑定文件下载事件
    $('#downLoadBtn').bind("click",downLoad)
});

function upLoad() {
    var formData = new FormData(document.getElementById("uploadForm"));
    $.ajax({
        url: 'upLoadFile',
        type: "POST",
        data: formData,
        enctype: 'multipart/form-data',
        processData: false,  // tell jQuery not to process the data
        contentType: false,   // tell jQuery not to set contentType
        success: function (data) {
            console.log(data)
        }
    });
}

function downLoad() {
    var a = document.createElement("a");
    a.href = "downLoadFile";
    a.click();
}
