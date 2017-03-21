package text2period;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

import br.com.rsystem.Duration;

public class ParseTest {
	private static Duration parse;
	
	@BeforeClass
	public static void setupTests() {
		parse = new Duration("1w");
	}
	
	@Test
	public void shouldReturnOneHourInSeconds() throws Exception {
		assertEquals("1h in seconds emit a problem", (Long)3600L, parse.setDuration("1h").toSeconds());
	}
	
	@Test
	public void shouldConverterOneWeekInSeconds() throws Exception {
		assertEquals("1w in seconds emit a problem", (Long)604800L, parse.toSeconds());
	}
}
