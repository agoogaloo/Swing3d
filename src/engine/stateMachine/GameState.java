package engine.stateMachine;

import java.awt.Color;
import java.awt.Graphics2D;

import engine.shapes.Polygon;
import engine.shapes.Vector2;

public class GameState implements State {
    // using java awts polygon class
    java.awt.Polygon javaPolygon = new java.awt.Polygon(new int[] { 100, 85, 120, 130, 130 },
            new int[] { 50, 60, 80, 80, 50 }, 5);
    // using the custom polygon class
    Polygon customPolygon = new Polygon(new Vector2[] { new Vector2(50, 10), new Vector2(50, 30), new Vector2(62, 50),
            new Vector2(80, 30), new Vector2(80, 10) });

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
        customPolygon.draw(g);
        // drawing java polygon
        g.setColor(Color.BLUE);
        g.fill(javaPolygon);
    }

    @Override
    public void end() {
        System.out.println("ending game state");
    }

}
