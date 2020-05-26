package airplane;

import java.awt.image.BufferedImage;

/**Ӣ�ۻ��Ƿ�����**/
public class Hero extends FlyingObject{

	//��
	private int life;
	
	//����
	private int fire;
	
	//������
	private int protect;
	
	public Hero(){
		super(97,139,190,400);
		protect = 0;
		life = 3;
		fire =0;
	}
	/**��дstep()Ӣ�ۻ�ͼƬ�л�*/
	public void step() {
	}
	
	private int index = 0;//����ͼƬ�л�
	/**��дgetImage()����*/
	@Override
	public BufferedImage getImage() {
		if(isLife()) {//�����ŵ�
			return Images.heros[index++%2];//�Ժ���������2����0������1
		}
		return null;
	}
	
	public BufferedImage getImage(int i) {
		return Images.protect;
	}
	
	/**Ӣ�ۻ������ӵ�����*/
	public Bullet[] shoot() {
		int xStep = this.width/2;
		int yStep = 10;
		if(fire>0) {//˫��
			Bullet[] bs = new Bullet[2];
			bs[0] = new Bullet(x+xStep-43, y-yStep,false);
			bs[1] = new Bullet(x+2*xStep-48, y-yStep,false);
			fire-=2;
			return bs;
		}else {//����
			Bullet[] bs = new Bullet[1];
			bs[0] = new Bullet(x+xStep-23, y-yStep,false);
			return bs;
		}
	}
	
	/**Ӣ�ۻ���������ƶ�*/
	public void moveTo(int x,int y) {
		this.x = x - this.width/2;//Ӣ�ۻ���x = ����x-Ӣ�ۻ��Ŀ�/2
		this.y = y - this.height/2;//Ӣ�ۻ���y = ����y-Ӣ�ۻ��ĸ�/2
	}
	
	/**Ӣ�ۻ���������*/
	public void addLife() {
		life++;//��������1
	}
	
	/**������*/
	public int getLife() {
		return life;
	}
	/**Ӣ�ۻ����ӻ���ֵ*/
	public void addFire() {
		fire+=40;//���ӻ���ֵ40
	}
	
	/**Ӣ�ۻ��͵л���ײ����һ��*/
	public void subtractLife() {
		life--;
	}
	
	/**����ֵ����*/
	public void clearFire() {
		fire = 0;
	}
	
	/**��ñ�����*/
	public int getProtect() {
		return protect;
	}
	
	public void addProtect() {
		protect = 1;
	}
	
	/**ʧȥ������*/
	public void subProtect() {
		protect = 0;
	}
	
	/**��ȡԮ��С�ɻ�*/
	public SmallHero[] getHeros() {
		SmallHero[] smallHero = new SmallHero[2];
		smallHero[0] = new SmallHero(this.x-20,this.y+100);
		smallHero[1] = new SmallHero(this.x+this.width-20,this.y+100);
		return smallHero;
	}
}
