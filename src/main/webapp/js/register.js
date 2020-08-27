$(function () {
    $("#submit").click(function () {
        const d = {};
        let flag = true;
        const user_data = $("#registerForm").serializeArray();
        $.each(user_data,function () {
            d[this.name] = this.value;
        });
        $.each(d,function (n1,v1) {
            if(v1 == ""){
                $("#err_"+n1).text("此项不能为空!")
                flag = false;
            }else {
                $("#err_"+n1).text("")
            }
        })
        if(flag) {
            $.ajax({
                url: "registerUser",
                data: d,
                type: "POST",
                contentType: "application/x-www-form-urlencoded; charset=utf-8",
                dataType: "json",
                success: function (data) {
                    const json = eval(data);
                    if (json.status == "1") {
                        location.href = "register_ok.html";
                        return;
                    }
                    if (json.username != null && json.username != "") {
                        $("#err_username").text(json.username);
                    } else {
                        $("#err_username").text("");
                    }

                    if (json.email != null && json.email != "") {
                        $("#err_email").text(json.email);
                    } else {
                        $("#err_email").text("");
                    }

                    if (json.checkCode != null && json.checkCode != "") {
                        $("#err_check").text(json.checkCode);
                    } else {
                        $("#err_check").text("");
                    }
                },
                error: function () {
                    alert("error")
                }
            })
        }
    });
});