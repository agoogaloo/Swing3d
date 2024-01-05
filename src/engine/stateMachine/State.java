package engine.stateMachine;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public interface State {

	public abstract void start(State prevState);

	public abstract void update();

	public abstract void render(BufferedImage image);

	public abstract void end();
}
