package Domain.GameSession;

public final class  GameSettings {
  public static final int GAME_WIDTH = 100;
  public static final int GAME_HEIGHT = 20;
  public static final int COUNT_ROOMS = 9;
  public static final int MIN_ROOM_DISTANCE = 2;
  public static final int MIN_ROOM_WIDTH = 8;
  public static final int MIN_ROOM_HEIGHT = 5;
  public static final int MAX_COUNT_ITEM_IN_ROOM= 3;
  public static final int MAX_COUNT_ENEMIES_IN_ROOM = 3;

  private GameSettings() {} // Запрещаем создание экземпляра
}
