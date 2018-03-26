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
 * �ҵ���һ���׵�
 * @author Houhansen
 *ͨ���������������ȷ�����λ�ù�������ֵ
 */
public class NextPointFinder {
public int[] find(BufferedImage image,int[] myPos){//����ͼƬ�Լ��ҵ�λ��
	if(image==null)
		return null;
	//Ŀ��ƽ̨����ɫ���ص�
	 int targetR=0,targetG=0,targetB=0;
	 int red,green,blue;//��ɫ��
	int[] res=new int[6];
	int width=image.getWidth();//�õ�ͼƬ�Ŀ��
	int height=image.getHeight();//�õ�ͼƬ�Ŀ��
	int pixel=image.getRGB(0, 200);//�õ�(0,200)������
	int red1=(pixel&0xff0000)>>16;//��ɫ��ͨ����λʮ��������������ʾ��ǰ��λΪ��ɫ���м���λΪ��ɫ�������λΪ��ɫ
	int green1=(pixel&0xff00)>>8;
	int blue1=(pixel&0xff);
	//�����洢��Ͷ���ɫ��,������ɫ��ֵ�Ǻ�����,Ϊ���ǵõ��Ͷ˱�������ɫ��ͨ����ɫ��ķ�ʽ����ȡ��
	Map<Integer,Integer> map=new HashMap<>();
	for(int i=0;i<width;i++){
		pixel=image.getRGB(i, height-1);
		map.put(pixel, map.getOrDefault(pixel, 0)+1);//map.getOrDefault���������ԭ����ֵ���ж�Ӧ����ֵ����ԭ����ֵ��û���򷵻������Ĭ��ֵ
	}
	int max=0;
	for(Map.Entry<Integer, Integer> entry:map.entrySet()){
		if(entry.getValue()>max){
			pixel=entry.getKey();
			max=entry.getKey();
		}
	}
	//�ڶ���λ�õ���ɫ,��ɫ��Χ����	
	int red2=(pixel&0xff0000)>>16;//��ɫ��ͨ����λʮ��������������ʾ��ǰ��λΪ��ɫ���м���λΪ��ɫ�������λΪ��ɫ
	int green2=(pixel&0xff00)>>8;
	int blue2=(pixel&0xff);
	int t=16;//��ɫ�Ĺ��Χ
	//������������ɫ�ķ�Χ��ȷ��
	int maxR=Integer.max(red2, red1)+t;
	int minR=Integer.min(red2, red1)-t;
	int maxG=Integer.max(green1, green2)+t;
	int minG=Integer.min(green2, green1)-t;
	int maxB=Integer.max(blue1,blue2)+t;
	int minB=Integer.min(blue2, blue1)-t;
	System.out.println("��ɫ��Χ��"+"maxR:"+maxR+"minR:"+minR+"maxG:"+
	maxG+"minG:"+minG+"maxB:"+maxB+"minB:"+minB);
	boolean found=false;
	int tx;//��ʱ�ĵ����ɨ���ĺ������
	int ty;//������룬�������������������ʱ��û�����
	//�����ҳ���һ�����
	for(int j=height/4;j<myPos[1];j++){
		for(int i=width/8;i<width*7/8;i++){
			tx=Math.abs(i-myPos[0]);
			ty=Math.abs(j-myPos[1]);
			//��Ϊ��һ�����tx=ty��ty����ʱ���Կ��Ǻ��ԣ����������
			if(tx<ty)
				continue;
		    pixel=image.getRGB(i, j);
		    //�ҵ���ɫ��Ӧ��ֵ
		    red=(pixel&0xff0000)>>16;
			green=(pixel&0xff00)>>8;
	        blue=pixel&0xff;
	        if(red<minR||red>maxR||green<minG||green>maxG||blue<minB||blue>maxB){
	        	 System.out.println("NextPostionFinder��ǰ��ɫ"+"red:"+red+",green:"+green+
	        			 ",blue:"+blue+",i:"+i+",j:"+j);
	        	//��ǰ��ɫ����������������ɫ��Χ�ڣ������������һ��Ŀ���
	        	res[0]=i;res[1]=j;//���Ƕ��˶��������
	        	System.out.println("Ŀ��ƽ̨�Ķ������꣺x"+res[0]+",y:"+res[1]);
	        //ȡ����ɫ��ƽ��ֵ
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
	        System.out.println("��ɫ��targetR:"+targetR+",targetG:"+targetG+",targetB:"+targetB);
	        break;
	        }
		}
		//�ҵ���Ŀ��ĵ�
		if(found){
//			System.out.println(found);
			break;
		}
	}
	//�����ɫ��ƿ�ӵ���ɫ�򽫲�ȡ��ƿ�ӵķ�ʽ����
	if(targetB==BottleFind.white&&targetG==BottleFind.white&&targetR==BottleFind.white){
		return BottleFind.find(image, res[0], res[1]);//����ƿ�ӷ���
	}
	boolean[][] matchMap=new boolean[width][height];//�Ƿ�����ƥ�䣬��ÿ��������
	boolean[][] HasMap=new boolean[width][height];//�Ƿ������Ѿ�������
	res[2]=Integer.MAX_VALUE;//����������,������
	res[3]=Integer.MAX_VALUE;//������
	res[4]=Integer.MIN_VALUE;//���ҵ������
	res[5]=Integer.MAX_VALUE;//�����������
	Queue<int[]> q=new ArrayDeque<>();//��������Ķ���
	q.add(res);
	int[] point;//��
	int i,j;//��������
	while(!q.isEmpty()){
		point=q.poll();
		i=point[0];
		j=point[1];
//		System.out.println("NextPointFinder point:"+"i��"+i+",j:"+j);
		if(j>myPos[1])//�����Ѿ��������½�
			continue;
		//�Ż�����ʱ�䣬���Ѿ��������跶Χ����ֱ������,�����Ѿ���������ֱ�ӽ�������
		if(i<Integer.max(0, res[0])-300||i>Integer.min(width, res[0]+300)||
				j<Integer.max(0, res[1]-300)||j>Integer.min(height, res[1]+300)||HasMap[i][j])
			continue;
		HasMap[i][j]=true;//���
		pixel=image.getRGB(i, j);
		red=(pixel&0xff0000)>>16;
		green=(pixel&0xff00)>>8;
		blue=(pixel&0xff);
//		System.out.println("NextPointFinder point��ɫ��"+"red:"+red+",green:"+green+",blue:"+blue);
		//�ж��Ƿ���ɫƥ��
		matchMap[i][j]=ColorTolerance.work(red, green, blue, targetR, targetG, targetB, 16);
		if(matchMap[i][j]){//����ƥ��������ҵ����Եλ�õ�
//			System.out.println("---------------------------------");
			if(i<res[2]){//����˺�����	
				res[2]=i;
				res[3]=j;
			}
			else if(i==res[2]&&j<res[3])
				res[3]=j;
			//���Ҷ˵������
			if(i>res[4]){
				res[4]=i;
				res[5]=j;
			}
			else if(i==res[4]&&j<res[5])
				res[5]=j;
			//���϶˵������
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
	System.out.println("NextPostionFinder����˵����꣺"+"x:"+res[2]+",y:"+res[3]);
	System.out.println("NextPostionFinder���Ҷ˵����꣺"+"x:"+res[4]+",y:"+res[5]);
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
	long time=0;//��¼ÿ�δ����ʱ��
	//����ÿ��ͼƬ���������ĸ�·��
	for(File f:FSrc.listFiles()){
		time=System.currentTimeMillis();
		BufferedImage img=ImgLoader.load(f.getAbsolutePath());
		int[] myPos=mp.findPos(img);//��ǰλ��
		int[] nextPos=np.find(img, myPos);//��һ����λ��
		time=System.currentTimeMillis()-time;//���ʱ��
		//���ͼƬ
		BufferedImage desc=new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
		Graphics g=desc.getGraphics();//Ϊ�˻���ͼ��
		g.drawImage(img, 0, 0, img.getWidth(), img.getHeight(),null);//������ͼ
		g.setColor(Color.PINK);
		//�����ĸ���Ҫλ�õ�
		g.drawRect(nextPos[0]-5, nextPos[1]-5, 10, 10);
		g.drawRect(nextPos[2]-5, nextPos[3]-5, 10, 10);
		g.drawRect(nextPos[0]-5, nextPos[1]-5, 10, 10);
		//�������ĵ�
		if(nextPos[2]!=Integer.MAX_VALUE&&nextPos[4]!=Integer.MIN_VALUE){
			g.drawRect((nextPos[2]+nextPos[4])/2-5, (nextPos[3]+nextPos[5])/2-5, 10, 10);
		}
		else
			g.drawRect(nextPos[0]-5, nextPos[1]+40, 10, 10);
		//����ͼƬ��λ�ü�������
		File FileDesc=new File(imgsDesc, f.getName());
		//������������ļ����丸·����������·��
		if(!FileDesc.exists()){
			FileDesc.mkdirs();
			FileDesc.createNewFile();
		}
		ImageIO.write(desc, "png", FileDesc);//��ͼƬд���ļ��У���׺Ϊpng
	}
	System.out.println("ƽ��ʱ��"+time/(FSrc.listFiles().length));
}
}
