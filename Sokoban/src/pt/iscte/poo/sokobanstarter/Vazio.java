package pt.iscte.poo.sokobanstarter;
import pt.iscte.poo.utils.Point2D;

public class Vazio extends NotCollidable {

    private Point2D Point2D;

    public Vazio(Point2D Point2D) {
        this.Point2D = Point2D;
    }

    @Override
    public String getName() {
        return "Vazio";
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