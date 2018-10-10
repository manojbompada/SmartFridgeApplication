package smartFridgeService;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import UserDefinedException.ItemNotFoundException;
import fridgeDao.SmartFridgeDaoImpl;
import model.Item;

public class SmartFridgeManagerImpl implements SmartFridgeManager {
	
	//Managed by spring container if spring framework is used
	private SmartFridgeDaoImpl smartFridgeDaoImpl = new SmartFridgeDaoImpl();

	@Override
	public void handleItemRemoved(String itemUUID) throws ItemNotFoundException {
		
		Item item = smartFridgeDaoImpl.getItem(itemUUID);
		if (item == null) {
			throw new ItemNotFoundException(itemUUID);
		}
		
		smartFridgeDaoImpl.remove(itemUUID);
		System.out.println("An item with UUID "+itemUUID+" is removed from the fridge");
	}

	@Override
	public void handleItemAdded(long itemType, String itemUUID, String name, Double fillFactor) {
		if ("".equals(itemUUID) || itemUUID == null) {
			throw new IllegalArgumentException("Invalid parameter, itemUUID can't be null or empty");
		} 
		
		Item item = smartFridgeDaoImpl.getItem(itemUUID);
		
		if (item == null) {
			// create new item and add to list
			Item newItem = new Item(itemUUID, name, itemType, fillFactor);
			smartFridgeDaoImpl.addItem(newItem);
		} else {
			//update the item fill factor
			item.setFillFactor(fillFactor);
			smartFridgeDaoImpl.update(item);
		}
		
		Item newItem = new Item(itemUUID, name, itemType, fillFactor);
		smartFridgeDaoImpl.addItem(newItem);
		
		System.out.println("An item with UUID "+itemUUID+" is added to fridge");
		
	}

	@Override
	public Object[] getItems(Double fillFactor) {
	
		if (fillFactor == null) {
			throw new IllegalArgumentException("Invalid parameter, fillFactor can't be null");
		}
		
		List<Item> filteredItemsByfillFactor = null;
		
		// get all the items
		Map<String,Item> fridgeItems = smartFridgeDaoImpl.getAllItems();
		
		// get values set from the items map
		Set<Item> items = (Set<Item>) fridgeItems.values();
		
		if (!items.isEmpty()) {
			boolean allItemsInFridgeEmpty = true;
			// check if not at least one item in the fridge is non-empty
			allItemsInFridgeEmpty = !items.stream().anyMatch(x -> x.getFillFactor().doubleValue() > 0.0);
			
			if (allItemsInFridgeEmpty) {
				// if all the containers are empty in the fridge, return entire list
				return items.toArray();
			}
			
			// filter items list by fill factor 
			filteredItemsByfillFactor = items.stream().filter(x -> x.getFillFactor().doubleValue() <= fillFactor && x.getFillFactor().doubleValue() > 0.0)
										.collect(Collectors.toList());
		}
		
		return filteredItemsByfillFactor.toArray();
		
	}

	@Override
	public Double getFillFactor(long itemType) {
		/**
		 * note: I'm assuming item type is not unique for an item i.e many items can have same item type
		 */
		double itemFillFactor = 0.0d;
		
		// get all the items
		Map<String,Item> fridgeItems = smartFridgeDaoImpl.getAllItems();
		
		// get values set from the items map
		Set<Item> items = (Set<Item>) fridgeItems.values();
		
		if (!items.isEmpty()) {
			
			// filter out the items from the list if an item is stopped from tracking, i.e if the item is ignored
			items = items.stream().filter(x -> !x.isItemIgnored()).collect(Collectors.toSet());
			
			// filter by item type
			List<Item> filterByItemsType = null;
			filterByItemsType = items.stream().filter(x -> x.getItemType() == itemType).collect(Collectors.toList());
			
			boolean allItemsOfGivenTypeInFridgeEmpty = true;
			// check if not at least one item in the fridge of given item type is non-empty
			allItemsOfGivenTypeInFridgeEmpty = !filterByItemsType.stream().anyMatch(x -> x.getFillFactor().doubleValue() > 0.0);
			if (allItemsOfGivenTypeInFridgeEmpty) {
				// if all the containers of given item type are empty in the fridge, return  0
				return itemFillFactor;
			}
			
			for (Item item: filterByItemsType) {
				// add the fill factors of all the containers for a given item type
				itemFillFactor += item.getFillFactor().doubleValue();
			}
		}
		
		return itemFillFactor;
	}

	@Override
	public void forgetItem(long itemType) {
		/**
		 * note: I'm assuming item type is not unique for an item i.e many items can have same item type
		 */
		// get all the items
		Map<String,Item> fridgeItems = smartFridgeDaoImpl.getAllItems();
		
		// get values set from the items map
		Set<Item> items = (Set<Item>) fridgeItems.values();
		
		if (!items.isEmpty()) {
			List<Item> filteredByItemsType = null;
			// filter the items by item type
			filteredByItemsType = items.stream().filter(x -> x.getItemType() == itemType).collect(Collectors.toList());
			
			for (Item item: filteredByItemsType) {
				item.setItemIgnored(true);
			}
			
			update(filteredByItemsType);
		}
		
	}

	private void update(List<Item> filteredByItemsType) {
		
		if (filteredByItemsType.isEmpty()) {
			return;
		}
		smartFridgeDaoImpl.updateItems(filteredByItemsType);
	}

}
