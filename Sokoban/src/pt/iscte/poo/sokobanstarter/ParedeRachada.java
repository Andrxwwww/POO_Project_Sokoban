package pt.iscte.poo.sokobanstarter;
import pt.iscte.poo.utils.Point2D;

public class ParedeRachada extends Collidable {

	private Point2D position;
    private boolean b;
	
	public ParedeRachada(Point2D position , boolean b){
		this.position = position;
        this.b = b;
	}

    @Override
    public String getName() {
        return "ParedeRachada";
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
        return false;
    }

    @Override
    public Point2D nextPosition(int key) {
        return position;
    }

	@Override
	public boolean isAWall() {
		return b;
	}

	public void setAsNewWall(boolean b) {
        this.b = b;
    }
    
}