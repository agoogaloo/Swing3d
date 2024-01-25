package engine;

import engine.input.InputManager;
import engine.stateMachine.GameState;
import engine.stateMachine.StateManager;
import engine.window.Window;

public class Main {
	public static final double aspectRatio = 16.0/9.0;
	public static final int WIDTH = 320, HEIGHT = (int)((double)WIDTH/aspectRatio);
	private static Window window;
	private static boolean running = true;
	public static boolean lagging = false;
	public static double currentFPS = 60;

	public static void main(String[] args) {
		final int FPS = 60, DELAY = 1000000000 / FPS;

		window = new Window(WIDTH, HEIGHT);
		InputManager.addInputListeners(window.getFrame());
		StateManager.setCurrentState(new GameState());

		while (running) {
			double startTime = System.nanoTime();// getting the time at the start of the frame

			
			// updating
			StateManager.update();
			window.render();
			InputManager.update();
			
			lagging = true;

			double endTime = System.nanoTime();// the time at the end of the frame
			double delta = endTime - startTime;// how long the frame took
			currentFPS =  1f / (delta / 1000000000f);
			// saying that things are lagging if it took more than 1/60th of a second
			if (delta >= DELAY) {
				lagging = true;
				System.out.println("\nuh oh things are lagging");
				System.out.println("it is " + (delta - DELAY) + " nanoseconds behind");
				System.out.println("FPS is " + currentFPS);

			}
			// stopping the main loop until it has been 1/60th of a second after the start
			// of the frame
			while (delta < (DELAY)) {
				endTime = System.nanoTime();
				delta = endTime - startTime;
				Thread.currentThread().setPriority(2);

			}
			currentFPS =  1f / (delta / 1000000000f);
			Thread.currentThread().setPriority(8);
		}
		// cleanup/end program code
		window.getFrame().dispose();
	}

	public void quitGame() {
		running = false;
	}

	public static Window getWindow() {
		return window;
	}
}
