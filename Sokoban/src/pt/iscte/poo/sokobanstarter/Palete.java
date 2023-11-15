package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.utils.Point2D;

public class Palete extends Movable{

	private Point2D position;
	
	public Palete(Point2D initialPosition){
		position = initialPosition;
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
}