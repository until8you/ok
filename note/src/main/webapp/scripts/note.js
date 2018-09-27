// scripts/note.js  编码utf-8

var SUCCESS = 0;
var ERROR = 1;

$(function(){
	
	//把notebook的初始页码绑定到docment上
	$(document).data('page',0);
	//网页加载完成后立即执行该方法(读取笔记本列表)
	//loadNotebooks();
	//使用页码翻页来加载notebook
	loadpagedNotebooks();
	//对notebook的more 绑定单击事件
	$('#notebook-list').on('click','.more',loadpagedNotebooks);
	//绑定笔记本列表区域的点击事件 事件冒泡
	/*
	 * on:绑定事件
	 * click:绑定单击事件
	 * li：元素过滤,指出事件源单击li才会触发on  '.notebook'过滤器选择
	 * function：是事件触发后执行的函数
	 * 
	 * on()方法绑定事件可以区别事件源
	 * click方法绑定事件，无法区别事件源
	 */
	$('#notebook-list').on('click','.notebook',loadNotes);
	//笔记列表绑定单击事件
	$('#note-list').on('click','.note',loadNote);
	//添加笔记事件
	$('#note-list').on('click','#add_note',showAddNoteDialog);
	//新增笔记的创建按钮的监听事件
	$('#can').on('click','.create_note',addNote);
	//新增笔记按钮的取消和返回监听事件
	$('#can').on('click','.close,.cancle',closeDialog);
	//监听保存按钮
	$('#save_note').click(saveNote);
	
	//添加笔记本事件
	$('#notebook-list').on('click','#add_notebook',showAddNotebookDialog);
	//绑定创建笔记本按钮事件
	$('#can').on('click','.create-notebook',addNotebook);
	//绑定笔记本子菜单触发事件
	$('#note-list').on('click','.btn-note-menu',showNoteMenu);
	//点击任意区域将菜单收起
	$(document).click(hideNoteMenu);
	//笔记移动对话框显示功能
	$('#note-list').on('click','.btn_move',showNoteMove);
	//笔记本移动功能
	$('#can').on('click','.move-note',noteMove);
	//笔记移动对话框显示功能
	$('#note-list').on('click','.btn_delete',showDeleteNote);
	//监听确认删除按钮事件
	$('#can').on('click','.delete-note',noteDelete);
	//监听垃圾按钮
	$('#trash_button').click(showTrashBin);
	//监听垃圾回收回复按钮
	$('#trash-bin').on('click','.btn_replay',showReplayDialog);
	//监听垃圾列表中的垃圾回复按钮
	$('#can').on('click','.btn-replay',replayNote);
	//监听垃圾列表最终删除的按钮
	$('#trash-bin').on('click','.btn_delete',showDeleteDialog);
	//监听彻底删除的继续按钮
	$('#can').on('click','.rollback-note',rollbackNote);
	//心跳检测 用于防止session过期
	startHartbat();
});


//page翻页显示notebook
function loadpagedNotebooks(){
	var page = $(document).data('page');
	var userId = getCookie('userId');
	var url = 'notebook/page.do';
	var data = {userId:userId,page:page};
	//从服务器拉去数据
	$.getJSON(url,data,function(result){
		if(result.status==0){
			var notebooks = result.data;
			showPagedNotebooks(notebooks,page);
			//将页码加1
			$(document).data('page',page+1);
		}else{
			alert(result.message);
		}
	});
	
}

//显示page查询结果
function showPagedNotebooks(notebooks,page){
	var ul = $('#notebook-list ul');
	if(page==0){
		ul.empty();
	}else{
		//删除more
		ul.find('.more').remove();
	}
	
	for(var i=0;i<notebooks.length;i++){
		var notebook = notebooks[i];
		var li = notebookTemplate.replace('[name]',notebook.name);
		li = $(li);
		li.data('notebookId',notebook.id);
		ul.append(li);
	}
	//append可以加jquery对象也可以添加字符串，并传换成jquery对象
	//当没有数据时，就不添加more
	if(notebooks.length!=0){
		ul.append(moreTemplate);
	}
}

var moreTemplate = '<li class="online more">' +
	               '<a>'+
                   '<i class="fa fa-plus" title="online" rel="tooltip-bottom">'+
                   '</i> MORE...</a></li>';
//心跳检测
function startHartbat(){
	var url = 'user/hart.do';
	//每隔5秒向服务器端发送一次请求，用保持与服务器的连接，避免session过期
	setInterval(function(){
		$.getJSON(url,function(result){
			//console.log(result.message);
		});
	},5000);
}

//执行彻底删除
function rollbackNote(){
	var url = 'note/rollback.do';
	var li = $(document).data('replayItem');
	var noteId = li.data('noteId');
	var data = {noteId:noteId,userId:getCookie('userId')};
	$.post(url,data,function(result){
		if(result.status==SUCCESS){
			console.log(result.data);
			closeDialog();
			//删除当前li
			li.slideUp(200,function(){
				$(this).remove();
			});
			
		}else{
			alert(result.message);
		}
		
	});
}
//显示彻底删除对话框
function showDeleteDialog(){
	//获取li
	var li = $(this).parent().parent();
	var id = li.data('noteId');
	//将noteId绑定到li上
	li.data('replayNoteId',id);
	//将li绑定到document上
	$(document).data('replayItem',li);
	if(id){
		$('#can').load('alert/alert_delete_rollback.html');
		$('.opacity_bg').show();
		return;
	}
	alert("必须选择笔记");
}
//垃圾回复
function replayNote(){
	var li = $(document).data('replayItem');
	var url = 'note/replay.do';
	var noteId = li.data('replayNoteId');
	var notebookId = $('#replaySelect').val();
	var data = {noteId:noteId,notebookId:notebookId};
	$.post(url,data,function(result){
		if(result.status==SUCCESS){
			console.log(result.data);
			li.slideUp(200,function(){
				$(this).remove();
			});
			closeDialog();
		}else{
			alert(result.message);
		}
	});
}

//显示立即回复会话框
function showReplayDialog(){
	//获取li
	var li = $(this).parent().parent();
	var id = li.data('noteId');
	//将noteId绑定到li上
	li.data('replayNoteId',id);
	//将li绑定到document上
	$(document).data('replayItem',li);
	if(id){
		$('#can').load('alert/alert_replay.html',loadReplayOptions);
		$('.opacity_bg').show();
		return;
	}
	alert("必须选择笔记");
}
//显示垃圾回复会话框列表
function loadReplayOptions(){
	var url = 'notebook/list.do';
	var data = {userId:getCookie('userId')};
	$.getJSON(url,data,function(result){
		if(result.status==0){
			var notebooks = result.data;
			//将笔记本名称添加到下拉选中
			//先清楚下拉框原有的内容
			$('#replaySelect').empty();
			var id = $(document).data('notebookId');
			for(var i=0;i<notebooks.length;i++){
				var notebook = notebooks[i];
				var opt = $('<option></option>').val(notebook.id).html(notebook.name);
				//选定默认笔记本Id
				if(notebook.id==id){
					opt.attr('selected','selected');
				}
				$('#replaySelect').append(opt);
			}
		}else{
			alert(result.message);
		}
	});
}

/** 监听回收按钮被点击*/
function showTrashBin(){
	//隐藏笔记列表
	$('#note-list').hide();
	$('#trash-bin').show();
	//显示待删除的数据
	var url = 'note/listtrash.do';
	var data = {userId:getCookie('userId')};
	$.getJSON(url,data,function(result){
		if(result.status==SUCCESS){
			//成功获取数据
			var notes = result.data;
			console.log(notes);
			showTrashNotes(notes);
			
		}
	});
}

//显示回收笔记列表
function showTrashNotes(notes){
	//先清空回收笔记列表
	var ul = $('#trash-bin .contacts-list');
	//先把列表置空
	ul.empty();
	for(var i=0;i<notes.length;i++){
		var li = trashNoteTemplate.replace('[name]',notes[i].title);
		li = $(li);
		li.data('noteId',notes[i].noteId);
		ul.append(li);
	}
}
//删除笔记
function noteDelete(){
	var url = 'note/delete.do';
	var noteId = $(document).data('note').noteId;
	var data = {noteId:noteId};
	$.post(url,data,function(result){
		if(result.status==SUCCESS){
			var li = $('#note-list .checked').parent();
			var lis = li.siblings();
			console.log(lis);
			if(lis.size()>0){
				lis.eq(0).click();
			}else{
				$('#input_note_title').val("");
				um.setContent("");
			}
			li.slideUp(200,function(){
				$(this).remove();
			});
			closeDialog();
		}
		
	});
}
//弹出删除对话框
function showDeleteNote(){
	var userId = getCookie('userId');
	if(userId){
		$('#can').load('alert/alert_delete_note.html');
		$('.opacity_bg').show();
		return;
	}
	alert("必须选定笔记");
}

//笔记移动
function noteMove(){
	var url = 'note/move.do';
	var noteId = $(document).data('note').noteId;
	var notebookId = $('#moveSelect').val();
	//笔记本Id没有变化时，就不进行操作了
	if(notebookId==$(document).data('notebookId')){
		return;
	}
	var data = {noteId:noteId,notebookId:notebookId};
	$.post(url,data,function(result){
		if(result.status==0){
			//移动成功在当前笔记列表中删除移动的笔记
			//将笔记本第一个笔记设置为当前笔记，否则清空编辑区域
			var li = $('#note-list .checked').parent();
			var lis = li.siblings();
			if(lis.size()>0){
				lis.eq(0).click();
			}else{
				//标题显示
				$('#input_note_title').val("");
				//显示内容区
				um.setContent("");
			}
			//删除移动的笔记
			li.remove();
			//关闭对话框
			closeDialog();
		}else{
			alert(result.message);
		}
	});
}
//显示笔记移动对话框
function showNoteMove(){
	var userId = getCookie('userId');
	if(userId){
		$('#can').load('alert/alert_move.html',function(){
			//在对话框中显示笔记本列表 
			var url = 'notebook/list.do';
			var data = {userId:userId};
			$.getJSON(url,data,function(result){
				if(result.status==0){
					var notebooks = result.data;
					//将笔记本名称添加到下拉选中
					//先清楚下拉框原有的内容
					$('#moveSelect').empty();
					var id = $(document).data('notebookId');
					for(var i=0;i<notebooks.length;i++){
						var notebook = notebooks[i];
						var opt = $('<option></option>').val(notebook.id).html(notebook.name);
						//选定默认笔记本Id
						if(notebook.id==id){
							opt.attr('selected','selected');
						}
						$('#moveSelect').append(opt);
					}
				}else{
					alert(result.message);
				}
			});
		});
		return;
	}
	alert("必须选择笔记本!");
}

//显示笔记本菜单
function showNoteMenu(){
	//找到菜单对象，显示笔记本菜单
	var btn = $(this);
	//隐藏已经显示出的笔记本菜单
	//显示目标菜单  btn.parent('.checked')如果parent包含属性checked那么就进行toggle操作，如果不包含返还null
	//查找parent中包含checked类选择器的元素
	btn.parent('.checked').next().toggle();
	return false;//在事件方法中返还false，事件不会传播到document上来，就是终止事件传播
}
function hideNoteMenu(){
	$('.note_menu').hide();
}
//显示添加笔记本会话框
function showAddNotebookDialog(){
	var userId = getCookie('userId');
	console.log(userId);
	if(userId){
		$('#can').load('alert/alert_notebook.html',function(){
			$('.opacity_bg').show();
			$('#input_notebook').focus();
			}); 
		//跳出函数
		return;
	}
	alert('必须登陆');
}
//添加笔记本创建按钮动作
function addNotebook(){
	console.log("addNoteBook");
	var url = "notebook/add.do";
	var name = $('#input_notebook').val();
	var data = {name:name,userId:getCookie('userId')};
	$.post(url,data,function(result){
		if(result.status==0){
			console.log(result);
			var notebook = result.data;
			var ul = $('#notebook-list ul');
			var li = notebookTemplate.replace('[name]',notebook.name);
			li = $(li);
			//设置选定效果
			ul.find('a').removeClass('checked');
			li.find('a').addClass('checked');
			//插入到第一个位置
			ul.prepend(li);
			//关闭添加对话框
			closeDialog();
		}else{
			alert(result.message);
		}
	});
	
}
//保存note 实现更改的方法
function saveNote(){
	//获取之前保存的笔记对象
	var note = $(document).data('note');
	console.log(note);
	//获取页面的参数
	var title = $('#input_note_title').val();
	var body = um.getContent();
	//判断是否发生改变
//	if(title!=null&&title!=note.title){
//		title=note.title;
//	}
//	if(body!=null&&body!=note.body){
//		body=note.body;
//	}
	var url = "note/update.do";
	var data = {noteId:note.noteId,title:title,body:body};
	console.log(data);
	$.post(url,data,function(result){
		if(result.status==0){
			console.log(result);
			//修改笔记列表的名字
			
		}
	});
	
}

//关闭笔记对话框
function closeDialog(){
	$('.opacity_bg').hide();
	$('#can').empty();
}
//创建笔记方法
function addNote(){
	console.log("addNote");
	var url = "note/add.do";
	var title = $('#can #input_note').val();
	var notebookId = $(document).data("notebookId");
	var data = {title:title,notebookId:notebookId,userId:getCookie('userId')};
	$.post(url,data,function(result){
		if(result.status==0){
			var note = result.data;
			console.log(note);
			showNote(note);
			//找到显示笔记列表的ul对象
			var ul = $('#note-list ul');
			var li = noteTemplate.replace('[title]',note.title);
			li = $(li);
			//将noteId绑定到li
			li.data('noteId',note.id);
			//设置选定效果
			ul.find('a').removeClass('checked');
			li.find('a').addClass('checked');
			//插入到第一个位置
			ul.prepend(li);
			//关闭添加对话框
			closeDialog();
		}else{
			alert(result.message);
		}
	});
}

//添加笔记本函数
function showAddNoteDialog(){
	var id = $(document).data('notebookId');
	if(id){
		$('#can').load('alert/alert_note.html',function(){
			$('.opacity_bg').show();
			$('#input_note').focus();
			}); 
		//跳出函数
		return;
	}
	alert('必须选择笔记本!');
}
//笔记单击函数
function loadNote(){
	//当前点击的对象
	var li = $(this);
	//笔记上的样式
	li.parent().find('a').removeClass();
	li.find('a').addClass('checked');
	
	var url = "note/body.do";
	var data ={noteId:li.data("noteId")};
	console.log(data);
	$.getJSON(url,data,function(result){
		if(result.status==0){
			console.log(result);
			var note = result.data;
			$(document).data('note',note);
			//显示出信息
			showNote(note);
		}
	});
}

//将信息显示到显示框上
function showNote(note){
	//标题显示
	$('#input_note_title').val(note.title);
	//显示内容区
	um.setContent(note.body);
}
function loadNotes(){
	var li =$(this);//当前被点击的对象
	//在li上点击的，增加选定需要
	li.parent().find('a').removeClass('checked');
	li.find('a').addClass('checked');
	var url = 'note/findnote.do'
	var data = {notebookId:li.data('notebookId')};
	$.getJSON(url,data,function(result){
		if(result.status==0){
			var notes = result.data;
			showNotes(notes);
		}else{
			alert(result.message);
		}
	});
	
	//将notebokId绑定到document,用于添加笔记功能
	$(document).data('notebookId',li.data('notebookId'));
}

/**
 * 将笔记本列表信息显示到屏幕上
 */
function showNotes(notes){
	//在点击笔记本，为了显示笔记列表先关闭回收站，显示笔记列表
	$('#trash-bin').hide();
	$('#note-list').show();
	
	console.log(notes);
	var ul = $('#note-list ul');
	ul.empty();
	for(var i=0;i<notes.length;i++){
		var note = notes[i];
		var li = noteTemplate.replace('[title]',note.title);
		li = $(li);
		//绑定noteId数据
		li.data("noteId",note.id);
		$(document).data("noteId",note.id);
		ul.append(li);
	}
}


function loadNotebooks(){
	//利用ajax从服务器获取数据
	var url = "notebook/list.do";
	var data = {userId:getCookie('userId')};
	$.getJSON(url,data,function(result){
		console.log(result);		
		if(result.status==0){
			var notebooks = result.data;
			//在showNotebooks方法中将笔记本数据显示到notebook-list区域
			showNotebooks(notebooks);
		}else{
			alert(result.message);
		}
	});
}
/*
 * 在notebook-list区域中显示笔记本列表
 */
function showNotebooks(notebooks){
	//找到显示笔记本区域
	var ul = $('#notebook-list ul');
	//清掉默认 默认笔记本
	ul.empty();
	//遍历notebooks，将每个对象创建一个li，添加到ul元素中
	for(var i=0;i<notebooks.length;i++){
		var notebook = notebooks[i];
		//$('<li></li>')创建一个li元素的jquery对象
		var li = notebookTemplate.replace('[name]',notebook.name);
		li = $(li);
		//将notebook.id 绑定到li上
		li.data('notebookId',notebook.id);
		ul.append(li); 
	}
}

//notebookTemplate表示是notebook的一个模板
var notebookTemplate = '<li class="online notebook">' +
					   '<a>'+
					   '<i class="fa fa-book" title="online" rel="tooltip-bottom">'+
					   '</i> [name]</a></li>';

//noteTemplate表示notelist显示的模板
var noteTemplate = '<li class="online note note-li">'+
	'<a>'+
    '<i class="fa fa-file-text-o" title="online" rel="tooltip-bottom"></i>[title]<button type="button" class="btn btn-default btn-xs btn_position btn_slide_down btn-note-menu"><i class="fa fa-chevron-down"></i></button>'+
    '</a>'+
    '<div class="note_menu" tabindex="-1">'+
    '<dl>'+
	'<dt><button type="button" class="btn btn-default btn-xs btn_move" title="移动至..."><i class="fa fa-random"></i></button></dt>'+
	'<dt><button type="button" class="btn btn-default btn-xs btn_share" title="分享"><i class="fa fa-sitemap"></i></button></dt>'+
	'<dt><button type="button" class="btn btn-default btn-xs btn_delete" title="删除"><i class="fa fa-times"></i></button></dt>'+
    '</dl>'+
    '</div>'+
    '</li>';

var trashNoteTemplate ='<li class="disable"><a ><i class="fa fa-file-text-o" title="online" rel="tooltip-bottom"></i>'+
	                   '[name]<button type="button" class="btn btn-default btn-xs btn_position btn_delete"><i class="fa fa-times">'+
	                   '</i></button><button type="button" class="btn btn-default btn-xs btn_position_2 btn_replay"><i class="fa fa-reply">'+
	                  '</i></button></a></li>';
									