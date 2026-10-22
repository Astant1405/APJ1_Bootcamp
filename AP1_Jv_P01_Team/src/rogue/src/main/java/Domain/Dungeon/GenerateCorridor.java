package Domain.Dungeon;

import Domain.Navigator.PositionInterface;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

public class GenerateCorridor {

  public static List<Corridor> generateCorridors(List<Room> rooms) {
    List<Corridor> corridors = new ArrayList<>();
    // Добавляем set для хранения уже связанных комнат
    Set<Room> connectedRooms = new HashSet<>();
    // Создаем очередь с сортировкой для хранения ребер
    PriorityQueue<Edge> edgesQueue = new PriorityQueue<>(Comparator.comparingInt(e -> e.distance));

    // Создаём рёбра только между основными комнатами
    for (int i = 0; i < rooms.size(); i++) {
      for (int j = i + 1; j < rooms.size(); j++) {
        Room r1 = rooms.get(i);
        Room r2 = rooms.get(j);
        int distance = getManhattanDistance(r1.getCenter(), r2.getCenter());
        edgesQueue.add(new Edge(r1, r2, distance));
      }
    }

    // Начинаем с первой основной комнаты
    for (Room room : rooms) {
      if (room.isStart()) {
        connectedRooms.add(room);
        break;
      }
    }

    while (connectedRooms.size() < rooms.size()) {
      //  Хранит ребро с минимальной длиной между связанной комнатой и несвязанной
      Edge shortestEdge = null;

      //  Перебираем все рёбра в очереди edgesQueue.
      //  Ребра в очереди отсортированы по возрастанию расстояний между комнатами
      //  Это дает нам минимальное расстояние между связанной комнатой и несвязанной
      for (Edge edge : edgesQueue) {
        if (connectedRooms.contains(edge.room1) && !connectedRooms.contains(edge.room2)) {
          shortestEdge = edge;
          break;
        } else if (connectedRooms.contains(edge.room2) && !connectedRooms.contains(edge.room1)) {
          shortestEdge = edge;
          break;
        }
      }
      // Нет новых соединений
      if (shortestEdge == null) {
        break;
      }

      // Генерируем путь между этими точками

      corridors.add(new Corridor(shortestEdge.room1.getCenter(), shortestEdge.room2.getCenter(), rooms));
      // Заносим в сет связанные комнаты
      connectedRooms.add(shortestEdge.room1);
      connectedRooms.add(shortestEdge.room2);
      // Удаляем ребро из очереди
      edgesQueue.remove(shortestEdge);
    }

    return corridors;
  }



  private static int getManhattanDistance(PositionInterface p1, PositionInterface p2) {
    return Math.abs(p1.getX() - p2.getX()) + Math.abs(p1.getY() - p2.getY());
  }

  // Класс для хранения 2 комнат и расстояния между ними
  private static class Edge {
    Room room1, room2;
    int distance;

    Edge(Room room1, Room room2, int distance) {
      this.room1 = room1;
      this.room2 = room2;
      this.distance = distance;
    }
  }

}
