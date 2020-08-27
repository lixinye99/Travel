$(function () {
   $.ajax({
       url:"headListSearch",
       dataType:"text",
       type:"POST",
       contentType:"application/x-www-form-urlencoded; charset=utf-8",
       success:function (data) {
            var str = data.split(',')
            for(let i=0; i<str.length-1; i++){
                const item = "<li><a href='route_list.html?category=" + encodeURI(str[i]) + "'>" + str[i] + "</a></li>";
                $("#firstPage").after(item);
            }
       },
       error:function () {
            alert("error");
       }
   });

    /**
     * @author Arno Clare
     */
   $("#btn_search").click(function() {
       window.location.href = "route_list.html?name=" + encodeURI($("#input_search").val());
   });
});