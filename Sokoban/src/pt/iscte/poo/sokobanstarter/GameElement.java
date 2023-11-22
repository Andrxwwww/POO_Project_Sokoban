package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;


public abstract class GameElement implements ImageTile {

    public static GameElement create (char c, Point2D position){
        switch (c){
            case '#':
                return new Parede(position);
            case 'O':
                return new Buraco(position);
            case 'X':
                return new Alvo(position,false);
            case 'C':
                return new Caixote(position,true);
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
                return new ParedeRachada(position,true); // mais tarde ira ser posto a false quando se implementar o martelo
            case 'B':
                return new Bateria(position);
            case 'M':
                return new Martelo(position);
            
            default:
                throw new IllegalArgumentException("Invalid element name: " + c + "the element doesnt exist :(");
        }
    }
}
