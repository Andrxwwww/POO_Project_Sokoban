package pt.iscte.poo.sokobanstarter;

import java.util.Iterator;

import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Empilhadora extends Movable{

	private String imageName;
	private int Battery;
	private boolean setHammer;

	private final int BATTERY_RELOAD = 50;
	private final int FULL_BATTERY = 100;
	GameEngine gameEngine = GameEngine.getInstance();
	
	public Empilhadora(Point2D position){
        super(position);
		this.Battery = FULL_BATTERY;
		this.imageName = "Empilhadora_D";
		this.setHammer = false;
	}

	public int getBattery() {
		return Battery;
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
	
	// Move a empilhadora para a direcao dada, se estiver dentro dos limites
	public void driveTo(Direction direction) {
		Point2D newPosition = getPosition().plus(direction.asVector());
		if (PosChecker(newPosition)){
			setPosition(newPosition);
			Battery--;
			if( Battery == 0 ) {
				gameEngine.infoBox("Click SPACE for restart ", "You ran out of battery :(");
				gameEngine.restartGame(gameEngine.level_num);
			}
		}
	}

	public void interactWith(GameElement ge) {
		if (ge instanceof Caixote || ge instanceof Palete) {
			Point2D newPosition = ge.getPosition().plus(Direction.directionFor(gameEngine.getGui().keyPressed()).asVector());
			if (PosChecker(newPosition)) {
				ge.setPosition(newPosition);
				Battery--;
			}
		}
	}

	public void pickUpBattery() {
		Iterator<GameElement> iterator = gameEngine.getGameElementsList().iterator();
		while (iterator.hasNext()) {
			GameElement item = iterator.next();
			if (item instanceof Bateria) {
				if (item.getPosition().equals(this.getPosition())) {
					this.addBattery(BATTERY_RELOAD);
					iterator.remove();
					gameEngine.getGui().removeImage(item);
				}
			}
		}
	}

	public void pickUpHammer() {
		Iterator<GameElement> iterator = GameEngine.getInstance().getGameElementsList().iterator();
		while (iterator.hasNext()) {
			GameElement item = iterator.next();
			if (item instanceof Martelo) {
				if (item.getPosition().equals(this.getPosition())) {
					this.setHammer(true);
					iterator.remove();
					gameEngine.getGui().removeImage(item);
				}
			}
		}
	}
}