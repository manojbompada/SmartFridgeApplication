package model;

import java.util.List;

public class SmartFridge {
	
	public List<Item> getItemsList() {
		return itemsList;
	}

	public void setItemsList(List<Item> itemsList) {
		this.itemsList = itemsList;
	}

	private List<Item> itemsList;
	
}
