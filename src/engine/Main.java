package engine;

import engine.input.InputManager;
import engine.stateMachine.GameState;
import engine.stateMachine.StateManager;
import engine.window.Window;

public class Main {
	public static final double aspectRatio = 16.0/9.0;
	public static final int WIDTH = 160, HEIGHT = (int)((double)WIDTH/aspectRatio);
	private static Window window;
	private static boolean running = true;

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
			

			double endTime = System.nanoTime();// the time at the end of the frame
			double delta = endTime - startTime;// how long the frame took
			// saying that things are lagging if it took more than 1/60th of a second
			if (delta >= DELAY) {

				System.out.println("\nuh oh things are lagging");
				System.out.println("it is " + (delta - DELAY) + " nanoseconds behind");
				System.out.println("FPS is " + 1f / (delta / 1000000000f));

			}
			// stopping the main loop until it has been 1/60th of a second after the start
			// of the frame
			while (delta < (DELAY)) {
				endTime = System.nanoTime();
				delta = endTime - startTime;
				Thread.currentThread().setPriority(2);

			}
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
