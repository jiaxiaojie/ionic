package com.thinkgem.jeesite.common.utils;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Hashtable;

/**
 * 条形码和二维码编码解码
 *
 * @author ThinkGem
 * @version 2014-02-28
 */
public class ZxingHandler {

    private static final String CHARSET = "UTF-8";

    private static final String FILE_TYPE = "png";
    /**
     * 边缘
     */
    private static final int MARGIN = 1;

    private static final Logger logger = LoggerFactory.getLogger(ZxingHandler.class);

    /**
     * 条形码编码
     *
     * @param contents
     * @param width
     * @param height
     * @param imgPath
     */
    public static void encode(String contents, int width, int height, String imgPath) {
        int codeWidth = 3 + // start guard
                (7 * 6) + // left bars
                5 + // middle guard
                (7 * 6) + // right bars
                3; // end guard
        codeWidth = Math.max(codeWidth, width);
        try {
            BitMatrix bitMatrix = new MultiFormatWriter().encode(contents, BarcodeFormat.EAN_13, codeWidth, height, null);

            MatrixToImageWriter.writeToFile(bitMatrix, FILE_TYPE, new File(imgPath));

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * 条形码解码
     *
     * @param imgPath
     * @return String
     */
    public static String decode(String imgPath) {
        BufferedImage image = null;
        Result result = null;
        try {
            image = ImageIO.read(new File(imgPath));
            if (image == null) {
                logger.error("xxx the decode image may be not exit.");
            }
            LuminanceSource source = new BufferedImageLuminanceSource(image);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

            result = new MultiFormatReader().decode(bitmap, null);
            return result.getText();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 二维码编码
     * PNG格式文件 有问题
     * @param contents
     * @param width
     * @param height
     * @param object
     */
    public static void encode2(String contents,int width, int height, String fileType, Object object) {
        Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
        hints.put(EncodeHintType.MARGIN, 1);
        // 指定纠错等级
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
        // 指定编码格式
        hints.put(EncodeHintType.CHARACTER_SET, CHARSET);
        try {
            BitMatrix bitMatrix = new MultiFormatWriter().encode(contents, BarcodeFormat.QR_CODE, width, height, hints);
            if(object != null){
                if(object instanceof File){
                    MatrixToImageWriter.writeToFile(bitMatrix, fileType, (File)object);
                }else if(object instanceof OutputStream){
                    MatrixToImageWriter.writeToStream(bitMatrix, fileType, (OutputStream)object);
                }else{
                    MatrixToImageWriter.writeToFile(bitMatrix,fileType, new File(String.valueOf(object)));
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * 生成二维码图片
     *
     * @param content  内容
     * @param width    宽度
     * @param height   高度
     * @param fileType 图片格式 jpg
     * @param object   图片文件对象（文件、输出流、文件路径）
     * @throws Exception
     */
    public static void createQRCode(String content, int width, int height, String fileType, Object object) {
        try {
            Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
            hints.put(EncodeHintType.MARGIN, MARGIN);
            // 指定纠错等级
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
            // 指定编码格式
            hints.put(EncodeHintType.CHARACTER_SET, CHARSET);
            //生成二维码图片
            BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);
            int imgWidth = bitMatrix.getWidth();
            int imgHeight = bitMatrix.getHeight();
            BufferedImage image = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_RGB);
            for (int x = 0; x < imgWidth; x++) {
                for (int y = 0; y < imgHeight; y++) {
                    image.setRGB(x, y, bitMatrix.get(x, y) ? Color.BLACK.getRGB() : Color.WHITE.getRGB());
                }
            }
            //写入文件或输出流
            if (object != null) {
                if (object instanceof File) {
                    ImageIO.write(image, fileType, (File) object);
                } else if (object instanceof OutputStream) {
                    ImageIO.write(image, fileType, (OutputStream) object);
                } else {
                    ImageIO.write(image, fileType, new File(String.valueOf(object)));
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * 生成二维码（去掉白边）
     *
     * @param content      二维码
     * @param width        宽度
     * @param height       高度
     * @param format       图片格式 jpg
     * @param encode       编码 utf-8
     * @param filePathName 路径名称
     * @throws WriterException
     * @throws IOException
     * @throws Exception
     */
    public static void createQRCodeByNoMargin(String content, int width, int height, String format, String encode, String filePathName) throws WriterException, IOException {
        Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
        hints.put(EncodeHintType.MARGIN, MARGIN);
        // 指定纠错等级
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
        // 指定编码格式
        hints.put(EncodeHintType.CHARACTER_SET, encode);

        BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);

        //去掉白边
        int[] rec = bitMatrix.getEnclosingRectangle();
        int resWidth = rec[2] + 0;
        int resHeight = rec[3] + 0;
        BitMatrix resMatrix = new BitMatrix(resWidth, resHeight);
        resMatrix.clear();
        for (int i = 0; i < resWidth; i++) {
            for (int j = 0; j < resHeight; j++) {
                if (bitMatrix.get(i + rec[0], j + rec[1])) {
                    resMatrix.set(i, j);
                }
            }
        }

        int newWidth = resMatrix.getWidth();
        int newHeight = resMatrix.getHeight();
        BufferedImage image = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        for (int x = 0; x < newWidth; x++) {
            for (int y = 0; y < newHeight; y++) {
                image.setRGB(x, y, resMatrix.get(x, y) ? Color.BLACK.getRGB() : Color.WHITE.getRGB());
            }
        }
        ImageIO.write(image, format, new File(filePathName));
    }

    /**
     * 给二维码图片添加Logo
     *
     * @param qrPic
     * @param logoPic
     */
    public static void addLogo_QRCode(File qrPic, File logoPic, String fileType, Object object) {
        try {
            if (!qrPic.isFile() || !logoPic.isFile()) {
                logger.error("xxx qrPic or logoPic unfind !");
            }

            // 读取二维码图片，并构建绘图对象
            BufferedImage image = ImageIO.read(qrPic);
            Graphics2D g = image.createGraphics();
            //读取Logo图片
            BufferedImage logo = ImageIO.read(logoPic);

            int widthLogo = logo.getWidth();
            int heightLogo = logo.getHeight();

            // 计算图片放置位置
            int x = (image.getWidth() - widthLogo) / 2;
            int y = (image.getHeight() - logo.getHeight()) / 2;

            //开始绘制图片
            g.drawImage(logo, x, y, widthLogo, heightLogo, null);
//            g.drawRoundRect(x, y, widthLogo, heightLogo, 15, 15);
            g.setStroke(new BasicStroke(3f));
//            g.setColor(Color.WHITE);
//            g.drawRect(x, y, widthLogo, heightLogo);
            g.dispose();

            if (object != null) {
                if (object instanceof File) {
                    ImageIO.write(image, fileType, (File) object);
                } else if (object instanceof OutputStream) {
                    ImageIO.write(image, fileType, (OutputStream) object);
                } else {
                    ImageIO.write(image, fileType, qrPic);
                }
            } else {
                ImageIO.write(image, fileType, qrPic);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * 二维码解码
     *
     * @param imgPath
     * @return String
     */
    public static String decode2(String imgPath) {
        BufferedImage image = null;
        Result result = null;
        try {
            image = ImageIO.read(new File(imgPath));
            if (image == null) {
                logger.error("the decode image may be not exit.");
            }
            LuminanceSource source = new BufferedImageLuminanceSource(image);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

            Hashtable<DecodeHintType, Object> hints = new Hashtable<DecodeHintType, Object>();
            hints.put(DecodeHintType.CHARACTER_SET, CHARSET);

            result = new MultiFormatReader().decode(bitmap, hints);
            return result.getText();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {

        // 条形码
        String imgPath = "target\\zxing_EAN13.png";
        String contents = "6923450657713";
        int width = 105, height = 50;

        ZxingHandler.encode(contents, width, height, imgPath);
        System.out.println("finished zxing EAN-13 encode.");

        String decodeContent = ZxingHandler.decode(imgPath);
        System.out.println("解码内容如下：" + decodeContent);
        System.out.println("finished zxing EAN-13 decode.");

        // 二维码
        String imgPath2 = "target\\zxing.png";
        String contents2 = "Hello Gem, welcome to Zxing!"
                + "\nBlog [ http://thinkgem.iteye.com ]"
                + "\nEMail [ thinkgem@163.com ]";
        int width2 = 300, height2 = 300;

        ZxingHandler.createQRCode(contents2, width2, height2, "png", imgPath2);
        System.out.println("finished zxing encode.");

        String decodeContent2 = ZxingHandler.decode2(imgPath2);
        System.out.println("解码内容如下：" + decodeContent2);
        System.out.println("finished zxing decode.");

    }


}