package pt.iscte.poo.sokobanstarter;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;
import java.util.Iterator;
import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.JOptionPane;

import pt.iscte.poo.gui.ImageMatrixGUI;
import pt.iscte.poo.observer.Observed;
import pt.iscte.poo.observer.Observer;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class GameEngine implements Observer {

	public static final int GRID_HEIGHT = 10;
	public static final int GRID_WIDTH = 10;

	private static GameEngine INSTANCE; // Referencia para o unico objeto GameEngine (singleton)
	private ImageMatrixGUI gui; // Referencia para ImageMatrixGUI (janela de interface com o utilizador)
	private List<GameElement> gameElementsList; // lista com os gameElements do Jogo
	public Empilhadora bobcat; // Referencia para a empilhadora (player principal)
	public int level_num; // Numero do nivel
	public int numberOfTargets; // Numero de alvos
	public int numberOfRestarts; // Numero de restarts
	public int score; // Pontuacao
	public String playerName; // Nome do jogador

	public final int BATTERY_RELOAD = 50;
	public final int FIRST_LEVEL = 0;

	private GameEngine() {
		gameElementsList = new ArrayList<>();
	}

	public static GameEngine getInstance() {
		if (INSTANCE == null)
			return INSTANCE = new GameEngine();
		return INSTANCE;
	}

	//--GETTERS--
	public ImageMatrixGUI getGui() { 
		return this.gui;
	}
	
	public List<GameElement> getGameElementsList() {
		return this.gameElementsList;
	}

	//---FUNCAO QUE INICIA O JOGO---
	public void start() {

		gui = ImageMatrixGUI.getInstance(); // 1. obter instancia ativa de ImageMatrixGUI
		gui.setSize(GRID_HEIGHT, GRID_WIDTH); // 2. configurar as dimensoes
		gui.registerObserver(this); // 3. registar o objeto ativo GameEngine como observador da GUI
		gui.go(); // 4. lancar a GUI

		this.numberOfRestarts = 0;
		this.numberOfTargets = 0;
		this.level_num = FIRST_LEVEL; // comeca no nivel 0
		this.score = 0;
		inputPlayerName();

		Score.createHighScoreFile(); // criar o ficheiro de scores , se nao existir
		createLevel(level_num); // criar o nivel
		sendImagesToGUI(); // enviar as imagens para a GUI
	}

	@Override
	public void update(Observed source) {
		int key = gui.keyPressed(); // obtem o codigo da tecla pressionada

		otherKeyInteractions(key); 
		gui.update();
		if (bobcat != null && Direction.isDirection(key)) { // se a empilhadora nao for null e a tecla pressionada for uma direcao(setinhas)
			bobcatKeyMechanics(key);
			gui.setStatusMessage(" SOKOBAN " + " | Player: " + playerName + " | Level: " + level_num + " | Battery: " + bobcat.getBattery() + " | Moves: " + bobcat.getMoves() + " | Score: " + score);
			bobcat.pickUpItem(Bateria.class, b -> bobcat.addBattery(BATTERY_RELOAD));
			bobcat.pickUpItem(Martelo.class, m -> bobcat.setHammer(true));
			winGame(); // verifica se o jogo foi ganho a cada movimento
		}
	}
	
	private void bobcatKeyMechanics(int key) { // movimentacao da empilhadora (visual e logico)
		bobcat.move(key);
		bobcat.driveTo(Direction.directionFor(key));
	}

	// --- FUNCAO PARA ADICIONAR OUTRO TIPO DE UTILIZACAO COM OUTRAS TECLAS ---
	public void otherKeyInteractions(int key) {
		if (key == KeyEvent.VK_SPACE) {
			numberOfRestarts++;
			restartGame();
		} else if ( key == KeyEvent.VK_L) {
			System.exit(0);
		}
	}

	// ---FUNCOES PARA A GUI & INTERACOES COM O UTILIZADOR---
	public void infoBox(String infoMessage, String titleBar) {
		JOptionPane.showMessageDialog(null, infoMessage, titleBar, javax.swing.JOptionPane.INFORMATION_MESSAGE);
	}

	public void inputPlayerName() {
		this.playerName = JOptionPane.showInputDialog("Insert your player name:");
		if (this.playerName == null) {
			infoBox("You have to insert a name ,try again", "Error");
			GameEngine.getInstance().start();
		}
	}

	// ---FUNCOES AUXILIARES---
	public static List<GameElement> selectGEList(List<GameElement> list, Predicate<GameElement> predicate) {
		List<GameElement> selection = new ArrayList<>();
		for (GameElement ge : list) {
			if (predicate.test(ge)) {
				selection.add(ge);
			}
		}
		return selection;
	}

	public void removeGameElement(GameElement ge) {
		Iterator<GameElement> iterator = gameElementsList.iterator();
		while (iterator.hasNext()) {
			GameElement item = iterator.next();
			if (item.equals(ge)) {
				iterator.remove();
			}
		}
	}

	// ---FUNCOES PARA O ESTADO DO JOGO---
	private void winGame() {
		int numberOfTargetsWithBoxes = 0;
		for (GameElement ge : selectGEList(gameElementsList, ge -> ge instanceof Alvo)) {
			if (((Alvo)ge).isOccupied()) {
				numberOfTargetsWithBoxes++;
			}
		}

		if (numberOfTargetsWithBoxes == numberOfTargets) {
			this.score += 100000 / ((bobcat.getMoves()) + (bobcat.getBattery()) + (numberOfRestarts)); // Por isso , quanto mair o score , menor os movimentos e a bateria
			this.numberOfRestarts = 0;
			this.level_num++;
			if (this.level_num > 6) {
				infoBox("you got " + score + ", press ENTER for Exit", "You Won the Game :D !!");
				Score.writePlayerScoreInFile(this.level_num, this.playerName, score );
				System.exit(0);
			} else {
				infoBox("press ENTER for next level", "Congrats!!");
			}
			restartGame();
		}
	}

	public void restartGame() {
		bobcat.setHammer(false);

		gui.clearImages(); // apaga todas as imagens atuais da GUI
		gameElementsList.clear(); // apaga todos os elementos da lista de elementos
		numberOfTargets = 0;

		createLevel(this.level_num);
		sendImagesToGUI();
	}

	//---FUNCOES PARA A CRIACAO DO NIVEL & ADICIONAR ELEMENTOS A LISTA---
	private void createLevel(int level_num) {
		try {
			Scanner scanner = new Scanner(new File("levels\\level" + level_num + ".txt"));
			while (scanner.hasNextLine()) {
				for (int y = 0; y < GRID_HEIGHT; y++) { // loop pela altura da Tela
					String line = scanner.nextLine(); // meter a string/linha numa var
					for (int x = 0; x < line.length(); x++) {// loop pela a length da palavra que vai acabar por ser alargura da tela tambem
						GameElement gameElement = GameElement.create(line.charAt(x), new Point2D(x, y)); // criar o gameElement
						addGameElementToGUI(gameElement); // adicionar a lista correspondente
					}
				}
			}
			scanner.close();
		} catch (FileNotFoundException e) { // se nao encontrar o ficheiro entao
			System.err.println("Erro: ficheiro/level não encontrado :(");
		}
		gui.update();
	}

	private void addGameElementToGUI(GameElement gameElement) {
		if (gameElement instanceof Caixote || gameElement instanceof Palete 
		|| gameElement instanceof ParedeRachada || gameElement instanceof Bateria 
		|| gameElement instanceof Martelo || gameElement instanceof Buraco) {
			gameElementsList.add(gameElement);
			gameElementsList.add(GameElement.create(' ', gameElement.getPosition()));
		} else if (gameElement instanceof Empilhadora) {
			bobcat = (Empilhadora) gameElement;
			gameElementsList.add(bobcat);
			gameElementsList.add(GameElement.create(' ', gameElement.getPosition()));
		} else if (gameElement instanceof Alvo) {
			gameElementsList.add(gameElement);
			numberOfTargets++;
		} else {
			gameElementsList.add(gameElement);
		}
	}

	//---FUNCAO QUE ENVIA OS ELEMENTOS PARA A GUI---
	private void sendImagesToGUI() {
		gui.addImage(bobcat);
		for (GameElement ge : gameElementsList) {
			gui.addImage(ge);
		}
	}

}