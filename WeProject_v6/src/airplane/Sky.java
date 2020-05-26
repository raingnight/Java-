package airplane;

import java.awt.image.BufferedImage;

/**���**/
public class Sky extends FlyingObject{

	
	private int speed;//�ƶ��ٶȣ�
	private int y1; //�ڶ���ͼƬ��y����
	
	public Sky(){

		super(World.WIDTH,World.HEIGHT,0,0);
		speed = 1;
		y1 = -World.HEIGHT;
	}
	/**��дstep()����ƶ�*/
	public void step() {
		y += speed;//y+(����)
		y1 += speed;//y1+(����)
		if(y>=World.HEIGHT) {
			y = -World.HEIGHT;
		}
		if(y1>=World.HEIGHT) {
			y1 = -World.HEIGHT;
		}
	}
	
	/**��дgetImage()����*/
	public BufferedImage getImage(int level) {
		return Images.sky[level];//ֱ�ӷ���skyͼƬ����
	}
	public int getY1() {
		return y1;//����y1����
	}
	@Override
	public BufferedImage getImage() {
		// TODO Auto-generated method stub
		return null;
	}
}
