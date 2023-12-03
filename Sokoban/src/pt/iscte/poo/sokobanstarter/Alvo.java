package pt.iscte.poo.sokobanstarter;
import pt.iscte.poo.utils.Point2D;

public class Alvo extends GameElement implements Interactable {

    private boolean occupied = false;

    public Alvo(Point2D position ) {
        super(position);
    }

    @Override
    public String getName() {
        return "Alvo";
    }

    @Override
    public int getLayer() {
        return 1;
    }

    public boolean isOccupied() {
        return occupied;
    }

    @Override
    public void interactWith(GameElement ge) {

        if (ge instanceof Caixote){
            occupied = true;
        } else if (ge instanceof Empilhadora){
            occupied = false;
        }
    }
}