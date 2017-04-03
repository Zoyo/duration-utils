package br.com.rsystem.builders;

import static br.com.rsystem.utils.DurationUtils.ensureHasNoInvalidSymbol;
import static br.com.rsystem.utils.DurationUtils.ensureObjectNotNull;
import static br.com.rsystem.utils.DurationUtils.loadPropertiesFrom;

import java.io.InputStream;
import java.util.Properties;

import br.com.rsystem.config.DurationSymbols;
import br.com.rsystem.config.defaults.ConfigDefaultValues;
import br.com.rsystem.config.properties.DurationSymbolsReadProperties;

public class DurationSymbolBuilder {
	private String yearSymbol = ConfigDefaultValues.YEAR_SYMBOL;      
	private String monthSymbol = ConfigDefaultValues.MONTH_SYMBOL;     
	private String weekSymbol = ConfigDefaultValues.WEEK_SYMBOL;      
	private String daySymbol = ConfigDefaultValues.DAY_SYMBOL;       
	private String hourSymbol = ConfigDefaultValues.HOUR_SYMBOL;      
	private String minuteSymbol = ConfigDefaultValues.MINUTE_SYMBOL;    
	private String secondSymbol = ConfigDefaultValues.SECOND_SYMBOL;    
	private String millisecondSymbol = ConfigDefaultValues.MILLISECOND_SYMBOL;
	
	public DurationSymbolBuilder withYearSymbol(String yearSymbol) throws IllegalArgumentException {
		ensureHasNoInvalidSymbol(yearSymbol);
		
		this.yearSymbol = yearSymbol;
		return this;
	}
	
	public DurationSymbolBuilder withMonthSymbol(String monthSymbol) throws IllegalArgumentException {
		ensureHasNoInvalidSymbol(monthSymbol);
		
		this.monthSymbol = monthSymbol;
		return this;
	}
	
	public DurationSymbolBuilder withWeekSymbol(String weekSymbol) throws IllegalArgumentException {
		ensureHasNoInvalidSymbol(weekSymbol);
		
		this.weekSymbol = weekSymbol;
		return this;
	}
	
	public DurationSymbolBuilder withDaySymbol(String daySymbol) throws IllegalArgumentException {
		ensureHasNoInvalidSymbol(daySymbol);
		
		this.daySymbol = daySymbol;
		return this;
	}
	
	public DurationSymbolBuilder withHourSymbol(String hourSymbol) throws IllegalArgumentException {
		ensureHasNoInvalidSymbol(hourSymbol);
		
		this.hourSymbol = hourSymbol;
		return this;
	}
	
	public DurationSymbolBuilder withMinuteSymbol(String minuteSymbol) throws IllegalArgumentException {
		ensureHasNoInvalidSymbol(minuteSymbol);
		
		this.minuteSymbol = minuteSymbol;
		return this;
	}

	public DurationSymbolBuilder withSecondSymbol(String secondSymbol) throws IllegalArgumentException {
		ensureHasNoInvalidSymbol(secondSymbol);
		
		this.secondSymbol = secondSymbol;
		return this;
	}

	public DurationSymbolBuilder withMillisecondSymbol(String millisecondSymbol) throws IllegalArgumentException {
		ensureHasNoInvalidSymbol(millisecondSymbol);
		
		this.millisecondSymbol = millisecondSymbol;
		return this;
	}
	
	public DurationSymbolBuilder loadFromFile(String fileName) throws IllegalArgumentException {
		return this.loadFromFile(this.getClass().getClassLoader().getResourceAsStream(fileName));
	}
	
	public DurationSymbolBuilder loadFromFile(InputStream inputStreamConfig) throws IllegalArgumentException {
		ensureObjectNotNull(inputStreamConfig, "Config file");
		
		Properties props = loadPropertiesFrom(inputStreamConfig);
		
		this.withYearSymbol(DurationSymbolsReadProperties.YEAR.read(props));
		this.withMonthSymbol(DurationSymbolsReadProperties.MONTH.read(props));
		this.withWeekSymbol(DurationSymbolsReadProperties.WEEK.read(props));
		this.withDaySymbol(DurationSymbolsReadProperties.DAY.read(props));
		this.withHourSymbol(DurationSymbolsReadProperties.HOUR.read(props));
		this.withMinuteSymbol(DurationSymbolsReadProperties.MINUTE.read(props));
		this.withSecondSymbol(DurationSymbolsReadProperties.SECOND.read(props));
		this.withMillisecondSymbol(DurationSymbolsReadProperties.MILLISECOND.read(props));
		
		return this;
	}
	
	public DurationSymbols build() {
		StringBuilder textSymbols = new StringBuilder();
		textSymbols
			.append(this.yearSymbol).append("*")
			.append(this.monthSymbol).append("*")
			.append(this.weekSymbol).append("*")
			.append(this.daySymbol).append("*")
			.append(this.hourSymbol).append("*")
			.append(this.minuteSymbol).append("*")
			.append(this.secondSymbol).append("*")
			.append(this.millisecondSymbol).append("*");
		
		return new DurationSymbols(textSymbols.toString());
	}
}
