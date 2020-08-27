$(function() {
    const rid = window.location.href.split('=')[1];
    $.ajax({
        url:"showRouteDetail",
        type: "POST",
        data:{"rid":rid},
        contentType:"application/x-www-form-urlencoded; charset=utf-8",
        datatype: "json",
        success:function (data) {
            const json = $.parseJSON(data);
            $.each(json,function (n1,v1) {
                const jsonitem = $.parseJSON(v1);
                if(n1 == "seller"){
                    $("#sname").text("经营商家："+jsonitem.sname);
                    $("#consphone").text("咨询电话："+jsonitem.consphone);
                    $("#address").text("地址："+jsonitem.address);
                }
                if(n1 == "detail"){
                     $("#name_lab").text(jsonitem.rname);
                     $("#name").text(jsonitem.rname);
                     $("#price").text(jsonitem.price)
                     $(".big_img").attr("src",jsonitem.rimage);
                }
            })
        },
        error:function () {
            alert("error")
        }
    });

    /**
     * @author Arno Clare
     */
    $("#a_favorite").click(function() {
        $.ajax({
            url:"favorite",
            type: "POST",
            data:{"rid": rid},
            contentType:"application/x-www-form-urlencoded; charset=utf-8",
            datatype: "json",
            success: function(data) {
                const json = $.parseJSON(data);
                alert(json.msg);
                $("#count_favorite").text("已收藏" + json.count + "次");
            },
            error: function() {
                alert("favorite error");
            }
        });
    });
});