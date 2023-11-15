package pt.iscte.poo.sokobanstarter;
import pt.iscte.poo.utils.Point2D;

public class Alvo extends NotMovable {

    private Point2D Point2D;

    public Alvo(Point2D Point2D) {
        this.Point2D = Point2D;
    }

    @Override
    public boolean doesElapse() {
        return false;
    }

    @Override
    public String getName() {
        return "Alvo";
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