package br.com.rsystem;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Duration {
	private static final String DURATION_PATTERN = "\\d+[a-z]";
	private final String originalDuration;
	private final String duration;
	private final Map<Units, Long> unitsValues;
	private final Long totalMilliseconds;

	
	// -------------------------------------------------------------------------
	// CONSTRUCTORS
	// -------------------------------------------------------------------------
	/**
	 * Create a duration form a string.
	 * 
	 * @param textDuration String represents a duration
	 * 		  ex: '1w 3d 15m' means 1 week 3 days and 15 minutes.
	 */
	public Duration(String textDuration) {
		this.unitsValues = new LinkedHashMap<Units, Long>(Units.values().length);
		this.totalMilliseconds = setup(textDuration);
		this.originalDuration = textDuration;
		this.duration = this.normalizeDuration(this.totalMilliseconds);
	}

	/**
	 * Create duration from total milliseconds informed;
	 * 
	 * @param totalMilliseconds
	 */
	public Duration(Long totalMilliseconds) {
		this.totalMilliseconds = totalMilliseconds;
		this.originalDuration = this.normalizeDuration(this.totalMilliseconds);
		this.duration = this.normalizeDuration(this.totalMilliseconds);
		this.unitsValues = new LinkedHashMap<Units, Long>(Units.values().length);
		setup(this.duration);
	}
	
	/**
	 * Create a duration from the total time of the unit specified.
	 * 
	 * @param totalTime Total time of duration
	 * @param unit Unit of total time.
	 */
	public Duration(Long totalTime, Units unit) {
		this(totalTime * unit.getMillisecondsFactor());
	}
	// -------------------------------------------------------------------------
	// PUBLIC METHODS
	// -------------------------------------------------------------------------
	/**
	 * Create a new duration that hold the sum of <code>duration</code> parameter and this. 
	 * @param duration Duration to sum on this.
	 * @return new Duration with de sum between <code>duration</code> parameter and this.
	 */
	public Duration add(Duration duration) {
		Long ms = duration.getTotalMilliseconds() + this.totalMilliseconds;
		return new Duration(ms);
	}
	
	/**
	 * Create a new duration that hold the sum of <code>duration</code> parameter and this. 
	 * @param duration Duration as string like <i>'3d 5h'</i>.
	 * @return new Duration with de sum between <code>duration</code> parameter and this.
	 */
	public Duration add(String duration) {
		return new Duration(duration).add(this);
	}
	
	/**
	 * @return Total seconds containing in this.
	 */
	public Long toSeconds() {
		return this.totalMilliseconds / Units.SECOND.getMillisecondsFactor();
	}

	/**
	 * Create a new duration that hold the sum of <code>duration</code> parameter and this.
	 * @param milliseconds Duration as milliseconds.
	 * @return new Duration with de sum between <code>duration</code> parameter and this.
	 */
	public Duration add(Long milliseconds) {
		return new Duration(milliseconds).add(this);
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
//		long totalMillis  = (((((milliseconds % weekInMillis) % dayInMillis) % hourInMillis) % minuteInMillis) % secondsInMillis);
		
		StringBuilder durationText = new StringBuilder();
		
		if(totalWeeks > 0) {
			durationText.append(totalWeeks).append(Units.WEEK.getUnitCode().toLowerCase()).append(" ");
		}
		
		if(totalDays > 0) {
			durationText.append(totalDays).append(Units.DAY.getUnitCode().toLowerCase()).append(" ");
		}
		
		if(totalHours > 0) {
			durationText.append(totalHours).append(Units.HOUR.getUnitCode().toLowerCase()).append(" ");
		}
		
		if(totalMinutes > 0) {
			durationText.append(totalMinutes).append(Units.MINUTE.getUnitCode().toLowerCase()).append(" ");
		}
		
		if(totalSeconds > 0) {
			durationText.append(totalSeconds).append(Units.SECOND.getUnitCode().toLowerCase()).append(" ");
		}
		
		return durationText.toString().trim();
	}
	
	private Long setup(String textDuration) {
		for (Units u : Units.values()) {
			this.unitsValues.put(u, 0L);
		}

		Pattern compile = Pattern.compile(DURATION_PATTERN);
		Matcher matcher = compile.matcher(textDuration);
		
		while(matcher.find()) {
			String group = matcher.group();
			Units unit = Units.getFromPredicate(extractUnitSymbol(group));
			Long value = extractValue(group) + unitsValues.get(unit);
			
			this.unitsValues.put(unit, value);
		}
		
		Long msAcumulated = 0L;
		Set<Entry<Units,Long>> entrySet = unitsValues.entrySet();
		for (Entry<Units, Long> e : entrySet) {
			msAcumulated += e.getKey().getMillisecondsFactor() * e.getValue();
		}
		return msAcumulated;
	}
	
	// **************************
	// DEFAULT GET/SET
	/**
	 * When duration is created with string constructor, the original
	 * string is saved on this attribute then this one is normalized.<br>
	 * Ex.: new Duration("1w 14d") has your duration normalized to "3w".<br><br>
	 * <b>Obs.:</b> When duration is create with milliseconds constructor,
	 * 			    origialDuration and duration are always the same. 
	 * @return Original duration
	 */
	public String getOriginalDuration() {
		return this.originalDuration;
	}
	
	/**
	 * When duration is created with string constructor, the original
	 * string is saved on <code>orignalDuration</code> then this one is normalized.<br>
	 * Ex.: new Duration("1w 14d") has your duration normalized to "3w".<br><br>
	 * <b>Obs.:</b> When duration is create with milliseconds constructor,
	 * 			    origialDuration and duration are always the same. 
	 * @return Return string duration already normalized.
	 */
	public String getDuration() {
		return duration;
	}
	
	/**
	 * @return The total of milliseconds present in this duration.
	 */
	public Long getTotalMilliseconds() {
		return this.totalMilliseconds;
	}
}
