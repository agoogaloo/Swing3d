package engine.stateMachine;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import engine.CollisionData;
import engine.Debug;
import engine.rendering.*;
import engine.rendering.Components.Custom.ShaderManager;
import engine.rendering.Components.Custom.Startup;
import engine.rendering.UI.TextObject;
import engine.shapes.*;

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

    public Mesh loadObjectFromFile(String fileName) {
        try {
            File file = new File("./src/engine/obj/" + fileName);
            Scanner fileReader = new Scanner(file);

            ArrayList<double[][]> triangles = new ArrayList<double[][]>();
            ArrayList<double[]> vertices = new ArrayList<double[]>();
            while (fileReader.hasNextLine()) {
                String data = fileReader.nextLine();
                String[] line = data.split(" ");
                if(line[0].equals("v")) {
                    vertices.add(new double[] {
                        Double.parseDouble(line[2]),
                        Double.parseDouble(line[1]),
                        Double.parseDouble(line[3])
                    });
                }
                if(line[0].equals("f")) {
                    int v1 = Integer.parseInt(line[1])-1;
                    int v2 = Integer.parseInt(line[2])-1;
                    int v3 = Integer.parseInt(line[3])-1;
                    triangles.add(new double[][] {
                        vertices.get(v1),
                        vertices.get(v2),
                        vertices.get(v3)
                    });
                } 
            }
            double[][][] trianglesArray = new double[triangles.size()][3][3];
            double[][] colors = new double[triangles.size()][4];
            for (int i = 0; i < trianglesArray.length; i++) {
                colors[i] = new double[] { 1, 1, 1, 1 };
                trianglesArray[i] = triangles.get(i);
            }
            fileReader.close();
            return new Mesh(trianglesArray, colors);
        } catch (FileNotFoundException e) {
            System.out.println("File could not be found");
            e.printStackTrace();

            return new Mesh(new double[][][] {
                {{ 0.0, 0.0, 0.0 }, { 0.0, 1.0, 0.0 }, { 1.0, 1.0, 0.0 }}
            }, new double[][] {
                { 1, 1, 0, 0 }
            });
        }
    }

    @Override
    public void end() {
        System.out.println("ending game state");
    }

}
