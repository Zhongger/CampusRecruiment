var user=null;
//
$(document).ready(function(){
	//alert("1100");
	user=checkLogin();
	getNewMsg();
	if(user!=null){
		if(user.type!=undefined&&user.type!=null){
			if(user.type=="user"){
				$("#username").html(user.username);
				$("#myFindJob").css("display","inline");
			}
			else if(user.type=="com"){
				$("#username").html(user.username);
				$("#publishJob").css("display","inline");
				$("#myPublishJob").css("display","inline");
			}
		}
	}
	else{
		$("#login").css("display","inline");
		$("#register").css("display","inline");
	}
	//
	/*var data=[{"companyName":"虎牙","id":123,"salary":"5k/month","deadLine":"2020-07-23","applyPosition":"java后台开发"}];
	showMsg(data);*/
});

//搜索按钮
function search()
{
	console.log("点击了搜索");
	var key=$("#search-input").val();
	if(key==""){
		alert("搜索内容为空，请检查！");
		return;
	}
	var url="user/recruitInfo";
	var ajaxData={"operate":"condition","condition":key};
	$.ajax({
        url:url,
		type:"post",
		data:ajaxData,
        dataType:"JSON",
        success:function(res){
			console.log(res);
			if(res.flag=="success"){
				$("#showMsg").html("");
				$("#showMsg").append("<div style='text-align: center;color:#00BFBF;'>搜索结果:"+res.data.length+"</div>");
				$("#showMsg").append("<hr>");
				showMsg(res.data);
			}
        },
        error:function(res){
			alert("连接服务器失败！");
            console.log(res);
        }
    });
}
//获取最新招聘信息
function getNewMsg()
{
	console.log("正在获取最新招聘信息...");
	var url="user/recruitInfo";
	var ajaxData={"operate":"all"};
	$.ajax({
        url:url,
		type:"post",
		data:ajaxData,
        dataType:"JSON",
        success:function(res){
			if(res.flag=="success"){
				showMsg(res.data);
			}
        },
        error:function(res){
            console.log(res);
        }
    });
	
}
//显示招聘简介
function showMsg(data){
	var len=data.length;
	for(var i=0;i<len;i++){
			var temp=data[i];
			var str="<div class='msg-body' onclick='seeMsg("+temp.id+",1)'>"
					+"<div style='font-size: 18px;color: rgb(0,255,255);'>"+temp.companyName+"</div>"
					+"<div style='font-size: 12px;'>截止时间："+temp.deadLine+"</div>"
					+"<div style='margin-top: 5px;'>招聘职位："+temp.applyPosition+"</div>"
					+"<div style='margin-top: 5px;'>薪资："+temp.salary+"</div>"
					+"<div class='text_seeMore'>点击查看详情</div>"
					+"</div>"
			$("#showMsg").append(str);
	}
}

