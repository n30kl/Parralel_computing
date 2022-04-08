package bounceThread;

import java.awt.geom.*;

public class Pool {
    private final int x;
    private final int y;
    private static final int RADIUS = 45;

    public Pool(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getCenterX() {
        return x + RADIUS;
    }

    public int getCenterY() {
        return y + RADIUS;
    }

    public int getY() {
        return y;
    }

    public static int getRadius() {
        return RADIUS;
    }

    public Ellipse2D getShape()
    {
        return new Ellipse2D.Double(x, y, 2*RADIUS, 2*RADIUS);
    }

}
