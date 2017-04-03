package br.com.rsystem.builders;

import static br.com.rsystem.utils.DurationUtils.ensureGreaterThanZero;
import static br.com.rsystem.utils.DurationUtils.ensureObjectNotNull;

import java.io.InputStream;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import br.com.rsystem.Units;
import br.com.rsystem.config.ConfigDuration;
import br.com.rsystem.config.DurationSymbols;
import br.com.rsystem.config.defaults.ConfigDefaultValues;
import br.com.rsystem.config.properties.ConfigDurationReadProperties;
import br.com.rsystem.utils.DurationUtils;

public class ConfigDurationBuilder {
	private int daysInYear = ConfigDefaultValues.DAYS_IN_YEAR;
	private int daysInMonth = ConfigDefaultValues.DAYS_IN_MONTH;
	private int daysInWeek = ConfigDefaultValues.DAYS_IN_WEEK;
	private int hoursInDay = ConfigDefaultValues.HOURS_IN_DAY;
	private String textSeparator = ConfigDefaultValues.TEXT_SEPARATOR;
	private Set<Units> useUnits = new HashSet<Units>();
	private DurationSymbols symbols;

	public ConfigDurationBuilder withDaysInYear(int daysInYear) throws IllegalArgumentException {
		ensureGreaterThanZero(daysInYear, "daysInYear");
		
		this.daysInYear = daysInYear; 
		return this;
	}
	
	public ConfigDurationBuilder withDaysInMonth(int daysInMonth) throws IllegalArgumentException {
		ensureGreaterThanZero(daysInMonth, "daysInMonth");
		
		this.daysInMonth = daysInMonth; 
		return this;
	}

	public ConfigDurationBuilder withDaysInWeek(int daysInWeek) throws IllegalArgumentException {
		ensureGreaterThanZero(daysInWeek, "daysInWeek");
		
		this.daysInWeek = daysInWeek; 
		return this;
	}

	public ConfigDurationBuilder withHoursInDay(int hoursInDay) throws IllegalArgumentException {
		ensureGreaterThanZero(hoursInDay, "hoursInDay");
		
		this.hoursInDay = hoursInDay;
		return this;
	}

	public ConfigDurationBuilder withTextSeparator(String textSeparator ) throws IllegalArgumentException {
		ensureObjectNotNull(textSeparator, "Text separator");
		
		this.textSeparator = textSeparator; 
		return this;
	}

	public ConfigDurationBuilder addUnit(Units unitToUse) throws IllegalArgumentException {
		ensureObjectNotNull(unitToUse, "Unit to use");
		
		return addUnits(new Units[] {unitToUse});
	}

	public ConfigDurationBuilder addUnits(Units... useUnits) throws IllegalArgumentException {
		for (Units u : useUnits) {
			ensureObjectNotNull(u, "Unit to use");
			this.useUnits.add(u);
		}
		
		return this;
	}
	
	public ConfigDurationBuilder withSymbols(DurationSymbols symbols ) throws IllegalArgumentException {
		ensureObjectNotNull(symbols, "Duration symbols");
		
		this.symbols = symbols; 
		return this;
	}
	
	public ConfigDurationBuilder loadFromFile(String fileName) {
		return this.loadFromFile(this.getClass().getClassLoader().getResourceAsStream(fileName));
	}
	
	public ConfigDurationBuilder loadFromFile(InputStream fisConfig) {
		ensureObjectNotNull(fisConfig, "Config file");
		
		Properties props = DurationUtils.loadPropertiesFrom(fisConfig);
		
		this.withDaysInYear(new Integer(ConfigDurationReadProperties.DAYS_IN_YEAR.read(props)));
		this.withDaysInMonth(new Integer(ConfigDurationReadProperties.DAYS_IN_MONTH.read(props)));
		this.withDaysInWeek(new Integer(ConfigDurationReadProperties.DAYS_IN_WEEK.read(props)));
		this.withHoursInDay(new Integer(ConfigDurationReadProperties.HOURS_IN_DAY.read(props)));
		this.withTextSeparator(ConfigDurationReadProperties.TEXT_SEPARATOR.read(props));
		
		String useUnits = ConfigDurationReadProperties.USE_UNITS.read(props, null);
		if(useUnits != null) {
			String[] unitsLoaded = useUnits.split(",");
			for (String unit : unitsLoaded) {
				this.addUnit(Units.getFromPredicate(unit));
			}
		}
		
		return this;
	}

	public ConfigDuration build() {
		return 
				new ConfigDuration(
						this.daysInYear,
						this.daysInMonth,
						this.daysInWeek,
						this.hoursInDay,
						this.textSeparator,
						this.symbols,
						this.useUnits.isEmpty() ? ConfigDefaultValues.USE_UNITS : this.useUnits);
	}
}
