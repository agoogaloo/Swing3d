package engine.stateMachine;

import java.awt.image.BufferedImage;

import engine.rendering.Renderer;
import engine.shapes.Mesh;

public class GameState implements State {
    Renderer renderer;
    Mesh[] meshes;

    Mesh cube = new Mesh(new double[][] {
        { 0.0, 0.0, 0.0,    0.0, 1.0, 0.0,    1.0, 1.0, 0.0 },
		{ 0.0, 0.0, 0.0,    1.0, 1.0, 0.0,    1.0, 0.0, 0.0 },

		{ 1.0, 0.0, 0.0,    1.0, 1.0, 0.0,    1.0, 1.0, 1.0 },
		{ 1.0, 0.0, 0.0,    1.0, 1.0, 1.0,    1.0, 0.0, 1.0 },

		{ 1.0, 0.0, 1.0,    1.0, 1.0, 1.0,    0.0, 1.0, 1.0 },
		{ 1.0, 0.0, 1.0,    0.0, 1.0, 1.0,    0.0, 0.0, 1.0 },

		{ 0.0, 0.0, 1.0,    0.0, 1.0, 1.0,    0.0, 1.0, 0.0 },
		{ 0.0, 0.0, 1.0,    0.0, 1.0, 0.0,    0.0, 0.0, 0.0 },

		{ 0.0, 1.0, 0.0,    0.0, 1.0, 1.0,    1.0, 1.0, 1.0 },
		{ 0.0, 1.0, 0.0,    1.0, 1.0, 1.0,    1.0, 1.0, 0.0 },

		{ 1.0, 0.0, 1.0,    0.0, 0.0, 1.0,    0.0, 0.0, 0.0 },
		{ 1.0, 0.0, 1.0,    0.0, 0.0, 0.0,    1.0, 0.0, 0.0 },
    });

    @Override
    public void start(State prevState) {
        meshes = new Mesh[] {
            cube
        };
        this.renderer = new Renderer();
    }

    @Override
    public void update() {

    }

    @Override
    public void render(BufferedImage image) {
        renderer.render(image, meshes, cube.triangles.length*3);
    }

    @Override
    public void end() {
        System.out.println("ending game state");
    }

}
