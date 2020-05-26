package airplane;

import java.awt.image.BufferedImage;
import java.util.Random;
/**С�۷��Ƿ�����*/
public class Bee extends FlyingObject implements Award{
	private int xSpeed;//x�����ƶ��ٶ�
	private int ySpeed;//y�����ƶ��ٶ�
	private int awardType;//��������
	public Bee(){
		super(100,73);
		Random r = new Random();//���������
		xSpeed= 1;
		ySpeed= 2;
		awardType = r.nextInt(4);//0��3֮��������
	}
	/**��дstep()С�۷��ƶ�*/
	public void step() {
		x += xSpeed;
		y += ySpeed;
		if(x<=0 || x >= World.WIDTH-this.width ) {//��x<=0����x>=World.WIDTH-this.width,��˵�����߿���
			xSpeed *= -1;//�л�����(���为��������)
		}
	}
	
	private int index = 0;
	private int flyindex = 0;
	@Override
	public BufferedImage getImage() {
		if(isLife()) {//������
			return Images.bees[flyindex++%2];//������žͷ��ص�һ��
		}else if(isDead()){//������
			BufferedImage img = Images.boms[index++];
			if(index == Images.boms.length) {
				state = REMOVE;
			}
			return img;
		}
		return null;//ɾ��״̬ʱ��������ͼƬ
	}
	@Override
	public int getAwardType() {
		return awardType;//���ؽ�������
	}
	public Bullet[] shoot() {
		int xStep = this.width/4;
		int yStep = 70;
		Bullet[] bs = new Bullet[1];
		bs[0] = new Bullet(x+2*xStep-5, y+yStep,true);
		return bs;
	}
}
