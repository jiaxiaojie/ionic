<!DOCTYPE html>
<html>
<head>
</head>
<body>
  <input type="text" id="type"></input>
  <p id="p"></p>
  <script>
 var html={}
 //m:模型
    html.model ={       
        content:{
            "0":"你好",
            "1":"ok",
            "2":"大家好",
            "3":"你好",
            "4":"ok",
            "5":"大家好"
        },
        data:function(id){
            if(!(id in this.content)){
                var  id = 0;
            }          
            return this.content[id]
        }
    }
 //c：控制器
    html.controller = {             
        init:function(){
            var id = html.view.id();  //获取id
            var value = html.model.data(id);     //通过id 过滤数据        
            html.view.init(value);    //把数据加载到页面中
        }
    }
 //v：视图
    html.view={
        init:function(value){
            document.getElementById("p").innerHTML= value;
        },
        id:function(){
            return document.getElementById("type").value;
        }
 }
 document.getElementById("type").onchange = html.controller.init;
</script>
</body>
</html>