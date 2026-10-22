package Domain.Character.Player;

import Domain.Item.ItemInterface;
import java.util.ArrayList;
import java.util.List;

public class Bag implements Domain.Character.Player.BagInterface {

  private List<ItemInterface> itemInterfaces = new ArrayList<>();

  public void putItem(ItemInterface itemInterface){
    int maxCapacity = 9;
    if(itemInterfaces.size() < maxCapacity){
      itemInterfaces.add(itemInterface);
    }
  }

  public List<ItemInterface> getItems() {
    return itemInterfaces;
  }

  public void setItems(List<ItemInterface> itemInterfaces) {
    this.itemInterfaces = itemInterfaces;
  }
}
