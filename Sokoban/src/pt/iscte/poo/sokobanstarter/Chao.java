package pt.iscte.poo.sokobanstarter;
import pt.iscte.poo.utils.Point2D;

public class Chao extends GameElement {

    public Chao(Point2D position) {
        super(position);
    }

    @Override
    public String getName() {
        return "Chao";
    }

    @Override
    public int getLayer() {
        return 0;
    }

}