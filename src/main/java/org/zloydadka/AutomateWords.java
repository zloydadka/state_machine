package org.zloydadka;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;



public class AutomateWords {
	
	public static void main(String[] args) throws IOException {


		String splitters = " ,!-.\n\t\r…«»:©()";
		StateMachine sm = new StateMachine(new BufferedReader(new InputStreamReader(new FileInputStream(new File(args[0])))), splitters);

		HashSet<String> matches = sm.match(new BufferedReader(new InputStreamReader(new FileInputStream(new File(args[1])))));
        
        
        System.out.println("Finded: "+matches.size());
//        for (String string : matches) {
//			System.out.println("--> "+string);
//		}
        
		
	}

}
