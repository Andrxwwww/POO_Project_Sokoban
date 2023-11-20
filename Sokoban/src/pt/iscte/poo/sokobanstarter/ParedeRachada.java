package pt.iscte.poo.sokobanstarter;
import pt.iscte.poo.utils.Point2D;

public class ParedeRachada extends Collidable {

    private Point2D Point2D;

    public ParedeRachada(Point2D Point2D) {
        this.Point2D = Point2D;
    }

    @Override
    public String getName() {
        return "ParedeRachada";
    }

    @Override
    public Point2D getPosition() {
        return Point2D;
    }

    @Override
    public int getLayer() {
        return 2;
    }

    @Override
    public boolean isAWall() {
        return true;
    }

    @Override
    public boolean isMovable() {
        return false;
    }

    @Override
    public Point2D nextPosition(int key) {
        return Point2D;
    }

    @Override
    public int collidableLevel() {
        return 2;
    }
    
}