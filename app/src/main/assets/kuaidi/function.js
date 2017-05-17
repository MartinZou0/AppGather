//"use strict";
function query(){
	//alert("test ok");
	var nu=$("#nu").val();
	var com=$("#com").val();
	//alert(com);
	//alert(nu);
	$('#queryRs').html("");
	
	$('#queryRs').show();
	$('#queryRs').append("<div class='container'>加载中，请稍侯</div>");
	//queryRs();
	$.ajax(
		{
			url :"http://www.kdcx.cn/index.php?r=site/query&nu="+nu+"&exname="+com,
			crossDomain : true, 
			dataType : 'json',  
			success: function(data){
				//alert("Data: " + data );
				//alert(data.success);
				if(data.success){
					//alert("查询成功了");
					queryRsSuccess(data);
				}else{
					//alert("查询失败了");
					queryRsWrong();
				}
			}
		}
	);
}
function queryRsWrong(){
	$('#queryRs').html("");
	$('#queryRs').append("<div class='container'>查询不到相关的快递信息，请联系快递公司客服</div>");
}
function queryRsSuccess(data){
	$('#queryRs').html("");
	var rs=data.data;
	for(var i=0;i<rs.length;i++){
		//alert(rs.time+rs.context);
		//alert(rs[i].time+rs[i].context);
		$('#queryRs').append("<div class='container'><span style='color: #FF0000'>"+rs[i].time+":	</span>"+rs[i].context+"</div>");
	}
	//$('#queryRs').show();
	//alert("数据长度"+rs.length);
}