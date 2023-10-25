package engine.stateMachine;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class StateManager {
	private static State currentState;
	private static GameState gameState;
	private static Color bgColour = Color.black;

	public static void update() {
		if (currentState != null) {
			// System.out.println(currentState);
			currentState.update();
		}

	}

	public static void render(BufferedImage image) {
		if (currentState != null) {
			currentState.render(image);
		}
	}

	public static void setCurrentState(State newState) {
		if (currentState != null) {
			currentState.end();
		}
		if (newState instanceof GameState) {
			gameState = (GameState) newState;
		}
		newState.start(currentState);
		currentState = newState;
	}

	public static State getCurrentState() {
		return currentState;
	}

	public static GameState getGameState() {
		return gameState;
	}

	public static void setBgColour(Color bgColour) {
		StateManager.bgColour = bgColour;
	}

	public static Color getBgColour() {
		return bgColour;
	}

}
