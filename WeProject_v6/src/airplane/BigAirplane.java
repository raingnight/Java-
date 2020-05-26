package airplane;

import java.awt.image.BufferedImage;
import java.util.Random;

/**��л��Ƿ�����**/
public class BigAirplane extends FlyingObject implements Enemy{

	private int speed;//�ƶ��ٶȣ�
	
	public BigAirplane() {
		super(100,63);
		speed= 2;
	}
	
	/**��дstep()��л��ƶ�*/
	public void step() {
		y+= speed;
	}


	private int index = 0;
	private int flyindex = 0;
	@Override
	public BufferedImage getImage() {
		if(isLife()) {//������
			return Images.bairs[flyindex++%2];//������žͷ��ص�һ��
		}else if(isDead()){//������
			BufferedImage img = Images.boms[index++];
			if(index == Images.boms.length) {
				state = REMOVE;
			}
			return img;
		}
		return null;//ɾ��״̬ʱ��������ͼƬ
	}

	/**��дgetScore()�÷�**/
	public int getScore() {
		return 3;
	}
	
	public Bullet[] shoot() {
		int xStep = this.width/4;
		int yStep = 70;
		Bullet[] bs = new Bullet[1];
		bs[0] = new Bullet(x+2*xStep-5, y+yStep,true);
		return bs;
	}
}
