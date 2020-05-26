package airplane;

import java.awt.image.BufferedImage;
import java.util.Random;
/**
 * boss�Ƿ�����
 */
public class Boss extends FlyingObject{

	private int speed = 2;
	private int xSpeed = 1;
	private int level;
	
	public Boss() {
		super(200, 244);
		x = 150;
	}
	
	/**
	 * boss���ƶ�
	 */
	public void step() {
		if(y<=100) {
			y+=speed;
		}else {
			speed = 0;
			x -= xSpeed;
			if(x<=0 || x >= World.WIDTH-this.width ) {
				xSpeed *= -1;//�л�����(���为��������)
			}
		}
	}

	/**
	 * ����boss���ӵ�
	 */
	public Bullet[] shoot() {
		int xStep = this.width/4;
		int yStep = 50;
		Random r = new Random();
		int type = r.nextInt(10);//ѡ���ӵ���������
		Bullet[] bs = new Bullet[10];
		if(level == 0) {//�жϹؿ��Ƿ��ǵ�һ�ء�
			if(type <= 6){
				for (int i = 0; i < bs.length; i++) {
					bs[i] = new Bullet(x+i*xStep-5, y+ (10-i) * yStep,true);
				}
			}else {
				for (int i = 0; i < bs.length; i++) {
					bs[i] = new Bullet(x+i*xStep-5, y+ i * yStep,true);
				}
			}
		}else if(level == 1) {//�жϹؿ��Ƿ��ǵڶ��ء�
			if(type <= 5) {
				for (int i = 0; i < bs.length; i++) {
					bs[i] = new Bullet(x+i*xStep-5, y+ i * yStep,true);
				}
			}else{
				for (int i = 0; i < bs.length; i++) {
					bs[i] = new Bullet(x+i*xStep-5, y+ (10-i) * yStep,true);
				}
			}
		}else {
			
			if(type <= 3) {
				for (int i = 0; i < bs.length; i++) {
					bs[i] = new Bullet(x+i*xStep-5, y+ 2 * yStep,true);
				}
			}else if(type <= 6){
				for (int i = 0; i < bs.length; i++) {
					bs[i] = new Bullet(x+i*xStep-5, y+ (10-i) * yStep,true);
				}
			}else {
				for (int i = 0; i < bs.length; i++) {
					bs[i] = new Bullet(x+i*xStep-5, y+ 2 * yStep,true);
				}
			}
			
		}
		
		
		return bs;
	}
	
	/**
	 * ����getImage����
	 * ���ݹؿ�������boss��ͼƬ
	 */
	private int index = 0;
	private int bomindex = 0;
	public BufferedImage getImage(int level) {
		this.level = level;
		
		if(isLife()) {//������
			if(level == 0) {
				return Images.boss[index++%2];
			}else if(level == 1){
				return Images.boss[(index++%2)+2];
			}else {
				return Images.boss[index++%2+4];
			}
				
		}else if(isDead()){//������
			BufferedImage img = Images.boms[bomindex++];
			if(index == Images.boms.length) {
				state = REMOVE;
			}
			return img;
		}

		return null;//ɾ��״̬ʱ��������ͼƬ
		
	}
	

	/**
	 * ����boss��Ѫ��
	 */
	public BufferedImage getBloodImage(int i) {
		return Images.blood[i];
	}

	/**����boss����getScore()�÷�*/
	public int getScore() {
		return 20;
	}
	/**
	 * ��д������ĳ��󷽷�
	 */
	public BufferedImage getImage() {
		return null;
	}

}
