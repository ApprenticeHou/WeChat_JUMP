package jump_algorithm;
/**
 * �������ص�֮�����ɫ�Ĺ���Ӷ����ж�ƽ̨��λ�ý����ĵ������λ��
 * @author �ɭ
 *@version 1.0
 *
 */
public class ColorTolerance {
	/**
	 * ����
	 * @param i Ҫ�ⶨ�����صĺ�ɫ
	 * @param j Ҫ�ⶨ�����ص���ɫ
	 * @param k Ҫ�ⶨ�����ص���ɫ
	 * @param x ʵ�����ص�ĺ�ɫ
	 * @param y ʵ�����ص����ɫ
	 * @param z ʵ�����ص����ɫ
	 * @param tolerance ������
	 * @return �Ƿ����Ҫ��
	 */
public static boolean work(int i,int j,int k,int x,int y,int z,int tolerance){
	return i>x-tolerance&&i<x+tolerance&&j>y-tolerance
			&&j<y+tolerance&&k>z-tolerance&&k<z+tolerance;
}
}
