package br.com.rsystem;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Duration {
	private static final String DURATION_PATTERN = "\\d+[a-z]";
	private final String duration;
	private final Map<Units, Long> unitsValues;
	private final Long totalMilliseconds;
	
	/**
	 * Create a duration form a string.
	 * @param textDuration String represents a duration
	 * 		  ex: '1w 3d 15m' means 1 week 3 days and 15 minutes.
	 */
	public Duration(String textDuration) {
		this.unitsValues = new LinkedHashMap<Units, Long>(Units.values().length);
		for (Units u : Units.values()) {
			unitsValues.put(u, 0L);
		}
		
		Pattern compile = Pattern.compile(DURATION_PATTERN);
		Matcher matcher = compile.matcher(textDuration);
		
		while(matcher.find()) {
			String group = matcher.group();
			Units unit = Units.getFromPredicate(extractUnitSymbol(group));
			Long value = extractValue(group) + unitsValues.get(unit);
			
			unitsValues.put(unit, value);
		}
		
		Long msAcumulated = 0L;
		Set<Entry<Units,Long>> entrySet = unitsValues.entrySet();
		for (Entry<Units, Long> e : entrySet) {
			msAcumulated += e.getKey().getMillisecondsFactor() * e.getValue();
		}
		
		this.totalMilliseconds = msAcumulated;
		
		this.duration = textDuration;
	}
	
	/**
	 * Create duration from total milliseconds informed;
	 * @param totalMilliseconds
	 */
	public Duration(Long totalMilliseconds) {
		this.unitsValues = new LinkedHashMap<Units, Long>();
		this.totalMilliseconds = totalMilliseconds;
		
		Long year = this.totalMilliseconds / Units.YEAR.getMillisecondsFactor();
		Long month = this.totalMilliseconds / Units.MONTH.getMillisecondsFactor();
		Long week = this.totalMilliseconds / Units.WEEK.getMillisecondsFactor();
		Long day = this.totalMilliseconds / Units.DAY.getMillisecondsFactor();
		Long hour = this.totalMilliseconds / Units.HOUR.getMillisecondsFactor();
		Long minute = this.totalMilliseconds / Units.MINUTES.getMillisecondsFactor();
		Long second = this.totalMilliseconds / Units.SECONDS.getMillisecondsFactor();
		
		
		this.duration = "";
	}
	
	public Long toSeconds() {
		return this.totalMilliseconds / Units.SECONDS.getMillisecondsFactor();
	}
	
	public Duration add(Duration duration) {
		// TODO
		return null;
	}
	
	public Duration add(String duration) {
		// TODO
		return null;
	}

	/**
	 * Create a new Duration
	 * @param duration
	 * @return
	 */
	public Duration setDuration(String duration) {
		// FIXME change (or remove) for more useful things
		return new Duration(duration);
	}
	// -------------------------------------------------------------------------
	// PRIVATE METHODS
	// -------------------------------------------------------------------------
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
	
	private String normalize() {
		// TODO
		return null;
	}
	
	// **************************
	// DEFAULT GET/SET
	public String getDuration() {
		return this.duration;
	}
	
	public Long getTotalMilliseconds() {
		return this.totalMilliseconds;
	}
}
