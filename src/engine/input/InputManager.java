package engine.input;

import javax.swing.JFrame;

public class InputManager {
    public static ButtonInput foreward = new ButtonInput(), back = new ButtonInput(), left = new ButtonInput(),
            right = new ButtonInput(), jump = new ButtonInput();
    private static KeyHandler keyHandler = new KeyHandler(foreward, back, left, right, jump);

    public static void addInputListeners(JFrame jFrame) {
        jFrame.addKeyListener(keyHandler);
    }

    public static void update(){
        foreward.update();
        back.update();
        left.update();
        right.update();
        jump.update();

    }

}
