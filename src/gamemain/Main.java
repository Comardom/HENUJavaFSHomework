package gamemain;


import util.Default;
import util.TrashBin;

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

			frame.requestFocusInWindow();    // 尝试请求焦点

			//引入新字体
			JLabel label = new JLabel();
			label.setFont(Default.getMyFont());

			// 设置窗口图标（从资源中加载）
			Image icon = Toolkit.getDefaultToolkit().getImage(
					Main.class.getResource("/img/Me.png")
			);
			frame.setIconImage(icon);
//			frame.setResizable(false);

			frame.setSize(Default.getWindowWidth(), Default.getWindowHeight());
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			JPanel container = getContainer(frame);
			frame.add(container);
			frame.pack(); // 自动适应
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);
		});
		TrashBin._$();
	}

	private static JPanel getContainer(JFrame frame)
	{
		CardLayout layout = new CardLayout();
		JPanel container = new JPanel(layout);
		MenuPanel menu = new MenuPanel((width, height) -> {
			// 设置 Default 中的宽高（动态更新）
			Default.setWindowSize(width, height);

			// 创建游戏面板
			GamePanel gamePanel = new GamePanel();
			container.add(gamePanel, "game");
			layout.show(container, "game");

			// 设置窗口大小
			frame.setSize(width, height);
			frame.setLocationRelativeTo(null);
			gamePanel.requestFocusInWindow();
		});
		container.add(menu, "menu");
		return container;
	}
}
