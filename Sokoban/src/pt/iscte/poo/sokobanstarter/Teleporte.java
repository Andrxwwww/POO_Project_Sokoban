package pt.iscte.poo.sokobanstarter;
import pt.iscte.poo.utils.Point2D;

public class Teleporte extends GameElement implements Interaction{

    public Teleporte(Point2D position) {
        super(position);
    }

    @Override
    public String getName() {
        return "Teleporte";
    }

    @Override
    public int getLayer() {
        return 1;
    }

    @Override
    //TODO: quando a empilhadora passa ou um Movable ,tentar evitar que ela vá para dentro da parede
    public void interactWith(GameElement ge) {
        for (GameElement ge2 : GameEngine.getInstance().getGameElementsList()) {
            if ((ge instanceof Empilhadora || ge instanceof Movable) && ge2 instanceof Teleporte && !ge2.getPosition().equals(this.getPosition())) {
                ge.setPosition(ge2.getPosition());
            }
        } 
    }

}