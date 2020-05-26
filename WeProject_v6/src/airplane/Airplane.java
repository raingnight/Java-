package airplane;

import java.awt.image.BufferedImage;
import java.util.Random;

import org.omg.PortableInterceptor.INACTIVE;

/**С�л��Ƿ�����**/
public class Airplane extends FlyingObject implements Enemy{

	private int speed;//�ƶ��ٶȣ�
	
	public Airplane() {
		super(100,95);
		speed= 2;
	}
	
	/**��дstep() С�л��ƶ�**/
	public void step() {
		y+= speed;
	}

	private int index = 0;
	private int flyindex = 0;
	@Override
	public BufferedImage getImage() {
		if(isLife()) {//������
			return Images.airs[flyindex++%2];//������žͷ��ص�һ��
		}else if(isDead()){//������
			BufferedImage img = Images.boms[index++];
			if(index == Images.boms.length) {
				state = REMOVE;
			}
			return img;
		}
		return null;//ɾ��״̬ʱ��������ͼƬ
	}

	/**��дgetScore()�÷�*/
	public int getScore() {
		return 1;
	}
	
	public Bullet[] shoot() {
		int xStep = this.width/4;
		int yStep = 90;
		Bullet[] bs = new Bullet[1];
		bs[0] = new Bullet(x+2*xStep-5, y+yStep,true);
		return bs;
	}
}
