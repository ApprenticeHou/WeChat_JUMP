package jump_algorithm;
/**
 * @author Houhansen
 * 根据所给的路径将图片读取进行使用
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
		// TODO 自动生成的 catch 块
		e.printStackTrace();
	}
	finally{
		if(is!=null)
			is.close();
	}
	return Image;
}
}
