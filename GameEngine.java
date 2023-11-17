package pt.iscte.poo.sokobanstarter;

import java.awt.RenderingHints.Key;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;
import java.io.File;
import java.io.FileNotFoundException;

import pt.iscte.poo.gui.ImageMatrixGUI;
import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.observer.Observed;
import pt.iscte.poo.observer.Observer;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;

// Note que esta classe e' um exemplo - nao pretende ser o inicio do projeto, 
// embora tambem possa ser usada para isso.
//
// No seu projeto e' suposto haver metodos diferentes.
// 
// As coisas que comuns com o projeto, e que se pretendem ilustrar aqui, sao:
// - GameEngine implementa Observer - para  ter o metodo update(...)  
// - Configurar a janela do interface grafico (GUI):
//        + definir as dimensoes
//        + registar o objeto GameEngine ativo como observador da GUI
//        + lancar a GUI
// - O metodo update(...) e' invocado automaticamente sempre que se carrega numa tecla
//
// Tudo o mais podera' ser diferente!


public class GameEngine implements Observer {

	// Dimensoes da grelha de jogo
	public static final int GRID_HEIGHT = 10;
	public static final int GRID_WIDTH = 10;

	private static GameEngine INSTANCE; // Referencia para o unico objeto GameEngine (singleton)
	private ImageMatrixGUI gui;  		// Referencia para ImageMatrixGUI (janela de interface com o utilizador) 
	private List<GameElement> gameElementsList;	// Lista de imagens
	private List<NotMovable> notMovableList;	// Lista de imagens
	private List<Movable> movableList;	// Lista de imagens
	private Empilhadora bobcat;	        // Referencia para a empilhadora
	private int level_num;	// Numero do nivel a carregar


	// Construtor - neste exemplo apenas inicializa uma lista de ImageTiles
	private GameEngine() {
		gameElementsList = new ArrayList<>();   
	}

	// Implementacao do singleton para o GameEngine
	public static GameEngine getInstance() {
		if (INSTANCE==null)
			return INSTANCE = new GameEngine();
		return INSTANCE;
	}

	// Inicio
	public void start() {

		// Setup inicial da janela que faz a interface com o utilizador
		// algumas coisas poderiam ser feitas no main, mas estes passos tem sempre que ser feitos!
		
		gui = ImageMatrixGUI.getInstance();    // 1. obter instancia ativa de ImageMatrixGUI	
		gui.setSize(GRID_HEIGHT, GRID_WIDTH);  // 2. configurar as dimensoes 
		gui.registerObserver(this);            // 3. registar o objeto ativo GameEngine como observador da GUI
		gui.go();                              // 4. lancar a GUI

		
		// Criar o cenario de jogo
		createWarehouse();      // criar o armazem
		sendImagesToGUI();      // enviar as imagens para a GUI

		// Escrever uma mensagem na StatusBar
		gui.setStatusMessage("Sokoban Starter - demo");
	}

	// O metodo update() e' invocado automaticamente sempre que o utilizador carrega numa tecla
	// no argumento do metodo e' passada uma referencia para o objeto observado (neste caso a GUI)
	@Override
	public void update(Observed source) {
		int key = gui.keyPressed();    // obtem o codigo da tecla pressionada
		
		bobcatKeyMechanics(key);
			
		gui.update();                  // redesenha a lista de ImageTiles na GUI, tendo em conta as novas posicoes dos objetos
	}


	// Criacao da planta do armazem - so' chao neste exemplo 
	

	private void createWarehouse() {
		level_num = 1;
		notMovableList = new ArrayList<>();
 		movableList = new ArrayList<>();	
		try {
			Scanner scanner = new Scanner(new File("levels\\level" + level_num + ".txt"));
			while (scanner.hasNextLine()) {
					for (int y=0; y<GRID_HEIGHT; y++){ //loop pela altura da Tela
					String symbol = scanner.nextLine(); // meter a string/linha numa var
						for(int i = 0; i < symbol.length(); i++){// loop pela a length da palavra que vai acabar por ser a largura da tela tambem
							correspondSymbol(symbol.charAt(i), new Point2D(i,y));// verifica qual é o char correspondente e ,mete na tela ( isso e na outra func a baixo )
						}
					}
			}
			scanner.close();
		} catch (FileNotFoundException e) { // se nao encontrar o ficheiro entao
			System.err.println("Erro: ficheiro/level não encontrado :(");
		}
		gui.update();	
	}

	private void bobcatKeyMechanics(int key){

		if(!isAWall() && !canBeMoved()){
			bobcat.movePosition(Direction.directionFor(key));
		}
		
	}

	private boolean isAWall(){

		for (NotMovable notMovable : notMovableList) {
			if (notMovable instanceof Parede) {
				if(bobcat.nextPosition(gui.keyPressed()).equals(notMovable.getPosition())){
					bobcat.move(gui.keyPressed());
					return true;
				}
			}
		}
		bobcat.move(gui.keyPressed());
		return false;
	}

	private boolean canBeMoved(){
		for (Movable movable : movableList) {
			if (movable instanceof Caixote) {
				if(bobcat.nextPosition(gui.keyPressed()).equals(movable.getPosition())){
					// falta funcao para mover a caixa
					return true;
				}
			}
		}
		return false;
	}

	private void correspondSymbol(char symbol, Point2D point) {
		switch (symbol) {
			case '=':
				gameElementsList.add(new Vazio(point));
				notMovableList.add(new Vazio(point));
				break;
			case '#':
				gameElementsList.add(new Parede(point));
				notMovableList.add(new Parede(point));
				break;
			case ' ':
				gameElementsList.add(new Chao(point));
				notMovableList.add(new Chao(point));
				break;
			case 'X':
				gameElementsList.add(new Alvo(point));
				notMovableList.add(new Alvo(point));
				break;
			case 'C':
				gameElementsList.add(new Caixote(point));
				movableList.add(new Caixote(point));
				break;
			case 'E':
				movableList.add(new Empilhadora(point));
				gameElementsList.add(new Chao(point));
				bobcat = new Empilhadora(point);
				gameElementsList.add(bobcat);
				break;
			case 'T':
				gameElementsList.add(new Teleporte(point));
				gameElementsList.add(new Chao(point));
				notMovableList.add(new Teleporte(point));
				break;
			case 'O':
				gameElementsList.add(new Buraco(point));
				notMovableList.add(new Buraco(point));
				break;
			case 'P':
				gameElementsList.add(new Palete(point));
				movableList.add(new Palete(point));
				break;
			case '%':
				gameElementsList.add(new ParedeRachada(point));
				notMovableList.add(new ParedeRachada(point));
				break;
			default:
				break;
		}
	}

	// Envio das mensagens para a GUI - note que isto so' precisa de ser feito no inicio
	// Nao e' suposto re-enviar os objetos se a unica coisa que muda sao as posicoes  
	private void sendImagesToGUI() {
		for (ImageTile img : gameElementsList) {
			gui.addImage(img);
		}
	}
}