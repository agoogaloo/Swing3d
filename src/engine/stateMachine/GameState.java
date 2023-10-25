package engine.stateMachine;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import engine.shapes.Polygon2D;
import engine.shapes.Polygon3D;
import engine.shapes.Vector2;
import engine.shapes.Vector3;

public class GameState implements State {
    int FOCAL_LENGTH = 200;
    Polygon3D[] polygons;
    
    Polygon2D customPolygon = new Polygon2D(new int[][] { 
        {0, 0}, {100, 0}, {100, 100}, {0, 100} 
    }, new int[][] {
        {0, 1}, {1, 2}, {2, 3}, {3, 0}
    }, Color.red);

    Polygon3D cube = new Polygon3D(new int[][] { 
        {0, 0, 0}, {100, 0, 0}, {100, 100, 0}, {0, 100, 0},
        {0, 0, 100}, {100, 0, 100}, {100, 100, 100}, {0, 100, 100}
    }, new int[][] {
        {0, 1}, {1, 2}, {2, 3}, {3, 0}, //front face
        {4, 5}, {5, 6}, {6, 7}, {7, 4}, //back face
        {0, 4}, {1, 5}, {2, 6}, {3, 7}, //conection 
    }, Color.blue);
    Polygon3D pyramid = new Polygon3D(new int[][] { 
        {0, 100, 0}, {100, 100, 0}, {100, 100, 100}, {0, 100, 100},
        {50, 0, 50}
    }, new int[][] {
        {0, 1}, {1, 2}, {2, 3}, {3, 0}, //front face
        {0, 4}, {1, 4}, {2, 4}, {3, 4}, //conection 
    }, Color.yellow);

    @Override
    public void start(State prevState) {
        System.out.println("starting game state");
        cube.translate(new Vector3(50, 50, 0));
        pyramid.translate(new Vector3(200, 0, 0));

        polygons = new Polygon3D[] {
            cube, pyramid
        };
    }

    @Override
    public void update() {

    }

    @Override
    public void render(BufferedImage image) {
        // drawing custom polygon
        Graphics2D g2d = (Graphics2D) image.getGraphics();
		g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        
        drawPolygons(image, polygons);
        //image.setRGB(0, 0, getIntFromColor(255, 255, 0, 0));
    }

    void drawPolygons(BufferedImage image, Polygon3D[] polygons) {
        for (int i = 0; i < polygons.length; i++) {
            Polygon3D polygon = polygons[i];
            Vector3[] vertices = polygon.getVertices();
            Vector2[] projectedVertices = new Vector2[vertices.length];
            for (int j = 0; j < vertices.length; j++) {
                projectedVertices[j] = vertices[j].getProjected(FOCAL_LENGTH);
            }
            
            Color color = polygon.getColor();
            for (Vector2 vertex : projectedVertices) {
                image.setRGB(vertex.x, vertex.y, color.getRGB());
                image.setRGB(vertex.x+1, vertex.y, color.getRGB());
                image.setRGB(vertex.x, vertex.y+1, color.getRGB());
                image.setRGB(vertex.x+1, vertex.y+1, color.getRGB());
            }

            for (int[] edge : polygon.getEdges()) {
                Vector2 v1 = projectedVertices[edge[0]];
                Vector2 v2 = projectedVertices[edge[1]];
            }
        }
    }

    public int getIntFromColor(int alpha, int red, int green, int blue){
        alpha = (alpha << 24) & 0xFF000000; //Shift red 16-bits and mask out other stuff
        red = (red << 16) & 0x00FF0000; //Shift red 16-bits and mask out other stuff
        green = (green << 8) & 0x0000FF00; //Shift Green 8-bits and mask out other stuff
        blue = blue & 0x000000FF; //Mask out anything not blue.
    
        return alpha | red | green | blue; //0xFF000000 for 100% Alpha. Bitwise OR everything together.
    }

    @Override
    public void end() {
        System.out.println("ending game state");
    }

}
