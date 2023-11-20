package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public abstract class Collidable extends GameElement implements CollidableInterface {

    private Point2D position;

    public void movePosition(Direction  direction) {
        Point2D newPosition = position.plus(direction.asVector());
        if (newPosition.getX()>=0 && newPosition.getX()<10 && newPosition.getY()>=0 && newPosition.getY()<10 ){
            position = newPosition;
        }
    }

}
