package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.utils.Point2D;

public abstract class Movable extends GameElement implements MovableInterface {
    
    public static Movable create( String objectName , Point2D position ){
        switch (objectName) {
            case "Palete": 
                return new Palete(position);
            case "Caixote":
                return new Caixote(position);
            case "Empilhadora":
                return new Empilhadora(position);
            default: 
                throw new IllegalArgumentException("This object doesn't exist :( [Movable]");
        }
    }
}
