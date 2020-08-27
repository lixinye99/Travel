/**
 * @author Arno Clare
 */
$(function() {
    //初始化
    const str = window.location.search.substring(1);
    const pars = str.split("&");
    //获取 url 参数
    let pageNum = getParameter("pageNum");
    if (pageNum == null)
        pageNum = 1;
    else
        pageNum = parseInt(pageNum);
    const category = decodeURI(getParameter("category"));
    const name = decodeURI(getParameter("name"));
    //调整页数情况
    const pageSize = 8;
    let pageCount = -1;

    //请求数据
    $.ajax({
        url: "routelist",
        type: "POST",
        data: {
            "pageNum" : pageNum,
            "pageSize" : pageSize,
            "category" : category,
            "name" : name
        },
        contentType:
            "application/x-www-form-urlencoded; charset=utf-8",
        datatype: "json",
        success: function(data) {
            const json = $.parseJSON(data);
            //显示列表
            if (json.routeList != null && json.routeList !== "") {
                let c = 0;
                $.each(json.routeList, function(index, value) {
                    const i = "#main_list>li:eq("+index+") ";
                    $(i + "img").attr("src", value.rimage);
                    $(i + ".text1 p:first").text(value.rname);
                    $(i + ".text1 p:last").text(value.routeIntroduce);
                    $(i + ".price_num span:eq(1)").text(value.price);
                    const href = $(i + ".price a").attr("href");
                    $(i + ".price a").attr("href", href + "?rid=" + value.rid);
                    ++c;
                });
                //如果数量不足 pageSize 个
                for (; c < pageSize; ++c) {
                    $("#main_list>li:eq("+c+")").css("display", "none");
                }
            }
            //显示页码
            const routeCount = json.routeCount;
            pageCount = (routeCount - 1) / pageSize + 1;
            //js 不会舍弃小数
            pageCount = parseInt(pageCount);
            $("#all_page").text(pageCount);
            $("#all_route").text(routeCount);
        },
        error: function() {
            alert("route list error");
        }
    });

    function getParameter(par) {
        for (let i = 0; i < pars.length; ++i) {
            const pair = pars[i].split("=");
            if (pair[0] === par)
                return pair[1];
        }
        return null;
    }

    $("#a_first").click(function() {
        if (pageNum === 1) {
            alert("已经是首页");
            return false;
        }
        window.location.href = getBaseUrl() + 1;
    });
    $("#a_prev").click(function() {
        if (pageNum === 1) {
            alert("已经是第一页");
            return false;
        }
        window.location.href = getBaseUrl() + (pageNum - 1);
    });
    $("#a_next").click(function() {
        if (pageNum === pageCount) {
            alert("已经是最后一页");
            return false;
        }
        window.location.href = getBaseUrl() + (pageNum + 1);
    });
    $("#a_last").click(function() {
        if (pageNum === pageCount) {
            alert("已经是末页");
            return false;
        }
        window.location.href = getBaseUrl() + pageCount;
    });

    function getBaseUrl() {
        let res = "route_list.html?";
        if (category == null || category === "null") {
            res += "name=" + encodeURI(name) + "&pageNum=";
        } else {
            res += "category=" + encodeURI(category) + "&pageNum=";
        }
        return res;
    }
});