package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.utils.Point2D;

public interface MovableInterface {
    
    public boolean doesElapse(NotMovable notMovable);

    public boolean doesElapse(Movable movable);

    public Point2D nextPosition(int key);

    public void moveToPoint(Point2D point);
    
}
