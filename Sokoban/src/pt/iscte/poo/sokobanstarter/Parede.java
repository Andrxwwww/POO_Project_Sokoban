package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;

public class Parede implements ImageTile {

    private Point2D Point2D;

    public Parede(Point2D Point2D) {
        this.Point2D = Point2D;
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
