import java.awt.Graphics;

public class SmallFastEnemy extends FastEnemy
{
	public SmallFastEnemy(int gameWidth, int gameHeight)
	{
		super(gameWidth, gameHeight);
		height = 20;
		width = 25;
	}
	//moves the SmallFastEnemy down
	@Override
	public void move()
	{
		y += speed;
	}
	//draws the SmallFastEnemy with the image
	@Override
	public void draw(Graphics g)
	{
		g.drawImage(GamePanel.smallFastEnemies, x, y, 25,20, null);
	}
}