import java.awt.Graphics;
public class Enemy extends GameObject implements Movable{
	public Enemy(int gameWidth, int gameHeight)
	{
		super(gameWidth, gameHeight);
		speed = 4;
		x = (int)(Math.random() * (gameWidth -49)+1);
		y = 0;
		height = 25;
		width = 30;
	}
	//moves bullet up
	@Override
	public void move()
	{
		y += speed;
	}
	//draws bullet with bullet image
	@Override
	public void draw(Graphics g)
	{
		g.drawImage(GamePanel.enemies, x, y, 30,25, null);
	}
}