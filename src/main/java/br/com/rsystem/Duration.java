package br.com.rsystem;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.rsystem.config.ConfigDuration;
import br.com.rsystem.config.defaults.UnitBitValues;

public final class Duration {
	private static final String DURATION_PATTERN = "\\d+[a-z]+";
	private final String originalDuration;
	private final String duration;
	private final Map<Units, Long> unitsValues;
	private final Long totalMilliseconds;
	private final ConfigDuration config;

	
	// **************************************************
	// Constructors
	// **************************************************
	public Duration(String textDuration) {
		this(textDuration, new ConfigDuration());
	}
	
	public Duration(String textDuration, ConfigDuration config) {
		this.config = config;
		
		this.unitsValues = new LinkedHashMap<Units, Long>(Units.values().length);
		this.totalMilliseconds = setup(textDuration, this.config);
		this.originalDuration = textDuration;
		this.duration = this.normalizeDuration(this.totalMilliseconds);
	}
	
	public Duration(Long totalMilliseconds) {
		this(totalMilliseconds, new ConfigDuration());
	}
	
	public Duration(Long totalMilliseconds, ConfigDuration config) {
		this.config = config;
		
		this.totalMilliseconds = totalMilliseconds;
		this.originalDuration = this.normalizeDuration(this.totalMilliseconds);
		this.duration = this.originalDuration;
		this.unitsValues = new LinkedHashMap<Units, Long>(Units.values().length);
		
		setup(this.duration, this.config);
	}
	
	public Duration(Long totalTime, Units unit) {
		this(totalTime, unit, new ConfigDuration());
	}
	
	public Duration(Long totalTime, Units unit, ConfigDuration config) {
		this(totalTime * unit.getInMillis(config));
	}
	
	public Duration(Calendar date) {
		this(date, new ConfigDuration());
	}
	
	public Duration(Calendar date, ConfigDuration config) {
		this.config = config;
		this.totalMilliseconds = date.getTimeInMillis() - Calendar.getInstance().getTimeInMillis() + 1000;
		this.unitsValues = new LinkedHashMap<Units, Long>(Units.values().length);
		this.originalDuration = this.normalizeDuration(this.totalMilliseconds);
		this.duration = this.originalDuration;
		setup(this.duration, this.config);
		
	}
	
	// **************************************************
	// Public methods
	// **************************************************
	public Duration add(Duration duration) {
		Map<Units, Long> acumulateUnits = this.acumulateUnits(duration.getUnitsValues());
		Long millisAcumulated = this.unitsToMillis(acumulateUnits, this.config);
		
		return new Duration(millisAcumulated, this.config);
	}
	
	public Duration add(String duration) {
		return new Duration(duration, this.config).add(this);
	}
	
	public Duration add(Long milliseconds) {
		return new Duration(milliseconds, this.config).add(this);
	}
	
	public Long toSeconds() {
		return this.totalMilliseconds / Units.SECOND.getInMillis(this.config);
	}
	
	public Calendar getDateFromDuration() {
		Calendar nowPlusDuration = Calendar.getInstance();
		nowPlusDuration.add(Calendar.MILLISECOND, this.totalMilliseconds.intValue());
		return nowPlusDuration;
	}
	
	public Calendar calculateDate(Calendar date) {
		Calendar plusDuration = (Calendar) date.clone();
		plusDuration.add(Calendar.MILLISECOND, this.totalMilliseconds.intValue());
		return plusDuration;
	}
	
	public Calendar calculateDate(Date date) {
		Calendar plusDuration = Calendar.getInstance();
		plusDuration.setTime(date);
		plusDuration.add(Calendar.MILLISECOND, this.totalMilliseconds.intValue());
		return plusDuration;
	}
	
	// **************************************************
	// Private methods
	// **************************************************
	private String extractUnitSymbol(String unitFull) {
		if(!unitFull.matches(DURATION_PATTERN)) {
			throw new IllegalArgumentException("Inválid unit format: " + unitFull);
		}
		
		return unitFull.replaceAll("\\d", "");
	}
	
	private Long extractValue(String unitFull) {
		if(!unitFull.matches(DURATION_PATTERN)) {
			throw new IllegalArgumentException("Inválid unit format: " + unitFull);
		}
		
		return new Long(unitFull.replaceAll("\\D", ""));
	}
	
	private String normalizeDuration(Long milliseconds) {
		int unitsOn = config.getMaxUnit();
		
		long ms = milliseconds;
		
		Long totalYears = null;
		if((UnitBitValues.YEAR & unitsOn) > 0) {
			Long yearInMillis = Units.YEAR.getInMillis(this.config);
			totalYears = ms / yearInMillis;
			ms -= (totalYears * yearInMillis);
		}
		
		Long totalMonths = null;
		if((UnitBitValues.MONTH & unitsOn) > 0) {
			Long monthInMillis = Units.MONTH.getInMillis(this.config);
			totalMonths = ms / monthInMillis;
			ms -= (totalMonths * monthInMillis);
		}
		
		Long totalWeeks = null;
		if((UnitBitValues.WEEK & unitsOn) > 0) {
			Long weekInMillis = Units.WEEK.getInMillis(this.config);
			totalWeeks = ms / weekInMillis;
			ms -= (totalWeeks * weekInMillis);
		}
		
		Long totalDays = null;
		if((UnitBitValues.DAY & unitsOn) > 0) {
			Long dayInMillis = Units.DAY.getInMillis(this.config);
			totalDays = ms / dayInMillis;
			ms -= (totalDays * dayInMillis);
		}
		
		Long totalHours = null;
		if((UnitBitValues.HOUR & unitsOn) > 0) {
			Long hourInMillis = Units.HOUR.getInMillis(this.config);
			totalHours = ms / hourInMillis;
			ms -= (totalHours * hourInMillis);
		}
		
		Long totalMinutes  = null;
		if((UnitBitValues.MINUTE & unitsOn) > 0) {
			Long minuteInMillis = Units.MINUTE.getInMillis(this.config);
			totalMinutes = ms / minuteInMillis;
			ms -= (totalMinutes * minuteInMillis);
		}
		
		Long totalSeconds = null;
		if((UnitBitValues.SECOND & unitsOn) > 0) {
			Long secondInMillis = Units.SECOND.getInMillis(this.config);
			totalSeconds = ms / secondInMillis;
			ms -= (totalSeconds * secondInMillis);
		}
		
		Long totalMilliseconds = null;
		if((UnitBitValues.MILLISECOND & unitsOn) > 0) {
			totalMilliseconds = ms;
		}
		
		StringBuilder durationText = new StringBuilder();
		
		if(totalYears != null && totalYears > 0) {
			durationText.append(totalYears).append(this.config.getSymbols().getYear()).append(this.config.getTextSeparator());
		}
		
		if(totalMonths != null && totalMonths > 0) {
			durationText.append(totalMonths).append(this.config.getSymbols().getMonth()).append(this.config.getTextSeparator());
		}
		
		if(totalWeeks != null && totalWeeks > 0) {
			durationText.append(totalWeeks).append(this.config.getSymbols().getWeek()).append(this.config.getTextSeparator());
		}
		
		if(totalDays != null && totalDays > 0) {
			durationText.append(totalDays).append(this.config.getSymbols().getDay()).append(this.config.getTextSeparator());
		}
		
		if(totalHours != null && totalHours > 0) {
			durationText.append(totalHours).append(this.config.getSymbols().getHour()).append(this.config.getTextSeparator());
		}
		
		if(totalMinutes != null && totalMinutes > 0) {
			durationText.append(totalMinutes).append(this.config.getSymbols().getMinute()).append(this.config.getTextSeparator());
		}
		
		if(totalSeconds != null && totalSeconds > 0) {
			durationText.append(totalSeconds).append(this.config.getSymbols().getSecond()).append(this.config.getTextSeparator());
		}
		
		if(totalMilliseconds != null && totalMilliseconds > 0) {
			durationText.append(totalMilliseconds).append(this.config.getSymbols().getMillisecond());
		}
		
		return durationText.toString().trim();
	}
	
	private Long setup(String textDuration, ConfigDuration config) {
		for (Units u : Units.values()) {
			this.unitsValues.put(u, 0L);
		}

		Pattern compile = Pattern.compile(DURATION_PATTERN);
		Matcher matcher = compile.matcher(textDuration);
		
		while(matcher.find()) {
			String group = matcher.group();
			
			Units unit = config.getUnitFromSymbol(this.extractUnitSymbol(group));
			Long value = this.extractValue(group) + unitsValues.get(unit);
			
			this.unitsValues.put(unit, value);
		}
		
		Long msAcumulated = 0L;
		for (Entry<Units, Long> e : unitsValues.entrySet()) {
			msAcumulated += e.getKey().getInMillis(config) * e.getValue();
		}
		return msAcumulated;
	}
	
	private Map<Units, Long> acumulateUnits(Map<Units, Long> forAcumulate) {
		 Map<Units,Long> acumulatedValues = new LinkedHashMap<Units, Long>();
		 
		for (Entry<Units, Long> e : this.unitsValues.entrySet()) {
			
			Long valueForAcumulate = forAcumulate.get(e.getKey());
			if(valueForAcumulate == null) {
				valueForAcumulate = 0L;
			}
			
			acumulatedValues.put(e.getKey(), e.getValue() + valueForAcumulate);
		}
		
		return acumulatedValues;
	}
	
	private Long unitsToMillis(Map<Units, Long> units, ConfigDuration config) {
		Long totalMillis = 0L;
		
		for (Entry<Units, Long> e : units.entrySet()) {
			Long valueOfUnit = e.getValue();
			totalMillis += (valueOfUnit * e.getKey().getInMillis(config));
		}
		
		return totalMillis;
	}
	
	// **************************************************
	// Default get/set
	// **************************************************
	public String getOriginalDuration() {
		return new String(this.originalDuration);
	}
	
	public String getDuration() {
		return new String(duration);
	}
	
	public Long getTotalMilliseconds() {
		return new Long(this.totalMilliseconds);
	}
	
	public Map<Units, Long> getUnitsValues() {
		return Collections.unmodifiableMap(this.unitsValues);
	}

	// **************************************************
	// hashCode, equals, toString
	// **************************************************
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((totalMilliseconds == null) ? 0 : totalMilliseconds.hashCode());
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
		Duration other = (Duration) obj;
		if (totalMilliseconds == null) {
			if (other.totalMilliseconds != null)
				return false;
		} else if (!totalMilliseconds.equals(other.totalMilliseconds))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Duration [" + this.duration + "]";
	}
}
