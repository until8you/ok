/* scripts/login.js 编码utf-8*/

$(function(){
	//console.log('Hello Word');
	//登陆功能
	$('#login').click(loginAction);
	$('#count').blur(checkname);
	$('#password').blur(checkpassword);
	//注册功能
	$('#regist_button').click(registAction);
	$('#regist_username').blur(checkRegistName);
	$('#regist_password').blur(checkRegistPassword);
	$('#final_password').blur(checkConfirm);
	
});

//检验注册用户名
function checkRegistName(){
	var registName = $('#regist_username').val().trim();
	var rule = /^\w{4,10}$/;
	if(rule.test(registName)){
		 $('#regist_username').next().hide();
		 return true;
	}
	$('#regist_username').next().show().find('span').html("4~10个字符");
	return false;
	
}

//检验注册密码
function checkRegistPassword(){
	var registPassword = $('#regist_password').val().trim();
	var rule =/^\w{4,10}$/;
	if(rule.test(registPassword)){
		$('#regist_password').next().hide();
		return true;
	}
	$('#regist_password').next().show().find('span').html("4~10个字符");
	return false;
}

//检查验证密码
function checkConfirm(){
	var password = $('#regist_password').val();
	var confirm = $('#final_password').val();
	if(password && password==confirm){
		$('#final_password').next().hide();
		return true;
	}
	$('#final_password').next().show().find('span').html("确认密码不一致");
	return false;
	
}
//注册按钮
function registAction(){
	//console.log("success");
	//获取界面表单数据
	var registName = $('#regist_username').val();
	var registNick = $('#nickname').val();
	var registPassword = $('#regist_password').val();
	var registConfirm = $('#final_password').val();
	//检查界面参数
	var n = checkRegistPassword()+checkRegistName()+checkConfirm();
	if(n!=3){
		return;
	}
	
	console.log("registData");
	//ajax请求
	var url = 'user/regist.do';
	var data = {"name":registName,
			"nick":registNick,
			"password":registPassword,
			"confirm":registConfirm};
	//$.post 封装了$.ajax 是简化版
	$.post(url,data,function(result){
		console.log(result);
		if(result.status==0){
			//注册成功，退回登陆页面
			$('#back').click();//触发事件
			var name = result.data.name;
			$('#count').val(name);
			$('#password').focus();//触发焦点事件，输入光标处于password框内
			//清空表单值
			$('#regist_username').val('');
			$('#nickname').val('');
			$('#regist_password').val('');
			$('#final_password').val('');
		}else{
			//注册失败
			var msg = result.message;
			if(result.status==3){
				$('#regist_password').next().show().find('span').html(msg);
			}else if(result.status==4){
				$('#regist_username').next().show().find('span').html(msg);
			}else{
				alert(msg);
			}
		}
	});
//	$.ajax({
//		url:'user/regist.do',
//		data:registData,
//		type:'post',
//		dataType:'json',
//		success:function(result){
//			console.log(result);
//			if(result.status==0){
//				//注册成功
//				var data = result.data;
//				console.log(data);
//				alert("注册成功");
//			}else{
//				//注册失败
//				var msg = result.message;
//				if(result.status==2){
//					$('#regist_password').next().show().find('span').html(msg);
//				}else if(result.status==4){
//					$('#regist_username').next().show().find('span').html(msg);
//				}else{
//					alert(msg);
//				}
//			}
//		},
//		error:function(){
//			alert("通讯错误");
//		}
//	});
}

//登陆检查
function checkname(){
	var name = $('#count').val();
	//检验name和password
	var rule = /^\w{4,10}$/;
	if(!rule.test(name)){
		$('#count').next().html("4~10个字符");
		return false;
	}
	$('#count').next().empty();
	return true;
}

function checkpassword(){
	var password = $('#password').val();
	//检验name和password
	var rule = /^\w{4,10}$/;
	if(!rule.test(password)){
		$('#password').next().html("4~10个字符");
		return false;
	}
	$('#password').next().empty();
	return true;
}

function loginAction(){
	//console.log('loginAction');
	//获取用户输入的用户名和密码
	var name = $('#count').val();
	var password = $('#password').val();
	
	//name和password进行验证，防止出现空指针异常
	var n = checkname()+checkpassword();
	if(n!=2){
		return;
	}
	//data 对象中的属性名要和服务器控制器的参数名一样!login(name,passowrd)
	var data ={"name":name,"password":password};
	$.ajax({
		url:'user/login.do',
		data:data,
		type:'post',
		dataType:'json',
		success:function(result){
			console.log(result);
			if(result.status==0){
				//登陆成功
				var user = result.data;
				console.log(data);
				//登陆成功以后，将userId保存到cookie中，时间是30天
				addCookie("userId",user.id);
				//跳转到edit.html
				location.href = 'edit.html';
			}else{
				//登陆失败
				var msg = result.message;
				if(result.status==2){
					$('#count').next().html(msg);
				}else if(result.status==3){
					$('#password').next().html(msg);
				}else{
					alert(msg);
				}
			}
		},
		error:function(e){
			alert("通信失败");
		}
	});
}