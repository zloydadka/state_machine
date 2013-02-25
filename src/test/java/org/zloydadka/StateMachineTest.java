package org.zloydadka;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import org.junit.Before;
import org.junit.Test;


public class StateMachineTest {

	private URL book_1;
	private URL book_2;
	private String splitters;

	@Before
	public void setUp() throws Exception {
		
		book_1 = ClassLoader.getSystemResource("tolstoi_l_n__voina_i_mir_utf8.txt");
		book_2 = ClassLoader.getSystemResource("book2.txt");
		splitters = " ,!-.\n\t\r…«»:©()\"'*/<>=?{}^`˄;„";

		System.out.println("Working Directory = " + book_1.getFile());
	}

	@Test
	public final void testStateMachine() {
		File file = new File(book_1.getFile());
		FileInputStream stream;
		try {
			stream = new FileInputStream(file);
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
			StateMachine sm = new StateMachine(bufferedReader, splitters);

			assertEquals(sm.getStates().size(), 71);
		} catch (FileNotFoundException e) {
			fail("File not found"); 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// TODO
	}

	@Test
	public final void testMatch() {
		fail("Not yet implemented"); // TODO
	}

}
