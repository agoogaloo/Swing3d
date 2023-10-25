package engine.window;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import engine.Main;
import engine.stateMachine.StateManager;

public class Display extends JPanel {
	// idk what this is, but the internet said its important
	private static final long serialVersionUID = 7385492982541933056L;
	/*
	 * this is the display where everything is actually drawn onto the display is
	 * then put onto the window so we can see it extending JPanel so that you have
	 * access to the PaintComponent to actually draw things everything is drawn onto
	 * a display which is then added onto the window, because you aren't supposed to
	 * draw directly onto it
	 */
	private int width, height, scale;

	public Display(int width, int height, int scale) {
		// setting the proper size so that the window will pack properly
		// the display is scaled up to look 8-bit so the
		// resolution is actually 1/3 of the screen width
		this.scale = scale;
		this.width = width;
		this.height = height;

		this.setPreferredSize(new Dimension(width * scale, height * scale));

		// setting the preferred size to the inputed one so that the pack method will
		// work

	}

	@Override
	public void paintComponent(Graphics g) {// where everything is actually drawn
		// all rendering code goes here
		// the image the everything is drawn onto
		g.setColor(StateManager.getBgColour());
		g.fillRect(0, 0, Main.getWindow().getWindowWidth(), Main.getWindow().getWindowHeight());
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		
		// doing the rendering
		StateManager.render(image);

		// putting the image onto the display and scaling it
		g.drawImage(image, Main.getWindow().getxOffset(), Main.getWindow().getyOffset(),
				width * Main.getWindow().getScale(), height * Main.getWindow().getScale(), null);

	}

	public int getRelativeWidth() {
		return width;
	}

	public int getRelativeHeight() {
		return height;
	}

	// getters
	public int getScale() {
		return scale;
	}
}
