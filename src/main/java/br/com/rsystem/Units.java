package br.com.rsystem;

public enum Units {
	MILLISECONDS("L"),
	SECONDS("S"),
	MINUTES("M"),
	HOUR("H"),
	DAY("D"),
	WEEK("W"),
	MONTH("T"),
	YEAR("Y");
	
	private String unitCode;

	private Units(String unitCode) {
		this.unitCode = unitCode;
	}
	
	public String getUnitCode() {
		return unitCode;
	}
	
	public static Units getFromPredicate(String predicate) {
		for (Units u : values()) {
			if(u.getUnitCode().equalsIgnoreCase(predicate)) {
				return u;
			}
		}
		
		return null;
	}
}
