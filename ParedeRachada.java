package pt.iscte.poo.sokobanstarter;
import pt.iscte.poo.utils.Point2D;

public class ParedeRachada extends NotMovable {

    private Point2D Point2D;

    public ParedeRachada(Point2D Point2D) {
        this.Point2D = Point2D;
    }

    @Override
    public boolean doesElapse() {
        // add a function ??
        return true;
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
    
}