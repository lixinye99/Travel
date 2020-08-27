$(function() {

    $.ajax({
        url:"sendEmail",
        type: "POST",
        contentType:"application/x-www-form-urlencoded; charset=utf-8",
        datatype: "json",
        success:function (data) {
            const json = $.parseJSON(data)
            if(json.status == "1"){
                $("#message").text("恭喜您，邮箱可以使用,已经向您的注册邮箱发送验证邮件!");
            }
            if(json.status == "0"){
                $("#message").text("您输入的邮箱有问题那，验证邮件无法送达!账号无法使用")
            }
        },
        error:function () {
            alert("error")
        }
    });

});