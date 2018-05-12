
import java.awt.Graphics;

public class Lives extends GameObject implements Counter{
	private int lives;
	public Lives(int gameWidth, int gameHeight) {
		super(gameWidth, gameHeight);
		lives = 3;
		x = gameWidth - 50;
		y = 50;
	}
	@Override
	public void count()
	{
		lives--;
	}
	public int getLives()
	{
		return lives;
	}
	@Override
	public void draw(Graphics g)
	{
		if(lives == 3) {
			g.drawImage(GamePanel.heart, gameWidth -50, y -40, 30,20, null);
			g.drawImage(GamePanel.heart, 515, y -40, 30,20, null);
			g.drawImage(GamePanel.heart, 480, y -40, 30,20, null);
		}
		else if(lives == 2)
		{
			g.drawImage(GamePanel.lostHeart, gameWidth -50, y -40, 30,20, null);
			g.drawImage(GamePanel.heart, 515, y -40, 30,20, null);
			g.drawImage(GamePanel.heart, 480, y -40, 30,20, null);
		}
		else if(lives ==1)
		{
			g.drawImage(GamePanel.lostHeart, gameWidth -50, y -40, 30,20, null);
			g.drawImage(GamePanel.lostHeart, 515, y -40, 30,20, null);
			g.drawImage(GamePanel.heart, 480, y -40, 30,20, null);
		}
		else if(lives ==0)
		{
			g.drawImage(GamePanel.lostHeart, gameWidth -50, y -40, 30,20, null);
			g.drawImage(GamePanel.lostHeart, 515, y -40, 30,20, null);
			g.drawImage(GamePanel.lostHeart, 480, y -40, 30,20, null);
		}
	}
}
