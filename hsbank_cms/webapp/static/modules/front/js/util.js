/** 内容区域高度 */
var _contentHeight = 0;
/** 内容区域宽度 */
var _contentWidth = 0;

/**
 * 得到内容区域的尺寸
 */
function getContentSize(){
	//得到窗口宽度
	if (window.innerWidth) {
		_contentWidth = window.innerWidth;
	} else if ((document.body) && (document.body.clientWidth)) {
		_contentWidth = document.body.clientWidth;
	}
	//得到窗口高度
	if (window.innerHeight) {
		_contentHeight = window.innerHeight;
	} else if ((document.body) && (document.body.clientHeight)) {
		_contentHeight = document.body.clientHeight;
	}
	//通过深入Document内部对body进行检测，得到窗口大小
	if (document.documentElement  
		&& document.documentElement.clientHeight 
		&& document.documentElement.clientWidth) {
		_contentHeight = document.documentElement.clientHeight;
		_contentWidth = document.documentElement.clientWidth;
	}
}

/**
 * 得到当前按键事件的按键码
 * 兼容ie、firefox、opera
 * 回车键的keyCode=13
 */
function getKeyCode(e) { 
    var curKeyCode = 0, e = e||event;
    curKeyCode = e.keyCode || e.which || e.charCode;
    return curKeyCode;
}

/**
 * 从文件路径中得到文件名称
 * @param filePathName
 */
function getFileName(filePathName) {
    var pos = filePathName.lastIndexOf("/");
    if(pos == -1){
       pos = filePathName.lastIndexOf("\\")
    }
    return filePathName.substring(pos + 1);  
}

/**得到当前年*/
function getCurrentYear(){
    var dd = new Date();
    var year = dd.getFullYear();
    return year;
}

/**得到当前季*/
function getCurrentQuarter(){
    var dd = new Date();
    var year = dd.getFullYear();
    var month = dd.getMonth() + 1;
    var quarter = parseInt((month - 1) / 3) + 1;
    return quarter;
}

/**得到当前月*/
function getCurrentMonth(){
    var dd = new Date();
    var year = dd.getFullYear();
    var month = dd.getMonth() + 1;
    return month;
}

/**得到当前天*/
function getCurrentDay(){
    var dd = new Date();
    return dd.getDate();
}

/**得到当前年-月-日（yyyy-mm-dd)*/
function getCurrentYYYYMMDD(){
    var dd = new Date();
    var y = dd.getFullYear();
    var m = dd.getMonth() + 1;
    var d = dd.getDate();
    return y + "-" + (m > 9 ? m : "0" + m) + "-" + (d > 9 ? d : "0" + d);
}

/**得到本周周日*/
function getCurrentSunday(){ 
    var dd = new Date(); 
    //一周中的第几天  
    var weekDay = dd.getDay(); 
    //一天的毫秒数  
    var millisecond = 1000 * 60 * 60 * 24; 
    //本周周日
    return new Date(dd.getTime() - weekDay * millisecond);
}

/**
 * 得到指定偏移天数的日期（yyyy-mm-dd)
 * @param dayOffset		偏移的天数
 */
function getDate(dayOffset) {
    var dd = new Date();
    dd.setDate(dd.getDate() + dayOffset);
    var y = dd.getFullYear();
    var m = dd.getMonth() + 1;
    var d = dd.getDate();
    return y + "-" + (m > 9 ? m : "0" + m) + "-" + (d > 9 ? d : "0" + d);
}

/**
 * 得到指定字段的值 
 * @param fieldId			指定字段Id
 */
function getFieldValue(fieldId) {
	var fieldValue = $('#' + fieldId).val();
    return $.trim(fieldValue);
}

/**
 * 得到指定单选框选中的值 
 * @param fieldId			指定字段Id
 */
function getRadioBoxValue(fieldId) {
    var fieldValue = $('input[name=' + fieldId + ']:checked').val();
    return $.trim(fieldValue);
}

/**
 * 得到指定复选框的值 
 * @param fieldId			指定字段Id
 */
function getCheckBoxValue(fieldId) {
    var fieldValue = document.getElementById(fieldId).checked;
    return $.trim(fieldValue);
}

/**
 * 得到指定下拉框选中的值 
 * @param fieldId			指定字段Id
 */
function getSelectValue(fieldId) {
    var fieldValue = $('#' + fieldId).find('option:selected').val();
    return $.trim(fieldValue);
}
