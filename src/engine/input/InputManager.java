package engine.input;

import java.util.HashMap;

import javax.swing.JFrame;

import engine.shapes.Vector2;

public class InputManager {
    private static HashMap<Keybind, ButtonInput> keyInputs;
    private static HashMap<Keybind, Integer> keyCodes;

    private static KeyHandler keyHandler;
    private static MouseMotionHandler mouseMotionHandler;

    public static void addInputListeners(JFrame jFrame) {
        keyInputs = new HashMap<Keybind, ButtonInput>();
        keyCodes = new HashMap<Keybind, Integer>();

        for (Keybind keybind : Keybind.values()) {
            keyInputs.put(keybind, new ButtonInput());
            keyCodes.put(keybind, keybind.default_bind);
		}

        keyHandler = new KeyHandler(keyInputs, keyCodes);
        mouseMotionHandler = new MouseMotionHandler();
        jFrame.addKeyListener(keyHandler);
    }

    public static void update(){
        mouseMotionHandler.update();
        keyHandler.updateKeys();
    }

    public static boolean pressed(Keybind keybind) {
        return keyInputs.get(keybind).pressed;
    }
    public static boolean released(Keybind keybind) {
        return keyInputs.get(keybind).released;
    }
    public static boolean held(Keybind keybind) {
        return keyInputs.get(keybind).held;
    }

    public static Vector2 mousePosition() {
        return mouseMotionHandler.position();
    }
    public static Vector2 mouseSpeed() {
        return mouseMotionHandler.speed();
    }
}
