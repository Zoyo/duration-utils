package br.com.rsystem.config;

import br.com.rsystem.Units;
import br.com.rsystem.config.defaults.ConfigDefaultValues;

public final class ConfigDuration {
	private int daysInYear;
	private int daysInMonth;
	private int daysInWeek;
	private int hoursInDay;
	private String textSeparator;
	private Units maxUnit;
	private DurationSymbols symbols;
	
	// **************************************************
	// Constructors
	// **************************************************
	public ConfigDuration() {
		this(ConfigDefaultValues.DAYS_IN_YEAR, ConfigDefaultValues.DAYS_IN_MONTH, ConfigDefaultValues.DAYS_IN_WEEK, ConfigDefaultValues.HOURS_IN_DAY, ConfigDefaultValues.TEXT_SEPARATOR, new DurationSymbols(), Units.WEEK);
	}
	
	public ConfigDuration(int daysInYear, int daysInMonth, int daysInWeek, int hoursInDay, String textSeparator) {
		this(daysInYear, daysInMonth, daysInWeek, hoursInDay, textSeparator, new DurationSymbols(), Units.YEAR);
	}
	
	public ConfigDuration(int daysInYear, int daysInMonth, int daysInWeek, int hoursInDay, String textSeparator, DurationSymbols symbols, Units maxUnit) {
		this.daysInYear = daysInYear;
		this.daysInMonth = daysInMonth;
		this.daysInWeek = daysInWeek;
		this.hoursInDay = hoursInDay;
		this.textSeparator = textSeparator;
		this.symbols = symbols;
		this.maxUnit = maxUnit;
	}
	
	// **************************************************
	// Public methods
	// **************************************************
	public boolean symbolsIsCaseSensitive() {
		return this.symbols.isCaseSensitive();
	}
	
	public Units getUnitFromSymbol(String symbol) {
		return this.symbols.getUnitFromSymbol(symbol);
	}
	
	// **************************************************
	// Default get/set
	// **************************************************
	public int getDaysInYear() {
		return daysInYear;
	}

	public void setDaysInYear(int daysInYear) {
		this.daysInYear = daysInYear;
	}

	public int getDaysInMonth() {
		return daysInMonth;
	}

	public void setDaysInMonth(int daysInMonth) {
		this.daysInMonth = daysInMonth;
	}

	public int getDaysInWeek() {
		return daysInWeek;
	}

	public void setDaysInWeek(int daysInWeek) {
		this.daysInWeek = daysInWeek;
	}

	public int getHoursInDay() {
		return hoursInDay;
	}

	public String getTextSeparator() {
		return textSeparator;
	}
	
	public void setHoursInDay(int hoursInDay) {
		this.hoursInDay = hoursInDay;
	}

	public DurationSymbols getSymbols() {
		return symbols;
	}

	public void setSymbols(DurationSymbols symbols) {
		this.symbols = symbols;
	}

	public Units getMaxUnit() {
		return maxUnit;
	}
	// **************************************************
	// hashCode, equals and toString
	// **************************************************
}
