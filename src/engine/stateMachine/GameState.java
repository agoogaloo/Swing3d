package engine.stateMachine;

import java.awt.image.BufferedImage;

import engine.rendering.Renderer;
import engine.shapes.Mesh;

public class GameState implements State {
    Renderer renderer;
    Mesh[] meshes;

    Mesh cube = new Mesh(new double[][][] {
        {{ 0.0, 0.0, 0.0 }, { 0.0, 1.0, 0.0 }, { 1.0, 1.0, 0.0 }},
		{{ 0.0, 0.0, 0.0 }, { 1.0, 1.0, 0.0 }, { 1.0, 0.0, 0.0 }},

		{{ 1.0, 0.0, 0.0 }, { 1.0, 1.0, 0.0 }, { 1.0, 1.0, 1.0 }},
		{{ 1.0, 0.0, 0.0 }, { 1.0, 1.0, 1.0 }, { 1.0, 0.0, 1.0 }},

		{{ 1.0, 0.0, 1.0 }, { 1.0, 1.0, 1.0 }, { 0.0, 1.0, 1.0 }},
		{{ 1.0, 0.0, 1.0 }, { 0.0, 1.0, 1.0 }, { 0.0, 0.0, 1.0 }},

		{{ 0.0, 0.0, 1.0 }, { 0.0, 1.0, 1.0 }, { 0.0, 1.0, 0.0 }},
		{{ 0.0, 0.0, 1.0 }, { 0.0, 1.0, 0.0 }, { 0.0, 0.0, 0.0 }},

		{{ 0.0, 1.0, 0.0 }, { 0.0, 1.0, 1.0 }, { 1.0, 1.0, 1.0 }},
		{{ 0.0, 1.0, 0.0 }, { 1.0, 1.0, 1.0 }, { 1.0, 1.0, 0.0 }},

		{{ 1.0, 0.0, 1.0 }, { 0.0, 0.0, 1.0 }, { 0.0, 0.0, 0.0 }},
		{{ 1.0, 0.0, 1.0 }, { 0.0, 0.0, 0.0 }, { 1.0, 0.0, 0.0 }},
    });

    @Override
    public void start(State prevState) {
        cube.translate(new double[] { 0, 0, 1.5 });
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
        //cube.translate(new double[] { 0.1, 0, 0.1 });
        renderer.render(image, meshes, cube.triangles.length*3);
    }

    @Override
    public void end() {
        System.out.println("ending game state");
    }

}
