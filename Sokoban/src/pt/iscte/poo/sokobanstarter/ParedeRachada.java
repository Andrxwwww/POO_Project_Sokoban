package pt.iscte.poo.sokobanstarter;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class ParedeRachada extends GameElement implements Interactable  {

	private Point2D position;
    GameEngine engine = GameEngine.getInstance();
	
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
            engine.bobcat.setPosition(engine.bobcat.getPosition().plus(Direction.directionFor(engine.getGui().keyPressed()).asVector()));
            engine.removeGameElement(this);
            engine.getGui().removeImage(this);
        }
    }

}