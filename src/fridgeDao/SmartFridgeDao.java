package fridgeDao;

import java.util.List;
import java.util.Map;

import model.Item;

public interface SmartFridgeDao {

	/**
	 * removes item from fridge 
	 * @param itemUUID
	 */
	void remove(String itemUUID);

	/**
	 * gets all the items in the fridge
	 * @return
	 */
	Map<String, Item> getAllItems();

	/**
	 * add item to fridge
	 * @param newItem
	 */
	void addItem(Item newItem);

	/**
	 * get item from fridge
	 * @param itemUUID
	 * @return
	 */
	Item getItem(String itemUUID);

	/**
	 * update an item in fridge
	 * @param itemUUID
	 * @return
	 */
	void update(Item item);

	/**
	 * updates the list of items in the fridge
	 * @param itemsList
	 */
	void updateItems(List<Item> itemsList);

	
}
