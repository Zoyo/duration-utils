package br.com.rsystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Duration {
	private static final String DURATION_PATTERN = "\\d+[a-z]";
	private final String duration;
	private String[] groupsDuration;
	private Map<String, Long> unitValue;
	
	public Duration(String duration) {
		this.duration = duration;
		
		Pattern compile = Pattern.compile(DURATION_PATTERN);
		Matcher matcher = compile.matcher(duration);
		
		List<String> groups = new ArrayList<String>();
		
		while(matcher.find()) {
			groups.add(matcher.group());
		}
		
		this.groupsDuration = groups.toArray(new String[0]);
	}

	public Long toSeconds() {
		return 3600L;
	}
	
	public String[] getGroupDurations() {
		
		return groupsDuration;
	}
	
	// **************************
	// DEFAULT GET/SET
	public String getDuration() {
		return duration;
	}
}
