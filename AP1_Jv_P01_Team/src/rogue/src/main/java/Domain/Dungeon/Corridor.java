package Domain.Dungeon;

import Domain.Navigator.Position;
import Domain.Navigator.PositionInterface;
import Domain.Navigator.ValidateLocation;
import java.util.ArrayList;
import java.util.List;

public class Corridor implements ValidateLocation {

  private List<PositionInterface> path;
  private List<PositionInterface> door = new ArrayList<>();


  public Corridor(PositionInterface startPos, PositionInterface endPos, List<Room> rooms) {
    this.path = generatePath(startPos, endPos, rooms);
  }

  public List<PositionInterface> getPath() {
    return path;
  }

  public List<PositionInterface> getDoor() {return door;}

  @Override
  public boolean isValid(PositionInterface pos) {
    return path.contains(pos);
  }


  private List<PositionInterface> generatePath(PositionInterface start, PositionInterface end, List<Room> rooms) {
    List<PositionInterface> path = new ArrayList<>();

    int x = start.getX();
    int y = start.getY();

    while (x != end.getX()) { // Двигаемся по X
      x += Integer.compare(end.getX(), x);
      if (!isInsideRoom(x, y, rooms)) {
        path.add(new Position(x, y));
      }
      if(isDoor(x, y, rooms)) {
        door.add(new Position(x, y));
        path.add(new Position(x, y));
      }
    }

    while (y != end.getY()) { // Двигаемся по Y
      y += Integer.compare(end.getY(), y);
      if (!isInsideRoom(x, y, rooms)) {
        path.add(new Position(x, y));
      }
      if(isDoor(x, y, rooms)) {
        door.add(new Position(x, y));
        path.add(new Position(x, y));
      }
    }

    return path;
  }

  private boolean isInsideRoom(int x, int y, List<Room> rooms) {
    for (Room room : rooms) {
      int roomX = room.getPosition().getX();
      int roomY = room.getPosition().getY();
      int roomWidth = room.getWidth();
      int roomHeight = room.getHeight();

      // Проверяем, находится ли (x, y) внутри границ комнаты
      if (x >= roomX && x < roomX + roomWidth && y >= roomY && y < roomY + roomHeight) {
        return true;
      }
    }
    return false;
  }

  private boolean isDoor(int x, int y, List<Room> rooms) {
    for (Room room : rooms) {
      if (room.getWalls().contains(new Position(x, y))) {
        return true;
      }
    }
    return false;
  }

  public void setPath(List<PositionInterface> path) {
    this.path = path;
  }

  public void setDoor(List<PositionInterface> door) {
    this.door = door;
  }

  public boolean containsPlayer(PositionInterface player) {
    return path.contains(player);
  }
}
