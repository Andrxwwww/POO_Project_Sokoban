package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.utils.Point2D;

public class Martelo extends GameElement implements Interactable {

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

    @Override
    public void interactWith(GameElement ge) {
        // TODO Auto-generated method stub   
    }
    
}
