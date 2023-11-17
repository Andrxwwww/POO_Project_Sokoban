package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Palete extends Movable{

	private Point2D position;
	
	public Palete(Point2D initialPosition){
		position = initialPosition;
	}

	@Override
	public Point2D nextPosition(int key) {
		Direction direction = Direction.directionFor(key);
		return position.plus(direction.asVector());
	}
	
	@Override
	public String getName() {
		return "Palete";
	}

	@Override
	public Point2D getPosition() {
		return position;
	}

	@Override
	public int getLayer() {
		return 2;
	}

	@Override
	public boolean doesElapse(NotMovable element) {
		return true;
	}

	@Override
	public boolean doesElapse(Movable element) {
		return true;
	}

	@Override
	public void moveToPoint(Point2D point) {
		position = point;
	}
}