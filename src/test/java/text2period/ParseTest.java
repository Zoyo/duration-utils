package text2period;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import br.com.rsystem.Duration;

public class ParseTest {
	Duration parse;
	
	@Before
	public void setupTests() {
		parse = new Duration("1w");
	}
	
	@Test
	public void sholdReturnOneGroupDuration() throws Exception {
		assertArrayEquals("Group durations (1 duration) emits a problem", new String[] {"1w"}, parse.getGroupDurations());
	}
	
	@Test
	public void shoulReturnTwoGroupDurations() throws Exception {
		Duration duration = new Duration("1w 3d");
		assertArrayEquals("Group durations (2 durations) emits a problem", new String[] {"1w", "3d"}, duration.getGroupDurations());
	}
	
	@Test
	public void shouldReturnOneHourInSeconds() throws Exception {
		assertEquals("1h in seconds emit a problem", (Long)3600L, parse.toSeconds());
	}
	
	@Test
	public void shouldConverterOneWeekInSeconds() throws Exception {
		assertEquals("1w in seconds emit a problem", (Long)604800L, parse.toSeconds());
	}
}
