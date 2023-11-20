package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.utils.Point2D;  

public class Bateria extends Item{

    private Point2D position;

    public Bateria(Point2D position) {
        this.position = position;
    }


    @Override
    public String getName() {
        return "Bateria";
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
