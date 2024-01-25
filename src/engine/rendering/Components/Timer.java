package engine.rendering.Components;

import engine.rendering.Scene;
import engine.rendering.Time;

public class Timer {
  double startTime;
  double time;
  boolean started;

  public Timer(double seconds) {
    this.time = seconds;
  }

  public void start() {
    if(!started) {
      this.startTime = Time.elapsedTime;
      started = true;
    }
  }

  public boolean running() {
    if(started && Time.elapsedTime - startTime >= time) {
      started = false;
    }
    return started;
  }

  public boolean ended() {
    return !running();
  }

  public void cancel() {
    started = false;
  }
}
