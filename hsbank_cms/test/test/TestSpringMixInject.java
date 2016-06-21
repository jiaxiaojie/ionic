/**
 * 
 */
package test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.thinkgem.jeesite.modules.project.dao.ProjectBaseInfoDao;

/**
 * @author yangtao
 *
 */
@ContextConfiguration(locations = {"/spring-context.xml"})
public class TestSpringMixInject {
	@Autowired
	static ProjectBaseInfoDao projectBaseInfoDao;
	

	
	public static void main(String[] args){
//		System.out.println("list count is "+projectBaseInfoDao.findAllList().size());
	}
}
