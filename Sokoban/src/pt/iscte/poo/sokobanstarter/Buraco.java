package pt.iscte.poo.sokobanstarter;
import pt.iscte.poo.utils.Point2D;

public class Buraco extends NotMovable {

    private Point2D Point2D;

    public Buraco(Point2D Point2D) {
        this.Point2D = Point2D;
    }

    @Override
    public boolean doesElapse() {
        return false;
    }

    @Override
    public String getName() {
        return "Buraco";
    }

    @Override
    public Point2D getPosition() {
        return Point2D;
    }

    @Override
    public int getLayer() {
        // se meter uma palete mais tarde a layer tem de mudar
        return 2;
    }
    
}