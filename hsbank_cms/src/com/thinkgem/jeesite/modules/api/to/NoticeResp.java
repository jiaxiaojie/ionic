package com.thinkgem.jeesite.modules.api.to;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * 公告输出参数
 */
public class NoticeResp {
    /**
     * 公告编号
     */
    private String noticeId;
    /**
     * 公告标题
     */
    private String title;

    /**
     * 公告摘要
     */
    private String description;
    /**
     * 公告内容
     */
    private String content;
    /**
     * 发布时间
     */
    private Date createDate;

    public String getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(String noticeId) {
        this.noticeId = noticeId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("NoticeResp{");
        sb.append("noticeId='").append(noticeId).append('\'');
        sb.append(", title='").append(title).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", createDate=").append(createDate);
        sb.append('}');
        return sb.toString();
    }
}
