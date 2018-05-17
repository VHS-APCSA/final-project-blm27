import java.awt.Graphics;

public class FastEnemy extends Enemy{
	public FastEnemy(int gameWidth, int gameHeight)
	{
		super(gameWidth, gameHeight);
		speed = 7;
	}
	//renders the fastEnemies image for FastEnemy
	@Override
	public void draw(Graphics g)
	{
		g.drawImage(GamePanel.fastEnemies, x, y, 30,25, null);
	}
}
