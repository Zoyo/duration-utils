package text2period;

import static org.junit.Assert.*;

import org.junit.Test;

import br.com.rsystem.Duration;
import br.com.rsystem.Units;

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
}
