package pt.iscte.poo.sokobanstarter;

import java.awt.Point;
import java.awt.RenderingHints.Key;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;
import java.io.File;
import java.io.FileNotFoundException;
import javax.swing.JOptionPane;
import java.util.Iterator;

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
	private List<NotCollidable> NotCollidableList;	// Lista de imagens
	private List<Collidable> CollidableList;	// Lista de imagens
	private List<Item> ItemList;	// Lista de imagens
	private Empilhadora bobcat;	        // Referencia para a empilhadora
	private int level_num;	// Numero do nivel a carregar

	private final int BATTERY_RELOAD = 50;


	// Construtor - neste exemplo apenas inicializa uma lista de ImageTiles
	private GameEngine() {
		gameElementsList = new ArrayList<>();
		NotCollidableList = new ArrayList<>();
		CollidableList = new ArrayList<>();
		ItemList = new ArrayList<>();
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

		this.level_num = 2;
		createLevel(level_num);      // criar o armazem
		sendImagesToGUI();      // enviar as imagens para a GUI


		// Escrever uma mensagem na StatusBar
		gui.setStatusMessage("Sokoban Starter - demo");
	}

	// O metodo update() e' invocado automaticamente sempre que o utilizador carrega numa tecla
	// no argumento do metodo e' passada uma referencia para o objeto observado (neste caso a GUI)
	@Override
	public void update(Observed source) {
		int key = gui.keyPressed();    // obtem o codigo da tecla pressionada

		otherKeyInteractions(key);
		if (bobcat != null && KeyChecker(key)) {
			bobcatKeyMechanics(key);
		}
		pickUpBattery();

		gui.update();                  // redesenha a lista de ImageTiles na GUI, tendo em conta as novas posicoes dos objetos
	}


	// Criacao da planta do armazem - so' chao neste exemplo 
	public boolean KeyChecker(int key){
		if (key == KeyEvent.VK_UP || key == KeyEvent.VK_DOWN || key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT) {
			return true;
		}
		return false;
	}

	public void infoBox(String infoMessage, String titleBar){
		JOptionPane.showMessageDialog(null, infoMessage, titleBar, javax.swing.JOptionPane.INFORMATION_MESSAGE);
	}

	public void restartGame(){
		gui.clearImages(); // apaga todas as imagens atuais da GUI
		//apaga o conteudo das listas
		gameElementsList.clear();
		NotCollidableList.clear();
		CollidableList.clear();
		ItemList.clear();

		//reecria o primeiro nivel
		level_num = 2;
		createLevel(level_num);      
		sendImagesToGUI();
	}

	public void otherKeyInteractions(int key){
		if (key == KeyEvent.VK_ENTER) {
			restartGame();
		}
	}
	

	private void createLevel(int level_num) {
		try {
			Scanner scanner = new Scanner(new File("levels\\level" + level_num + ".txt"));
			while (scanner.hasNextLine()) {
					for (int y=0; y<GRID_HEIGHT; y++){ //loop pela altura da Tela
					String line = scanner.nextLine(); // meter a string/linha numa var
						for(int x = 0; x < line.length(); x++){// loop pela a length da palavra que vai acabar por ser a largura da tela tambem
							GameElement gameElement = GameElement.create(line.charAt(x), new Point2D(x,y)); // criar o gameElement
							whatIsGameElement(gameElement); // adicionar a lista correspondente
						}
					}
			}
			scanner.close();
		} catch (FileNotFoundException e) { // se nao encontrar o ficheiro entao
			System.err.println("Erro: ficheiro/level não encontrado :(");
		}
		gui.update();	
	}

	// funcao que dado um gameElement ele vai adicionar a lista correspondente (tinha-se de fazer com gameElements é o porquê de ter feito asssim)
	private void whatIsGameElement(GameElement gameElement){
		if( gameElement instanceof Parede || gameElement instanceof Caixote || gameElement instanceof Palete || gameElement instanceof ParedeRachada){
			gameElementsList.add(gameElement);
			CollidableList.add( (Collidable) gameElement);
			NotCollidableList.add( (NotCollidable) GameElement.create(' ', gameElement.getPosition()));
		} else if ( gameElement instanceof Chao || gameElement instanceof Alvo || gameElement instanceof Buraco || gameElement instanceof Teleporte || gameElement instanceof Vazio){
			gameElementsList.add(gameElement);
			NotCollidableList.add( (NotCollidable) gameElement);
		} else if ( gameElement instanceof Empilhadora){
			NotCollidableList.add( (NotCollidable) GameElement.create(' ', gameElement.getPosition()) ); // adicionar um chao por baixo da empilhadora
			gameElementsList.add(gameElement);
			bobcat = (Empilhadora) gameElement;
		} else if ( gameElement instanceof Bateria || gameElement instanceof Martelo){
			ItemList.add( (Item) gameElement);
			gameElementsList.add(gameElement);
			NotCollidableList.add( (NotCollidable) GameElement.create(' ', gameElement.getPosition())); // adicionar um chao por baixo do item
		}
	}

	private void bobcatKeyMechanics(int key){

		if (bobcatHitWallChecker()) {
			bobcat.movePosition(Direction.directionFor(key));
			System.out.println(bobcat.getBattery()); // debug para ver a bateria se está correta
		}
	}

	private boolean bobcatHitWallChecker(){

		for (Collidable collidable : CollidableList) {
			Point2D nextPosition = bobcat.nextPosition(gui.keyPressed()); // guarda numa variavel a proxima posicao da empilhadora
			if (collidable.getPosition().equals(nextPosition)) { // se a proxima posicao da empilhadora for igual a posicao de um collidable
				if (collidable.isMovable()) { // e se esse collidable for movable (caixote OU palete)
					if (CollidableChecker(collidable)) { // chama uma funcao onde vai checkar se esse collidable pode ir contra a parede ou contra outro collidable 
						bobcat.move(gui.keyPressed()); // ssó para mudar de foto quando bate na parede ou no movable
						return false; // entao a empilhadora nao passa
					}
					collidable.movePosition(Direction.directionFor(gui.keyPressed())); // move o collidable [ por outras palavras a empilhadora move o caixote ou a palete]
					bobcat.move(gui.keyPressed());
					bobcat.addBattery(-1); // retira 1 de bateria que por sua vez no total tira -2 de bateria [ -1 por mover a empilhadora e -1 por mover o caixote ou a palete]
					return true; // entao a empilhadora passa
				} else if (collidable.isAWall()) { // ou se esse collidable for uma Parede ou ParedeRachada [ mais tarde com o martelo depois muda-se o isAWall() = false ]
					bobcat.move(gui.keyPressed());
					return false; // entao a empilhadora nao passa
				}
			}
		}
		bobcat.move(gui.keyPressed());
		return true;
	}

	private boolean CollidableChecker(Collidable c){
		for (Collidable collidableInList : CollidableList) { // loop por todos os collidables
			if (collidableInList.isMovable() || collidableInList.isAWall()){ // se o collidable for movable ou for uma parede
				if (c.nextPosition(gui.keyPressed()).equals(collidableInList.getPosition())) { // se o collidable que está a ser movido for contra outro collidable ou parede
					return true; // entao esse collidable nao passa tal como a empilhadora que nao passa tambem
				}
			}
		}
		return false;
	}



	public void pickUpBattery() {
		// usou se iterator porque dava java.util.ConcurrentModificationException ao remover [ pt nao sei pq é que usei iterator :) mas funciona]
		Iterator<Item> iterator = ItemList.iterator();
		while (iterator.hasNext()) {
			Item item = iterator.next();
			if (item instanceof Bateria) {
				if (item.getPosition().equals(bobcat.getPosition())) {
					bobcat.addBattery(BATTERY_RELOAD);
					iterator.remove();  
					gui.removeImage(item);
				}
			}
		}
	}



	// Envio das mensagens para a GUI - note que isto so' precisa de ser feito no inicio
	// Nao e' suposto re-enviar os objetos se a unica coisa que muda sao as posicoes  
	private void sendImagesToGUI() {

		gui.addImage(bobcat);

		for( Collidable collidable : CollidableList){
			gui.addImage(collidable);
		}

		for( NotCollidable notCollidable : NotCollidableList){
			gui.addImage(notCollidable);
		}

		for( Item item : ItemList){
			gui.addImage(item);
		}
	}
}