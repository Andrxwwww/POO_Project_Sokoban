package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public abstract class Movable extends GameElement {

    public Movable(Point2D position) {
        super(position);
    }

    public Point2D getPosition() {
        return super.getPosition();
    }

    public void setPosition(Point2D position) {
        super.setPosition(position);
    }

    public void moveTo(Direction direction) {
		Point2D newPosition = super.getPosition().plus(direction.asVector());
		if (newPosition.getX()>=0 && newPosition.getX()<10 && newPosition.getY()>=0 && newPosition.getY()<10 ){
			super.setPosition(newPosition);
		}
	}

    public GameElement getGameElementAtPosition(Point2D position) {
		for (GameElement gameElement : GameEngine.getInstance().getGameElementsList()) {
			if (gameElement.getPosition().equals(position)) {
				return gameElement;
			}
		}
		return null;
	}

    public boolean canMovableMove(Direction direction) {
        Point2D newPosition = super.getPosition().plus(direction.asVector());
        GameElement new_ge = getGameElementAtPosition(newPosition);
        if (new_ge instanceof Movable || new_ge instanceof Parede || new_ge instanceof ParedeRachada 
        || new_ge instanceof Bateria || new_ge instanceof Martelo){
            return false;
        }
        return true;
    }

    public void movableInteractWith(GameElement ge) {
        if (ge instanceof Empilhadora) {
            if (canMovableMove(Direction.directionFor(GameEngine.getInstance().getGui().keyPressed()))) {
                this.moveTo(Direction.directionFor(GameEngine.getInstance().getGui().keyPressed()));
                GameEngine.getInstance().bobcat.addBattery(-1);
            }
        }

        for (GameElement gameElement : GameEngine.getInstance().getGameElementsList()) {
            if (gameElement instanceof Interactable && gameElement.getPosition().equals(this.getPosition())) {
                ((Interactable) gameElement).interactWith(this);
                break;
            }
        }
    }


}
