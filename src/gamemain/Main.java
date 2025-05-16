package gamemain;

import javax.swing.*;


public class Main
{
	public static void main(String[] args)
	{
		createWindow();
	}
	static void createWindow()
	{
		//用来创建窗体
		SwingUtilities.invokeLater(() -> {
			JFrame frame = new JFrame("Game");
			frame.setSize(800, 600);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.add(new GamePanel());
			frame.setVisible(true);
		});
	}
}
