package engine.window;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import engine.Main;

/*
 * this class represents the window that the game is played on and uses java
 * swing not javaFX
 */
/* 
 * by: Matthew Milum
 */
public class Window {
	/*
	 * this class represents the window that the game is played on and uses java
	 * swing for some reason
	 */
	public static final int WIDTHOFFSET = 0, HEIGHTOFFSET = 39; //window header is ~39 pixels and for some reason is included in the height of the window
	private JFrame frame;
	private Display display;
	private int windowWidth, windowHeight;
	private double scale=5, xOffset, yOffset;

	ComponentAdapter screenResize = new ComponentAdapter() {
		public void componentResized(ComponentEvent evt) {
			Component c = (Component) evt.getSource();
			windowWidth = c.getWidth() - WIDTHOFFSET;
			windowHeight = c.getHeight() - HEIGHTOFFSET;

			double scaleX = (double)windowWidth/(double)Main.WIDTH;
			double scaleY = (double)windowHeight/(double)Main.HEIGHT;
			if(scaleX <= scaleY) {
				scale = scaleX;
			} else {
				scale = scaleY;
			}

			xOffset = (windowWidth - Main.WIDTH * scale) / 2;
			yOffset = (windowHeight - Main.HEIGHT * scale) / 2;
			//System.out.println(scale + " - " + xOffset + ", " + yOffset);
		}

	};

	public Window(int width, int height) {
		// initializing variables

		frame = new JFrame();

		// frame.setResizable(false);// not letting you resize the window so it doesn't
		// mess things up when rendering
		display = new Display(width, height, scale);// making the display
		frame.setTitle("Super Jumping on Abstract Shapes in the Void to Reach an Unkown Position 64");
		frame.setCursor(frame.getToolkit().createCustomCursor(
			new BufferedImage( 1, 1, BufferedImage.TYPE_INT_ARGB ),
			new Point(),
			null ) );
		frame.add(display);// adding the display to the window so it can actually show it
		frame.pack();// making the window fit the panel perfectly
		frame.setLocationRelativeTo(null);// centers the window
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// makes the program stop when you close the window
		// frame.addKeyListener(new Inputs());
		frame.addComponentListener(screenResize);

		frame.setVisible(true);// making the window visible

	}

	public void render() {
		display.repaint();
	}

	// getters/setters
	public Display getDisplay() {
		return display;
	}

	public JFrame getFrame() {
		return frame;
	}

	public double getScale() {
		return scale;
	}

	public double getxOffset() {
		return xOffset;
	}

	public double getyOffset() {
		return yOffset;
	}

	public int getWindowWidth() {
		return windowWidth;
	}

	public int getWindowHeight() {
		return windowHeight;
	}

}
