import javax.swing.JFrame;

public class GameRunner {
	public static void main(String[] args) 
	{
		int width = 600;
		int height = 700;
		GamePanel panel = new GamePanel(width, height);
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel);
		frame.setSize(width, height);
		frame.setVisible(true);
	}
}
