package pt.iscte.poo.sokobanstarter;
import pt.iscte.poo.utils.Point2D;

public class Chao extends NotMovable {

    private Point2D Point2D;

    public Chao(Point2D Point2D) {
        this.Point2D = Point2D;
    }

    @Override
    public boolean doesElapse() {
        return false;
    }

    @Override
    public String getName() {
        return "Chao";
    }

    @Override
    public Point2D getPosition() {
        return Point2D;
    }

    @Override
    public int getLayer() {
        return 0;
    }
    
}