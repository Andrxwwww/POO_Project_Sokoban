package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.utils.Point2D;

public class Caixote extends Movable{
	
	public Caixote(Point2D position){
        super(position);
	}
	
	@Override
	public String getName() {
		return "Caixote";
	}

	@Override
	public int getLayer() {
		return 3;
	}

}