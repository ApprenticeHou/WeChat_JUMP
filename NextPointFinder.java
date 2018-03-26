package jump_algorithm;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

import javax.imageio.ImageIO;

/**
 * 找到下一个白点
 * @author Houhansen
 *通过三个点的坐标来确定点的位置共有六个值
 */
public class NextPointFinder {
public int[] find(BufferedImage image,int[] myPos){//传入图片以及我的位置
	if(image==null)
		return null;
	//目标平台的颜色像素点
	 int targetR=0,targetG=0,targetB=0;
	 int red,green,blue;//颜色点
	int[] res=new int[6];
	int width=image.getWidth();//得到图片的宽度
	int height=image.getHeight();//得到图片的宽度
	int pixel=image.getRGB(0, 200);//得到(0,200)的像素
	int red1=(pixel&0xff0000)>>16;//颜色是通过六位十六进制数字来表示的前两位为红色，中间两位为绿色，最后两位为蓝色
	int green1=(pixel&0xff00)>>8;
	int blue1=(pixel&0xff);
	//用来存储最低端颜色的,键是颜色，值是横坐标,为的是得到低端背景的颜色，通过颜色多的方式来获取的
	Map<Integer,Integer> map=new HashMap<>();
	for(int i=0;i<width;i++){
		pixel=image.getRGB(i, height-1);
		map.put(pixel, map.getOrDefault(pixel, 0)+1);//map.getOrDefault方法是如果原来键值对有对应的数值返回原来数值若没有则返回输入的默认值
	}
	int max=0;
	for(Map.Entry<Integer, Integer> entry:map.entrySet()){
		if(entry.getValue()>max){
			pixel=entry.getKey();
			max=entry.getKey();
		}
	}
	//第二个位置的颜色,颜色范围控制	
	int red2=(pixel&0xff0000)>>16;//颜色是通过六位十六进制数字来表示的前两位为红色，中间两位为绿色，最后两位为蓝色
	int green2=(pixel&0xff00)>>8;
	int blue2=(pixel&0xff);
	int t=16;//颜色的公差范围
	//将整个背景颜色的范围来确定
	int maxR=Integer.max(red2, red1)+t;
	int minR=Integer.min(red2, red1)-t;
	int maxG=Integer.max(green1, green2)+t;
	int minG=Integer.min(green2, green1)-t;
	int maxB=Integer.max(blue1,blue2)+t;
	int minB=Integer.min(blue2, blue1)-t;
	System.out.println("颜色范围："+"maxR:"+maxR+"minR:"+minR+"maxG:"+
	maxG+"minG:"+minG+"maxB:"+maxB+"minB:"+minB);
	boolean found=false;
	int tx;//此时的点距离扫描点的横向距离
	int ty;//纵向距离，当横向距离大于纵向距离时则没有意见
	//遍历找出下一个落点
	for(int j=height/4;j<myPos[1];j++){
		for(int i=width/8;i<width*7/8;i++){
			tx=Math.abs(i-myPos[0]);
			ty=Math.abs(j-myPos[1]);
			//因为下一个点的tx=ty当ty过大时可以考虑忽略，则继续进行
			if(tx<ty)
				continue;
		    pixel=image.getRGB(i, j);
		    //找到颜色对应的值
		    red=(pixel&0xff0000)>>16;
			green=(pixel&0xff00)>>8;
	        blue=pixel&0xff;
	        if(red<minR||red>maxR||green<minG||green>maxG||blue<minB||blue>maxB){
	        	 System.out.println("NextPostionFinder当前颜色"+"red:"+red+",green:"+green+
	        			 ",blue:"+blue+",i:"+i+",j:"+j);
	        	//当前颜色不在所给背景的颜色范围内，则表明这是下一个目标点
	        	res[0]=i;res[1]=j;//这是顶端顶点的坐标
	        	System.out.println("目标平台的顶点坐标：x"+res[0]+",y:"+res[1]);
	        //取到颜色的平均值
	        for(int k=0;k<5;k++){
	        	pixel=image.getRGB(i, j+k);
	        	targetR+=(pixel&0xff0000)>>16;
	            targetG+=(pixel&0xff00)>>8;
	            targetB+=pixel&0xff;
	        }
	        targetB/=5;
	        targetG/=5;
	        targetR/=5;
	        found=true;
	        System.out.println("颜色：targetR:"+targetR+",targetG:"+targetG+",targetB:"+targetB);
	        break;
	        }
		}
		//找到了目标的点
		if(found){
//			System.out.println(found);
			break;
		}
	}
	//如果颜色是瓶子的颜色则将采取挑瓶子的方式来跳
	if(targetB==BottleFind.white&&targetG==BottleFind.white&&targetR==BottleFind.white){
		return BottleFind.find(image, res[0], res[1]);//采用瓶子方法
	}
	boolean[][] matchMap=new boolean[width][height];//是否像素匹配，将每个坐标标记
	boolean[][] HasMap=new boolean[width][height];//是否像素已经遍历过
	res[2]=Integer.MAX_VALUE;//最左点的坐标,横坐标
	res[3]=Integer.MAX_VALUE;//纵坐标
	res[4]=Integer.MIN_VALUE;//最右点横坐标
	res[5]=Integer.MAX_VALUE;//最左点纵坐标
	Queue<int[]> q=new ArrayDeque<>();//存入坐标的队列
	q.add(res);
	int[] point;//点
	int i,j;//横纵坐标
	while(!q.isEmpty()){
		point=q.poll();
		i=point[0];
		j=point[1];
//		System.out.println("NextPointFinder point:"+"i："+i+",j:"+j);
		if(j>myPos[1])//代表已经超过了下界
			continue;
		//优化运行时间，当已经不在所需范围内则直接跳过,或者已经遍历过则直接进行跳过
		if(i<Integer.max(0, res[0])-300||i>Integer.min(width, res[0]+300)||
				j<Integer.max(0, res[1]-300)||j>Integer.min(height, res[1]+300)||HasMap[i][j])
			continue;
		HasMap[i][j]=true;//标记
		pixel=image.getRGB(i, j);
		red=(pixel&0xff0000)>>16;
		green=(pixel&0xff00)>>8;
		blue=(pixel&0xff);
//		System.out.println("NextPointFinder point颜色："+"red:"+red+",green:"+green+",blue:"+blue);
		//判断是否颜色匹配
		matchMap[i][j]=ColorTolerance.work(red, green, blue, targetR, targetG, targetB, 16);
		if(matchMap[i][j]){//如若匹配则进行找到最边缘位置点
//			System.out.println("---------------------------------");
			if(i<res[2]){//最左端横坐标	
				res[2]=i;
				res[3]=j;
			}
			else if(i==res[2]&&j<res[3])
				res[3]=j;
			//最右端点的坐标
			if(i>res[4]){
				res[4]=i;
				res[5]=j;
			}
			else if(i==res[4]&&j<res[5])
				res[5]=j;
			//最上端点的坐标
			if(j<res[1]){
				res[0]=i;
				res[1]=j;
			}
			if(i>=1&&i<1079&&j>0&&j<height){
		q.add(WhitePointFind.buildArray(i-1, j));
		q.add(WhitePointFind.buildArray(i+1, j));
		q.add(WhitePointFind.buildArray(i, j-1));
		q.add(WhitePointFind.buildArray(i, j+1));
			}
		}
	}
	System.out.println("NextPostionFinder最左端点坐标："+"x:"+res[2]+",y:"+res[3]);
	System.out.println("NextPostionFinder最右端点坐标："+"x:"+res[4]+",y:"+res[5]);
	return res;
}
public static void main(String[] args) throws IOException {
	NextPointFinder np=new NextPointFinder();
	String root=np.getClass().getResource("/").getPath();
	System.out.println("root"+root);
	String imgsSrc=root+"imgs/src";
	String imgsDesc=root+"imgs/Desc";
	File FSrc=new File(imgsSrc);
	System.out.println(FSrc);
	Mypostion mp=new Mypostion();
	long time=0;//记录每次处理的时间
	//处理每个图片根据所给的根路径
	for(File f:FSrc.listFiles()){
		time=System.currentTimeMillis();
		BufferedImage img=ImgLoader.load(f.getAbsolutePath());
		int[] myPos=mp.findPos(img);//当前位置
		int[] nextPos=np.find(img, myPos);//下一个的位置
		time=System.currentTimeMillis()-time;//输出时间
		//结果图片
		BufferedImage desc=new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
		Graphics g=desc.getGraphics();//为了画出图形
		g.drawImage(img, 0, 0, img.getWidth(), img.getHeight(),null);//画出截图
		g.setColor(Color.PINK);
		//画出四个重要位置点
		g.drawRect(nextPos[0]-5, nextPos[1]-5, 10, 10);
		g.drawRect(nextPos[2]-5, nextPos[3]-5, 10, 10);
		g.drawRect(nextPos[0]-5, nextPos[1]-5, 10, 10);
		//画出中心点
		if(nextPos[2]!=Integer.MAX_VALUE&&nextPos[4]!=Integer.MIN_VALUE){
			g.drawRect((nextPos[2]+nextPos[4])/2-5, (nextPos[3]+nextPos[5])/2-5, 10, 10);
		}
		else
			g.drawRect(nextPos[0]-5, nextPos[1]+40, 10, 10);
		//存入图片的位置及其名称
		File FileDesc=new File(imgsDesc, f.getName());
		//如果不存在其文件及其父路径，创建此路径
		if(!FileDesc.exists()){
			FileDesc.mkdirs();
			FileDesc.createNewFile();
		}
		ImageIO.write(desc, "png", FileDesc);//将图片写入文件夹，后缀为png
	}
	System.out.println("平均时间"+time/(FSrc.listFiles().length));
}
}
