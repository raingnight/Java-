package airplane;
/**ͼƬ������*/

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class Images {

  //������   ��̬��      ��������       ����
	public static BufferedImage[] sky;//���ͼƬ
	public static BufferedImage[] bullet;//�ӵ�ͼƬ
	public static BufferedImage[] heros;//Ӣ�ۻ�ͼƬ
	public static BufferedImage[] airs;//С�л�ͼƬ
	public static BufferedImage[] bairs;//��л�ͼƬ
	public static BufferedImage[] bees;//С�۷�ͼƬ
	public static BufferedImage protect;//������ͼƬ
	public static BufferedImage smallhero;//Ԯ���ɻ�ͼƬ
	public static BufferedImage[] boss;//bossͼƬ
	public static BufferedImage[] blood;//bossѪ��
	public static BufferedImage[] boms;//��ըͼƬ
	public static BufferedImage clearance;//ͨ������
	public static BufferedImage black; //ȫ�ڱ���(����ʹ��)
	public static BufferedImage[] load;//����
	
	public static BufferedImage start;//����ͼƬ
	public static BufferedImage pause;//��ͣͼ
	public static BufferedImage gameOver;//��Ϸ����ͼ
	
	static {//��̬��ʼ��
		  sky = new BufferedImage[3];
		  sky[0] = readImage("image/background.jpg");
		  sky[1] = readImage("image/background2.jpg");
		  sky[2] = readImage("image/background3.jpg");
		  
		  bullet= new BufferedImage[2];
		  bullet[0] = readImage("image/bullet.png");
		  bullet[1] = readImage("image/bullet1.png");
		  heros = new BufferedImage[2];
		  heros[0] = readImage("image/hero0.png");
		  heros[1] = readImage("image/hero1.png");
		  smallhero = readImage("image/sHero.png");
		  
		  protect = readImage("image/protect.png");
		  airs = new BufferedImage[2];
		  bairs = new BufferedImage[2];
		  bees = new BufferedImage[2];
		  airs[0] = readImage("image/smenemyair01.png");
		  airs[1] = readImage("image/smenemyair01_1.png");
		  
		  bairs[0] = readImage("image/bigenemyair1.png");
		  bairs[1] = readImage("image/bigenemyair1_1.png");
		  
		  bees[0] = readImage("image/bee01.png");
		  bees[1] = readImage("image/bee01_1.png");
		  
		  boms = new BufferedImage[6];
		  for (int i = 0; i < boms.length; i++) {
			  boms[i] = readImage("image/bom-"+(i+1)+".png");
		  }
		  
		  
		  start = readImage("image/start.png");
		  pause = readImage("image/pause.png");
		  gameOver = readImage("image/gameover.png");
		  
		  boss = new BufferedImage[6];
		  boss[0] = readImage("image/boss1.png");
		  boss[1] = readImage("image/boss1_1.png");
		  boss[2] = readImage("image/boss2.png");
		  boss[3] = readImage("image/boss2_1.png");
		  boss[4] = readImage("image/boss3.png");
		  boss[5] = readImage("image/boss3_1.png");

		  blood = new BufferedImage[21];
		  for (int i = 0; i < blood.length; i++) {
			blood[i] = readImage("image/blood/Ѫ��"+i+".png");
		}
		  
		  clearance = readImage("image/�廳.png");//ͨ�ر���
		  black = readImage("image/black.jpg");//ȫ��
		  load = new BufferedImage[2];
		  load[0] = readImage("image/load.png");
		  load[1] = readImage("image/loado.png");
	}
	
	//��ȡͼƬ
	public static BufferedImage readImage(String fileName){///------�˷�����Ҫ�����գ�֪������
		try{
			BufferedImage img = ImageIO.read(FlyingObject.class.getResource(fileName));
			return img;
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
}
