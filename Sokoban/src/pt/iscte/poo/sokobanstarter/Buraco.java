package pt.iscte.poo.sokobanstarter;
import pt.iscte.poo.utils.Direction;
//import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Buraco extends GameElement implements Interactable {

    GameEngine gameEngine = GameEngine.getInstance();

    public Buraco(Point2D position) {
        super(position);
    }

    @Override
    public String getName() {
        return "Buraco";
    }

    @Override
    public int getLayer() {
        return 1;
    }

    @Override
    public void interactWith(GameElement ge) {
        if (ge instanceof Palete) {
            gameEngine.bobcat.setPosition(gameEngine.bobcat.getPosition().plus(Direction.directionFor(gameEngine.getGui().keyPressed()).asVector()));
            gameEngine.removeGameElement(this);
            gameEngine.removeGameElement(ge);
        } else if ( ge instanceof Caixote ) {
            gameEngine.bobcat.setPosition(gameEngine.bobcat.getPosition().plus(Direction.directionFor(gameEngine.getGui().keyPressed()).asVector()));
            gameEngine.removeGameElement(ge);
            gameEngine.getGui().removeImage(ge);
            gameEngine.infoBox("     [Box fell in a hole] \n" + "Press SPACE for restart", "You Lost :(");
            gameEngine.restartGame();
        } else if ( ge instanceof Empilhadora) {
            gameEngine.removeGameElement(ge);
            gameEngine.getGui().removeImage(ge);
            gameEngine.infoBox("   [Bobcat fell in a hole] \n" + "Press SPACE for restart", "You Lost :(");
            gameEngine.restartGame();
        }
    }

}