package engine.audio;

import java.io.IOException;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;

// fully stolen from the penguin peak code

public class Song extends Sound{
	private String path;
	private Clip clip;
	private boolean loops;

	public Song(String path, boolean loops) { 
		this.path = path;
		this.loops = loops;
		try {
			loadAudioStream(this.path);
			clip = AudioSystem.getClip();
			clip.open(audioStream);
			
		}catch (LineUnavailableException ex) {
			System.out.println("Audio line for playing back is unavailable.");
			ex.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void update() {
		if (playing) {
			FloatControl gainControl;
		
			gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			gainControl.setValue(Sound.volumeToDb(volume));
		}
	}

	public void play() {
		playing = true;
		
		FloatControl gainControl;
		gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
		gainControl.setValue(Sound.volumeToDb(volume));
		clip.setMicrosecondPosition(0);
		clip.start();
		if(loops) {
			clip.loop(Clip.LOOP_CONTINUOUSLY);
		}
	}

  public void stop() {
    playing = false;
    clip.stop();
  }

	public void close() {
		clip.stop();
		clip.close();
	}
}

