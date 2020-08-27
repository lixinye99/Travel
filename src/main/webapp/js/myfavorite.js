$(function() {
    const pageNum = window.location.href.split('=')[1];
    let pageCount = -1;
    $.ajax({
        url:"myFavorite",
        type: "POST",
        data:{"pageNum":pageNum},
        contentType:"application/x-www-form-urlencoded; charset=utf-8",
        datatype: "json",
        success:function (data) {
            const json = $.parseJSON(data);
            if(json.unlogin != null && json.unlogin != ""){
                alert(json.unlogin);
                location.href = "login.html";
            }
            if(json.pageerror != null && json.pageerror != ""){
                alert(json.unlogin);
            }
            if(json.pageCount != null && json.pageCount != ""){
                pageCount = json.pageCount;
                $("#endpage").children().attr("href","myfavorite.html?pageNum="+json.pageCount)
                if(parseInt(pageNum) -1 > 0){
                    $("#previouspage").children().attr("href","myfavorite.html?pageNum="+(parseInt(pageNum)-1))
                }
                if(parseInt(pageNum)+1 <= json.pageCount){
                    $("#nextpage").children().attr("href","myfavorite.html?pageNum="+(parseInt(pageNum)+1))
                }
                for(let i=1;i<=json.pageCount;i++){
                    /*<li><a href="#">1</a></li>*/
                    const item = "<li><a href='myfavorite.html?pageNum="+ i +"'>"+i+"</a></li>";
                    $("#nextpage").before(item)
                }
            }
            if(json.favoriteList != null && json.favoriteList != ""){
                $.each(json.favoriteList,function (n1,v1) {
                    const routeitem = '<div class="col-md-3">' +
                                        '<a href="route_detail.html?rid='+ v1.rid +'">' +
                                            '<img src="'+ v1.rimage +'" alt="">' +
                                            '<div class="has_border">\n' +
                                                '<h3>'+ v1.routeIntroduce +'</h3>' +
                                                '<div class="price">网付价<em>￥</em><strong>'+ v1.price +'</strong><em>起</em></div>' +
                                            '</div>' +
                                        '</a>' +
                                        '</div>'
                    $("#myfavoriteroute").append(routeitem);
                })
            }
        },
        error:function () {
            alert("error")

        }
    });

    $("#previouspage").click(function (event) {
        if(pageNum == 1){
            alert("已经是第一页了!")
            event.preventDefault();
        }
    });

    $("#nextpage").click(function (event) {
        if(pageNum == pageCount){
            alert("已经是最后一页了!")
            event.preventDefault();
        }
    });
    $("#firstPage").click(function (event) {
        if(pageNum == 1){
            alert("已经是第一页了!")
            event.preventDefault();
        }
    });

    $("#endpage").click(function (event) {
        if(pageNum == pageCount){
            alert("已经是最后一页了!")
            event.preventDefault();
        }
    });


});