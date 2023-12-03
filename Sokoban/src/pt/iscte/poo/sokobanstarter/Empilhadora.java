package pt.iscte.poo.sokobanstarter;

import java.util.Iterator;
import java.util.function.Consumer;

import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Empilhadora extends Movable{

	private String imageName;
	private int Battery;
	private boolean setHammer;
	private int moves;

	private final int FULL_BATTERY = 100;
	GameEngine gameEngine = GameEngine.getInstance();
	
	
	public Empilhadora(Point2D position){
        super(position);
		this.Battery = FULL_BATTERY;
		this.imageName = "Empilhadora_D";
		this.setHammer = false;
		this.moves = 0;
	}

	public int getBattery() {
		return Battery;
	}

	public int getMoves() {
		return moves;
	}

	@Override
	public String getName() {
		return imageName;
	}

	@Override
	public int getLayer() {
		return 4;
	}

	public boolean hasHammer() {
		return setHammer;
	}

	public void setHammer(boolean setHammer) {
		this.setHammer = setHammer;
	}

	public int addBattery(int sumBattery) {
		this.Battery += sumBattery;
		if (Battery > FULL_BATTERY) {
			Battery = FULL_BATTERY;
		}
		return Battery;
	}

	// Muda a imagem segundo a direcao dada 
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

	public boolean PosChecker(Point2D position) {
		if (position.getX()>=0 && position.getX()<10 && position.getY()>=0 && position.getY()<10 ){
			return true;
		}
		return false;
	}

	public boolean canBobcatMove(Direction direction) {
		Point2D newPosition = getPosition().plus(direction.asVector());
		for (GameElement ge : gameEngine.getGameElementsList()) {
			if (ge instanceof Movable && ge.getPosition().equals(newPosition)) {
				return false;
			} else if ( (ge instanceof Parede || ge instanceof ParedeRachada) && ge.getPosition().equals(newPosition)) {
				return false;
			}
		}
		return true;
	}
	
	// Move a empilhadora para a direcao dada, se estiver dentro dos limites
	public void driveTo(Direction direction) {
		Point2D newPosition = getPosition().plus(direction.asVector());
		if (PosChecker(newPosition)){
			for (GameElement ge : gameEngine.getGameElementsList()) {
				if (ge instanceof Movable && ge.getPosition().equals(newPosition)) {
					((Movable)ge).movableInteractWith(this);
					break;
				}
			}

			if (canBobcatMove(direction)) {
				setPosition(newPosition);
				Battery--;
				moves++;
			}

			//interagir com um Alvo ou outro elemento
			for ( GameElement ge : gameEngine.getGameElementsList()) {
				if (ge instanceof Interactable && ge.getPosition().equals(newPosition)) {
					((Interactable)ge).interactWith(this);
					break;
				}
			}

			if( Battery == 0 ) {
				gameEngine.infoBox("Click SPACE for restart ", "You ran out of battery :(");
				gameEngine.restartGame();
			}
			
		}
	}

	public void pickUpItem(Class<?> itemClass, Consumer<Boolean> action) {
    	Iterator<GameElement> iterator = gameEngine.getGameElementsList().iterator();
    	while (iterator.hasNext()) {
        	GameElement item = iterator.next();
        	if (itemClass.isInstance(item)) {
            	if (item.getPosition().equals(this.getPosition())) {
                	action.accept(true);
                	iterator.remove();
                	gameEngine.getGui().removeImage(item);
            	}
        	}
    	}
	}

}