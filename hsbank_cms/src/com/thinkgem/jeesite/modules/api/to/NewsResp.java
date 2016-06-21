package com.thinkgem.jeesite.modules.api.to;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * 媒体报道输出参数
 */
public class NewsResp {
    /**
     * 报道编号
     */
    private String newsId;
    /**
     * 报道标题
     */
    private String title;
    /**
     * 报道摘要
     */
    private String description;

    /**
     * 原文连接
     */
    private String link;

    /**
     * 发布时间
     */
    private Date createDate;

    /**
     * 内容
     */
    private String content;

    /**
     * 来源：网易、新浪、搜狐等
     */
    private String newsSource;

    /**
     * 来源logoUrl(绝对路径)
     */
    private String logoUrl;

    /**
     * 缩略图
     */
    private String thumbnail;

    public String getNewsId() {
        return newsId;
    }

    public void setNewsId(String newsId) {
        this.newsId = newsId;
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

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getNewsSource() {
        return newsSource;
    }

    public void setNewsSource(String newsSource) {
        this.newsSource = newsSource;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("NewsResp{");
        sb.append("newsId='").append(newsId).append('\'');
        sb.append(", title='").append(title).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", link='").append(link).append('\'');
        sb.append(", createDate=").append(createDate);
        sb.append(", content='").append(content).append('\'');
        sb.append(", newsSource='").append(newsSource).append('\'');
        sb.append(", logoUrl='").append(logoUrl).append('\'');
        sb.append(", thumbnail='").append(thumbnail).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
