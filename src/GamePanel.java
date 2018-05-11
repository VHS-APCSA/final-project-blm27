import java.awt.Color;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

public class GamePanel extends JPanel implements Runnable, MouseListener {
	//game width and height
	private int width;
	private int height;
	//the game thread
	private Thread thread;
	private Ship ship;
	
	private ArrayList<GameObject> pieces;
	
	
	private Action left = new AbstractAction("left") {
		@Override
		public void actionPerformed(ActionEvent ae) {
			ship.setDir(Dir.LEFT);
		}
	};//<--- semicolon goes here!
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
	private Action fire = new AbstractAction("fire") {
		@Override
		public void actionPerformed(ActionEvent ae) {
//			if(bullet == null)
//			{
//				bullet = new Bullet(width, height, ship);
//				pieces.add(bullet);
//			}
		}
	};
	
	
	
	public GamePanel(int width, int height) {
		this.width = width;
		this.height = height;
		ship = new Ship(width, height);
		pieces = new ArrayList<GameObject>();
		pieces.add(ship);
		thread = new Thread(this);
		thread.start();
		setBackground(Color.black);
		addMouseListener(this);
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
	}
	private void registerKeyBinding(KeyStroke keyStroke, String name, Action action) {
		InputMap im = getInputMap(WHEN_IN_FOCUSED_WINDOW);
		ActionMap am = getActionMap();

		im.put(keyStroke, name);
		am.put(name, action);		
	}
	
	@Override
	public void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
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
		Toolkit.getDefaultToolkit().sync();
	}
	
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
				//System.out.println("Thread stopped");
				//e.printStackTrace();
				return;
			}
			repaint();
		}
	}
}