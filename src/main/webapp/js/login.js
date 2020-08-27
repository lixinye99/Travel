$(function () {
    $("#login").click(function () {
        const d = {};
        const user_data = $("#loginForm").serializeArray();
        $.each(user_data,function () {
            d[this.name] = this.value;
        });
        $.ajax({
            url:"login",
            data:d,
            type:"POST",
            contentType:"application/x-www-form-urlencoded; charset=utf-8",
            dataType:"json",
            success:function (data) {
                const json = eval(data);
                if(json.username != null&&json.username != ""){
                    $("#errorMessage").text(json.username);
                }else if (json.password != null&&json.password != ""){
                    $("#errorMessage").text(json.password);
                }else if(json.checkCode != null&&json.checkCode != ""){
                    $("#errorMessage").text(json.checkCode);
                }
                if(json.status == "1"){
                    location.href = "index.html";
                    return;
                }
            },
            error:function () {
                alert("error")
            }
        })
    })
});