$(function () {
    function touch(obj,background,endBackground){
        obj.on("touchstart", function(e) {
            $(this).css({"background":background});
        });
        obj.on("touchmove", function(e) {
            $(this).css({"background":background})
        });
        obj.on("touchend", function(e) {
            $(this).css({"background":endBackground})
        });
    }
    touch($(".alertCon .ok"),"#eee","#fff");


    $(".alertCon .ok").click(function () {
        $(".alertBox").fadeOut();
    })
})