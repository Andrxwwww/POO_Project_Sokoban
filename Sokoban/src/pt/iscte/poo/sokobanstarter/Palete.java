package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Palete extends Collidable{

	private Point2D position;
	
	public Palete(Point2D position){
		this.position = position;
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
	public boolean isMovable() {
		return true;
	}

	@Override
	public boolean isAWall() {
		return false;
	}

	public void moveToPoint(Point2D point) {
		position = point;
	}
	
	public Point2D nextPosition(int key) {
		Direction direction = Direction.directionFor(key);
		return position.plus(direction.asVector());
	}

	public void movePosition(Direction direction) {
		Point2D newPosition = position.plus(direction.asVector());
		if (newPosition.getX()>=0 && newPosition.getX()<10 && newPosition.getY()>=0 && newPosition.getY()<10 ){
			position = newPosition;
		}
	}
}