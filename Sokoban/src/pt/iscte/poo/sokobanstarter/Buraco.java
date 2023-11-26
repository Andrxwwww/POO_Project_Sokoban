package pt.iscte.poo.sokobanstarter;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Buraco extends GameElement implements Interaction {

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
            gameEngine.getGameElementsList().remove(ge);
            gameEngine.getGameElementsList().remove(this);
        } else if ( ge instanceof Caixote) {
            gameEngine.bobcat.driveTo(Direction.directionFor(gameEngine.getGui().keyPressed()));
            gameEngine.bobcat.move(gameEngine.getGui().keyPressed());
            gameEngine.getGameElementsList().remove(ge);
            gameEngine.getGui().removeImage(ge);
            gameEngine.infoBox("Press SPACE for restart", "The number of boxes on inferior than the targets :(");
            gameEngine.restartGame(gameEngine.level_num);
        } else if ( ge instanceof Empilhadora) {
            gameEngine.getGameElementsList().remove(ge);
            gameEngine.getGui().removeImage(ge);
            gameEngine.infoBox("Press SPACE for restart", "You Lost :(");
            gameEngine.restartGame(gameEngine.level_num);
        }
    }

}