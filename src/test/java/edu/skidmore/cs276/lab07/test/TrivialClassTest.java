package edu.skidmore.cs276.lab07.test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

import edu.skidmore.cs276.lab07.TrivialClass;

public class TrivialClassTest {
	private TrivialClass trivialObject;

	@Before
	public void setup() {
		trivialObject = new TrivialClass();
	}

	@Test
	public void testConstructorNull() {
		assertNull(trivialObject.getValue());
	}

	@Test
	public void testConstructorInitializer() {
		TrivialClass tc = new TrivialClass("A value");
		assertEquals("Initial value incorrect", "A value", tc.getValue());
	}

	@Test
	public void testSetValue() {
		trivialObject.setValue("Some value");
		assertEquals("Incorrect value", "Some value", trivialObject.getValue());
	}
}
