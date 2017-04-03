package br.com.rsystem.config.properties;

import java.util.Properties;

import br.com.rsystem.config.defaults.ConfigDefaultValues;

public enum ConfigDurationReadProperties {
	DAYS_IN_YEAR("ConfigDuration.days.in.year", String.valueOf(ConfigDefaultValues.DAYS_IN_YEAR)),
	DAYS_IN_MONTH("ConfigDuration.days.in.month", String.valueOf(ConfigDefaultValues.DAYS_IN_MONTH)),
	DAYS_IN_WEEK("ConfigDuration.days.in.week", String.valueOf(ConfigDefaultValues.DAYS_IN_WEEK)),
	HOURS_IN_DAY("ConfigDuration.hours.in.day", String.valueOf(ConfigDefaultValues.HOURS_IN_DAY)),
	TEXT_SEPARATOR("ConfigDuration.text.separator", ConfigDefaultValues.TEXT_SEPARATOR),
	USE_UNITS("ConfigDuration.use.units", ConfigDefaultValues.USE_UNITS_STR);
	
	private String key;
	private String defaultIfNull;

	private ConfigDurationReadProperties(String key, String defaultIfNull) {
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
