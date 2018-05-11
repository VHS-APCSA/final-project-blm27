import java.awt.Color;

public class Ship extends GameObject implements Movable
{
	private Dir dir;
	public Ship(int gwidth, int gheight)
	{
		super(gwidth, gheight);
		width = 25;
	    height = width;
		//position and speed of Ship
		x = (gameWidth - width) / 2;
		y = gameHeight - height - 20;
		dir = Dir.NONE;
		speed = 5;
		color = Color.blue;
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
}
