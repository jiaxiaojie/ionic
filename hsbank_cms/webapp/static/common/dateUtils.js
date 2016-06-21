function DateUtils() {
}
DateUtils.prototype.date = new Date();
DateUtils.prototype.pattern = "yyyy-MM-dd";
DateUtils.prototype.formatDate = function(date, pattern) {
	if(arguments.length == 0) {
		
	} else if(arguments.length == 1 && arguments[0].constructor == Date) {
		this.date = date;
	} else if(arguments.length == 1 && arguments[0].constructor == String) {
		this.pattern = arguments[0];
	} else if(arguments.length == 2 && arguments[0].constructor == Date && arguments[1].constructor == String) {
		this.date = date;
		this.pattern = pattern;
	} else {
		throw new Error("arguments are not support");
	}
	var year = this.date.getFullYear();
	var month = this.date.getMonth() + 1;			//1-12
	var day = this.date.getDate();					//1-31
	var hour = this.date.getHours();				//0-23
	var minute = this.date.getMinutes();			//0-59
	var second = this.date.getSeconds();			//0-59
	switch(this.pattern) {
	case "yyyy-MM-dd":
		return year + "-" + (month < 10 ? ("0" + month) : month) + "-"
			+ (day < 10 ? ("0" + day) : day);
	case "yyyy-MM-dd HH:mm:ss":
		return year + "-" + (month < 10 ? ("0" + month) : month) + "-"
			+ (day < 10 ? ("0" + day) : day) + " " + (hour < 10 ? ("0" + hour) : hour) + ":"
			+ (minute < 10 ? ("0" + minute) : minute) + ":" + (second < 10 ? ("0" + second) : second);
	case "yyyy-MM-dd HH:mm:ss start":
		return year + "-" + (month < 10 ? ("0" + month) : month) + "-"
			+ (day < 10 ? ("0" + day) : day) + " 00:00:00";
	case "yyyy-MM-dd HH:mm:ss last":
		return year + "-" + (month < 10 ? ("0" + month) : month) + "-"
			+ (day < 10 ? ("0" + day) : day) + " 23:59:59";
	case "HH:mm:ss":
		return (hour < 10 ? ("0" + hour) : hour) + ":"
			+ (minute < 10 ? ("0" + minute) : minute) + ":" + (second < 10 ? ("0" + second) : second);
	case "yyyy年MM月dd日":
		return year + "年" + (month < 10 ? ("0" + month) : month) + "月"
		+ (day < 10 ? ("0" + day) : day) + "日";
	default:
		throw new Error("pattern is not support");
	}
}
DateUtils.prototype.dayOfTheStartTime = function(date) {
	if(arguments.length == 0) {
		date = new Date()
	}
	return this.formatDate(date, "yyyy-MM-dd HH:mm:ss start");
}
DateUtils.prototype.dayOfTheLastTime = function(date) {
	if(arguments.length == 0) {
		date = new Date()
	}
	return this.formatDate(date, "yyyy-MM-dd HH:mm:ss last");
}
DateUtils.prototype.addDays = function(date, days, pattern) {
	var d = new Date(date);
	d.setDate(d.getDate() + days);
	if(arguments.length == 3) {
		return this.formatDate(d, pattern);
	}
	return this.formatDate(d);
}
DateUtils.prototype.addMonths = function(date, months, pattern) {
	var d = new Date(date);
	d.setMonth(d.getMonth() + months);
	if(arguments.length == 3) {
		return this.formatDate(d, pattern);
	}
	return this.formatDate(d);
}
DateUtils.prototype.addWeeks = function(date, weeks, pattern) {
	var d = new Date(date);
	d.setDate(d.getDate() + weeks * 7);
	if(arguments.length == 3) {
		return this.formatDate(d, pattern);
	}
	return this.formatDate(d);
}
DateUtils.prototype.addDaysAndMonths = function(date, days, months, pattern) {
	var d = new Date(date);
	days = this.getDays(d, days, months);
	d.setDate(d.getDate() + days);
	d.setMonth(d.getMonth() + months);
	if(arguments.length == 4) {
		return this.formatDate(d, pattern);
	}
	return this.formatDate(d);
}

DateUtils.prototype.getDays = function(date, days, months){
	var c_day = date.getDate();
	var c_month = date.getMonth() + 1;
	var n_month = c_month + months;
	if(months == 0 || (n_month != 2 && c_day != 31)){
		return days;
	}else{
		var year = date.getFullYear();
		var ly = (year % 4 == 0) && (year % 100 != 0 || year % 400 == 0);
		if(n_month == 2){
			days = ly ? days - 2 : days - 3;
		}else{
			days = n_month == 4 || n_month == 6 || n_month == 9 || n_month == 11 ? days - 1 : days;
		}
		return days;
	}
}
