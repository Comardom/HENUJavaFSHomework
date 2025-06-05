package gamemain;

import logic.GameState;

import javax.swing.*;
import java.awt.*;
import java.util.function.BiConsumer;

public class MenuPanel extends JPanel
{
	public MenuPanel(BiConsumer<Integer, Integer> onStartGame)
	{
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setPreferredSize(new Dimension(400, 300));

		JLabel title = new JLabel("选择窗口大小");
		title.setFont(new Font("Serif", Font.BOLD, 24));
		title.setAlignmentX(CENTER_ALIGNMENT);

		JButton btnSmall = new JButton("800 x 600");
		JButton btnBig = new JButton("1200 x 800");
		JButton btnFHD = new JButton("1800 x 900");
		JButton btn2K = new JButton("2400 x 1200");
		JButton btnTnt = new JButton("2200 x 1400");
		JButton btn4K = new JButton("3600 x 2000");

		btnSmall.setAlignmentX(CENTER_ALIGNMENT);
		btnBig.setAlignmentX(CENTER_ALIGNMENT);
		btnFHD.setAlignmentX(CENTER_ALIGNMENT);
		btn2K.setAlignmentX(CENTER_ALIGNMENT);
		btnTnt.setAlignmentX(CENTER_ALIGNMENT);
		btn4K.setAlignmentX(CENTER_ALIGNMENT);


		btnSmall.addActionListener(e ->{
			onStartGame.accept(800, 600);
			GamePanel.setGameState(GameState.RUNNING);
		});
		btnBig.addActionListener(e ->{
			onStartGame.accept(1200, 800);
			GamePanel.setGameState(GameState.RUNNING);
		});
		btnFHD.addActionListener(e ->{
			onStartGame.accept(1800, 900);
			GamePanel.setGameState(GameState.RUNNING);
		});
		btn2K.addActionListener(e ->{
			onStartGame.accept(2400, 1200);
			GamePanel.setGameState(GameState.RUNNING);
		});
		btnTnt.addActionListener(e ->{
			onStartGame.accept(2200, 1400);
			GamePanel.setGameState(GameState.RUNNING);
		});
		btn4K.addActionListener(e ->{
			onStartGame.accept(3600, 2000);
			GamePanel.setGameState(GameState.RUNNING);
		});

		add(Box.createVerticalGlue());
		add(title);
		add(Box.createVerticalStrut(20));
		add(btnSmall);
		add(Box.createVerticalStrut(10));
		add(btnBig);
		add(Box.createVerticalStrut(10));
		add(btnFHD);
		add(Box.createVerticalStrut(10));
		add(btn2K);
		add(Box.createVerticalStrut(10));
		add(btnTnt);
		add(Box.createVerticalStrut(10));
		add(btn4K);
		add(Box.createVerticalGlue());
	}
}
