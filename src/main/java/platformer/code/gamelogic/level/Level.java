package platformer.code.gamelogic.level;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import platformer.code.gameengine.PhysicsObject;
import platformer.code.gameengine.graphics.Camera;
import platformer.code.gameengine.loaders.Mapdata;
import platformer.code.gameengine.loaders.Tileset;
import platformer.code.gamelogic.GameResources;
import platformer.code.gamelogic.Main;
import platformer.code.gamelogic.enemies.Enemy;
import platformer.code.gamelogic.player.Player;
import platformer.code.gamelogic.tiledMap.Map;
import platformer.code.gamelogic.tiles.Flag;
import platformer.code.gamelogic.tiles.Flower;
import platformer.code.gamelogic.tiles.Gas;
import platformer.code.gamelogic.tiles.SolidTile;
import platformer.code.gamelogic.tiles.Spikes;
import platformer.code.gamelogic.tiles.Tile;
import platformer.code.gamelogic.tiles.Water;

public class Level {

	private LevelData leveldata;
	private Map map;
	private Enemy[] enemies;
	public static Player player;
	private Camera camera;

	private boolean active;
	private boolean playerDead;
	private boolean playerWin;
	private boolean transitioningToChess;

	private ArrayList<Enemy> enemiesList = new ArrayList<>();
	private ArrayList<Flower> flowers = new ArrayList<>();
	private ArrayList<Water> waters = new ArrayList<>();

	private List<PlayerDieListener> dieListeners = new ArrayList<>();
	private List<PlayerWinListener> winListeners = new ArrayList<>();

	private Mapdata mapdata;
	private int width;
	private int height;
	private int tileSize;
	private Tileset tileset;
	private long waterTimer = 0;
	private long timeAmount = 5;
	public static float GRAVITY = 70;

	public Level(LevelData leveldata) {
		this.leveldata = leveldata;
		mapdata = leveldata.getMapdata();
		width = mapdata.getWidth();
		height = mapdata.getHeight();
		tileSize = mapdata.getTileSize();
		restartLevel();
	}

	public LevelData getLevelData() {
		return leveldata;
	}

	public void restartLevel() {
		enemiesList.clear();
		flowers.clear();
		waters.clear();
		enemies = new Enemy[0];

		int[][] values = mapdata.getValues();
		Tile[][] tiles = new Tile[width][height];

		for (int x = 0; x < width; x++) {
			int xPosition = x;
			for (int y = 0; y < height; y++) {
				int yPosition = y;

				tileset = GameResources.tileset;

				tiles[x][y] = new Tile(xPosition, yPosition, tileSize, null, false, this);
				if (values[x][y] == 0)
					tiles[x][y] = new Tile(xPosition, yPosition, tileSize, null, false, this); // Air
				else if (values[x][y] == 1)
					tiles[x][y] = new SolidTile(xPosition, yPosition, tileSize, tileset.getImage("Solid"), this);

				else if (values[x][y] == 2)
					tiles[x][y] = new Spikes(xPosition, yPosition, tileSize, Spikes.HORIZONTAL_DOWNWARDS, this);
				else if (values[x][y] == 3)
					tiles[x][y] = new Spikes(xPosition, yPosition, tileSize, Spikes.HORIZONTAL_UPWARDS, this);
				else if (values[x][y] == 4)
					tiles[x][y] = new Spikes(xPosition, yPosition, tileSize, Spikes.VERTICAL_LEFTWARDS, this);
				else if (values[x][y] == 5)
					tiles[x][y] = new Spikes(xPosition, yPosition, tileSize, Spikes.VERTICAL_RIGHTWARDS, this);
				else if (values[x][y] == 6)
					tiles[x][y] = new SolidTile(xPosition, yPosition, tileSize, tileset.getImage("Dirt"), this);
				else if (values[x][y] == 7)
					tiles[x][y] = new SolidTile(xPosition, yPosition, tileSize, tileset.getImage("Grass"), this);
				else if (values[x][y] == 8)
					enemiesList.add(new Enemy(xPosition * tileSize, yPosition * tileSize, this)); // TODO: objects vs
																									// tiles
				else if (values[x][y] == 9)
					tiles[x][y] = new Flag(xPosition, yPosition, tileSize, tileset.getImage("Flag"), this);
				else if (values[x][y] == 10) {
					tiles[x][y] = new Flower(xPosition, yPosition, tileSize, tileset.getImage("Flower1"), this, 1);
					flowers.add((Flower) tiles[x][y]);
				} else if (values[x][y] == 11) {
					tiles[x][y] = new Flower(xPosition, yPosition, tileSize, tileset.getImage("Flower2"), this, 2);
					flowers.add((Flower) tiles[x][y]);
				} else if (values[x][y] == 12)
					tiles[x][y] = new SolidTile(xPosition, yPosition, tileSize, tileset.getImage("Solid_down"), this);
				else if (values[x][y] == 13)
					tiles[x][y] = new SolidTile(xPosition, yPosition, tileSize, tileset.getImage("Solid_up"), this);
				else if (values[x][y] == 14)
					tiles[x][y] = new SolidTile(xPosition, yPosition, tileSize, tileset.getImage("Solid_middle"), this);
				else if (values[x][y] == 15)
					tiles[x][y] = new Gas(xPosition, yPosition, tileSize, tileset.getImage("GasOne"), this, 1);
				else if (values[x][y] == 16)
					tiles[x][y] = new Gas(xPosition, yPosition, tileSize, tileset.getImage("GasTwo"), this, 2);
				else if (values[x][y] == 17)
					tiles[x][y] = new Gas(xPosition, yPosition, tileSize, tileset.getImage("GasThree"), this, 3);
				else if (values[x][y] == 18) {
					Water waterTile = new Water(xPosition, yPosition, tileSize, tileset.getImage("Falling_water"), this, 0);
					tiles[x][y] = waterTile;
					waters.add(waterTile);
				} else if (values[x][y] == 19) {
					Water waterTile = new Water(xPosition, yPosition, tileSize, tileset.getImage("Full_water"), this, 3);
					tiles[x][y] = waterTile;
					waters.add(waterTile);
				} else if (values[x][y] == 20) {
					Water waterTile = new Water(xPosition, yPosition, tileSize, tileset.getImage("Half_water"), this, 2);
					tiles[x][y] = waterTile;
					waters.add(waterTile);
				} else if (values[x][y] == 21) {
					Water waterTile = new Water(xPosition, yPosition, tileSize, tileset.getImage("Quarter_water"), this, 1);
					tiles[x][y] = waterTile;
					waters.add(waterTile);
				}
			}

		}
		enemies = new Enemy[enemiesList.size()];
		map = new Map(width, height, tileSize, tiles);
		camera = new Camera(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT, 0, map.getFullWidth(), map.getFullHeight());
		for (int i = 0; i < enemiesList.size(); i++) {
			enemies[i] = new Enemy(enemiesList.get(i).getX(), enemiesList.get(i).getY(), this);
		}
		player = new Player(leveldata.getPlayerX() * map.getTileSize(), leveldata.getPlayerY() * map.getTileSize(),
				this);
		camera.setFocusedObject(player);

		active = true;
		playerDead = false;
		playerWin = false;
		transitioningToChess = false;
	}

	public void onPlayerDeath() {
		active = false;
		playerDead = true;
		throwPlayerDieEvent();
	}

	public void onPlayerWin() {
		active = false;
		playerWin = true;
		throwPlayerWinEvent();
	}

	public void updatePlayerMovementSpeed(boolean inWater) {
		if (player != null) {
			player.updateMovementSpeed(inWater);
		}
	}

	public void update(float tslf) {
		if (active) {
			boolean inWater = false;
			boolean inGas = false;

			for (int x = 0; x < map.getWidth(); x++) {
				for (int y = 0; y < map.getHeight(); y++) {
					Tile tile = map.getTiles()[x][y];
					if (tile == null || tile.getHitbox() == null) {
						continue;
					}

					if (tile instanceof Water && tile.getHitbox().isIntersecting(player.getHitbox())) {
						inWater = true;
					}
					if (tile instanceof Gas && tile.getHitbox().isIntersecting(player.getHitbox())) {
						inGas = true;
					}
				}
			}

			updatePlayerMovementSpeed(inWater);
			player.setInGas(inGas);

			// Update the player
			player.update(tslf);

			// Player death
			if (map.getFullHeight() + 100 < player.getY())
				onPlayerDeath();
			if (player.getCollisionMatrix()[PhysicsObject.BOT] instanceof Spikes)
				onPlayerDeath();
			if (player.getCollisionMatrix()[PhysicsObject.TOP] instanceof Spikes)
				onPlayerDeath();
			if (player.getCollisionMatrix()[PhysicsObject.LEF] instanceof Spikes)
				onPlayerDeath();
			if (player.getCollisionMatrix()[PhysicsObject.RIG] instanceof Spikes)
				onPlayerDeath();

			for (int i = 0; i < flowers.size(); i++) {
				if (flowers.get(i).getHitbox().isIntersecting(player.getHitbox())) {
					if(flowers.get(i).getType() == 1)
						water(flowers.get(i).getCol(), flowers.get(i).getRow(), map, 3);
					else
						addGas(flowers.get(i).getCol(), flowers.get(i).getRow(), map, 20, new ArrayList<Gas>());
					flowers.remove(i);
					i--;
				}
			}

			// Update the enemies
for (int i = 0; i < enemies.length; i++) {
				enemies[i].update(tslf);
				if (player.getHitbox().isIntersecting(enemies[i].getHitbox())) {
					if (!transitioningToChess) {
						transitioningToChess = true;
						this.active = false;

						javax.swing.SwingUtilities.invokeLater(new platformer.code.gamelogic.Chess.StartMenu());
					}
					return;
				}
			}

			// Update the map
			map.update(tslf);

			// Update the camera
			camera.update(tslf);
		}

	}

	// Carson Kim - this method handles all of the water physics from touching a
	// flower in the game.
	// Pre con - map/level has already been created and is populated by tiles
	// post con - creates new water tiles that flow until they reach a border or a
	// wall.
	private void water(int col, int row, Map map, int fullness) {
   		if (col < 0 || col >= map.getTiles().length || row < 0 || row >= map.getTiles()[0].length
				|| (map.getTiles()[col][row] != null && map.getTiles()[col][row].isSolid())){
					return;
					//Makes sure that the water only goes to where it is allowed to go
		}

			String[] coconutWater = {"Falling_water", "Quarter_water", "Half_water", "Full_water"};
			Water waterTile = new Water(col, row, tileSize, tileset.getImage(coconutWater[fullness]), this, fullness);
			map.addTile(col, row, waterTile);
			waters.add(waterTile);
			//created a new string array to be able to access the images
			//replaces old tile with the new water with certain fullness

	if (row + 1 < map.getTiles()[0].length) {
    	// If inside bounds, check if the tile below is empty or not solid
    	if (map.getTiles()[col][row + 1] == null || !map.getTiles()[col][row + 1].isSolid()) {
        	water(col, row + 1, map, 0);
        	return;
    	    // makes water flow down if there is no solid tile below it
    	}
	} else {
    	//triggers when row + 1 is out of bounds
    	return;
	}

		if (fullness == 0) {
			fullness = 3;
			map.addTile(col, row, new Water(col, row, tileSize, tileset.getImage(coconutWater[3]), this, 3));
			//turns falling water into sideways water
		}

		int next = 1;
		if (fullness == 3){ 
			next = 2;
		}
		//calculates how much the water must shrink when spreading

		if (col + 1 < map.getTiles().length
				&& (map.getTiles()[col + 1][row]==null||!map.getTiles()[col + 1][row].isSolid())
				&& !(map.getTiles()[col + 1][row] instanceof Water)) {
			water(col + 1, row, map, next);
			//stretchs water rightward - changed 3 to next so it doesnt stay full

		}
		if (col - 1 >= 0 && (map.getTiles()[col-1][row]==null||!map.getTiles()[col - 1][row].isSolid())
				&& !(map.getTiles()[col - 1][row] instanceof Water)) {
			water(col - 1, row, map, next);
		}
				//stretchs water leftward - changed 3 to next so it doesnt stay full


	}

	//Carson Kim
	//pre con -map/level has already been created and is populated by tiles
	// post con -creates gas tiles based on the layout shown in the directions (up,upright,etc)
	//Ends when 20 gas tiles have been created or is sealed/off the map
	private void addGas(int col, int row, Map map, int numSquaresToFill, ArrayList<Gas> placedThisRound) {
		//Checks if gas is in bounds
		if(col<0||col>=map.getTiles().length||row<0||row>=map.getTiles()[0].length){
			return;
		}
		//cannot put one gas block over another
		if(map.getTiles()[col][row] instanceof Gas){
			return;
		}

		//make the initial origin gas block to start expansion of gas from there
		Gas gasInitial = new Gas(col, row, tileSize, tileset.getImage("GasOne"), this,0);
		map.addTile(col,row,gasInitial);
		placedThisRound.add(gasInitial);
		int tilesPlaced = 1;

		//checks if the volume target is reached immediately
		if(tilesPlaced>=numSquaresToFill){
			return;
		}

		//loop through layout list/spread outward
		for(int i = 0;i<placedThisRound.size();i++){
			Gas currentGas = placedThisRound.get(i);
			int curCol = currentGas.getCol();
			int curRow=	currentGas.getRow();
			int[][] directions = {{0,-1}, {1,-1},{-1,-1},{1,0},{-1,0},{0,1},{1,1},{-1,1}};
			//scans the tiles based on priority
			for(int[] dir: directions){
				int nextCol=curCol+dir[0];
				int nextRow = curRow+dir[1];
				if(nextCol>=0&&nextCol<map.getTiles().length && nextRow >=0 && nextRow < map.getTiles()[0].length){
					Tile targTile = map.getTiles()[nextCol][nextRow];
					if(targTile==null||(!targTile.isSolid() && !(targTile instanceof Gas))){
						Gas newGas = new Gas(nextCol,nextRow,tileSize,tileset.getImage("GasOne"),this,0);
						map.addTile(nextCol,nextRow,newGas);
						placedThisRound.add(newGas);
						tilesPlaced++;
						//another check for targeted volume threshold
						if(tilesPlaced>=numSquaresToFill){
							return;
						}
					}
				}
			}
		}
	}	


	public void draw(Graphics g) {
	   	 g.translate((int) -camera.getX(), (int) -camera.getY());
	   	 for (int x = 0; x < map.getWidth(); x++) {
	   		 for (int y = 0; y < map.getHeight(); y++) {
	   			 Tile tile = map.getTiles()[x][y];
	   			 if (tile == null)
	   				 continue;
	   			 if(tile instanceof Gas) {
	   				
	   				 int adjacencyCount =0;
	   				 for(int i=-1; i<2; i++) {
	   					 for(int j =-1; j<2; j++) {
	   						 if(j!=0 || i!=0) {
	   							 if((x+i)>=0 && (x+i)<map.getTiles().length && (y+j)>=0 && (y+j)<map.getTiles()[x].length) {
	   								 if(map.getTiles()[x+i][y+j] instanceof Gas) {
	   									 adjacencyCount++;
	   								 }
	   							 }
	   						 }
	   					 }
	   				 }
	   				 if(adjacencyCount == 8) {
	   					 ((Gas)(tile)).setIntensity(2);
	   					 tile.setImage(tileset.getImage("GasThree"));
	   				 }
	   				 else if(adjacencyCount >5) {
	   					 ((Gas)(tile)).setIntensity(1);
	   					tile.setImage(tileset.getImage("GasTwo"));
	   				 }
	   				 else {
	   					 ((Gas)(tile)).setIntensity(0);
	   					tile.setImage(tileset.getImage("GasOne"));
	   				 }
	   			 }
	   			 if (camera.isVisibleOnCamera(tile.getX(), tile.getY(), tile.getSize(), tile.getSize()))
	   				 tile.draw(g);
	   		 }
			 g.setColor(Color.RED);;
			 g.setFont(new Font("Arial", Font.BOLD,40));
			 g.drawString((System.currentTimeMillis()-waterTimer)/1000+"", (int) player.getX(), (int)player.getY()-20);
	   	 }


	   	 // Draw the enemies
	   	 for (int i = 0; i < enemies.length; i++) {
	   		 enemies[i].draw(g);
	   	 }


	   	 // Draw the player
	   	 player.draw(g);




	   	 // used for debugging
	   	 if (Camera.SHOW_CAMERA)
	   		 camera.draw(g);
	   	 g.translate((int) +camera.getX(), (int) +camera.getY());
	    }

	// --------------------------Die-Listener
	public void throwPlayerDieEvent() {
		for (PlayerDieListener playerDieListener : dieListeners) {
			playerDieListener.onPlayerDeath();
		}
	}

	public void addPlayerDieListener(PlayerDieListener listener) {
		dieListeners.add(listener);
	}

	// ------------------------Win-Listener
	public void throwPlayerWinEvent() {
		for (PlayerWinListener playerWinListener : winListeners) {
			playerWinListener.onPlayerWin();
		}
	}

	public void addPlayerWinListener(PlayerWinListener listener) {
		winListeners.add(listener);
	}

	// ---------------------------------------------------------Getters
	public boolean isActive() {
		return active;
	}

	public boolean isPlayerDead() {
		return playerDead;
	}

	public boolean isPlayerWin() {
		return playerWin;
	}

	public Map getMap() {
		return map;
	}

	public Player getPlayer() {
		return player;
	}
}