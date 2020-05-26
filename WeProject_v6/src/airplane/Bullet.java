package airplane;

import java.awt.image.BufferedImage;

/**�ӵ��Ƿ�����*/
public class Bullet extends FlyingObject{

	
	private int speed = 3;//�ƶ��ٶȣ�
	private boolean isEnem = true;//�ж��ӵ��Ƿ��ǵл��ӵ�
	public Bullet(int x ,int y,boolean isEnem) {
		super(43,43,x,y);
		this.isEnem=isEnem;
	}
	
	/**��дstep()�ӵ��ƶ�*/
	public void step() {
		if(isEnem) {
			y+=speed;
		}else {
			y-=speed;
		}
	}

	/**��дgetImage()����*/
	@Override
	public BufferedImage getImage() {
		/*
		 * �����ţ��򷵻�bulletͼƬ
		 * �����˵ģ�״̬��Ϊɾ����������ͼƬ
		 * ��ɾ���ģ��򲻷���ͼƬ
		 */
		 if(isLife()) {
			 if(isEnem) {
				 return Images.bullet[1];
			}else {
				 return Images.bullet[0];
				}	 
		}else if(isDead()) {
				state = REMOVE;
		}
		return null;//���˺�ɾ����������ͼƬ7

	}
	
	/**��дoutOfBounds()����ӵ��Ƿ����*/
	public boolean outOfBounds() {
		return this.y<=-this.height || this.y >= World.HEIGHT;
	}
	
	public boolean isEnem() {
		return isEnem;
	}
	
}
