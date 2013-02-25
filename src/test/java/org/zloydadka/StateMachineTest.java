package org.zloydadka;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;


public class StateMachineTest {

	private URL book_1 = ClassLoader.getSystemResource("dostoevskii_fedor_idiot.txt");
	private URL book_2 = ClassLoader.getSystemResource("tolstoi_l_n__voina_i_mir_utf8.txt");
	private String splitters = " ,!-.\n\t\r…«»:©()\"'*/<>=?{}^`˄;„";
	private BufferedReader book1Reader;
	private BufferedReader book2Reader;

	@Before
	public void setUp() throws Exception {

		FileInputStream book1Stream = new FileInputStream(new File(book_1.getFile()));
		book1Reader = new BufferedReader(new InputStreamReader(book1Stream));
		
		FileInputStream book2Stream = new FileInputStream(new File(book_2.getFile()));
		book2Reader = new BufferedReader(new InputStreamReader(book2Stream));
	}

	@Test
	public final void testStateMachine() throws IOException {
		

			
			StateMachine sm = new StateMachine(book1Reader, splitters);

			assertEquals(81, sm.getStates().size());
			
			HashSet<String> match = sm.match(book2Reader);

			assertEquals(48200, match.size());
			
			assertEquals(true, match.contains("мудрость"));
			assertEquals(true, match.contains("помеха"));
			assertEquals(true, match.contains("исповедь"));
			assertEquals(false, match.contains("темный!"));
			assertEquals(true, match.contains("темнота"));
	
	}


}
