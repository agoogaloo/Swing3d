package engine.stateMachine;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import engine.CollisionData;
import engine.Debug;
import engine.input.InputManager;
import engine.input.Keybind;
import engine.rendering.*;
import engine.rendering.Components.*;
import engine.shapes.*;

public class GameState implements State {
    Renderer renderer;
    double startTime;
    double[] cameraAngle = new double[] { 0, 0, 0 };
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

        cubeObject.addComponent(new Rigidbody());

        Rigidbody rb = (Rigidbody)cubeObject.getComponent(Rigidbody.class);
        rb.velocity = new Vector3(0.0, -0.01, 0);

        cubeObject.transform.translate(new Vector3(0, -2, 2));
        cubeObject.transform.rotate(new Vector3(0, 45, 45));
        cubeObject.transform.setScale(new Vector3(0.5, 0.5, 0.5));
        
        ground.transform.translate(new Vector3(0, 1, 4));
        ground.transform.setScale(new Vector3(10, 1, 10));
        ground.transform.rotate(new Vector3(0, 0, 0));

        this.renderer = new Renderer();

        CollisionData.meshes = new Mesh[] {
            cubeObject.getWorldMesh(),
            ground.getWorldMesh()
        };
    }

    @Override
    public void update() {
        double elapsedTime = (System.currentTimeMillis() - startTime)/1000;

        // cubeObject.transform.rotate(new Vector3(1, 1, 1));
        // ground.transform.rotate(new Vector3(1, 1, 1));
        
            
        Rigidbody rb = (Rigidbody)cubeObject.getComponent(Rigidbody.class);
        rb.velocity.y+=0.004;
        
        // if (InputManager.pressed(Keybind.JUMP)){
        //     System.out.println("jump!");
        //     rb.velocity.y-=0.3;
        // }
        double velX = 0, velY = 0;
        Vector2 mouseSpeed = InputManager.mouseSpeed();
        if(InputManager.held(Keybind.FORWARD)) {
            velX = 0.1;
        }
        if(InputManager.held(Keybind.BACK)) {
            velX = -0.1;
        }
        if(InputManager.held(Keybind.LEFT)) {
            velY = -0.1;
        }
        if(InputManager.held(Keybind.RIGHT)) {
            velY = 0.1;
        }
        if(InputManager.held(Keybind.JUMP)) {
            cameraPosition[1] += 0.1;
        }
        if(InputManager.held(Keybind.DOWN)) {
            cameraPosition[1] -= 0.1;
        }
        if(!mouseSpeed.zero()) {
            rotateCamera(new double[] { -mouseSpeed.x, -mouseSpeed.y, 0 });
        }
        if(velX != 0 || velY != 0) {
            cameraMove(velX, velY);
        }

        cubeObject.update();
        ground.update();
    }

    @Override
    public void render(BufferedImage image) {
        CollisionData.meshes = new Mesh[] {
            cubeObject.getWorldMesh(),
            ground.getWorldMesh()
        };

        renderer.render(image, CollisionData.meshes, cameraPosition, cameraDirection);
        Debug.clearPoints();
    }

    public void cameraForward(double amount) {
        double[] forwardVector = Vector.scalarMultiple(cameraDirection, amount);
        cameraPosition = Vector.add(cameraPosition, forwardVector);
    }

    public void cameraMove(double x, double y) {
        double angle = cameraAngle[0]*3.14159/180;
        double xDist = Math.cos(-angle) * x + Math.sin(angle) * y;
        double yDist = Math.sin(-angle) * x + Math.cos(angle) * y;

        cameraPosition[2] += xDist;
        cameraPosition[0] += yDist;
    }

    public void rotateCamera(double[] angle) {
        cameraAngle = Vector.add(cameraAngle, angle);
        if(cameraAngle[0] >= -90 && cameraAngle[0] <= 90 && cameraAngle[1] >= -90 && cameraAngle[1] <= 90) {
            double[][] rotationMatrix = Matrix.makeRotationMatrixY(angle[0]);
            cameraDirection = Matrix.multiplyVectorMatrix344(cameraDirection, rotationMatrix);
            rotationMatrix = Matrix.makeRotationMatrixX(angle[1]);
            cameraDirection = Matrix.multiplyVectorMatrix344(cameraDirection, rotationMatrix);
        } else {
            cameraAngle = Vector.subtract(cameraAngle, angle);
        }
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
