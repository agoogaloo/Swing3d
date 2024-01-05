package engine.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;

public class KeyHandler implements KeyListener {
    private HashMap<Integer, ButtonInput> keyInputs;

    public KeyHandler(ButtonInput forward, ButtonInput back, ButtonInput left, ButtonInput right, ButtonInput jump) {
        keyInputs = new HashMap<Integer, ButtonInput>();
        keyInputs.put(87, forward);
        keyInputs.put(83, back);
        keyInputs.put(65, left);
        keyInputs.put(68, right);
        keyInputs.put(32, jump);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // System.out.println("pressed " + String.valueOf(e.getKeyCode()));
        if (keyInputs.containsKey(e.getKeyCode())) {
            keyInputs.get(e.getKeyCode()).press();
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        // System.out.println("released " + String.valueOf(e.getKeyCode()));
        if (keyInputs.containsKey(e.getKeyCode())) {
            keyInputs.get(e.getKeyCode()).release();
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

}
