package pt.iscte.poo.sokobanstarter;

import java.awt.RenderingHints.Key;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

import pt.iscte.poo.gui.ImageMatrixGUI;
import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.observer.Observed;
import pt.iscte.poo.observer.Observer;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

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
	private Empilhadora bobcat;	        // Referencia para a empilhadora


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
		
		switch (key) {
			case KeyEvent.VK_UP:
				bobcat.move(Direction.UP);
			break;
			case KeyEvent.VK_DOWN:
				key = KeyEvent.VK_DOWN;
				bobcat.move(Direction.DOWN);
			break;
			case KeyEvent.VK_LEFT:
				key = KeyEvent.VK_LEFT;
				bobcat.move(Direction.LEFT);
			break;
			case KeyEvent.VK_RIGHT:
				key = KeyEvent.VK_RIGHT;
				bobcat.move(Direction.RIGHT);
			break;

			default:
			break;
		}
		gui.update();                  // redesenha a lista de ImageTiles na GUI, 
		                               // tendo em conta as novas posicoes dos objetos
	}


	// Criacao da planta do armazem - so' chao neste exemplo 
	private void createWarehouse() {

		try {
			Scanner scanner = new Scanner(new File("levels\\level1.txt"));
			while (scanner.hasNextLine()) {
					for (int y=0; y<GRID_HEIGHT; y++){ //loop pela altura da Tela
					String symbol = scanner.nextLine(); // meter a string/linha numa var
						for(int i = 0; i < symbol.length(); i++){// loop pela a length da palavra que vai acabar por ser a largura da tela tambem
							correspondSymbol(symbol.charAt(i),i,y);// verifica qual é o char correspondente e ,mete na tela ( isso e na outra func a baixo )
						}
					}
			}
			scanner.close();
		} catch (FileNotFoundException e) { // se nao encontrar o ficheiro entao
			System.err.println("Erro: ficheiro/level não encontrado :(");
		}  	
	}

	private void correspondSymbol (char symbol , int x, int y) {
				switch (symbol) {
					case '=':
						gameElementsList.add(new Vazio(new Point2D(x,y)));
					break;
					case '#':
						gameElementsList.add(new Parede(new Point2D(x,y)));
					break;
					case ' ':
						gameElementsList.add(new Chao(new Point2D(x,y)));
					break;
					case 'X':
						gameElementsList.add(new Alvo(new Point2D(x,y)));
					break;
					case 'C':
						gameElementsList.add(new Caixote(new Point2D(x,y)));
					break;
					case 'E':
						gameElementsList.add(new Chao(new Point2D(x,y)));
						bobcat = new Empilhadora( new Point2D(x,y));
						gameElementsList.add(bobcat);
					break;
					default:
					break;
				}
	}

	// Envio das mensagens para a GUI - note que isto so' precisa de ser feito no inicio
	// Nao e' suposto re-enviar os objetos se a unica coisa que muda sao as posicoes  
	private void sendImagesToGUI() {
		gui.addImages(gameElementsList);
	}
}