package jump_algorithm;
/**
 * @author Houhansen
 * 对于药瓶的判断因为比较特殊
 */
import java.awt.image.BufferedImage;
import java.util.ArrayDeque;
import java.util.Queue;

public class BottleFind {
public static final int white=255;
public static int[] find(BufferedImage image,int i,int j){//图片以及顶点两个坐标位置
	if(image==null)
		return null;
	int[] res=new int[6];
	res[0]=i;//最上点的横坐标
	res[1]=j;//最上点的纵坐标
	res[2]=Integer.MAX_VALUE;//最左点的横坐标
	res[3]=Integer.MAX_VALUE;//最左点的纵坐标
	res[4]=Integer.MIN_VALUE;//最右点的横坐标
	res[5]=Integer.MAX_VALUE;//最右点的纵坐标
	int width=image.getWidth();
	int hight=image.getHeight();
	boolean[][] vMap=new boolean[width][hight];//存储此点是否标记
	Queue<int[]> q=new ArrayDeque<>();//队列又来加载点
	int[] pos={i,j};
	q.add(pos);
	while(!q.isEmpty()){
		pos=q.poll();//出队
		int x=pos[0];
		int y=pos[1];
		if(x<0||x>width||y<0||y>hight||vMap[x][y]){//判断是否越界和已经标记过
			continue;
		}
		vMap[x][y]=true;
		int pixel=image.getRGB(x, y);
		int red=(pixel&0xff0000)>>16;
		int green=(pixel&0xff00)>>8;
		int blue=pixel&0xff;
		if(red==white&&green==white&&blue==white){
			if(i<res[2]){//取到最右点，并且更新坐标
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
