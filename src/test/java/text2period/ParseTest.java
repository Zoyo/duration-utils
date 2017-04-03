package text2period;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.junit.Ignore;
import org.junit.Test;

import br.com.rsystem.Duration;
import br.com.rsystem.Units;
import br.com.rsystem.builders.ConfigDurationBuilder;
import br.com.rsystem.builders.DurationSymbolBuilder;
import br.com.rsystem.config.ConfigDuration;
import br.com.rsystem.config.DurationSymbols;

public class ParseTest {
	@Test
	public void shouldReturnOneHourInSeconds() throws Exception {
		Duration duration = new Duration("1h");
		assertEquals("1h in seconds emit a problem", (Long)3600L, duration.toSeconds());
	}
	
	@Test
	public void shouldConverterOneWeekInSeconds() throws Exception {
		Duration duration = new Duration("1w");
		assertEquals("1w in seconds emit a problem", (Long)604800L, duration.toSeconds());
	}
	
	@Test
	public void shouldNormalizeTextDuration() throws Exception {
		Duration duration = new Duration("1w 14d 1h");
		assertEquals("Normalize duration emits a problem", "3w 1h", duration.getDuration());
		assertNotEquals("Duration equals after normalize should be false", duration.getOriginalDuration(), duration.getDuration());
	}
	
	@Test
	public void shouldAddDuration() throws Exception {
		Duration d1 = new Duration("2h 30s");
		
		long eightHours = 28800000L;
		Duration d2 = new Duration(eightHours);
		assertEquals("Add duration emit a problem", d1.add(d2).getDuration(), "10h 30s");
	}
	
	@Test
	public void shouldCreateDurationOnUnitSpecified() throws Exception {
		Duration duration = new Duration(24L, Units.HOUR);
		assertEquals("Duration from unit emits a problem", "1d", duration.getDuration());
	}
	
	@Test
	public void shoulAddDurationOnDate() throws Exception {
		Duration duration = new Duration("1d");
		Date march24_2017 = new Date(1490358859976L);
		Calendar calculatedDate = duration.calculateDate(march24_2017);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		
		assertEquals("Add duration emits a problem", "20170325", sdf.format(calculatedDate.getTime()));
	}
	
	@Test
	public void shouldLoadDurationSymbolFromConfigFile() throws Exception {
		DurationSymbolBuilder builder = new DurationSymbolBuilder();
		DurationSymbols symbols = builder.loadFromFile("configTest.properties").build();
		
		assertEquals("Load year symbol emits a problem", symbols.getYear(), "sYear");
		assertEquals("Load month symbol emits a problem", symbols.getMonth(), "sMonth");
		assertEquals("Load week symbol emits a problem", symbols.getWeek(), "sWeek");
		assertEquals("Load day symbol emits a problem", symbols.getDay(), "sDay");
		assertEquals("Load hour symbol emits a problem", symbols.getHour(), "sHour");
		assertEquals("Load minute symbol emits a problem", symbols.getMinute(), "sMinute");
		assertEquals("Load second symbol emits a problem", symbols.getSecond(), "sSecond");
		assertEquals("Load millisecond symbol emits a problem", symbols.getMillisecond(), "sMillisecond");
	}
	
	@Test
	public void shouldLoadConfigDurationFromConfigFile() throws Exception {
		ConfigDurationBuilder builder = new ConfigDurationBuilder();
		ConfigDuration config = builder.loadFromFile("configTest.properties").build();
		
		assertEquals("Load days in year emits a problem", config.getDaysInYear(), 360);
		assertEquals("Load days in month emits a problem", config.getDaysInMonth(), 25);
		assertEquals("Load days in week emits a problem", config.getDaysInWeek(), 3);
		assertEquals("Load hour in day emits a problem", config.getHoursInDay(), 8);
		assertEquals("Load text separator emits a problem", config.getTextSeparator(), "-");
		
		assertTrue("Load unit year emits a problem", config.getUseUnits().contains(Units.YEAR));
		assertFalse("Load unit month emits a problem", config.getUseUnits().contains(Units.MONTH));
		assertTrue("Load unit week emits a problem", config.getUseUnits().contains(Units.WEEK));
		assertFalse("Load unit day emits a problem", config.getUseUnits().contains(Units.DAY));
		assertTrue("Load unit hour emits a problem", config.getUseUnits().contains(Units.HOUR));
		assertFalse("Load unit minute emits a problem", config.getUseUnits().contains(Units.MINUTE));
		assertTrue("Load unit second emits a problem", config.getUseUnits().contains(Units.SECOND));
		assertFalse("Load unit millisecond emits a problem", config.getUseUnits().contains(Units.MILLISECOND));
	}
	
	@Test
	@Ignore
	public void onlyDebugPurpose() throws Exception {
		DurationSymbols durationSymbols = new DurationSymbols();
		DurationSymbols durationSymbols2 = new DurationSymbols("ano*mes semana*dia*hora/minuto-segundo,milli");
		System.out.println(durationSymbols);
		System.out.println(durationSymbols2);
	}
}
