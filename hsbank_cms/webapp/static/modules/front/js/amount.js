/*
1. 只能输入1个小数点
2. 只能输入数字
3. 可以输入以下特殊字符：回退；删除；home；end；箭头
4. 限制用户不能拷贝粘贴；
代码中使用char code进行验证，同时如果按的是数字键，则不能同时按Shift键。
 */
function limit_amount_input(fieldId) {
    $('#' + fieldId).bind("contextmenu", function(){
        return false;
    });
    $('#' + fieldId).css('ime-mode', 'disabled');
    $('#' + fieldId).bind("keydown", function(e) {
        var key = window.event ? e.keyCode : e.which;
        if (isFullStop(key)) {
            return $(this).val().indexOf('.') < 0;
        }
        return (isSpecialKey(key)) || ((isNumber(key) && !e.shiftKey));
    });
}

function isNumber(key) {
    return (key >= 48 && key <= 57) || (key >= 96 && key <= 105);
}

function isSpecialKey(key) {
    //8:backspace; 46:delete; 37-40:arrows; 36:home; 35:end; 9:tab; 13:enter
    return key == 8 || key == 46 || (key >= 37 && key <= 40) || key == 35 || key == 36 || key == 9 || key == 13
}

function isFullStop(key) {
    return key == 190 || key == 110;
}
