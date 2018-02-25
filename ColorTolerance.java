package jump_algorithm;
/**
 * 对于像素点之间的颜色的公差，从而来判断平台的位置进而的到具体的位置
 * @author 侯汉森
 *@version 1.0
 *
 */
public class ColorTolerance {
	/**
	 * 测试
	 * @param i 要测定的像素的红色
	 * @param j 要测定的像素的绿色
	 * @param k 要测定的像素的蓝色
	 * @param x 实际像素点的红色
	 * @param y 实际像素点的绿色
	 * @param z 实际像素点的蓝色
	 * @param tolerance 允许公差
	 * @return 是否符合要求
	 */
public static boolean work(int i,int j,int k,int x,int y,int z,int tolerance){
	return i>x-tolerance&&i<x+tolerance&&j>y-tolerance
			&&j<y+tolerance&&k>z-tolerance&&k<z+tolerance;
}
}
