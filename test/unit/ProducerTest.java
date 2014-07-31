package unit;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class ProducerTest {
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

	@Before
	public void setUpStreams() {
	    System.setOut(new PrintStream(outContent));
	    System.setErr(new PrintStream(errContent));
	}

	@After
	public void cleanUpStreams() {
	    System.setOut(null);
	    System.setErr(null);
	}

	@Ignore
	@Test
	public void testBegin() {
		fail("Not yet implemented");
	}

	@Ignore
	@Test
	public void testProduce() {
		fail("Not yet implemented");
	}
	
	@Ignore
	@Test
	public void testClose() {
		fail("Not yet implemented");
	}

}
