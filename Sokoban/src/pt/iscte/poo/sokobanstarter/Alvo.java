package pt.iscte.poo.sokobanstarter;
import pt.iscte.poo.utils.Point2D;

public class Alvo extends NotCollidable {

    private Point2D Point2D;
    private boolean rightSpot;

    public Alvo(Point2D point2d , boolean rightSpot) {
        this.Point2D = point2d;
        this.rightSpot = rightSpot;
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

    public boolean getRightSpot() {
        return rightSpot;
    }

    public void setRightSpot(boolean rightSpot) {
        this.rightSpot = rightSpot;
    }

    
}