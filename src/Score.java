import java.awt.Color;
import java.awt.Graphics;

public class Score extends GameObject {
	private int score;
	public Score(int gameWidth, int gameHeight) {
		super(gameWidth, gameHeight);
		score = 0;
		x = gameWidth - gameWidth +5;
		y = gameHeight - gameHeight +30;
		color = Color.red;
	}
	//increases score
	public void increaseScore()
	{
		score++;
	}
	//gets score
	public int getScore()
	{
		return score;
	}
	//draws score on screen, also adds 0's in front of score.
	@Override
	public void draw(Graphics g)
	{
		g.setColor(color);
		g.setFont(GamePanel.arcadeFont);
		if(score >= 0 && score < 10)
		{
		g.drawString("0000" + score + "", x, y);
		}
		else if(score >= 10 && score < 100)
		{
		g.drawString("000" + score + "", x, y);
		}
		else if(score >= 100 && score < 1000)
		{
		g.drawString("00" + score + "", x, y);
		}
		else if(score >= 1000 && score < 10000)
		{
		g.drawString("0" + score + "", x, y);
		}
		else if(score >= 10000 && score < 100000)
		{
		g.drawString(score + "", x, y);
		}
		else if(score >= 100000)
		{
		g.drawString(score + "", x, y);
		}
	}
}