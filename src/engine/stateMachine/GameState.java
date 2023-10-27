package engine.stateMachine;

import java.awt.image.BufferedImage;

import engine.rendering.Renderer;

public class GameState implements State {
    Renderer renderer;

    @Override
    public void start(State prevState) {
        this.renderer = new Renderer();
    }

    @Override
    public void update() {

    }

    @Override
    public void render(BufferedImage image) {
        renderer.render(image);
    }

    @Override
    public void end() {
        System.out.println("ending game state");
    }

}
