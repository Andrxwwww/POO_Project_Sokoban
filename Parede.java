package pt.iscte.poo.sokobanstarter;
import pt.iscte.poo.utils.Point2D;

public class Parede extends NotMovable {

    private Point2D Point2D;

    public Parede(Point2D Point2D) {
        this.Point2D = Point2D;
    }

    @Override
    public boolean doesElapse() {
        return true;
    }

    public boolean itsAWall() {
        return true;
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
        return 2;
    }
    
}