package com.thinkgem.jeesite.modules.api.po;

/**
 * Description
 *
 * @author pc
 * @version 2016-05-19
 */
public class PersonalReservate {
      private String client;
      private String name;
      private String mobile;
      private String email;
      private String content;
      private String VerifyCode;
      private Integer projectId;

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getVerifyCode() {
        return VerifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        VerifyCode = verifyCode;
    }

    @Override
    public String toString() {
        return "PersonalReservate{" +
                "client='" + client + '\'' +
                ", name='" + name + '\'' +
                ", mobile='" + mobile + '\'' +
                ", email='" + email + '\'' +
                ", content='" + content + '\'' +
                ", VerifyCode='" + VerifyCode + '\'' +
                '}';
    }
}
