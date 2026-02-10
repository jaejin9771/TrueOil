package dto;

public class MaintenanceStatusDto {
	
    private int mId;
    private int userId;
    private String itemName;
    private int lastReplaceMileage;
    
	public int getmId() {
		return mId;
	}
	public void setmId(int mId) {
		this.mId = mId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public int getLastReplaceMileage() {
		return lastReplaceMileage;
	}
	public void setLastReplaceMileage(int lastReplaceMileage) {
		this.lastReplaceMileage = lastReplaceMileage;
	}
    
    
}