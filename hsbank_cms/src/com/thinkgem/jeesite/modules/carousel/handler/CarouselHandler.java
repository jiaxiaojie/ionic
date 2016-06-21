/**
 * 
 */
package com.thinkgem.jeesite.modules.carousel.handler;

import com.hsbank.util.type.NumberUtil;
import com.thinkgem.jeesite.modules.carousel.dao.CarouselInfoDao;
import com.thinkgem.jeesite.modules.carousel.dao.CarouselShowTermDao;
import com.thinkgem.jeesite.modules.entity.CarouselInfo;
import com.thinkgem.jeesite.modules.entity.CarouselShowTerm;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author hyc
 *轮播图显示信息列表
 */
@Component("carouselHandler")
public class CarouselHandler {
	Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	private CarouselInfoDao carouselInfoDao;
	@Autowired
	private CarouselShowTermDao carouselShowTermDao;

	/**
	 * 根据轮播图编号获取轮播图信息
	 * 
	 * @param id
	 * @return
	 */
	public CarouselInfo getCarouselInfoById(String carouselId) {
		CarouselInfo carouselInfo = carouselInfoDao.get(carouselId);
		List<CarouselShowTerm> carouselShowTermList = carouselShowTermDao.findListByCarouselId(NumberUtil.toLong(carouselId, 0L));
		carouselInfo.setCarouselShowTermList(carouselShowTermList);
		return carouselInfo;
	}


}
