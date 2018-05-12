import java.awt.Color;
import java.awt.Graphics;
public class Bullets extends GameObject implements Movable{
	public Bullets(int gameWidth, int gameHeight, Ship ship)
	{
		super(gameWidth, gameHeight);
		speed = 4;
		color = Color.green;
		x = ship.getX() + ship.getWidth() /2 + 13;
		y = gameHeight - 115;
	}
	@Override
	public void move()
	{
		y -= speed;
	}
	@Override
	public void draw(Graphics g)
	{
		g.drawImage(GamePanel.bullet, x, y, 17,21, null);
	}
}