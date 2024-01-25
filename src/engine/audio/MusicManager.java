package engine.audio;

public class MusicManager {
  private static final Song music = new Song("res/audio/song.wav",true);

  public void start() {
    music.setVolume(0.25);
    music.play();
  }

  public void update() {
    music.update();
  }
}
