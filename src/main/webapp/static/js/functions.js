
//获取url中的参数
function getQueryParam(name)
{
	var query = window.location.search.substring(1);
	var vars = query.split("&");
	for (var i=0;i<vars.length;i++) {
		var pair = vars[i].split("=");
		if(pair[0] == name){return pair[1];}
	}
	return null;
}
//
//检查登陆状态
function checkLogin()
{
	console.log("正在检查登陆...");
	var loginUser=null;
	var storage=window.sessionStorage;
	var type=storage.getItem("type");
	var username=storage.getItem("username");
	if(type!=null&&type!=undefined&&username!=null&&username!=undefined){
		loginUser={"username":username,"type":type};
	}
	return loginUser;
}
//
//查看招聘详情
function seeMsg(msg_id,src)
{
	console.log(src+":点击了查看招聘详情");
	if(src==1)
		location.href="pages/seeMsg.html?id="+msg_id;
	else
		location.href="seeMsg.html?id="+msg_id;
}

//创建验证码
function creatCode(type)
{
	if(type=="user"){
		return makeCode("codecanvas");
	}
	else if(type=="com"){
		return makeCode("codecanvas2");
	}
}
//
//创建验证码
function makeCode(id)
{
	showCode="";
	//document.getElementById('codecanvas')
	var canvas=document.getElementById(id);
	if(canvas!=null)
	{
		var width=canvas.clientWidth;
		var height=canvas.clientHeight;
		var context=canvas.getContext("2d");
		canvas.width=width;
		canvas.height=height;
		var sCode="q,w,e,r,t,y,u,i,o,p,a,s,d,f,g,h,j,k,l,z,x,c,v,b,n,m,1,2,3,4,5,6,7,8,9,0";
		var aCode=sCode.split(",");
		var codeLength=aCode.length;

		for(var i=0;i<=3;i++)
		{
			var index=Math.floor(Math.random()*codeLength);
			showCode+=aCode[index];
		}
		context.translate(10,25);
		context.font="bold 23px 微软雅黑";
		context.fillStyle=randomColor();
		context.fillText(showCode,0,0);

		for(var i=0;i<=10;i++)
		{
			context.translate(-5,-5);
			context.strokeStyle=randomColor();
			context.beginPath();
			context.moveTo(Math.random()*width,Math.random()*height);
			context.lineTo(Math.random()*width,Math.random()*height);
			context.stroke();
		}
		return showCode;
	}
}
//随机颜色
function randomColor()
{
	var r=Math.floor(Math.random()*256);
	var g=Math.floor(Math.random()*256);
	var b=Math.floor(Math.random()*256);
	return "rgb("+r+","+g+","+b+")";
}