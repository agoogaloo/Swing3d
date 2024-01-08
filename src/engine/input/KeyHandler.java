package engine.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;

public class KeyHandler implements KeyListener {
    private HashMap<Keybind, ButtonInput> keyInputs;
    private HashMap<Keybind, Integer> keyCodes;

    public KeyHandler(HashMap<Keybind, ButtonInput> keyInputs, HashMap<Keybind, Integer> keyCodes) {
        this.keyInputs = keyInputs;
        this.keyCodes = keyCodes;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        for (Keybind keybind : Keybind.values()) {
			if(e.getKeyCode() == keyCodes.get(keybind)) {
				keyInputs.get(keybind).press();
			}
		}
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
        for (Keybind keybind : Keybind.values()) {
            if(e.getKeyCode() == keyCodes.get(keybind)) {
                keyInputs.get(keybind).release();
            }
        }
    }
    
    public void updateKeys() {
        for (Keybind keybind : Keybind.values()) {
            keyInputs.get(keybind).update();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

}
