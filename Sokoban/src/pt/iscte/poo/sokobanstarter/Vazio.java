package pt.iscte.poo.sokobanstarter;
import pt.iscte.poo.utils.Point2D;

public class Vazio extends GameElement {

    public Vazio(Point2D position) {
        super(position);
    }

    @Override
    public String getName() {
        return "Vazio";
    }

    @Override
    public int getLayer() {
        return 0;
    }
    
}