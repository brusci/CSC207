package aircraft2;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class FlightDataRecorderTest {

	@Test
	public void testRecord() {
		FlightDataRecorder tester = new FlightDataRecorder();
		tester.record(5.0);
		tester.record(5.0);
		tester.record(5.0);
		tester.record(5.0);
		tester.record(5.0);
		tester.record(5.0);
		tester.record(5.0);
		tester.record(5.0);
		tester.record(5.0);
		tester.record(5.0);
		tester.record(5.0);
		List <Double> test = new ArrayList<Double>();
		test.add(5.0);
		test.add(5.0);
		test.add(5.0);
		test.add(5.0);
		test.add(5.0);
		test.add(5.0);
		test.add(5.0);
		test.add(5.0);
		test.add(5.0);
		test.add(5.0);
		test.add(5.0);
		assertEquals("Did not return ", test, tester.getLastDataPoints(1));
		//fail("Not yet implemented"); // TODO
	}

	@Test
	public void testGetRecordedData() {
		FlightDataRecorder tester = new FlightDataRecorder();
		tester.record(5.0);
		tester.record(2.0);
		tester.record(8.0);
		List <Double> test = new ArrayList<Double>();
		test.add(5.0);
		test.add(2.0);
		test.add(8.0);
		assertEquals("Did not return ", test, tester.getRecordedData());
		
		//fail("Not yet implemented"); // TODO
	}

	@Test
	public void testGetLastDataPoints() {
		FlightDataRecorder tester = new FlightDataRecorder();
		tester.record(5.0);
		tester.record(2.0);
		tester.record(8.0);
		List <Double> test = new ArrayList<Double>();
		test.add(8.0);
		test.add(2.0);
		assertEquals("Did not return ", test, tester.getLastDataPoints(2));
		//fail("Not yet implemented"); // TODO
	}

	@Test
	public void testAveragezero() {
		FlightDataRecorder tester = new FlightDataRecorder();
		assertEquals("Did not return zero\n", 0, tester.average(), 0.0000001);
		//fail("Not yet implemented"); // TODO
	}
	

}
