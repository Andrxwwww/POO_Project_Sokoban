package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Caixote extends Collidable{

	private Point2D position;
	private boolean movable;
	
	public Caixote(Point2D position  , boolean movable){
		this.position = position;
		this.movable = movable;
	}
	
	@Override
	public String getName() {
		return "Caixote";
	}

	@Override
	public Point2D getPosition() {
		return this.position;
	}

	@Override
	public int getLayer() {
		return 2;
	}

	@Override
	public boolean isAWall() {
		return false;
	}

	public boolean isMovable() {
		return movable;
	}


	public void setIfIsMovable(boolean movable) {
		this.movable = movable;
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