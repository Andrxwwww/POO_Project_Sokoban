package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;


public abstract class GameElement implements ImageTile {

    private Point2D position;

    public GameElement(Point2D position) {
        this.position = position;
    }

    public static GameElement create (char c, Point2D position){
        switch (c){
            case '#':
                return new Parede(position);
            case 'O':
                return new Buraco(position);
            case 'X':
                return new Alvo(position);
            case 'C':
                return new Caixote(position);
            case 'P':
                return new Palete(position);
            case 'E':
                return new Empilhadora(position);
            case 'T':
                return new Teleporte(position);
            case ' ':
                return new Chao(position);
            case '=':
                return new Vazio(position);
            case '%':
                return new ParedeRachada(position);
            case 'B':
                return new Bateria(position);
            case 'M':
                return new Martelo(position);
            
            default:
                throw new IllegalArgumentException("Invalid element name: " + c + "the element doesnt exist :(");
        }
    }

    public Point2D getPosition() {
        return position;
    }

    public void setPosition(Point2D position) {
        this.position = position;
    }

    public Point2D previousPosition() {
        Point2D d = this.getPosition().plus((Direction.directionFor(GameEngine.getInstance().getGui().keyPressed())).opposite().asVector());
        return d;
    }

     public Point2D nextPosition(int key) {
		Direction direction = Direction.directionFor(key);
		return this.getPosition().plus(direction.asVector());
	}

}
