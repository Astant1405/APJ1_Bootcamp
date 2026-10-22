package Domain.GameSession;


import Domain.Character.Player.Bag;
import Domain.Character.Player.Player;
import Domain.Dungeon.Level;
import Domain.Dungeon.Room;
import Domain.Item.ItemInterface;
import Domain.Navigator.Direction;
import Domain.Navigator.Position;
import Domain.Navigator.PositionInterface;
import Domain.Navigator.ValidateLocation;
import java.util.Iterator;
import java.util.logging.Logger;

public class Game {

  private Level level;
  private Player player;
  private PositionInterface position;
  private Bag bag;
  private boolean saveGame;
  private boolean gameOver = false;

  private static final Logger logger = Logger.getLogger(Game.class.getName());


  public Game() {
    initializeGame();
  }

  public Game(boolean saveGame) {
    this.saveGame = saveGame;
    //loadGameFromJson();
  }

  private void initializeGame() {
    position = new Position();
    bag = new Bag();
    level = new Level();
    player = new Player(100, 30, 30, true, position,
        bag);
    generateDungeon();
    setStartPlayerPosition();
    level.setLevel(1);
  }

  private void generateDungeon() {
    level.setRooms(level.generateRooms());
    level.setCorridors(level.generateCorridors());
  }

  private void setStartPlayerPosition() {
    for (Room room : level.getRooms()) {
      if (room.isStart()) {
        player.setPosition(room.getCenter());
      }
    }
  }

  public Player getPlayer() {
    return player;
  }

  public void processTurn(String command) {
    ValidateLocation currentLocation = level.getPositionAt(player.getPosition());
    switch (command) {
      case "w" -> movePlayer(Direction.UP, currentLocation);
      case "a" -> movePlayer(Direction.LEFT, currentLocation);
      case "d" -> movePlayer(Direction.RIGHT, currentLocation);
      case "s" -> movePlayer(Direction.DOWN, currentLocation);
      case "h" -> useWeapon(currentLocation);
      case "j" -> useFood();
      case "k" -> useElixir();
      case "e" -> useScroll();
    }
    moveEnemies(player, currentLocation);
  }

  private void moveEnemies(Player player, ValidateLocation location) {
    if(player != null && location != null) {
      location.moveEnemies(player);
    }
  }

  private void movePlayer(Direction direction, ValidateLocation location) {
    PositionInterface currentPos = player.getPosition();
    PositionInterface newPos = currentPos.move(direction, 1);

    ValidateLocation nextLocation = level.getPositionAt(newPos);

    if (nextLocation != null) {
      player.playerMove(direction, nextLocation);

      PositionInterface endPos = player.getPosition();

      // Любая локация сама обрабатывает вход игрока
      location.onPlayerEnter(player, endPos, this);

      if (!player.isAlive()) {
        gameOver = true;
      }
    }

  }


  public void newLevel() {
    level.setLevel(level.getLevel() + 1);
    isLastLevel();
    generateDungeon();
    setStartPlayerPosition();
    //saveGameToJson();
  }

  private void isLastLevel() {
    if (level.getLevel() == 22) {
      level.setLastLevel(true);
//      DataLayer dataLayer = new DataLayer();
//      dataLayer.writeRecordStatToTxt(this);
    }
  }

  private void useWeapon(ValidateLocation location) {
    Iterator<ItemInterface> iterator = player.getBag().getItems().iterator();
    while (iterator.hasNext()) {
      ItemInterface item = iterator.next();

      item.useItem(player);

      long count = player.getBag().getItems().stream()
          .filter(i -> i.isSameWeapon(item))
          .count();

      if (count > 1) {
        iterator.remove();
        item.deactivate(player);
        location.addItemToLocation(item, player);
        item.useItem(player);
      }
    }
  }



  private void useFood() {
    Iterator<ItemInterface> iterator = player.getBag().getItems().iterator();
    while (iterator.hasNext()) {
      ItemInterface item = iterator.next();
      if (item.isFood()) {
        item.useItem(player);
        iterator.remove();
      }
    }
  }

  private void useElixir() {
    Iterator<ItemInterface> iterator = player.getBag().getItems().iterator();
    while (iterator.hasNext()) {
      ItemInterface item = iterator.next();
      if (item.isElixir()) {
        item.useItem(player);
        iterator.remove(); // Безопасное удаление
      }
    }
  }

  private void useScroll() {
    Iterator<ItemInterface> iterator = player.getBag().getItems().iterator();
    while (iterator.hasNext()) {
      ItemInterface item = iterator.next();
      if (item.isScroll()) {
        item.useItem(player);
        iterator.remove(); // Безопасное удаление
      }
    }
  }

  public Level getLevel() {
    return level;
  }

  public void setPlayer(Player player) {
    this.player = player;
  }

  public void setLevel(Level level) {
    this.level = level;
  }

//  private void loadGameFromJson() {
//    GameDTO gameDTO = DataLayer.load();
//    if (gameDTO == null) {
//      logger.info("Failed to load game data. Initializing a new game...");
//      initializeGame();
//      return;
//    }
//
//    // Преобразуем DTO обратно в объект Game и обновляем текущее состояние
//    Game loadedGame = GameConverter.fromDTO(gameDTO);
//    if (loadedGame == null) {
//      logger.info("Failed to convert GameDTO to Game. Initializing a new game...");
//      initializeGame();
//      return;
//    }
//
//    // Обновляем текущее состояние игры
//    this.level = loadedGame.getLevel();
//    this.player = loadedGame.getPlayer();
//
//    logger.info("Game loaded successfully!");
//  }

//  private void saveGameToJson(){
//    GameDTO gameDTO = GameConverter.toDTO(this);
//    DataLayer.save(gameDTO);
//  }

  public boolean isGameOver() {
    return gameOver;
  }

}