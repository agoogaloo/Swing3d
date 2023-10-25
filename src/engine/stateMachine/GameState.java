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
    Polygon2D customPolygon = new Polygon2D(new Vector2[] { 
        new Vector2(0, 0), new Vector2(100, 0), 
        new Vector2(100, 100), new Vector2(0, 100) 
    }, new int[][] {
        {0, 1},
        {1, 2},
        {2, 3},
        {3, 0}
    }, Color.red);
    Polygon3D customPolygon3d = new Polygon3D(new Vector3[] { 
        new Vector3(0, 0, 0), new Vector3(100, 0, 0), 
        new Vector3(100, 100, 0), new Vector3(0, 100, 0),
        new Vector3(0, 0, 100), new Vector3(100, 0, 100), 
        new Vector3(100, 100, 100), new Vector3(0, 100, 100)
    }, new int[][] {
        //front face
        {0, 1},
        {1, 2},
        {2, 3},
        {3, 0},
        //back face
        {4, 5},
        {5, 6},
        {6, 7},
        {7, 4},
        //conection
        {0, 4},
        {1, 5},
        {2, 6},
        {3, 7},
    }, Color.blue);

    @Override
    public void start(State prevState) {
        System.out.println("starting game state");
        customPolygon3d.translate(new Vector3(50, 50, 0));
    }

    @Override
    public void update() {

    }

    @Override
    public void render(Graphics2D g) {
        // drawing custom polygon
        customPolygon.draw(g);
        customPolygon3d.draw(g, FOCAL_LENGTH);
    }

    @Override
    public void end() {
        System.out.println("ending game state");
    }

}
