package Domain.Navigator;

public interface PositionInterface {

  int getX();
  int getY();
  void setX(int x);
  void setY(int y);

  PositionInterface move(Direction direction, int distance);

}
