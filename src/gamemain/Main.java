package gamemain;

import javax.swing.*;
import java.awt.*;


public class Main
{
	public static void main(String[] args)
	{
		createWindow();
	}
	static void createWindow()
	{
		// 创建主游戏窗口
		SwingUtilities.invokeLater(() -> {
			JFrame frame = new JFrame("Game");

			// 设置窗口图标（从资源中加载）
			Image icon = Toolkit.getDefaultToolkit().getImage(
					Main.class.getResource("/img/Me.png")
			);
			frame.setIconImage(icon);

			frame.setSize(800, 600);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			// 添加游戏面板
			frame.add(new GamePanel());

			frame.setLocationRelativeTo(null); // 居中显示
			frame.setVisible(true);
		});
	}
}
