import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class GameObject {
	//object x,y position
	protected int x, y;
	//speed of object
	protected int speed;
	//width and height of object
	protected int width, height;
	//JPanel width and height
	protected int gameWidth;
	protected int gameHeight;
	//color of object
	protected Color color;
	protected GameObject(int gameWidth, int gameHeight)
	{
		this.gameWidth = gameWidth;
		this.gameHeight = gameHeight;
	}	
	public void draw(Graphics g) {
		g.setColor(color);
        g.fillRect(x, y, width, height);
	}
	public Rectangle getRect() 
	{
        return new Rectangle(x, y, width, height);
    }
	//checks to see if the two rectangle around the objects intersect each other
	public boolean intersects(GameObject go) 
	{
		 return go.getRect().intersects(this.getRect());
	}
	public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public int getWidth() {
        return width;
    }
    public Color getColor() {
    	return color;
	}
}