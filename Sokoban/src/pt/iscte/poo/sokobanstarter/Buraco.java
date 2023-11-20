package pt.iscte.poo.sokobanstarter;
import pt.iscte.poo.utils.Point2D;

public class Buraco extends NotCollidable {

    private Point2D position;

    public Buraco(Point2D point2d) {
        this.position = point2d;
    }

    @Override
    public String getName() {
        return "Buraco";
    }

    @Override
    public Point2D getPosition() {
        return position;
    }

    @Override
    public int getLayer() {
        // se meter uma palete mais tarde a layer tem de mudar
        return 1;
    }
    
}