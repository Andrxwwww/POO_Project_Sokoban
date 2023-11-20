package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.utils.Point2D;

public class Martelo extends Item {

    private Point2D position;

    public Martelo(Point2D position) {
        this.position = position;
    }

    @Override
    public String getName() {
        return "Martelo";
    }

    @Override
    public int getLayer() {
        return 1;
    }

    @Override
    public Point2D getPosition() {
        return position;
    }

    
}
