package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.utils.Point2D;

public abstract class NotMovable extends GameElement implements NotMovableInterface {
    
    public static NotMovable create( String objectName , Point2D position ){
        switch (objectName) {
            case "Parede": 
                return new Parede(position);
            case "Buraco":
                return new Buraco(position);
            case "ParedeRachada":
                return new ParedeRachada(position);
            case "Teleporte":
                return new Teleporte(position);
            default: 
                throw new IllegalArgumentException("This object doesn't exist :( [notMovable]");
        }
    }
}
