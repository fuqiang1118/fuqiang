/**
 * Created by fuqiang on 2019/11/11.
 */

// 登出处理
$("#logout").click(function () {
    layer.confirm('真退出么', function(index){
        $.ajax({
            url:'/logout',
            type:'post',
            dataType:'json',
            success:function(data){
                if (data.code == 200){
                    window.location.href = "/login_page";
                }
            }
        });
        layer.close(index);
    });



});