package jump_algorithm;
/**
 * @author Houhansen
 * ����������·����ͼƬ��ȡ����ʹ��
 */
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class ImgLoader {
public static BufferedImage	load(String path) throws IOException{
	if(path==null)
		return null;
	BufferedImage Image=null;
	InputStream is=null;
	try {
	is=new BufferedInputStream(new FileInputStream(path));
		Image=ImageIO.read(is);
	} catch (IOException e) {
		// TODO �Զ����ɵ� catch ��
		e.printStackTrace();
	}
	finally{
		if(is!=null)
			is.close();
	}
	return Image;
}
}
