package br.com.rsystem;

import static java.lang.String.format;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Duration {
	private static final String DURATION_PATTERN = "\\d+[a-z]";
	private final String originalDuration;
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
		this.originalDuration = textDuration;
		this.duration = this.normalizeDuration(this.totalMilliseconds);
	}
	
	/**
	 * Create duration from total milliseconds informed;
	 * @param totalMilliseconds
	 */
	public Duration(Long totalMilliseconds) {
		this.totalMilliseconds = totalMilliseconds;
		this.originalDuration = "";
		this.duration = this.normalizeDuration(this.totalMilliseconds);
		this.unitsValues = null;
	}
	
	public Long toSeconds() {
		return this.totalMilliseconds / Units.SECOND.getMillisecondsFactor();
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
	
	private String normalizeDuration(Long milliseconds) {
		long weekInMillis = TimeUnit.MILLISECONDS.convert(7, TimeUnit.DAYS);
		long dayInMillis = TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS);
		long hourInMillis = TimeUnit.MILLISECONDS.convert(1, TimeUnit.HOURS);
		long minuteInMillis = TimeUnit.MILLISECONDS.convert(60, TimeUnit.SECONDS);
		long secondsInMillis = TimeUnit.MILLISECONDS.convert(1, TimeUnit.SECONDS);
		
		long totalWeeks   = (milliseconds / weekInMillis);
		long totalDays    = (milliseconds % weekInMillis) / dayInMillis;
		long totalHours   = ((milliseconds % weekInMillis) % dayInMillis) / hourInMillis;
		long totalMinutes = (((milliseconds % weekInMillis) % dayInMillis) % hourInMillis) / minuteInMillis;
		long totalSeconds = ((((milliseconds % weekInMillis) % dayInMillis) % hourInMillis) % minuteInMillis) / secondsInMillis;
		long totalMillis  = (((((milliseconds % weekInMillis) % dayInMillis) % hourInMillis) % minuteInMillis) % secondsInMillis);
		
		StringBuilder durationText = new StringBuilder();
		
		if(totalWeeks > 0) {
			durationText.append(totalWeeks).append(Units.WEEK.getUnitCode().toLowerCase());
		}
		
		if(totalDays > 0) {
			durationText.append(totalDays).append(Units.DAY.getUnitCode().toLowerCase());
		}
		
		// TODO continuar calculando as outras unidades.
		
		return durationText.toString();
	}
	
	// **************************
	// DEFAULT GET/SET
	public String getOriginalDuration() {
		return this.originalDuration;
	}
	
	public Long getTotalMilliseconds() {
		return this.totalMilliseconds;
	}
}
