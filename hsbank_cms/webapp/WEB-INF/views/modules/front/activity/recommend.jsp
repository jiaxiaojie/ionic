<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="${ctxStatic }/modules/front/images/util/favicon.ico" rel="shortcut icon">
<link href="${ctxStatic }/bootstrap/2.3.2/css_cerulean/bootstrap.css" rel="stylesheet"/>
<link href="${ctxStatic }/modules/front/css/util/util.css?${version }" rel="stylesheet" />
<script src="${ctxStatic }/jquery/jquery-1.8.3.min.js" type="text/javascript"></script>


<script src="${ctxStatic }/modules/front/js/ThreeCanvas.js" type="text/javascript"></script> 
<script src="${ctxStatic }/modules/front/js/Snow-3d.js" type="text/javascript"></script> 

<script>
//百度站长统计代码
var _hmt = _hmt || [];
(function() {
  var hm = document.createElement("script");
  hm.src = "//hm.baidu.com/hm.js?07b2a85308e4705c86371a9310089957";
  var s = document.getElementsByTagName("script")[0]; 
  s.parentNode.insertBefore(hm, s);
})();

</script>


<script>
	var SCREEN_WIDTH = window.innerWidth;
	var SCREEN_HEIGHT = window.innerHeight;
	var container;
	var particle;
	var camera;
	var scene;
	var renderer;
	var mouseX = 0;
	var mouseY = 0;
	var windowHalfX = window.innerWidth / 2;
	var windowHalfY = window.innerHeight / 2;
	
	var particles = []; 
	var particleImage = new Image();//THREE.ImageUtils.loadTexture( "${ctxStatic }/modules/front/images/activity/recommend/snow.png" );
	particleImage.src = '${ctxStatic }/modules/front/images/activity/recommend/snow.png'; 


	function init() {
		container = document.createElement('div');
		document.body.appendChild(container);
		camera = new THREE.PerspectiveCamera( 75, SCREEN_WIDTH / SCREEN_HEIGHT, 1, 10000 );
		camera.position.z = 1000;
		scene = new THREE.Scene();
		scene.add(camera);
			
		renderer = new THREE.CanvasRenderer();
		renderer.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
		var material = new THREE.ParticleBasicMaterial( { map: new THREE.Texture(particleImage) } );
			
		for (var i = 0; i < 200; i++) {
			particle = new Particle3D( material);
			particle.position.x = Math.random() * 2000 - 1000;
			particle.position.y = Math.random() * 2000 - 1000;
			particle.position.z = Math.random() * 2000 - 1000;
			particle.scale.x = particle.scale.y =  1;
			scene.add( particle );
			
			particles.push(particle); 
		}
		container.appendChild( renderer.domElement );

		document.addEventListener( 'mousemove', onDocumentMouseMove, false );
		document.addEventListener( 'touchstart', onDocumentTouchStart, false );
		document.addEventListener( 'touchmove', onDocumentTouchMove, false );
		
		setInterval( loop, 1000 / 30 );
		
	}
	
	function onDocumentMouseMove( event ) {
		mouseX = event.clientX - windowHalfX;
		mouseY = event.clientY - windowHalfY;
	}
	function onDocumentTouchStart( event ) {
		if ( event.touches.length == 1 ) {
			event.preventDefault();
			mouseX = event.touches[ 0 ].pageX - windowHalfX;
			mouseY = event.touches[ 0 ].pageY - windowHalfY;
		}
	}
	function onDocumentTouchMove( event ) {
		if ( event.touches.length == 1 ) {
			event.preventDefault();
			mouseX = event.touches[ 0 ].pageX - windowHalfX;
			mouseY = event.touches[ 0 ].pageY - windowHalfY;
		}
	}
	//
	function loop() {
	for(var i = 0; i<particles.length; i++)
		{
			var particle = particles[i]; 
			particle.updatePhysics(); 

			with(particle.position)
			{
				if(y<-1000) y+=2000; 
				if(x>1000) x-=2000; 
				else if(x<-1000) x+=2000; 
				if(z>1000) z-=2000; 
				else if(z<-1000) z+=2000; 
			}				
		}
	
		camera.position.x += ( mouseX - camera.position.x ) * 0.05;
		camera.position.y += ( - mouseY - camera.position.y ) * 0.05;
		camera.lookAt(scene.position); 
		renderer.render( scene, camera );
		
	}
</script>

<title>新年红包发不停，好人缘就是大本钱！</title>

<style type="text/css">
body{ background:url(${ctxStatic }/modules/front/images/activity/recommend/body_bg.jpg) repeat; overflow-x:hidden;}
.font-size-12{ font-size:12px;}
.display-area{ position:relative; width:1000px; margin:0 auto;}
.main-banner{ height:599px; background: url(${ctxStatic }/modules/front/images/activity/recommend/recommend-banner.png) no-repeat center 0;}
.main-banner-time{ position:relative; z-index:3; padding-top:312px; text-align:center; font-size:19px; color:#ffe5b8;}
/*QR-code*/
.mb-qr-code{ position:relative; z-index:3;}
.mb-qr-code .qr-code{ margin:18px auto 0; width:346px; height:126px;}
.mb-qr-code .arrow-top{ position:absolute; bottom:-12px; left:50%; margin-left:-10px; width:20px; height:20px; background: url(${ctxStatic }/modules/front/images/activity/recommend/arrow-top.png) no-repeat; animation:arrow-top 2s infinite;-o-animation:arrow-top 2s infinite; -moz-animation:arrow-top 2s infinite; -webkit-animation:arrow-top 2s infinite;}
@-webkit-keyframes arrow-top {
0%, 100% {
-webkit-transform:translate3d(0, 0, 0);
transform:translate3d(0, 0, 0)
}
50% {
-webkit-transform:translate3d( 0, -8px, 0);
transform:translate3d( 0, -8px, 0)
}}

@-moz-keyframes arrow-top {
0%, 100% {
-moz-transform:translate3d(0, 0, 0);
transform:translate3d(0, 0, 0)
}
50% {
-webkit-transform:translate3d( 0, -8px, 0);
transform:translate3d( 0, -8px, 0)
}}

@keyframes arrow-top {
0%, 100% {
-webkit-transform:translate3d(0, 0, 0);
transform:translate3d(0, 0, 0)
}
50% {
-webkit-transform:translate3d( 0, -8px, 0);
transform:translate3d( 0, -8px, 0)
}}


.mb-qr-code .tip-img{ position:relative; margin-top:138px; width:119px; height:62px; background: url(${ctxStatic }/modules/front/images/activity/recommend/recommend-tip.png) no-repeat; animation:tip-img 2s infinite;-o-animation:tip-img 2s infinite; -moz-animation:tip-img 2s infinite; -webkit-animation:tip-img 2s infinite;}
@-webkit-keyframes tip-img {
0%, 100% {
-webkit-transform:translate3d(0, 0, 0);
transform:translate3d(0, 0, 0)
}
50% {
-webkit-transform:translate3d( -10px, 0, 0);
transform:translate3d( -10px, 0, 0)
}}
@-moz-keyframes tip-img {
0%, 100% {
-webkit-transform:translate3d(0, 0, 0);
transform:translate3d(0, 0, 0)
}
50% {
-webkit-transform:translate3d( -10px, 0, 0);
transform:translate3d( -10px, 0, 0)
}}
@keyframes tip-img {
0%, 100% {
-webkit-transform:translate3d(0, 0, 0);
transform:translate3d(0, 0, 0)
}
50% {
-webkit-transform:translate3d( -10px, 0, 0);
transform:translate3d( -10px, 0, 0)
}}
/*button*/
.recommend-button{ position:relative; margin:146px auto 0 auto; width:242px; height:70px; line-height:70px; display:block; font-size:22px; color:#5c457a; text-align:center; background:url(${ctxStatic }/modules/front/images/activity/recommend/recommend-button.png) no-repeat; overflow:hidden;}
.recommend-button > span { float:left;display: inline-block;opacity: 0;color: #db403b; padding:0;-webkit-transform: translate3d(0, -10px, 0);transform: translate3d(0, -10px, 0);-webkit-transition: -webkit-transform 0.3s, opacity 0.3s;transition: transform 0.3s, opacity 0.3s;-webkit-transition-timing-function: cubic-bezier(0.75, 0, 0.125, 1);transition-timing-function: cubic-bezier(0.75, 0, 0.125, 1);}
.recommend-button .word-1{ margin-left:34px;}
.recommend-button::before {content: attr(data-text);position: absolute;top: 0;left: 0;width: 100%;height: 100%;-webkit-transition: -webkit-transform 0.3s, opacity 0.3s;transition: transform 0.3s, opacity 0.3s;-webkit-transition-timing-function: cubic-bezier(0.75, 0, 0.125, 1);transition-timing-function: cubic-bezier(0.75, 0, 0.125, 1);}
.recommend-button:hover {}
.recommend-button:hover::before {opacity: 0;-webkit-transform: translate3d(0, 100%, 0);transform: translate3d(0, 100%, 0);}
.recommend-button:hover > span {opacity: 1;-webkit-transform: translate3d(0, 0, 0);transform: translate3d(0, 0, 0);}
.recommend-button:hover > span.word-1 {-webkit-transition-delay: 0.045s;transition-delay: 0.045s;}
.recommend-button:hover > span.word-2 {-webkit-transition-delay: 0.09s;transition-delay: 0.09s;}
.recommend-button:hover > span.word-3 {-webkit-transition-delay: 0.135s;transition-delay: 0.135s;}
.recommend-button:hover > span.word-4 {-webkit-transition-delay: 0.18s;transition-delay: 0.18s;}
.recommend-button:hover > span.word-5 {-webkit-transition-delay: 0.225s;transition-delay: 0.225s;}
.recommend-button:hover > span.word-6 {-webkit-transition-delay: 0.27s;transition-delay: 0.27s;}
.recommend-button:hover > span.word-7 {-webkit-transition-delay: 0.315s;transition-delay: 0.315s;}
.recommend-button:hover > span.word-8 {-webkit-transition-delay: 0.36s;transition-delay: 0.36s;}
.recommend-button:hover > span.word-9 {-webkit-transition-delay: 0.405s;transition-delay: 0.405s;}
.mc-one{ margin:0 auto; width:1000px;}
.mc-one .mc-one-title{ margin:50px auto 0; width:390px; height:45px; background:url(${ctxStatic }/modules/front/images/activity/recommend/recommend-title-1.png) no-repeat;}
.mc-one .mc-one-text{ position:relative; z-index:3; margin-top:13px; font-size:20px; color:#fff; text-align:center;}
.mc-one .mc-one-list{ margin-top:35px; height:160px; overflow:hidden; background:url(${ctxStatic }/modules/front/images/activity/recommend/recommend-bg-01.png) no-repeat;}
.mc-one .mc-one-list p{ margin:145px 32px 0px 3px;width:168px; height:108px; line-height:34px; text-align:center; display:inline-block; font-size:20px; color:#5c457a;}
.mc-two{ margin:100px auto 0 auto; width:1000px;}
.mc-two .mc-two-title{ height:101px; background:url(${ctxStatic }/modules/front/images/activity/recommend/recommend-title-2.png) no-repeat;}
.mc-two-content{ background:url(${ctxStatic }/modules/front/images/activity/recommend/recommend-content-bg.png) repeat-y;}
.mc-two-title-2{ position:relative; z-index:3; padding-top:13px; font-size:20px; color:#d82b1b; text-align:center;}
.mc-two-tip{ position:relative; z-index:3; padding-top:13px; font-size:16px; color:#d82b1b; text-align:center; background:url(${ctxStatic }/modules/front/images/activity/recommend/recommend-bg-03.png) no-repeat center 21px;}
.mc-two-list{ padding:20px 0;}
.mc-two-list li{ position:relative; width:227px; height:239px; float:left; margin:0 20px 30px 69px; background:url(${ctxStatic }/modules/front/images/activity/recommend/recommend-red-1.png) no-repeat center 22px;}
.mc-two-list li .re-card{ position:absolute; top:50px; left:15px; width:194px; height:148px; text-align:center; background:url(${ctxStatic }/modules/front/images/activity/recommend/recommend-red-2.png) no-repeat; transition: all ease-out .3s; -o-transition: all ease-out .3s; -ms-transition: all ease-out .3s; -moz-transition: all ease-out .3s; -webkit-transition: all ease-out .3s;}
.mc-two-list li:hover .re-card{ position:absolute; top:0px; left:15px;}
.mc-two-list li .re-card span{ margin-top:20px; display:inline-block;}
.mc-two-list li .re-card p{ padding-top:24px; font-size:14px; color:#e52a29;}
.mc-two-list li .re-text{ position:absolute; top:73px; left:0; z-index:5; width:227px; height:166px; text-align:center; font-size:16px; color:#fff; background:url(${ctxStatic }/modules/front/images/activity/recommend/recommend-red-3.png) no-repeat;}
.mc-two-list li .re-text p{ padding-top:85px; line-height:28px;}
.mc-two-list li.re-6 .re-text{ position:absolute; top:73px; left:0; z-index:5; width:227px; height:166px; text-align:center; font-size:16px; color:#fff; background:url(${ctxStatic }/modules/front/images/activity/recommend/recommend-red-5.png) no-repeat;}
.mc-two-list li.re-6 .re-text p{ padding-top:85px; line-height:20px;}
.mc-two-list li .re-text span{ color:#ffc600;}
.mc-two-list li .re-num{ position:absolute; top:116px; left:100px; z-index:6; width:27px; height:27px; line-height:27px; text-align:center; color:#fff; font-size:16px; background:url(${ctxStatic }/modules/front/images/activity/recommend/recommend-red-4.png) no-repeat;}
.mc-two-bottom{ position:relative; z-index:3; height:69px; line-height:69px; text-align:center; background:url(${ctxStatic }/modules/front/images/activity/recommend/recommend-bottom-bg.png) no-repeat;}
.mc-two-bottom a{ color:#d82b1b; font-size:16px;}
.mc-two-bottom a:hover{ text-decoration:underline;}
.mc-two-bottom span{ margin:0 20px; width:54px; height:14px; display:inline-block; background:url(${ctxStatic }/modules/front/images/activity/recommend/recommend-star.png) no-repeat;}
.mc-three{ margin:70px auto; width:1000px;}
.mc-three .mc-three-title{ margin:0 auto; width:390px; height:44px; background:url(${ctxStatic }/modules/front/images/activity/recommend/recommend-title-3.png) no-repeat;}
.mc-three .mc-three-text{ position:relative; z-index:3; margin-top:30px; font-size:19px; color:#fff;}
.mc-three .mc-three-text p{ margin-bottom:20px; line-height:28px;}
.activity-code{ position:fixed; top:0px; right:20px; width:130px; height:589px; z-index:5; background:url(${ctxStatic }/modules/front/images/activity/recommend/recommend-code.png) no-repeat;}
.re-bottom-tip{ position:relative; z-index:3; padding:10px 0; text-align:center; color:#d82b1b; font-size:16px;}
canvas{ position:fixed; z-index:0;}
</style>

</head>

<body onload="init()">
<!--二维码-->
<!--<div class="activity-code"></div>-->
<!--头部-->
<div class="activity-header">
	<div class="display-area clearfix">
    	<div class="header-logo pull-left">
        	<a href="${ctxFront }/index">
				<img id="logo" class="logo" src="${ctxStatic }/modules/front/images/util/logo.png">
        	</a>
        </div>
    	<div class="header-btn pull-right">
        	<a href="${ctxFront }/index">进入首页</a>
        </div>
    </div>
</div>
<!--主要的-->
<div class="activity-main">
	<!--banner-->
    <div class="main-banner">
    	<div class="display-area">
        	<div class="main-banner-time">活动时间：2015.12.23-2016.2.29</div>
            <div class="mb-qr-code">
            	<div class="qr-code"><img src="${ctxStatic }/modules/front/images/activity/recommend/QR-code.gif"></div>
            	<div class="arrow-top"></div>
            </div>
        </div>
    </div>
    <!--主要内容-->
    <div class="main-content">
    	<div class="mc-one">
        	<div class="mc-one-title"></div>
        	<div class="mc-one-text">简单3步，搞定好友，红包到手！</div>
        	<div class="mc-one-list"></div>            
        </div>
        
    	<div class="mc-two">
        	<div class="mc-two-title"></div>  
        	<div class="mc-two-content">
            	<div class="mc-two-title-2">邀请投资的好友越多，现金奖励越丰厚！</div>
            	<div class="mc-two-tip">活动期间，邀请好友注册，且每位好友累计投资额（活期产品除外）≥1000元，可获得以下奖励</div>
            	<div class="mc-two-list">
                	<ul class="clearfix">
                    	<li class="re-1">
                        	<div class="re-card">
                            	<span><img src="${ctxStatic }/modules/front/images/activity/recommend/recommend-money-1.png"></span>
                                <p>赶紧去邀请吧~</p>
                            </div>
                        	<div class="re-text">
                            	<p>邀请第1位好友<br>奖励<span>10</span>元现金</p>
                            </div>
                        	<div class="re-num">1</div>
                        </li>
                    	<li class="re-2">
                        	<div class="re-card">
                            	<span><img src="${ctxStatic }/modules/front/images/activity/recommend/recommend-money-2.png"></span>
                                <p>赶紧去邀请吧~</p>
                            </div>
                        	<div class="re-text">
                            	<p>邀请第2位好友<br>奖励<span>20</span>元现金</p>
                            </div>
                        	<div class="re-num">2</div>
                        </li>
                    	<li class="re-3">
                        	<div class="re-card">
                            	<span><img src="${ctxStatic }/modules/front/images/activity/recommend/recommend-money-3.png"></span>
                                <p>赶紧去邀请吧~</p>
                            </div>
                        	<div class="re-text">
                            	<p>邀请第3位好友<br>奖励<span>30</span>元现金</p>
                            </div>
                        	<div class="re-num">3</div>
                        </li>
                    	<li class="re-4">
                        	<div class="re-card">
                            	<span><img src="${ctxStatic }/modules/front/images/activity/recommend/recommend-money-4.png"></span>
                                <p>赶紧去邀请吧~</p>
                            </div>
                        	<div class="re-text">
                            	<p>邀请第4位好友<br>奖励<span>40</span>元现金</p>
                            </div>
                        	<div class="re-num">4</div>
                        </li>
                    	<li class="re-5">
                        	<div class="re-card">
                            	<span><img src="${ctxStatic }/modules/front/images/activity/recommend/recommend-money-5.png"></span>
                                <p>赶紧去邀请吧~</p>
                            </div>
                        	<div class="re-text">
                            	<p>邀请第5位好友<br>奖励<span>50</span>元现金</p>
                            </div>
                        	<div class="re-num">5</div>
                        </li>
                    	<li class="re-6">
                        	<div class="re-card">
                            	<span><img src="${ctxStatic }/modules/front/images/activity/recommend/recommend-money-1.png"></span>
                                <p>赶紧去邀请吧~</p>
                            </div>
                        	<div class="re-text">
                            	<p>邀请第6位及以上好友<br>每多邀请1位<br>奖励<span>10</span>元现金</p>
                            </div>
                        	<div class="re-num">6</div>
                        </li>
                    </ul>
                    <div class="re-bottom-tip">举例：邀请7位好友且符合活动规则，可获得10+20+30+40+50+10+10=170元 现金奖励</div>
                </div>
            </div>
        	<div class="mc-two-bottom">
            	<span class=""></span>
                <a href="${ctxFront }/activity/marketing.html#guize" name="#guize" target="_blank">本活动与推荐有奖叠加 点此查看>></a>
            	<span class=""></span>
            </div>
        </div>
        
    	<div class="mc-three">
        	<div class="mc-three-title"></div>
        	<div class="mc-three-text">
            	<p>1、活动时间：2015.12.23-2016.2.29</p>
                <p>2、参与用户：花生金服平台所有注册用户</p>
                <p>3、奖励条件：每位被邀请的好友在活动期间注册且累计投资额（活期产品除外）≥1000元</p>
                <p>4、奖励发放：现金奖励将在好友投资成功后5个工作日内发放至邀请人账户，可在“我的账户”-“可用余额”中查看</p>
                <p>5、本活动可与推荐有奖活动叠加，活动解释权归花生金服所有</p>
            </div>
        </div>
        
    </div>
</div>

<!-- 版权申明区域 -->
<div id="footer_area" class="footer_area" style=" position:relative; z-index:3;">
	<div class="footer_menu">
       	<span class=""><a href="${ctxFront }/gywm/index">关于我们</a></span>
           <span class="line">|</span>
       	<span class=""><a href="${ctxFront }/gywm/jrwm">加入我们</a></span>
           <span class="line">|</span>
       	<span class=""><a href="${ctxFront }/gywm/lxwm">联系我们</a></span>
           <span class="line">|</span>
       	<span class=""><a href="${ctxFront }/index#cooperationAgency">友情链接</a></span>
           <span class="line">|</span>
       	<span class=""><a href="javascript:void(0);">网站地图</a></span>
           <span class="line">|</span>
       	<span class=""><a href="javascript:void(0);">法律声明</a></span>
           <span class="line">|</span>
       	<span class=""><a href="javascript:void(0);">黑名单</a></span>
    </div>
	<div class="div_height_20"></div>
	<div class="footer_text">
    	<span>客服邮箱：service@fdjf.net</span>&nbsp;&nbsp;&nbsp;&nbsp;
        <span>服务热线：<b class="font_size_18">400-969-6599</b>&nbsp;(工作日9:00~18:00)</span>
    </div>
    <div class="div_height_10"></div>
	<div class="footer_text">©2015上海蓄积金融信息服务有限公司&nbsp;&nbsp;All right reserved.&nbsp;&nbsp;沪ICP备15025483号</div>
   	<div class="div_height_20"></div>
   	<div class="footer_logo">
    	<a href="javascript:void(0);" class="logo_gs"></a>
    	<a href="javascript:void(0);" class="logo_nt"></a>
    	<a href="javascript:void(0);" class="logo_jc"></a>
   	</div>
</div>

<!--Start of DoubleClick Floodlight Tag: Please do not remove Activity name of this tag: CN_Direct_HsBank360_Retargeting-->
<script type="text/javascript">
var axel = Math.random() + "";
var a = axel * 10000000000000;
document.write('<img src="https://ad.doubleclick.net/ddm/activity/src=5141770;type=invmedia;cat=urjh3w2a;ord=' + a + '?"  alt="" style="display:block;height:0;" />');
</script>
<noscript>
<img src="https://ad.doubleclick.net/ddm/activity/src=5141770;type=invmedia;cat=urjh3w2a;ord=1?" alt="" style="display:block;height:0;" />
</noscript>
<!-- End of DoubleClick Floodlight Tag: Please do not remove -->

</body>
</html>