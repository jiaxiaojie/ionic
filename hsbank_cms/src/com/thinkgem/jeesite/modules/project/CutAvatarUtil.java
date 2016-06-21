package com.thinkgem.jeesite.modules.project;

import com.hsbank.util.tool.FileUtil;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

public class CutAvatarUtil {
	
	/**
	 * 把【指定图片】【指定区域】裁剪成新的图片
	 * @param sourcePathName
	 * @param sourceWidth
	 * @param sourceHeight
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 * @param destPathName
	 * @throws IOException 
	 */
	public static void cutForAvatar(String sourcePathName, int sourceWidth, int sourceHeight, int x, int y, int w, int h, String destPathName) throws IOException {
		String ext = FileUtil.getFileExt(sourcePathName);
		String sourceFileName = FileUtil.getFileName(sourcePathName);
		String tempSourcePathName = FileUtil.getDirPath(sourcePathName) + File.separator + sourceFileName.substring(0, sourceFileName.lastIndexOf(".")) + "_scale." + ext;
		scale(sourcePathName, tempSourcePathName, sourceWidth, sourceHeight);
		cut(tempSourcePathName, x, y, w, h, destPathName);
		FileUtil.deleteFile(sourcePathName);
		FileUtil.deleteFile(tempSourcePathName);
	}
	
	/**
	 * 图片伸缩，不破坏图片
	 * @param sourcePathName 			原图片路径
	 * @param destPathName 				目标图片路径
	 * @param destWidth 				目标宽度
	 * @param destHeight 				目标高度
	 * @throws IOException 
	 */
	public static void scale(String sourcePathName, String destPathName, int destWidth, int destHeight) throws IOException {
		ImageInputStream iis = null;
		try {
			iis = ImageIO.createImageInputStream(new File(sourcePathName));
			Iterator<ImageReader> iterator = ImageIO.getImageReaders(iis);
			ImageReader reader = (ImageReader) iterator.next();
			reader.setInput(iis, true);
			BufferedImage source = reader.read(0);
			BufferedImage tag = new BufferedImage(destWidth, destHeight, source.getType());
			tag.getGraphics().drawImage(source, 0, 0, destWidth, destHeight, null);
			File file = new File(destPathName);
			ImageIO.write(tag, reader.getFormatName(), file);
		} finally {
			if (iis != null) {
				iis.close();
			}
		}
	}
	
	/**
	 * 图片剪裁，基于起始坐标(x,y)和范围[w,h]
	 * @param sourcePathName
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 * @param destPathName
	 * @throws IOException 
	 */
	public static void cut(String sourcePathName, int x, int y, int w, int h, String destPathName) throws IOException {
		FileInputStream is = null;
		ImageInputStream iis = null;
		try {
			String ext = FileUtil.getFileExt(sourcePathName);
			Iterator<ImageReader> itr = ImageIO.getImageReadersByFormatName(ext);
			is = new FileInputStream(sourcePathName);
			ImageReader reader = itr.next();
			iis = ImageIO.createImageInputStream(is);
			reader.setInput(iis, true);
			ImageReadParam irp = reader.getDefaultReadParam();
			Rectangle rect = new Rectangle(x, y, w, h);
			irp.setSourceRegion(rect);
			BufferedImage bi = reader.read(0, irp);
			FileUtil.createDirByFilePathName(destPathName);
			ImageIO.write(bi, ext, new File(destPathName));
		} finally {
			if (is != null) {
				is.close();
			}
			if (iis != null) {
				iis.close();
			}
		}
	}
}