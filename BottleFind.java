package jump_algorithm;
/**
 * @author Houhansen
 * ����ҩƿ���ж���Ϊ�Ƚ�����
 */
import java.awt.image.BufferedImage;
import java.util.ArrayDeque;
import java.util.Queue;

public class BottleFind {
public static final int white=255;
public static int[] find(BufferedImage image,int i,int j){//ͼƬ�Լ�������������λ��
	if(image==null)
		return null;
	int[] res=new int[6];
	res[0]=i;//���ϵ�ĺ�����
	res[1]=j;//���ϵ��������
	res[2]=Integer.MAX_VALUE;//�����ĺ�����
	res[3]=Integer.MAX_VALUE;//������������
	res[4]=Integer.MIN_VALUE;//���ҵ�ĺ�����
	res[5]=Integer.MAX_VALUE;//���ҵ��������
	int width=image.getWidth();
	int hight=image.getHeight();
	boolean[][] vMap=new boolean[width][hight];//�洢�˵��Ƿ���
	Queue<int[]> q=new ArrayDeque<>();//�����������ص�
	int[] pos={i,j};
	q.add(pos);
	while(!q.isEmpty()){
		pos=q.poll();//����
		int x=pos[0];
		int y=pos[1];
		if(x<0||x>width||y<0||y>hight||vMap[x][y]){//�ж��Ƿ�Խ����Ѿ���ǹ�
			continue;
		}
		vMap[x][y]=true;
		int pixel=image.getRGB(x, y);
		int red=(pixel&0xff0000)>>16;
		int green=(pixel&0xff00)>>8;
		int blue=pixel&0xff;
		if(red==white&&green==white&&blue==white){
			if(i<res[2]){//ȡ�����ҵ㣬���Ҹ�������
				res[2]=i;
				res[3]=j;
			}
			else if(i==res[2]&&j<res[3]){
				res[2]=i;
				res[3]=j;
			}
			if(i>res[4]){
				res[4]=i;
				res[5]=j;
			}
			else if(i==res[4]&&j<res[5]){
				res[4]=i;
				res[5]=j;
			}
			if(j<res[1]){
				res[0]=i;
				res[1]=j;
			}
			q.add(WhitePointFind.buildArray(x+1, y));
			q.add(WhitePointFind.buildArray(x-1, y));
			q.add(WhitePointFind.buildArray(x, y+1));
			q.add(WhitePointFind.buildArray(x, y-1));
		}
	}
	return res;
}
}
