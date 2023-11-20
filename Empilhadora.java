package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Empilhadora extends Movable{

	private Point2D position;
	private String imageName;
	
	public Empilhadora(Point2D initialPosition){
		position = initialPosition;
		imageName = "Empilhadora_D";
	}
	
	@Override
	public Point2D nextPosition(int key) {
		Direction direction = Direction.directionFor(key);
		return position.plus(direction.asVector());
	}


	@Override
	public String getName() {
		return imageName;
	}

	@Override
	public Point2D getPosition() {
		return position;
	}

	@Override
	public int getLayer() {
		return 3;
	}

	@Override
	public boolean doesElapse(NotMovable element) {
		return false;
	}

	@Override
	public boolean doesElapse(Movable element) {
		return true;
	}

	@Override
	public void moveToPoint(Point2D point) {
		position = point;
	}

	//S1
	// Move e muda a imagem segundo a direcao dada, se estiver dentro dos limites
	public void move(int key) {
		Direction direction = Direction.directionFor(key);
		switch (direction) {
			case UP:
				imageName = "Empilhadora_U";
			break;
			case DOWN:
				imageName = "Empilhadora_D";
			break;
			case LEFT:
				imageName = "Empilhadora_L";
			break;
			case RIGHT:
				imageName = "Empilhadora_R";
			break;
	
			default:
				imageName = "Empilhadora_U";
			break;
		}
	}

	public void movePosition(Direction direction) {
		Point2D newPosition = position.plus(direction.asVector());
		if (newPosition.getX()>=0 && newPosition.getX()<10 && newPosition.getY()>=0 && newPosition.getY()<10 ){
			position = newPosition;
		}
	}

	public void moveBox(Direction direction) {
		Point2D newPosition = position.plus(direction.asVector());
		if (newPosition.getX()>=0 && newPosition.getX()<10 && newPosition.getY()>=0 && newPosition.getY()<10 ){
			position = newPosition;
		}
	}
}