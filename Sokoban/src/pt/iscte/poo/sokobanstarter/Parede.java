package pt.iscte.poo.sokobanstarter;
import pt.iscte.poo.utils.Point2D;

public class Parede extends Collidable {

    private Point2D Point2D;

    public Parede(Point2D Point2D) {
        this.Point2D = Point2D;
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
    public String getName() {
        return "Parede";
    }

    @Override
    public Point2D getPosition() {
        return Point2D;
    }

    @Override
    public int getLayer() {
        return 3;
    }

    @Override
    public int collidableLevel() {
        return 3;
    }

    @Override
    public Point2D nextPosition(int key) {
        return Point2D;
    }

    
}