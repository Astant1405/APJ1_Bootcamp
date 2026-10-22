package Domain.Navigator;

import java.util.Objects;

public class Position implements PositionInterface {
  private int x;
  private int y;

  public Position() {}
  public Position(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  public void setX(int x) {
    this.x = x;
  }

  public void setY(int y) {
    this.y = y;
  }

  public Position move(Direction direction, int distance) {
    int newX = x;
    int newY = y;
    switch (direction) {
      case UP -> newY -= distance;
      case DOWN -> newY += distance;
      case LEFT -> newX -= distance;
      case RIGHT -> newX += distance;
    }
    return new Position(newX, newY);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Position position = (Position) o;
    return x == position.x && y == position.y;
  }

  @Override
  public int hashCode() {
    return Objects.hash(x, y);
  }

  @Override
  public String toString() {
    return "Position{" +
        "x=" + x +
        ", y=" + y +
        '}';
  }

}