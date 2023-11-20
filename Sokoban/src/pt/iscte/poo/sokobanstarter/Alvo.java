package pt.iscte.poo.sokobanstarter;
import pt.iscte.poo.utils.Point2D;

public class Alvo extends NotCollidable {

    private Point2D Point2D;

    public Alvo(Point2D point2d) {
        this.Point2D = point2d;
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
        return 1;
    }
    
}