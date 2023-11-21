package pt.iscte.poo.sokobanstarter;
import pt.iscte.poo.utils.Point2D;

public class Parede extends Collidable {

	private Point2D position;
	
	public Parede(Point2D position){
		this.position = position;
	}

    @Override
    public boolean isMovable() {
        return false;
    }
    
    @Override
    public String getName() {
        return "Parede";
    }

    @Override
    public int getLayer() {
        return 3;
    }

	@Override
	public boolean isAWall() {
		return true;
	}

    @Override
    public Point2D getPosition() {
        return position;
    }

    @Override
    public Point2D nextPosition(int key) {
        return position;
    }
    
}