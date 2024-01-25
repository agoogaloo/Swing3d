package engine.stateMachine;

import java.awt.image.BufferedImage;

import engine.Debug;
import engine.rendering.*;
import engine.rendering.Components.Custom.ShaderManager;
import engine.rendering.Components.Custom.Startup;
import engine.rendering.Components.Custom.TempText;

public class GameState implements State {
    Renderer renderer;
    
    GameObject cubeObject, ground;
    
    @Override
    public void start(State prevState) {
        Scene.mainCamera = new Camera();

        this.renderer = new Renderer();
        Scene.setRenderer(renderer);

        Scene.addScript(new Startup());
        Scene.addScript(new ShaderManager());
        Scene.addScript(new TempText());

        Scene.start();
    }

    @Override
    public void update() {
        Scene.update();
    }
    
    @Override
    public void render(BufferedImage image) {
        Scene.preRender();

        renderer.render(image, Scene.meshes, Scene.UI.text, Scene.mainCamera.cameraPosition, Scene.mainCamera.cameraDirection);
        Debug.clearPoints();
    }

    @Override
    public void end() {
        System.out.println("ending game state");
    }

}
