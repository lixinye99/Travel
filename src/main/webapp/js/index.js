$(function () {
    $.ajax({
        url:"indexListSearch",
        dataType:"json",
        type:"POST",
        contentType:"application/x-www-form-urlencoded; charset=utf-8",
        success:function (data) {
            const json = eval(data);
            $.each(json,function (n1,v1) {
                const jsonitem = $.parseJSON(v1);
                const item = "<div class='col-md-4'><a href='route_detail.html?rid="+jsonitem.rid +"'>" +
                    "<img src=" +jsonitem.rimage+" alt=''> " +
                    "<div class='has_border'> " +
                    "<h3>"+ jsonitem.rname +"</h3>" +
                    "<div class='price'>网付价<em>￥</em><strong>"+ jsonitem.price +"</strong><em>起</em></div>" +
                    "                </div> " +
                    "</a> " +
                    "</div>";
                $("#city_travel").append(item);
            })
        },
        error:function () {
            alert("error");
        }
    })
});