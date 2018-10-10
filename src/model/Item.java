package model;

public class Item {

	private String uuid;
	private String name;
	private long itemType; // I'm assuming item type is not unique for an item i.e many items can have same item type
	private Double fillFactor;
	private boolean isItemIgnored;  // this attribute is used to make the item remove from tracking
	
	public Item(String uuid, String name, long itemType, Double fillFactor) {
		this.uuid = uuid;
		this.name = name;
		this.itemType = itemType;
		this.fillFactor = fillFactor;
		this.isItemIgnored = false; // by default set this to false
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getFillFactor() {
		return fillFactor;
	}
	public void setFillFactor(Double fillFactor) {
		this.fillFactor = fillFactor;
	}
	public long getItemType() {
		return itemType;
	}
	public void setItemType(long itemType) {
		this.itemType = itemType;
	}
	
	public boolean isItemIgnored() {
		return isItemIgnored;
	}
	public void setItemIgnored(boolean isItemIgnored) {
		this.isItemIgnored = isItemIgnored;
	}
	
	@Override
	public String toString() {
		return "Item [uuid=" + uuid + ", name=" + name + ", itemType=" + itemType + ", fillFactor=" + fillFactor
				+ ", isItemIgnored=" + isItemIgnored + "]";
	}
	
}
