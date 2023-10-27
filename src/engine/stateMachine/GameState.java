package engine.stateMachine;

import java.awt.image.BufferedImage;

import engine.rendering.Renderer;
import engine.shapes.Mesh;

public class GameState implements State {
    Renderer renderer;
    Mesh[] meshes;
    double startTime;

    Mesh unitCube = new Mesh(new double[][][] {
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
        startTime = System.currentTimeMillis();
        this.renderer = new Renderer();
    }

    @Override
    public void update() {

    }

    @Override
    public void render(BufferedImage image) {
        double elapsedTime = (System.currentTimeMillis() - startTime)/1000;
        Mesh cube = Mesh.copy(unitCube);
        cube.rotate(new double[] { 25*elapsedTime, 50*elapsedTime, 75*elapsedTime });
        cube.translate(new double[] { 0, 0, 1.5 });

        meshes = new Mesh[] {
            cube
        };

        renderer.render(image, meshes, unitCube.triangles.length*3);
    }

    @Override
    public void end() {
        System.out.println("ending game state");
    }

}
