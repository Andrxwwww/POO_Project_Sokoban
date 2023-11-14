package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;


public abstract class GameElement implements ImageTile {

    private Point2D point;

    public static GameElement createGameElement(String gameElementname){
        if ( gameElementname.equals("Parede") || gameElementname.equals("Buraco") || gameElementname.equals("ParedeRachada") || gameElementname.equals("Teleporte") ){
            return NotMovable.create(gameElementname, new Point2D(0,0));
        } else {
            throw new IllegalArgumentException("This object doesn't exist :( [gameElement]");
        }
    }
    
}
