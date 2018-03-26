package jump_algorithm;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * 找到第一个人的位置
 * @author HOUHansen
 *@version 1.0
 */

public class Mypostion {
	/*
	 * 先进行对于初始屏幕颜色的记录
	 * 运用三元色来进行颜色的标注
	 */
public static final int RED_TARGET=40;//红色
public static final int GREEN_TARGET=43;//绿色
public static final int BULE_TARGET=86;//蓝色
/**
 * 编写一个找出当前位置的方法
 * @param image从adb截到手机上的截图
 * @return 返回当前的游戏人的位置
 */
public  static int[] findPos(BufferedImage image){
	if(image==null)
		return null;
	int width=image.getWidth();//获取屏幕的宽度
	int hight=image.getHeight();//获取屏幕的高度
	int[] res={0,0};
	int maxX=Integer.MIN_VALUE;//用来找取平台落点的边界
	int minX=Integer.MAX_VALUE;//边界的X的最小
	int maxY=Integer.MIN_VALUE;//平台的位置的Y的最大点
	int Pixel,Red,Green,Bule;
	//循环遍历出位置的三个点
	for(int i=0;i<width;i++){
		for(int j=hight/5;j<hight*3/5;j++){
			Pixel=image.getRGB(i, j);
			Red=(Pixel&0XFF0000)>>16;
		    Green=(Pixel&0XFF00)>>8;
		    Bule=(Pixel&0XFF);
		    if(ColorTolerance.work(Red, Green, Bule, RED_TARGET, GREEN_TARGET, BULE_TARGET, 16)){
		    	maxX=Integer.max(maxX,i );//取到x最大值
		    	minX=Integer.min(minX, i);//取到x最小值
		    	maxY=Integer.max(maxY, j);//取到y最大值
		    }
		}
	}
	res[0]=(maxX+minX)/2;
	res[1]=maxY-10;
	System.out.println("Mypostion pos："+"x:"+res[0]+"+y:"+res[1]);
	return res;
}
//主函数用来存储第一张照片
public static void main(String[] args) throws IOException {
	Mypostion mp=new Mypostion();//创建实例
	String root=mp.getClass().getResource("/").getPath();//获取当前路径
	System.out.println("root:"+root);
	String imagesSrc=root+"images/src";//图片储存位置
	String imagesDesc=root+"images/Desc";//信息储存的位置
	File src=new File(imagesSrc);
	System.out.println(src);
	for(File f:src.listFiles()){
		if(!f.getName().endsWith(".png")){//如果不是图片
			continue;
		}
		System.out.println(f.getAbsolutePath());
		BufferedImage img=ImgLoader.load(f.getAbsolutePath());
	}
}
}
