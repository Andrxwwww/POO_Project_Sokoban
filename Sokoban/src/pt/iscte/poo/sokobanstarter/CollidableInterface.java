package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.utils.Point2D;

public interface CollidableInterface {

    public int getLayer();

    public String getName();

    public Point2D getPosition();

    public boolean isAWall();

    public boolean isMovable();

    public Point2D nextPosition(int key);

    public int collidableLevel();
    
}
