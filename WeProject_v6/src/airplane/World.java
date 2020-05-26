package airplane;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/** ������Ϸ���� **/
public class World extends JPanel {
	private static final long serialVersionUID = 1L;
	
	public static final int WIDTH = 512;// ���ڿ�
	public static final int HEIGHT = 768;// ���ڸ�

	public static final int START = 0;// ����״̬
	public static final int RUNNING = 1;// ����
	public static final int PAUSE = 2;// ��ͣ
	public static final int GAME_OVER = 3;// ����
	protected static final int BOSS = 4;// BOSS����
	private static final int NEXT_LEVEL =5;// ��һ��
	private static final int CLEARANCE = 6;
	private int level = 2;// �ؿ�
	private int blood = 20;
	private boolean isOver = false;

	private int y = 0;
	
	private int state = START;// ��ǰ״̬��Ĭ��Ϊ����״̬��

	private static Music music = new Music();// bgm����

	private Boss boss;
	private Sky sky = new Sky();// ��ն���
	private Hero hero = new Hero();// Ӣ�ۻ�����
	private FlyingObject[] enemies = {};// ����(С�л�����л���С�۷�)����
	private Bullet[] bullets = {};// �ӵ�����
	private SmallHero[] smallHero = null;//Ԯ��С�ɻ�����
	

	/** �������ˣ���л���С�л���С�۷䣩���� */
	public FlyingObject nextOne() {
		Random r = new Random();
		int type = r.nextInt(30);
		
		if (((score >= 20&&score<=30) && boss == null)||((score>=80&&score<=90) && boss == null)||(score>=120&&score<=130)&& boss==null) {
			music.game_mucic.stop();// ս��bgmֹͣ
			music.alert_bgm.play();
			music.boss_bgm.loop();// bossר��bgm��ʼѭ��
			return new Boss();
		}

		if (type < 8 && boss == null) {//0-8���ش�л�
			return new BigAirplane();
		} else if (type < 25 && boss == null) {//8-25С�л�
			return new Airplane();
		} else {//25-30����С�۷�
			return new Bee();
		}
	}

	private int enterIndex = 0;

	// �л���С�л�����л���С�۷䣩�볡
	public void enterAction() {// ÿ10������һ��
		enterIndex++;
		if (enterIndex % 100 == 0) {// ÿ1000������һ��
			FlyingObject obj = nextOne();// ��ȡ���˶���
			enemies = Arrays.copyOf(enemies, enemies.length + 1);
			enemies[enemies.length - 1] = obj;
		}
	}

	private int shootIndex = 0;// �볡����

	/** �ӵ��볡 */
	public void shootAction() {// ÿ10������һ��
		shootIndex++;
		if (shootIndex % 30 == 0) {
			Bullet[] bs = hero.shoot();
			bullets = Arrays.copyOf(bullets, bullets.length + bs.length);// bs����������
			System.arraycopy(bs, 0, bullets, bullets.length - bs.length, bs.length);// ׷������
			if (smallHero != null) {
				Bullet[] smallbs1 = smallHero[0].shoot();
				Bullet[] smallbs2 = smallHero[1].shoot();

				bullets = Arrays.copyOf(bullets, bullets.length + smallbs1.length);// smallbs1����������
				System.arraycopy(smallbs1, 0, bullets, bullets.length - smallbs1.length, smallbs1.length);// ׷������
				bullets = Arrays.copyOf(bullets, bullets.length + smallbs2.length);// smallbs2����������
				System.arraycopy(smallbs2, 0, bullets, bullets.length - smallbs2.length, smallbs2.length);// ׷������
			}

		}
		if (shootIndex % 200 == 0) {
			for (int i = 0; i < enemies.length; i++) {
				Bullet[] bs = enemies[i].shoot();
				bullets = Arrays.copyOf(bullets, bullets.length + bs.length);// bs����������
				System.arraycopy(bs, 0, bullets, bullets.length - bs.length, bs.length);// ׷������
			}
		}
	}

	/** �������ƶ� */
	public void stepAction() {
		sky.step();
		for (int i = 0; i < bullets.length; i++) {
			bullets[i].step();
		}

		for (int i = 0; i < enemies.length; i++) {

			if (enemies[i] instanceof Boss) {
				boss = (Boss) enemies[i];
				boss.step();
				continue;
			} 
			enemies[i].step();
		}

		if (smallHero != null) {
			smallHero[0].step(hero.x - 20, hero.y + 100);
			smallHero[1].step(hero.x + hero.width - 20, hero.y + 100);
		}

		
	}

	/** ɾ��Խ��ĺ�״̬��ɾ��״̬�ӵ��͵л�----�����ڴ�й© */
	public void outOfBoundsAction() {// û10������һ��
		int index = 0;
		FlyingObject[] enemyLives = new FlyingObject[enemies.length];
		for (int i = 0; i < enemies.length; i++) {
			FlyingObject f = enemies[i];
			if (!f.outOfBounds() && !f.isRemove()) {
				enemyLives[index] = f;
				index++;
			}
		}
		enemies = Arrays.copyOf(enemyLives, index);

		Bullet[] bslives = new Bullet[bullets.length];
		int stIndex = 0;
		for (int i = 0; i < bullets.length; i++) {
			Bullet b = bullets[i];
			if (!b.outOfBounds() && !b.isRemove()) {
				bslives[stIndex] = b;
				stIndex++;
			}
		}
		bullets = Arrays.copyOf(bslives, stIndex);
	}

	/** �ӵ�����˺͵л��ӵ���ײ */
	private int score = 0;
	private int bulletsBangsBoss = 0;

	public void bulletBangAction() {
		for (int i = 0; i < bullets.length; i++) {// ���������ӵ�
			Bullet b = bullets[i];

			if (b.isEnem()) {// ����ǵл����ӵ�
				for (int j = i; j < bullets.length; j++) {
					if (!bullets[j].isEnem() && bullets[j].isLife() && b.isLife() && bullets[j].hit(b)) {
						b.goDead();
						bullets[j].goDead();
					}
				}
			} else {// ��������ҷ����ӵ�
				for (int j = 0; j < enemies.length; j++) {// �������е���
					FlyingObject f = enemies[j];

					if (b.isLife() && f.isLife() && f.hit(b)) {
						b.goDead();// �ӵ�ȥ��

						if (f instanceof Enemy) {
							f.goDead();// ����ȥ��
							music.boom_music.play();// �ӵ���ը��

							Enemy e = (Enemy) f;
							score += e.getScore();
						}

						if (f instanceof Award) {
							f.goDead();// ����ȥ��
							music.boom_music.play();// �ӵ���ը��
							music.award_bgm.play();//�õ�������Ч
							
							Award a = (Award) f;
							int type = a.getAwardType();
							switch (type) {
							case Award.FIRE:
								hero.addFire();
								break;
							case Award.LIFE:
								hero.addLife();
								break;
							case Award.PROTECT:
								if (hero.getProtect() == 0) {
									hero.addProtect();
								}
								break;
							case Award.SMALLHERO:
								music.outbreak_bgm.play();
								smallHero = hero.getHeros();
							}
						}

						if (f instanceof Boss) {
							bulletsBangsBoss++;
							if (bulletsBangsBoss % 5 ==0 ) {
								blood--;
							}
							
							if (bulletsBangsBoss == 100) {
								boss.goDead();
								bulletsBangsBoss=0;
								blood = 20;
								score += boss.getScore();
								
								music.boss_boom.play();// BOSS������ը��
								music.boss_bgm.stop();// ר��BOSS_bgmֹͣ
								music.game_mucic.loop();//��bgmѭ��
							}

						}
					}
				}
			}
		}
	}

	/** Ӣ�ۻ���л��͵з��ӵ���ײ */
	public void heroBangAction() {
		for (int j = 0; j < enemies.length; j++) {// �������е���
			FlyingObject f = enemies[j];
			if (hero.isLife() && f.isLife() && f.hit(hero)) {
				if (!(f instanceof Boss)) {
					f.goDead();// ����ȥ��
				} 
				if (hero.getProtect() == 1) {
					hero.subProtect();
				} else {
					//hero.subtractLife();
					hero.clearFire();
				}
			}
		}
		for (int i = 0; i < bullets.length; i++) {
			Bullet bs = bullets[i];
			if (bs.isEnem()) {
				if (bs.isLife() && hero.isLife() && hero.hit(bs)) {
					bs.goDead();
					if (hero.getProtect() == 1) {
						hero.subProtect();
					} else {
						//hero.subtractLife();
						hero.clearFire();
					}
				}
			}
		}
	}

	/** �����Ϸ���� */
	public void checkGameOverAction() {
		if (hero.getLife() <= 0) {
			state = GAME_OVER;
			music.game_mucic.stop();
			music.boss_bgm.stop();
			music.award_bgm.stop();
			music.boom_music.stop();
			music.boss_boom.stop();
			music.windows_music.loop();
		}
		if (boss!=null&&boss.isDead()&&state==RUNNING) {
			state = NEXT_LEVEL;
		}
	}

	public void action() {
		music.windows_music.loop();// ��Ϸδ��ʼǰ��bgm����

		// ��������
		MouseAdapter m = new MouseAdapter() {
			/** ��дmouseMoved()����ƶ��¼� **/
			public void mouseMoved(MouseEvent e) {
				if (state == RUNNING) {// ����������״̬��ִ��
					int x = e.getX();// ��ȡ����x����
					int y = e.getY();// ��ȡ����y����
					hero.moveTo(x, y);// ��Ӣ�ۻ���������ƶ�
				}

			}

			/** ��дmouseClicked������¼� **/
			public void mouseClicked(MouseEvent e) {
				switch (state) {// ���ݵ�ǰ״̬����ͬ�Ĵ���
				case START:// ����״̬ʱ
					state = RUNNING;
					music.game_mucic.loop();//���������Ϊ����״̬ʱ��ս��bgm����
					break;
				case GAME_OVER:// ��Ϸ����ʱ
					score = 0;
					level = 0;
					blood = 20;
					sky = new Sky();
					hero = new Hero();
					enemies = new FlyingObject[0];
					bullets = new Bullet[0];
					state = START;
					boss = null;
					smallHero = null;
					break;
				}
			}

			/** ��дmouseExited()����Ƴ��¼� */
			public void mouseExited(MouseEvent e) {
				if (state == RUNNING) {// ����״̬ʱ
					music.game_mucic.stop();
					state = PAUSE;// ��Ϊ��ͣ״̬
				}
			}

			/** ��дmouseEntered()��������¼� */
			public void mouseEntered(MouseEvent e) {
				if (state == PAUSE) {// ��ͣ״̬ʱ
					music.game_mucic.loop();
					state = RUNNING;// ��Ϊ����״̬
				}
			}
		};
		
		
		this.addMouseListener(m);// ��������װ�������
		this.addMouseMotionListener(m);// ��������װ�������

		
		Timer timer = new Timer();// ��ʱ��
		int intervel = 10;// ��ʱ���(�Ժ���Ϊ��λ)
		timer.schedule(new TimerTask() {
			@Override
			public void run() {// ��ʾ��ʱ�ɵ���
				if (state == RUNNING) {// ����������״̬��ִ��
					music.windows_music.stop();//��״̬��Ϊ����״̬����������bgmֹͣ
					enterAction();// �л���С�л�����л���С�۷�,boss���볡
					shootAction();
					stepAction();
					outOfBoundsAction();
					bulletBangAction();
					heroBangAction();
					checkGameOverAction();
				}
				if (state == NEXT_LEVEL) {
					if (level==2) {
						state = CLEARANCE;
					}else {
						level += 1;
						enemies = new FlyingObject[0];
						bullets = new Bullet[0];
						boss = null;
						isOver = true;
						state = RUNNING;
					}
				}
				repaint();// �ػ�
				if(isOver) {
					try {
						Thread.sleep(1000);
						Thread.sleep(1000);
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}		
				}
				
				if (state == CLEARANCE) {
					level = 0;
					blood = 0;
					sky = null;
					hero = null;
					enemies = new FlyingObject[0];
					bullets = new Bullet[0];
					boss = null;
					smallHero = null;
				}
				
			}
		}, intervel, intervel);// �ƻ���
	}

	/** ��дpaint()������g�ǻ��� */
	private int timeIndex = 0;

	public void paint(Graphics g) {
		if (state!=CLEARANCE) {
	
		g.drawImage(sky.getImage(level), sky.x, sky.y, this);
		g.drawImage(sky.getImage(level), sky.x, sky.getY1(), this);

		g.drawImage(hero.getImage(), hero.x, hero.y, this);
		for (int i = 0; i < enemies.length; i++) {
			FlyingObject f = enemies[i];
			if(f instanceof Boss) {
				
				g.drawImage(boss.getImage(level), boss.x, boss.y, this);
				g.drawString("BOSS: ", 350, 35);
				g.drawImage(boss.getBloodImage(blood), 400, 20, this);
				continue;
			}
			g.drawImage(f.getImage(), f.x, f.y, this);
		}
		for (int i = 0; i < bullets.length; i++) {
			Bullet b = bullets[i];
			g.drawImage(b.getImage(), b.x, b.y, this);
		}
		if (hero.getProtect() == 1) {
			g.drawImage(hero.getImage(1), hero.x - 50, hero.y - 30, null);
		}
		if (smallHero != null) {
			g.drawImage(smallHero[0].getImage(), smallHero[0].x, smallHero[0].y, null);
			g.drawImage(smallHero[1].getImage(), smallHero[1].x, smallHero[1].y, null);
		}
		
		if(level != 0&&isOver) {
		
			g.drawImage(Images.black, 0, 0,this);
			g.drawImage(Images.load[0], 100, 250,this);
			isOver = false;
		}
		g.drawString("SCORE: " + score, 10, 25);
		g.drawString("LIFE: " + hero.getLife(), 10, 45);

		switch (state) {
		case START:
			g.drawImage(Images.start, 0, 0, 530, 800, this);
			break;
		case PAUSE:
			g.drawImage(Images.pause, 0, 0, 530, 800, this);
			break;
		case GAME_OVER:
			g.drawImage(Images.gameOver, 0, 0, 530, 800, this);
			break;
		}
		}else {
			
			g.drawImage(Images.clearance, 0, y--,530,800, this);

		}
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("�ɻ���ս");
		World world = new World();
		frame.add(world);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(WIDTH, HEIGHT);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true); // 1)���ô��ڿɼ� 2������ĵ���paint()����
		frame.setResizable(false);// ��ֹ�϶����ڸı��С

		music.windows_music.loop();// ��¼����BGM����
		world.action();
	}

}
