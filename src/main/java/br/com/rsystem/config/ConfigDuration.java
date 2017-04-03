package br.com.rsystem.config;

import java.util.Set;

import br.com.rsystem.Units;
import br.com.rsystem.config.defaults.ConfigDefaultValues;

public final class ConfigDuration {
	private int daysInYear;
	private int daysInMonth;
	private int daysInWeek;
	private int hoursInDay;
	private String textSeparator;
	private Set<Units> availableUnits;
	private DurationSymbols symbols;
	
	// **************************************************
	// Constructors
	// **************************************************
	public ConfigDuration() {
		this(ConfigDefaultValues.DAYS_IN_YEAR, ConfigDefaultValues.DAYS_IN_MONTH, ConfigDefaultValues.DAYS_IN_WEEK, ConfigDefaultValues.HOURS_IN_DAY, ConfigDefaultValues.TEXT_SEPARATOR, new DurationSymbols(), ConfigDefaultValues.USE_UNITS);
	}
	
	public ConfigDuration(int daysInYear, int daysInMonth, int daysInWeek, int hoursInDay, String textSeparator) {
		this(daysInYear, daysInMonth, daysInWeek, hoursInDay, textSeparator, new DurationSymbols(), ConfigDefaultValues.USE_UNITS);
	}
	
	public ConfigDuration(int daysInYear, int daysInMonth, int daysInWeek, int hoursInDay, String textSeparator, DurationSymbols symbols, Set<Units> availableUnits) {
		this.daysInYear = daysInYear;
		this.daysInMonth = daysInMonth;
		this.daysInWeek = daysInWeek;
		this.hoursInDay = hoursInDay;
		this.textSeparator = textSeparator;
		this.symbols = symbols;
		this.availableUnits = availableUnits;
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

	public int getMaxUnit() {
		int maxUnit = 0;
		for(Units u : this.availableUnits) {
			maxUnit |= u.bitCode();
		}
		return maxUnit;
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
	// **************************************************
	// hashCode, equals and toString
	// **************************************************
}
