package engine.stateMachine;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import engine.rendering.GameObject;
import engine.rendering.Renderer;
import engine.shapes.Matrix;
import engine.shapes.Mesh;
import engine.shapes.Vector;
import engine.shapes.Vector3;

public class GameState implements State {
    Renderer renderer;
    Mesh[] meshes;
    double startTime;
    double[] cameraPosition = new double[] { 0, 0, 0 };
    double[] cameraDirection = new double[] { 0, 0, 1 };

    Mesh unitCube = new Mesh(new double[][][] {
        {{ 0.0, 0.0, 0.0 }, { 1.0, 1.0, 0.0 }, { 0.0, 1.0, 0.0 }},
		{{ 0.0, 0.0, 0.0 }, { 1.0, 0.0, 0.0 }, { 1.0, 1.0, 0.0 }},

		{{ 1.0, 0.0, 0.0 }, { 1.0, 1.0, 1.0 }, { 1.0, 1.0, 0.0 }},
		{{ 1.0, 0.0, 0.0 }, { 1.0, 0.0, 1.0 }, { 1.0, 1.0, 1.0 }},

		{{ 1.0, 0.0, 1.0 }, { 0.0, 1.0, 1.0 }, { 1.0, 1.0, 1.0 }},
		{{ 1.0, 0.0, 1.0 }, { 0.0, 0.0, 1.0 }, { 0.0, 1.0, 1.0 }},

		{{ 0.0, 0.0, 1.0 }, { 0.0, 1.0, 0.0 }, { 0.0, 1.0, 1.0 }},
		{{ 0.0, 0.0, 1.0 }, { 0.0, 0.0, 0.0 }, { 0.0, 1.0, 0.0 }},

		{{ 0.0, 1.0, 0.0 }, { 1.0, 1.0, 1.0 }, { 0.0, 1.0, 1.0 }},
		{{ 0.0, 1.0, 0.0 }, { 1.0, 1.0, 0.0 }, { 1.0, 1.0, 1.0 }},

		{{ 1.0, 0.0, 1.0 }, { 0.0, 0.0, 0.0 }, { 0.0, 0.0, 1.0 }},
		{{ 1.0, 0.0, 1.0 }, { 1.0, 0.0, 0.0 }, { 0.0, 0.0, 0.0 }},
    }, new double[][] {
        { 1, 1, 0, 0 }, { 1, 1, 0, 0 },
        { 1, 0, 1, 0 }, { 1, 0, 1, 0 },
        { 1, 1, 0, 0 }, { 1, 1, 0, 0 },
        { 1, 0, 1, 0 }, { 1, 0, 1, 0 },
        { 1, 0, 0, 1 }, { 1, 0, 0, 1 },
        { 1, 0, 0, 1 }, { 1, 0, 0, 1 },
    });

    Mesh shipMesh, cube, axisMesh;

    GameObject cubeObject, ground;

    @Override
    public void start(State prevState) {
        startTime = System.currentTimeMillis();
        cube = Mesh.copy(unitCube);
        shipMesh = loadObjectFromFile("VideoShip.obj");
        axisMesh = loadObjectFromFile("axis.obj");

        cubeObject = new GameObject(cube);
        ground = new GameObject(cube);

        cubeObject.transform.translate(new Vector3(0, -2, 2));
        cubeObject.velocity = new Vector3(0, 0.05, 0);
        
        ground.transform.translate(new Vector3(0, 1, 4));
        ground.transform.setScale(new Vector3(10, 1, 10));
        ground.transform.rotate(new Vector3(0, 0, 0));

        this.renderer = new Renderer();
    }

    @Override
    public void update() {
        double elapsedTime = (System.currentTimeMillis() - startTime)/1000;

        // cubeObject.transform.rotate(new Vector3(1, 1, 1));
        // ground.transform.rotate(new Vector3(1, 1, 1));

        cubeObject.update();
        ground.update();
    }

    @Override
    public void render(BufferedImage image) {
        meshes = new Mesh[] {
            cubeObject.getWorldMesh(),
            ground.getWorldMesh()
        };

        renderer.render(image, meshes, cameraPosition, cameraDirection);
    }

    public void cameraForward(double amount) {
        double[] forwardVector = Vector.scalarMultiple(cameraDirection, amount);
        cameraPosition = Vector.add(cameraPosition, forwardVector);
    }

    public void rotateCamera(double angle) {
        double[][] rotationMatrix = Matrix.makeRotationMatrixY(angle);
        cameraDirection = Matrix.multiplyVectorMatrix344(cameraDirection, rotationMatrix);
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
