package com.thinkgem.jeesite.modules.api.to;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;

/**
 * Description
 *
 * @author pc
 * @version 2016-05-19
 */
public class PersonalTailorResp  extends DataEntity<PersonalTailorResp> {
    private static final long serialVersionUID = 1L;
    @JsonProperty("projectId")
    private String id;
    @JsonProperty("projectName")
    private String name;		// 项目名称
    private String type;		// 项目类型
    private String amount;		// 项目金额
    @JsonProperty("projectDuration")
    private String duration;		// 项目期限
    @JsonProperty("details")
    private String descPic;		// 项目描述图片
    private String state;		// 状态
    private String rate;		// 最低年化利率
    private Date deadline;		// 投标截止日期
    private Date publishTime;		// 发布时间
    private String startingAmount;		// 起投金额

    public String getId() {
        return id;
    }
    public String getTypeName(){
        return DictUtils.getDictLabel(this.type, "personal_type", "");
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDescPic() {
        if(StringUtils.isNoneBlank(descPic)){
            return descPic.replace("|","");
        }
        return descPic;
    }

    public void setDescPic(String descPic) {
        this.descPic = descPic;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
 
    public String getRate() {
		return rate;
	}
	public void setRate(String rate) {
		this.rate = rate;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public String getStartingAmount() {
        return startingAmount;
    }

    public void setStartingAmount(String startingAmount) {
        this.startingAmount = startingAmount;
    }
}
