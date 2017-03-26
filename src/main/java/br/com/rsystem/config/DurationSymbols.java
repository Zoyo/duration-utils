package br.com.rsystem.config;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import br.com.rsystem.Units;
import br.com.rsystem.config.defaults.ConfigDefaultValues;

public final class DurationSymbols {
	private static final int MAX_SYMBOLS = 8;
	
	private Map<Units, String> symbols;
	private boolean caseSensitive;
	
	
	// **************************************************
	// Constructors
	// **************************************************
	public DurationSymbols() {
		this.setDefaultSymbols();
	}
	
	public DurationSymbols(String symbols) {
		if(symbols == null || symbols.trim().isEmpty()) {
			throw new IllegalArgumentException("No symbols reported");
		}
		
		this.setDefaultSymbols();
		this.updateSymbols(extractSymbols(symbols));
		this.assureSymbols();
	}

	// **************************************************
	// Public Methods
	// **************************************************
	public Units getUnitFromSymbol(String symbol) {
		Set<Entry<Units,String>> entrySet = this.symbols.entrySet();
		for (Entry<Units, String> entry : entrySet) {
			if(this.caseSensitive) {
				if(entry.getValue().equals(symbol)) {
					return entry.getKey();
				}
			} else {
				if(entry.getValue().equalsIgnoreCase(symbol)) {
					return entry.getKey();
				}
			}
		}
		
		throw new IllegalArgumentException("Symbol not found: " + symbol);
	}
	
	// **************************************************
	// Private Methods
	// **************************************************
	private void setDefaultSymbols() {
		this.symbols = new LinkedHashMap<Units, String>(MAX_SYMBOLS);
		
		this.symbols.put(Units.YEAR,        ConfigDefaultValues.YEAR_SYMBOL);
		this.symbols.put(Units.MONTH,       ConfigDefaultValues.MONTH_SYMBOL);
		this.symbols.put(Units.WEEK,        ConfigDefaultValues.WEEK_SYMBOL);
		this.symbols.put(Units.DAY,         ConfigDefaultValues.DAY_SYMBOL);
		this.symbols.put(Units.HOUR,        ConfigDefaultValues.HOUR_SYMBOL);
		this.symbols.put(Units.MINUTE,      ConfigDefaultValues.MINUTE_SYMBOL);
		this.symbols.put(Units.SECOND,      ConfigDefaultValues.SECOND_SYMBOL);
		this.symbols.put(Units.MILLISECOND, ConfigDefaultValues.MILLISECOND_SYMBOL);
	}
	
	private String[] extractSymbols(String symbols) {
		String[] splitedSymbols = symbols.split("[-_/,* ]", (MAX_SYMBOLS  + 1));
		
		if(splitedSymbols.length > 1) {
			return splitedSymbols;
		}
		
		if(symbols.length() > 1) {
			int symbolsSize = symbols.length();
			int iterate = Math.min(symbolsSize, MAX_SYMBOLS);
			
			String[] ret = new String[iterate];
			for (int i = 0; i < iterate; i++) {
				ret[i] = Character.toString(symbols.charAt(i));
			}
			
			return ret;
			
		} else {
			return new String[] {symbols};
		}
	}
	
	private void updateSymbols(String[] newSymbols) {
		for (int i = 0; i < newSymbols.length; i++) {
			this.symbols.put(Units.values()[i], newSymbols[i]);
		}
	}
	
	private void assureSymbols() {
		String[] symbolsReported = this.symbols.values().toArray(new String[0]);
		for (int i = 0; i < (MAX_SYMBOLS - 1); i++) {
			for(int j = (i + 1); j < MAX_SYMBOLS; j++) {
				if (symbolsReported[i].equals(symbolsReported[j])) {
					throw new IllegalStateException("Duplicated Unit: " + symbolsReported[i]);
					
				} else if(symbolsReported[i].equalsIgnoreCase(symbolsReported[j])) {
					this.caseSensitive = true;
				}
			}
		}
	}
	
	// **************************************************
	// Default getters
	// **************************************************
	public String getYear() {
		return this.symbols.get(Units.YEAR);
	}

	public String getMonth() {
		return this.symbols.get(Units.MONTH);
	}

	public String getWeek() {
		return this.symbols.get(Units.WEEK);
	}

	public String getDay() {
		return this.symbols.get(Units.DAY);
	}

	public String getHour() {
		return this.symbols.get(Units.HOUR);
	}

	public String getMinute() {
		return this.symbols.get(Units.MINUTE);
	}

	public String getSecond() {
		return this.symbols.get(Units.SECOND);
	}

	public String getMillisecond() {
		return this.symbols.get(Units.MILLISECOND);
	}
	
	public boolean isCaseSensitive() {
		return caseSensitive;
	}

	// **************************************************
	// hashCode, equals and toString
	// **************************************************
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (caseSensitive ? 1231 : 1237);
		result = prime * result + ((symbols == null) ? 0 : symbols.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DurationSymbols other = (DurationSymbols) obj;
		if (caseSensitive != other.caseSensitive)
			return false;
		if (symbols == null) {
			if (other.symbols != null)
				return false;
		} else if (!symbols.equals(other.symbols))
			return false;
		return true;
	}

	@Override
	public String toString() {
		Set<Entry<Units,String>> entrySet = this.symbols.entrySet();
		StringBuilder toStr = new StringBuilder("{");
		for (Entry<Units, String> entry : entrySet) {
			toStr.append(entry.getKey()).append(":").append(entry.getValue()).append(", ");
		}
		
		return "DurationSymbols [symbols=" + toStr.toString().replaceAll(", $", "}") + ", caseSensitive=" + caseSensitive + "]";
	}
	
	
}
