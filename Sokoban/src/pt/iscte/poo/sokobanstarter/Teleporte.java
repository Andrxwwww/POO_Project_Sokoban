package pt.iscte.poo.sokobanstarter;
import pt.iscte.poo.utils.Point2D;

public class Teleporte extends GameElement implements Interactable{

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
    //TOFIX: Se estiver uma caixa no outro teleporte e a empilhadora for para cima do portal livre ele vai para o outro portal mas entra dentro da caixa :|
    public void interactWith(GameElement ge) {
        for (GameElement ge2 : GameEngine.getInstance().getGameElementsList()) {
            if (ge instanceof Movable && ge2 instanceof Teleporte && !ge2.getPosition().equals(this.getPosition())) {
                ge.setPosition(ge2.getPosition());
            }
        } 
    }

}