package Domain.Dungeon;

import Domain.Navigator.Position;
import Domain.Navigator.PositionInterface;
import Domain.Navigator.ValidateLocation;
import java.util.ArrayList;
import java.util.List;

public class Level {

  private List<Room> rooms;
  private List<Corridor> corridors;  // Список коридоров
  private int level;
  private boolean isLastLevel;
  private PositionInterface exitPosition;
  private List<PositionInterface> path;


  public Level() {
    this.rooms = new ArrayList<>();
    this.corridors = new ArrayList<>();
    this.exitPosition = new Position();
    this.path = new ArrayList<>();
    level = 1;
  }

  public List<Room> generateRooms() {
    // Создаем 9 комнат, устанавливаем стартовую и конечную
    rooms = GenerateRooms.createRooms(level);
    return rooms;
  }

  public List<Corridor> generateCorridors() {
    // Соединяем комнаты коридорами
    corridors = GenerateCorridor.generateCorridors(rooms);
    setPathInRoom();
    setExitPosition();
    return corridors;
  }

  public ValidateLocation getPositionAt(PositionInterface position) {

    for (Room room : rooms) {
      if (room.isValid(position)) {
        return room;
      }

    }

    for (Corridor corridor : corridors) {
      if (corridor.isValid(position)) {
        return corridor;
      }
    }
    return null;
  }

  public int getLevel() {
    return level;
  }

  public void setLevel(int level) {
    this.level = level;
  }

  public boolean isLastLevel() {
    return isLastLevel;
  }

  public void setLastLevel(boolean lastLevel) {
    isLastLevel = lastLevel;
  }

  public void setExitPosition() {
    for(Room room : rooms){
      if (room.isEnd()) {
        exitPosition = room.getRandomPoint();
        break;
      }
    }
  }

  public PositionInterface getExitPosition() {
    return exitPosition;
  }

  public void setPathInRoom() {
    for(Room room : rooms){
      for (Corridor corridor : corridors) {
        room.setDoors(corridor.getPath());
      }
    }
  }

  public List<PositionInterface> getPath() {
    return path;
  }

  public List<Room> getRooms() {
    return rooms;
  }

  public List<Corridor> getCorridors() {
    return corridors;
  }

  public void setRooms(List<Room> rooms) {
    this.rooms = rooms;
  }

  public void setCorridors(List<Corridor> corridors) {
    this.corridors = corridors;
  }

  public void setExitPosition(PositionInterface exitPosition) {
    this.exitPosition = exitPosition;
  }

  public void setPath(List<PositionInterface> path) {
    this.path = path;
  }
}
