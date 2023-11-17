package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;


public abstract class GameElement implements ImageTile {

    public static GameElement createGameElement(String gameElementname , Point2D position){
        if ( gameElementname.equals("Parede") || gameElementname.equals("Buraco") || gameElementname.equals("ParedeRachada") || gameElementname.equals("Teleporte") 
        || gameElementname.equals("Vazio") || gameElementname.equals("Caixote") || gameElementname.equals("Alvo") ){
            return NotMovable.create(gameElementname, position);
        } else if ( gameElementname.equals("Palete") || gameElementname.equals("Caixote") || gameElementname.equals("Empilhadora") ){
            return Movable.create(gameElementname, position);
        } else {
            throw new IllegalArgumentException("This object doesn't exist :( [gameElement]");
        }
    }
    
}
