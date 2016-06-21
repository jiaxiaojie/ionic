/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.common.web;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;

import com.ckfinder.connector.configuration.Configuration;
import com.ckfinder.connector.data.AccessControlLevel;
import com.ckfinder.connector.utils.AccessControlUtil;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.modules.sys.security.SystemAuthorizingRealm.Principal;

/**
 * CKFinder配置
 * @author ThinkGem
 * @version 2014-06-25
 */
public class CKFinderConfig extends Configuration {

	public CKFinderConfig(ServletConfig servletConfig) {
        super(servletConfig);  
    }
	
	@Override
    protected Configuration createConfigurationInstance() {
		Object principalObject = SecurityUtils.getSubject().getPrincipal();
		if (principalObject == null){
			return new CKFinderConfig(this.servletConf);
		}
//		Principal principal = (Principal) UserUtils.getPrincipal();
		boolean isView = true;//UserUtils.getSubject().isPermitted("cms:ckfinder:view");
		boolean isUpload = true;//UserUtils.getSubject().isPermitted("cms:ckfinder:upload");
		boolean isEdit = true;//UserUtils.getSubject().isPermitted("cms:ckfinder:edit");
		boolean isDelete = false;
		AccessControlLevel alc = this.getAccessConrolLevels().get(0);
		alc.setFolderView(isView);
		alc.setFolderCreate(isEdit);
		alc.setFolderRename(isEdit);
		alc.setFolderDelete(isEdit);
		alc.setFileView(isView);
		alc.setFileUpload(isUpload);
		alc.setFileRename(isEdit);
		alc.setFileDelete(isDelete);
//		for (AccessControlLevel a : this.getAccessConrolLevels()){
//			System.out.println(a.getRole()+", "+a.getResourceType()+", "+a.getFolder()
//					+", "+a.isFolderView()+", "+a.isFolderCreate()+", "+a.isFolderRename()+", "+a.isFolderDelete()
//					+", "+a.isFileView()+", "+a.isFileUpload()+", "+a.isFileRename()+", "+a.isFileDelete());
//		}
		AccessControlUtil.getInstance(this).loadACLConfig();
		try {
//			Principal principal = (Principal)SecurityUtils.getSubject().getPrincipal();
//			this.baseURL = ServletContextFactory.getServletContext().getContextPath()+"/userfiles/"+principal+"/";
			if(principalObject instanceof Principal) {
				Principal principal = (Principal)principalObject;
				this.baseURL = Servlets.getRequest().getContextPath() + Global.USERFILES_BASE_URL + principal + "/";
				this.baseDir = Global.getUserfilesBaseDir() + Global.USERFILES_BASE_URL + principal + "/";
			}else if(principalObject instanceof com.thinkgem.jeesite.modules.sys.security.SystemAuthorizingRealmFront.Principal) {
					com.thinkgem.jeesite.modules.sys.security.SystemAuthorizingRealmFront.Principal principalCustomer
						= (com.thinkgem.jeesite.modules.sys.security.SystemAuthorizingRealmFront.Principal)principalObject;
					this.baseURL = Servlets.getRequest().getContextPath() + Global.USERFILES_BASE_URL + principalCustomer + "/";
					this.baseDir = Global.getUserfilesBaseDir() + Global.USERFILES_BASE_URL + principalCustomer + "/";
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return new CKFinderConfig(this.servletConf);
    }

    @Override  
    public boolean checkAuthentication(final HttpServletRequest request) {
        return SecurityUtils.getSubject().getPrincipal()!=null;
    }

}
