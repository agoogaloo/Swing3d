package engine.stateMachine;

import java.awt.Color;
import java.awt.image.BufferedImage;

import engine.rendering.Renderer;
import engine.shapes.Polygon2D;
import engine.shapes.Polygon3D;
import engine.shapes.Vector2;
import engine.shapes.Vector3;

public class GameState implements State {
    int FOCAL_LENGTH = 200;
    Polygon3D[] polygons;
    Renderer renderer;
    
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

        this.renderer = new Renderer();
    }

    @Override
    public void update() {

    }

    @Override
    public void render(BufferedImage image) {
        // drawing custom polygon
        renderer.render(image);
        for(int x = 0; x < image.getWidth(); x++) {
            for(int y = 0; y < image.getHeight(); y++) {
                int rgb = image.getRGB(x, y);
                int[] color = getColorFromInt(rgb);
                //System.out.println(String.format("a: %d, r: %d, g: %d, b: %d,", color[0], color[1], color[2], color[3]));
            }
        }

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

            int[][] edges = polygon.getEdges();
            for (int j = 0; j < edges.length; j++) {
                Vector2 v1 = new Vector2(projectedVertices[edges[j][0]]);
                Vector2 v0 = new Vector2(projectedVertices[edges[j][1]]);
                //System.out.println(String.format("j: %d, v0: %d, %d, v1: %d, %d, e: %d,%d", j, v0.x, v0.y, v1.x, v1.y, edges[j][0], edges[j][1]));
                // Vector2 v0 = new Vector2(0, 100);
                // Vector2 v1 = new Vector2(0, 0);

                //implementation of Bresenham's line drawing algorithm
                int dx = Math.abs(v1.x - v0.x);
                int sx = v0.x < v1.x ? 1 : -1;
                int dy = -Math.abs(v1.y - v0.y);
                int sy = v0.y < v1.y ? 1 : -1;
                int error = dx + dy;
                
                while(true) {
                    image.setRGB(v0.x, v0.y, color.getRGB());
                    if(v0.x == v1.x && v0.y == v1.y) break;
                    int e2 = 2 * error;
                    if(e2 >= dy) {
                        if(v0.x == v1.x) break;
                        error = error + dy;
                        v0.x = v0.x + sx;
                    }
                    if(e2 <= dx) {
                        if(v0.y == v1.y) break;
                        error = error + dx;
                        v0.y = v0.y + sy;
                    }
                }
            }
        }
    }

    public int[] getColorFromInt(int rgb){
        int alpha = (rgb >> 24) & 0xFF; //Shift red 16-bits and mask out other stuff
        int red = (rgb >> 16) & 0xFF; //Shift red 16-bits and mask out other stuff
        int green = (rgb >> 8) & 0xFF; //Shift Green 8-bits and mask out other stuff
        int blue = rgb & 0xFF; //Mask out anything not blue.
    
        return new int[] { alpha, red, green, blue }; //0xFF000000 for 100% Alpha. Bitwise OR everything together.
    }

    @Override
    public void end() {
        System.out.println("ending game state");
    }

}
