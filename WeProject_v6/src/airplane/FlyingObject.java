package airplane;

import java.util.Random;
import java.awt.image.BufferedImage;

/**������**/
public abstract class FlyingObject {

		public static final int LIFE = 0;//���ŵ�
		public static final int DEAD = 1;//���˵�
		public static final  int REMOVE = 2;//ɾ����
		protected int state = LIFE;//��ǰ״̬��Ĭ��Ϊ���ţ�
		/*
		 * ��ȡͼƬʹ��Ҫȥ���Ƕ����״̬����Ϊ�ڲ�ͬ״̬�»�ȡ
		 *��ͼƬ�ǲ�һ����ÿ��������һ��״̬����ζ��״̬Ϊ����
		 *�����е����ԣ���������ڳ����� ��״̬һ�㶼�ǹ̶��ģ�
		 *���Զ����Ϊ������ͬʱ���һ��state��������ʾ��ǰ��״̬
		 *------��FlyingObject�����LIFE��DEAO��REMOVE�������������
		 *state������ʾ��ǰ״̬
		 */
		
		//��С
		protected int width;
		protected int height;
		
		//����++
		protected int x;
		protected int y;
	
		/**ר�Ÿ�С�л�����л���С�۷��ṩ��*/
		public FlyingObject(int width,int height) {
			this.width = width;
			this.height = height;
			Random r = new Random();//���������
			x = r.nextInt(World.WIDTH-width);//0�������ڿ�-С�л������������
			y = -height;
		}
		
		/**ר�Ÿ�Ӣ�ۻ�����գ��ӵ��ṩ��*/
		public FlyingObject(int width,int height,int x,int y) {
			this.width = width;
			this.height = height;
			this.x = x;
			this.y = y;
		}
		
		/**�������ƶ�**/
		public abstract void step();
		
		/**��ȡ�����ص�ͼƬ*/
		public abstract BufferedImage getImage();
		
		/**�ж϶����Ƿ���ŵ�*/
		public boolean isLife() {
			return state == LIFE;//����ǰ״̬ΪLIFE����ʾ�����ǻ��ŵģ��Ż�true�����߷���false
		}
		
		/***/
		public boolean isDead() {
			return state == DEAD;//������ǰ״̬��DEAD����ʾ���������ģ�����true�����򷵻�false
		}
		
		/***/
		public boolean isRemove() {
			return state == REMOVE;//������ǰ״̬��REMOVE����ʾ���������ģ�����true�����򷵻�false
		}
		
		/**���л��Ƿ����*/
		public boolean outOfBounds() {
			return this.y>=World.HEIGHT;
		}
		
		/**���������ӵ�/Ӣ�ۻ�����ײ*/
		public boolean  hit(FlyingObject other) {
			int x1 = this.x-other.width;//x1�����˵�x-Ӣ�ۻ����ӵ��Ŀ�
			int x2 = this.x+this.width;//x2:���˵�x+���˵Ŀ�
			int y1 = this.y-other.height;//y1�����˵�y-Ӣ�ۻ����ӵ��ĸ�
			int y2 = this.y+this.height;//y2�����˵�y+���˵ĸ�
			int x = other.x;//Ӣ�ۻ����ӵ���x
			int y = other.y;//Ӣ�ۻ����ӵ���y
			return x>=x1 && x<=x2&&y>=y1&&y<=y2;//x��x1��x2֮�䣬y��y1��y2֮��
		}
		
		/**������ȥ��*/
		public void goDead() {
			state = DEAD;//����ǰ״̬��ΪDEAD
		}
		
		public Bullet[] shoot() {
			Bullet[] bs = new Bullet[1];
			return bs;
		}
		
}
