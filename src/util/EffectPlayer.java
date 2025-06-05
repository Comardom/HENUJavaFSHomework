package util;

import javax.sound.sampled.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class EffectPlayer
{
	private static final Map<String, byte[]> cache = new HashMap<>();

	public static void preload(String path) {
		try (InputStream in = EffectPlayer.class.getResourceAsStream(path)) {
			if (in == null) throw new FileNotFoundException("找不到音频：" + path);
			ByteArrayOutputStream buffer = new ByteArrayOutputStream();
			byte[] temp = new byte[4096];
			int n;
			while ((n = in.read(temp)) != -1) {
				buffer.write(temp, 0, n);
			}
			cache.put(path, buffer.toByteArray());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void play(String path) {
		byte[] soundData = cache.get(path);
		if (soundData == null) {
			preload(path);
			soundData = cache.get(path);
		}

		final byte[] finalData = soundData;
		new Thread(() -> {
			try (AudioInputStream ais = AudioSystem.getAudioInputStream(new BufferedInputStream(new ByteArrayInputStream(finalData)))) {
				Clip clip = AudioSystem.getClip();
				clip.open(ais);
				clip.start();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}).start();
	}
}
