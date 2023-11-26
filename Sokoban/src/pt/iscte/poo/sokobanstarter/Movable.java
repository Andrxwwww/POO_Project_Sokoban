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

    public void movePosition(Direction direction) {
		Point2D newPosition = super.getPosition().plus(direction.asVector());
		if (newPosition.getX()>=0 && newPosition.getX()<10 && newPosition.getY()>=0 && newPosition.getY()<10 ){
			super.setPosition(newPosition);
		}
	}

    public Point2D nextPosition(int key) {
		Direction direction = Direction.directionFor(key);
		return super.getPosition().plus(direction.asVector());
	}
    
}
