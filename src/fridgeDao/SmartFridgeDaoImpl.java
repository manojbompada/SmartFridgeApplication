package fridgeDao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Item;

public class SmartFridgeDaoImpl implements SmartFridgeDao {

	//HashMap for maintaining the list of items with UUID as key
	static Map<String,Item> fridgeItems = new HashMap<String,Item>();
		
	@Override
	public Map<String, Item> getAllItems() {
		return fridgeItems;
	}
	
	@Override
	public Item getItem(String itemUUID) {
		return fridgeItems.get(itemUUID);
	}
	
	@Override
	public void remove(String itemUUID) {
		fridgeItems.remove(itemUUID);
	}

	@Override
	public void addItem(Item newItem) {
		fridgeItems.put(newItem.getUuid(), newItem);
	}

	@Override
	public void update(Item item) {
		// remove the existing item 
		remove(item.getUuid());
		
		// add the updated item
		addItem(item);
		
	}

	@Override
	public void updateItems(List<Item> itemsList) {
		
		for (Item item: itemsList) {
			fridgeItems.put(item.getUuid(), item);
		}
		
	}

	

	
	
}
