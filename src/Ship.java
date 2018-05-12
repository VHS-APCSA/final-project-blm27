import java.awt.Graphics;

public class Ship extends GameObject implements Movable
{
	private Dir dir;
	public Ship(int gwidth, int gheight)
	{
		super(gwidth, gheight);
		//position and speed of Ship

	
		x = (gameWidth - width) / 2;
		y = gameHeight - height - 95;
		dir = Dir.NONE;
		speed = 7;
		gameWidth = gameWidth - 57;
		
	
	}
	@Override
	public void move()
	{
		if(dir == Dir.LEFT)
		{
			x -= speed;
		}
		else if(dir == Dir.RIGHT)
		{
			x += speed;
		}
		if(x + width > gameWidth)
		{
			x = gameWidth - width;
			dir = Dir.NONE;
		}
		else if(x < 0)
		{
			x = 0;
			dir = Dir.NONE;
		}
	}
	public void setDir(Dir dir)
	{
		this.dir = dir;
	}
	@Override
	public void draw(Graphics g)
	{
		g.drawImage(GamePanel.Galaga_Fighter, x, y, 40,50, null);
	}
}
