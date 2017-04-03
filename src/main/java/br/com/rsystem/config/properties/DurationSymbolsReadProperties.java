package br.com.rsystem.config.properties;

import java.util.Properties;

import br.com.rsystem.config.defaults.ConfigDefaultValues;

public enum DurationSymbolsReadProperties {
	YEAR("DurationSymbols.year", ConfigDefaultValues.YEAR_SYMBOL), 
	MONTH("DurationSymbols.month", ConfigDefaultValues.MONTH_SYMBOL),
	WEEK("DurationSymbols.week", ConfigDefaultValues.WEEK_SYMBOL), 
	DAY("DurationSymbols.day", ConfigDefaultValues.DAY_SYMBOL),
	HOUR("DurationSymbols.hour", ConfigDefaultValues.HOUR_SYMBOL), 
	MINUTE("DurationSymbols.minute", ConfigDefaultValues.MINUTE_SYMBOL),
	SECOND("DurationSymbols.second", ConfigDefaultValues.SECOND_SYMBOL),
	MILLISECOND("DurationSymbols.millisecond", ConfigDefaultValues.MILLISECOND_SYMBOL);
	
	private String key;
	private String defaultIfNull;

	private DurationSymbolsReadProperties(String key, String defaultIfNull) {
		this.key = key;
		this.defaultIfNull = defaultIfNull;
	}
	
	public String read(Properties config, String defaultIfNull) {
		String prop = config.getProperty(this.key);
		
		return prop != null ? prop : defaultIfNull;
	}
	
	public String read(Properties config) {
		return this.read(config, this.defaultIfNull);
	}
}
