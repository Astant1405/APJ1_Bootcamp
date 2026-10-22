package Domain.Character.Player;

import Domain.Item.ItemInterface;
import java.util.List;

public interface BagInterface {

  public void putItem(ItemInterface itemInterface);

  public List<ItemInterface> getItems();

  public void setItems(List<ItemInterface> itemInterfaces);

}
