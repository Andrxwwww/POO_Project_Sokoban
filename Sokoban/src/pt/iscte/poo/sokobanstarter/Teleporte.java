package pt.iscte.poo.sokobanstarter;
import pt.iscte.poo.utils.Point2D;

public class Teleporte extends NotMovable {

    private Point2D Point2D;

    public Teleporte(Point2D Point2D) {
        this.Point2D = Point2D;
    }

    @Override
    public boolean doesElapse() {
        return false;
    }

    @Override
    public String getName() {
        return "Teleporte";
    }

    @Override
    public Point2D getPosition() {
        return Point2D;
    }

    @Override
    public int getLayer() {
        return 3;
    }
    
}