//电视剧大图滚动

import $ from 'jquery';


var slider = () =>{

    //定义一个初始速度
    var sudu = 1000;
    var shangdi = false;

    //定义json
    var json0 = {"width":"174px","height":"122px","left":"-116px", "top":"100px"};
    var json1 = {"width":"386px","height":"482px","left":"0px", "top":"35px"};
    var json2 = {"width":"450px","height":"556px","left":"321px", "top":"0"};
    var json3 = {"width":"386px","height":"482px","left":"706px", "top":"35px"};
    var json4 = {"width":"174px","height":"122px","left":"1097px", "top":"100px"};

    var nowimg = 2;

    var timer = setInterval(clickRight,5000);
    $("#slide").mouseenter(
        function() {
            clearInterval(timer);
        }
    );

    $("#slide").mouseleave(
        function() {
            clearInterval(timer);
            timer = setInterval(clickRight,5000);
        }
    );


    $(".rightNav").click(clickRight);
    $(".rightFilter").click(clickRight);

    function clickRight(){
        if(!$(".tuul li").is(":animated") || shangdi == true){
            if(nowimg < 5){
                nowimg ++;
            }else{
                nowimg = 0;
            }
            $(".xiaoyuandian li").eq(nowimg).addClass("cur").siblings().removeClass("cur");

            //先交换位置
            $(".no1").animate(json0,sudu);
            $(".no2").animate(json1,sudu);
            $(".no3").animate(json2,sudu);
            $(".no4").animate(json3,sudu);
            $(".no0").css(json4);

            //再交换身份
            $(".no0").attr("class","waiting");
            $(".no1").attr("class","no0");
            $(".no2").attr("class","no1");
            $(".no3").attr("class","no2");
            $(".no4").attr("class","no3");
            //上面的交换身份，把no0搞没了！所以，我们让no1前面那个人改名为no0
            if($(".no3").next().length != 0){
                //如果no3后面有人，那么改变这个人的姓名为no4
                $(".no3").next().attr("class","no4");
            }else{
                //no3前面没人，那么改变此时队列最开头的那个人的名字为no0
                $(".tuul li:first").attr("class","no4");
            }

            //发现写完上面的程序之后，no6的行内样式还是json0的位置，所以强制：
            $(".no4").css(json4);
        }

    }

    $(".leftNav").click(clickLeft);
    $(".leftFilter").click(clickLeft);
        function clickLeft(){

            if(!$(".tuul li").is(":animated") || shangdi == true){

                if(nowimg > 0){
                    nowimg --;
                }else{
                    nowimg = 5;
                }
                $(".xiaoyuandian li").eq(nowimg).addClass("cur").siblings().removeClass("cur");
                //先交换位置:
                $(".no0").animate(json1,sudu);
                $(".no1").animate(json2,sudu);
                $(".no2").animate(json3,sudu);
                $(".no3").animate(json4,sudu);
                $(".no4").css(json0);

                //再交换身份
                $(".no4").attr("class","waiting");
                $(".no3").attr("class","no4");
                $(".no2").attr("class","no3");
                $(".no1").attr("class","no2");
                $(".no0").attr("class","no1");

                //上面的交换身份，把no0搞没了！所以，我们让no1前面那个人改名为no0
                if($(".no1").prev().length != 0){
                    //如果no1前面有人，那么改变这个人的姓名为no0
                    $(".no1").prev().attr("class","no0");
                }else{
                    //no1前面没人，那么改变此时队列最后的那个人的名字为no0
                    $(".tuul li:last").attr("class","no0");
                }

                $(".no0").css(json0);
            }

        }


};

export default slider