package engine.stateMachine;

import java.awt.Color;
import java.awt.Graphics2D;

import engine.shapes.Polygon2D;
import engine.shapes.Polygon3D;
import engine.shapes.Vector2;
import engine.shapes.Vector3;

public class GameState implements State {
    // using the custom polygon class

    //A B    A B
    //C D -> D C
    double FOCAL_LENGTH = 200;
    Polygon2D customPolygon = new Polygon2D(new Vector2[] { new Vector2(0, 0), new Vector2(100, 0), new Vector2(100, 100), new Vector2(0, 100) }, Color.red);
    Polygon3D face1 = new Polygon3D(new Vector3[] { 
        new Vector3(100, 50, 0), new Vector3(200, 50, 0), 
        new Vector3(200, 150, 0), new Vector3(100, 150, 0) 
    }, Color.red);
    Polygon3D face2 = new Polygon3D(new Vector3[] { 
        new Vector3(100, 50, 100), new Vector3(100, 50, 0), 
        new Vector3(100, 150, 0), new Vector3(100, 150, 100) 
    }, Color.blue);
    Polygon3D face3 = new Polygon3D(new Vector3[] { 
        new Vector3(100, 50, 100), new Vector3(200, 50, 100), 
        new Vector3(200, 50, 0), new Vector3(100, 50, 0)
    }, Color.green);

    @Override
    public void start(State prevState) {
        System.out.println("starting game state");
    }

    @Override
    public void update() {

    }

    @Override
    public void render(Graphics2D g) {
        // drawing custom polygon
        face2.draw(g, FOCAL_LENGTH);
        face3.draw(g, FOCAL_LENGTH);
        face1.draw(g, FOCAL_LENGTH);
    }

    @Override
    public void end() {
        System.out.println("ending game state");
    }

}
