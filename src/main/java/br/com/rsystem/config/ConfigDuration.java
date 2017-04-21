package br.com.rsystem.config;

import java.util.Collections;
import java.util.Set;

import br.com.rsystem.Units;
import br.com.rsystem.config.defaults.ConfigDefaultValues;

public final class ConfigDuration {
	private int daysInYear;
	private int daysInMonth;
	private int daysInWeek;
	private int hoursInDay;
	private String textSeparator;
	private Set<Units> useUnits;
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
	
	public ConfigDuration(int daysInYear, int daysInMonth, int daysInWeek, int hoursInDay, String textSeparator, DurationSymbols symbols, Set<Units> useUnits) {
		this.daysInYear = daysInYear;
		this.daysInMonth = daysInMonth;
		this.daysInWeek = daysInWeek;
		this.hoursInDay = hoursInDay;
		this.textSeparator = textSeparator;
		this.symbols = symbols;
		this.useUnits = useUnits;
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

	public int getUnitsOn() {
		int unitsOn = 0;
		for(Units u : this.useUnits) {
			unitsOn |= u.bitCode();
		}
		return unitsOn;
	}

	// **************************************************
	// Default get/set
	// **************************************************
	public int getDaysInYear() {
		return daysInYear;
	}

	public int getDaysInMonth() {
		return daysInMonth;
	}

	public int getDaysInWeek() {
		return daysInWeek;
	}

	public int getHoursInDay() {
		return hoursInDay;
	}

	public String getTextSeparator() {
		return textSeparator;
	}
	
	public DurationSymbols getSymbols() {
		return symbols;
	}

	public Set<Units> getUseUnits() {
		return Collections.unmodifiableSet(useUnits);
	}
	
	// **************************************************
	// hashCode, equals and toString
	// **************************************************
}
