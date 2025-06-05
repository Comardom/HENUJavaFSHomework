package util;

import javax.sound.sampled.*;
import java.io.*;

public class MusicPlayer implements Runnable
{
	private String resourcePath;
	private Clip clip;
	private AudioInputStream audioStream;

	private volatile boolean paused = false;
	private volatile boolean running = true;

	public MusicPlayer(String resourcePath)
	{
		this.resourcePath = resourcePath;
	}

	@Override
	public void run()
	{
		try
		{
			InputStream audioSrc = getClass().getResourceAsStream(resourcePath);
			if (audioSrc == null) {
				System.err.println("Cannot find audio resource: " + resourcePath);
				return;
			}
			InputStream bufferedIn = new BufferedInputStream(audioSrc);
			audioStream = AudioSystem.getAudioInputStream(bufferedIn);

			AudioFormat format = audioStream.getFormat();
			DataLine.Info info = new DataLine.Info(Clip.class, format);
			clip = (Clip) AudioSystem.getLine(info);
			clip.open(audioStream);
			clip.loop(Clip.LOOP_CONTINUOUSLY); // 循环播放
			clip.start();

			// 保持线程运行并控制暂停
			while (running)
			{
				if (paused && clip.isRunning()) {
					clip.stop();
				} else if (!paused && !clip.isRunning()) {
					clip.start();
				}
				Thread.sleep(200);
			}

			clip.stop();
			clip.close();
			audioStream.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void pause()
	{
		paused = true;
	}

	public void resume()
	{
		paused = false;
	}

	public void stop()
	{
		running = false;
	}
}
