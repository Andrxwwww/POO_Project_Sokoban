package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.utils.Point2D;

public class Martelo extends GameElement {

    public Martelo(Point2D position) {
        super(position);
    }

    @Override
    public String getName() {
        return "Martelo";
    }

    @Override
    public int getLayer() {
        return 1;
    }
    
}
