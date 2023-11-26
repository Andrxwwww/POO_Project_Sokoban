package pt.iscte.poo.sokobanstarter;
import pt.iscte.poo.utils.Point2D;

public class ParedeRachada extends GameElement implements Interaction  {

	private Point2D position;
	
	public ParedeRachada(Point2D position){
        super(position);
	}

    @Override
    public String getName() {
        return "ParedeRachada";
    }

    @Override
    public int getLayer() {
        return 2;
    }

    public Point2D nextPosition(int key) {
        return position;
    }

    public void interactWith(GameElement ge) {
        if (ge instanceof Empilhadora && ((Empilhadora) ge).hasHammer()) {
            GameEngine.getInstance().getGameElementsList().remove(this);
            GameEngine.getInstance().getGui().removeImage(this);
            ge.setPosition(this.getPosition());
        }
    }

}