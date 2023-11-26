package pt.iscte.poo.sokobanstarter;


import pt.iscte.poo.utils.Point2D;

public class Palete extends Movable{

	public Palete(Point2D position ){
        super(position);
	}

	@Override
	public String getName() {
		return "Palete";
	}

	@Override
	public int getLayer() {
		return 2;
	}
	
}