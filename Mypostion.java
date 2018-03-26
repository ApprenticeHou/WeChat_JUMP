package jump_algorithm;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * �ҵ���һ���˵�λ��
 * @author HOUHansen
 *@version 1.0
 */

public class Mypostion {
	/*
	 * �Ƚ��ж��ڳ�ʼ��Ļ��ɫ�ļ�¼
	 * ������Ԫɫ��������ɫ�ı�ע
	 */
public static final int RED_TARGET=40;//��ɫ
public static final int GREEN_TARGET=43;//��ɫ
public static final int BULE_TARGET=86;//��ɫ
/**
 * ��дһ���ҳ���ǰλ�õķ���
 * @param image��adb�ص��ֻ��ϵĽ�ͼ
 * @return ���ص�ǰ����Ϸ�˵�λ��
 */
public  static int[] findPos(BufferedImage image){
	if(image==null)
		return null;
	int width=image.getWidth();//��ȡ��Ļ�Ŀ��
	int hight=image.getHeight();//��ȡ��Ļ�ĸ߶�
	int[] res={0,0};
	int maxX=Integer.MIN_VALUE;//������ȡƽ̨���ı߽�
	int minX=Integer.MAX_VALUE;//�߽��X����С
	int maxY=Integer.MIN_VALUE;//ƽ̨��λ�õ�Y������
	int Pixel,Red,Green,Bule;
	//ѭ��������λ�õ�������
	for(int i=0;i<width;i++){
		for(int j=hight/5;j<hight*3/5;j++){
			Pixel=image.getRGB(i, j);
			Red=(Pixel&0XFF0000)>>16;
		    Green=(Pixel&0XFF00)>>8;
		    Bule=(Pixel&0XFF);
		    if(ColorTolerance.work(Red, Green, Bule, RED_TARGET, GREEN_TARGET, BULE_TARGET, 16)){
		    	maxX=Integer.max(maxX,i );//ȡ��x���ֵ
		    	minX=Integer.min(minX, i);//ȡ��x��Сֵ
		    	maxY=Integer.max(maxY, j);//ȡ��y���ֵ
		    }
		}
	}
	res[0]=(maxX+minX)/2;
	res[1]=maxY-10;
	System.out.println("Mypostion pos��"+"x:"+res[0]+"+y:"+res[1]);
	return res;
}
//�����������洢��һ����Ƭ
public static void main(String[] args) throws IOException {
	Mypostion mp=new Mypostion();//����ʵ��
	String root=mp.getClass().getResource("/").getPath();//��ȡ��ǰ·��
	System.out.println("root:"+root);
	String imagesSrc=root+"images/src";//ͼƬ����λ��
	String imagesDesc=root+"images/Desc";//��Ϣ�����λ��
	File src=new File(imagesSrc);
	System.out.println(src);
	for(File f:src.listFiles()){
		if(!f.getName().endsWith(".png")){//�������ͼƬ
			continue;
		}
		System.out.println(f.getAbsolutePath());
		BufferedImage img=ImgLoader.load(f.getAbsolutePath());
	}
}
}
