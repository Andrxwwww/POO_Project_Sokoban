package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.utils.Point2D;  

public class Bateria extends GameElement{

    public Bateria(Point2D position) {
        super(position);
    }

    @Override
    public String getName() {
        return "Bateria";
    }

    @Override
    public int getLayer() {
        return 1;
    }


}
