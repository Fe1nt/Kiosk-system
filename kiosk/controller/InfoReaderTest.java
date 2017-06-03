package kiosk.controller;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class InfoReaderTest{
	
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testMoiveName() {
		String buf = "LOGAN";
		assertTrue(buf.equals(InfoReader.getInfoReader().getMovieInfoArrayList().get(1).getMovieName()));
	}
	
	@Test
	public void testMoiveArrange() {
		String buf = "KONG: SKULL ISLAND";
		assertTrue(buf.equals(InfoReader.getInfoReader().getMovieInfoArrayList().get(0).getMovieName()));
	}

	@Test
	public void testLayOut() {
		int buf = 1;
		assertTrue(buf == InfoReader.getInfoReader().getScreenInfoArrayList().get(0).getAllSitsArrayList().get(0).getRow());
		
	}
	
	@Test
	public void testTicket() {
		int buf = 0;
		assertTrue(InfoReader.getInfoReader().getTicketIdArrayList().size() == 0);
	}
}
