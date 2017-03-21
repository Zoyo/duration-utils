package br.com.rsystem;

public enum Units {
	MILLISECONDS("L", (1)),
	SECONDS("S", (1 * 1000)),
	MINUTES("M", (1 * 1000 * 60)),
	HOUR("H", (1 * 1000 * 60 * 60)),
	DAY("D", (1 * 1000 * 60 * 60 * 24)),
	WEEK("W", (1 * 1000 * 60 * 60 * 24 * 7)),
	MONTH("T", (1 * 1000 * 60 * 60 * 24 * 30)),
	YEAR("Y", (1 * 1000 * 60 * 60 * 24 * 365));
	
	private String unitCode;
	private Integer millisecondsFactor;

	private Units(String unitCode, Integer millisecondsFactor) {
		this.unitCode = unitCode;
		this.millisecondsFactor = millisecondsFactor;
	}
	
	public String getUnitCode() {
		return unitCode;
	}
	
	public Integer getMillisecondsFactor() {
		return millisecondsFactor;
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
