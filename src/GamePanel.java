import java.awt.Color;
import java.awt.Component;
import java.awt.font.*;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

public class GamePanel extends JPanel implements Runnable, MouseListener {

	public static Image Galaga_Fighter;
	public static Image stars;
	public static Image bullet;
	public static Image heart;
	public static Image enemies;
	public static Image lostHeart;
	public static Image fastEnemies;
	public static Image smallFastEnemies;
	public static Font scoreFont;
	public static Font GOFont;
	public static Font roundFont;
	public static Image explosion;
	//game width and height
	private int width;
	private int height;
	//the game thread
	private Thread thread;
	private Ship ship;
	private Bullets bullets = null;
	private Lives lives;
	static Enemy enemy;
	private Score score;
	private boolean dead = false;
	private boolean resume1 = true;
	private int frameCount = 0;
	private final int SPAWN_SPEED = 60;

	private ArrayList<GameObject> pieces;
	private ArrayList<Bullets> allBullets;
	private ArrayList<Enemy> allEnemies;


	private Action left = new AbstractAction("left") {
		@Override
		public void actionPerformed(ActionEvent ae) {
			ship.setDir(Dir.LEFT);
		}
	};
	private Action right = new AbstractAction("right") {
		@Override
		public void actionPerformed(ActionEvent ae) {
			ship.setDir(Dir.RIGHT);
		}
	};
	private Action stop = new AbstractAction("stop") {
		@Override
		public void actionPerformed(ActionEvent ae) {
			ship.setDir(Dir.NONE);
		}
	};
	private Action resume = new AbstractAction("resume") {
		@Override
		public void actionPerformed(ActionEvent ae) 
		{
			System.out.println("test1");
			resume1 = true;
		}
	};
	//fire action which creates new Bullets and adds it to a list. removes bullets when it goes off screen.
	private Action fire = new AbstractAction("fire") {
		@Override
		public void actionPerformed(ActionEvent ae)
		{
			bullets = new Bullets(width, height, ship);
			allBullets.add(bullets);
			int index = 0;
			while (index < allBullets.size())
			{
				if (allBullets.get(index).getY() < 0)
				{
					allBullets.remove(index);
				} 
				else 
				{
					index++;
				}
			}
		}
	};
	public GamePanel(int width, int height) {
		this.width = width;
		this.height = height;
		ship = new Ship(width, height);
		lives = new Lives(width, height);
		score = new Score(width, height);
		pieces = new ArrayList<GameObject>();
		allBullets = new ArrayList<Bullets>();
		allEnemies = new ArrayList<Enemy>();

		allEnemies.add(new Enemy(width, height));
		pieces.add(lives);
		pieces.add(ship);
		pieces.add(score);
		pieces.add(enemy);
		thread = new Thread(this);
		thread.start();
		setBackground(Color.black);
		addMouseListener(this);
		try {                
			Galaga_Fighter = ImageIO.read(new File("./Galaga_Fighter.png"));
			stars = ImageIO.read(new File("./stars.gif"));
			bullet = ImageIO.read(new File("./bullet.png"));
			heart = ImageIO.read(new File("./heart.png"));
			lostHeart = ImageIO.read(new File("./lostHeart.png"));
			fastEnemies = ImageIO.read(new File("./fastEnemy.png"));
			smallFastEnemies = ImageIO.read(new File("./SmallFastEnemy.png"));
			scoreFont = Font.createFont(Font.TRUETYPE_FONT, new File("ARCADECLASSIC.ttf"));
			GOFont = Font.createFont(Font.TRUETYPE_FONT, new File("ARCADECLASSIC.ttf"));
			enemies = ImageIO.read(new File("./enemy.png"));
			explosion = ImageIO.read(new File("./explosion.png"));
			scoreFont = scoreFont.deriveFont(new Float(35));
			roundFont = Font.createFont(Font.TRUETYPE_FONT, new File("ARCADECLASSIC.ttf"));
			roundFont = roundFont.deriveFont(new Float(70));
			GOFont =  GOFont.deriveFont(new Float(70));
		} catch (IOException | FontFormatException e) {
			e.getStackTrace();
		}
		gameInit();
	}
	private void gameInit()
	{
		//left arrow key press
		registerKeyBinding(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, false), "left", left);
		//left arrow key release
		registerKeyBinding(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, true), "stop", stop);
		//right arrow key press
		registerKeyBinding(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, false), "right", right);
		//right arrow key release
		registerKeyBinding(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, true), "stop", stop);
		//space button press
		registerKeyBinding(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, false), "fire", fire);

		registerKeyBinding(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, false), "resume", resume);
	}
	private void registerKeyBinding(KeyStroke keyStroke, String name, Action action) {
		InputMap im = getInputMap(WHEN_IN_FOCUSED_WINDOW);
		ActionMap am = getActionMap();

		im.put(keyStroke, name);
		am.put(name, action);		
	}
	//	this checkCollision checks to see if a bullet intersects an enemy. if so, it removes 
	//	both the enemy and bullet from their list.. then increases the score.
	private void checkCollision(Bullets b)
	{
		if (b!=null)
		{
			for (int i = 0; i < allEnemies.size(); i++)
			{
				if (b.intersects(allEnemies.get(i)))
				{
					allEnemies.remove(i);
					allBullets.remove(b);
					score.count();
				}
			}
		}
	}
	//	this checkCollision checks to see if enemy and ship intersect, 
	//	if so then lives decrease by one. updates the graphics for the hearts.
	private void checkCollision(Graphics g)
	{
		if (ship != null)
		{
			for (int i = 0; i < allEnemies.size(); i++)
			{
				if (ship.intersects(allEnemies.get(i)))
				{
					allEnemies.remove(i);
					lives.setLives(lives.getLives() - 1);
					lives.draw(g);
					if (lives.getLives() == 0)
					{
						dead = true;
					}
				}
			}
		}
	}
	//renders Graphics objects on screen
	@Override
	public void paintComponent(Graphics g) 
	{
		if(score.getScore() == 20 && resume1 == true)
		{
			g.setColor(Color.red);
			g.setFont(GamePanel.scoreFont);
			g.drawString("ROUND 1 COMPLETED", width / 2 - 165, height / 2 - 10);
			resume1 = false;
			return;
		}
		if(resume1 == false)
		{
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			resume1 = true;
			System.out.println("sleeping");
			score.count();
			
		}
		else{
			//after score increases overtime it will start adding different types of enemies.
			//there is a less chance of spawning a FastEnemy & SmallFastEnemy
			frameCount++;
			int scoreRatio = (score.getScore() * -40) + 300;
			//after the score reaches 8, the difficulty increases
			if (scoreRatio < 5 && scoreRatio > -500)
			{
				if (frameCount > SPAWN_SPEED)
				{
					int random = (int)(Math.random() *7);
					if(random <= 5)
					{
						allEnemies.add(new Enemy(width, height));
					}
					else if(random == 6)
					{
						allEnemies.add(new FastEnemy(width,height));
					}
					frameCount = 0;
				}
			}
			//if score is 20 or greater, difficulty increases even more.
			else if(scoreRatio <= -500)
			{
				if(frameCount > SPAWN_SPEED)
				{
					int random = (int)(Math.random()*9);
					if(random <=5)
					{
						allEnemies.add(new Enemy(width, height));
					}
					else if(random == 6)
					{
						allEnemies.add(new FastEnemy(width, height));
					}
					else if(random == 7)
					{
						allEnemies.add(new SmallFastEnemy(width,height));
					}
					else if(random == 8)
					{
						allEnemies.add(new Enemy(width, height));
						allEnemies.add(new Enemy(width, height));
					}
					frameCount = 0;
				}
			}
			//spawns regular enemies until scoreRatio < 5
			else if (frameCount > scoreRatio)
			{
				allEnemies.add(new Enemy(width, height));
				frameCount = 0;
			}
			super.paintComponent(g);
			//draws background
			g.drawImage(GamePanel.stars, getX(), getY(), 550,700, null);
			//loops through pieces and draws them
			for(GameObject piece : pieces)
			{
				if(piece != null)
				{
					piece.draw(g);
					if(piece instanceof Movable)
					{
						((Movable) piece).move();
					}
				}
			}
			//		if a enemy goes off the screen, then the life decreases by one. renders the lost heart. if there are no lives
			//		 then boolean dead is set to true.
			for (int i = 0; i < allEnemies.size(); i++)
			{
				Enemy myE = allEnemies.get(i);
				if (myE.getY() > height)
				{
					allEnemies.remove(i);
					lives.count();
					lives.draw(g);
					if (lives.getLives() == 0)
					{
						dead = true;
					}
				}
				myE.draw(g);
				myE.move();
			}
			checkCollision(g);
			//draws and checks the bullets on the screen
			for (int i = 0; i < allBullets.size(); i++)
			{
				allBullets.get(i).draw(g);
				checkCollision(allBullets.get(i));
				if (allBullets.size() > i)
				{
					if(allBullets.get(i) instanceof Movable)
					{
						((Movable)allBullets.get(i)).move();
					}
				}
			}
			//if dead is true, then a explosion image is rendered in front of the ship.
			if(dead)
			{
				g.drawImage(GamePanel.explosion, ship.getX() - 17, ship.getY() - 10, 70,70, null);
			}
			//if lives are 0, then the text "GAME OVER" is rendered.
			if(lives.getLives() == 0)
			{
				g.setColor(Color.red);
				g.setFont(GamePanel.GOFont);
				g.drawString("GAME OVER", width / 2 - 165, height / 2 - 10);
				thread.interrupt();
			}
			//if score reaches 20, then full lives are restored
			if(score.getScore() == 20 && lives.getLives() != 3)
			{
				lives.setLives(3);
				lives.draw(g);
			}
			Toolkit.getDefaultToolkit().sync();
		}
	}

	//	public void resume()
	//	{
	//		if(resume1 = true)
	//		{
	//			System.out.println("test");
	//			thread.resume();
	//		}
	//	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}
	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}
	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}
	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}
	@Override
	public void run()
	{
		while(true)
		{
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) { 
				return;
			}
			repaint();
		}
	}
}