package org.zloydadka;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.HashSet;

public class StateMachine {

	
	class State extends HashMap<Integer, HashSet<Character>> {
		private static final long serialVersionUID = -8978771068796642430L;
	}
	
	private final HashMap<Character, State> states = new HashMap<Character, State>();
	private final HashSet<Character> splitters = new HashSet<Character>();
	private final char BreakChar = (char) -1;
	private State machine_state = null;
	private StringBuilder sb = new StringBuilder();
	private HashSet<String> matches = new HashSet<String>();

	private boolean caseInsensitive = true;
	
	public HashMap<Character, State> getStates() { return states; }
	public HashSet<String> getMatches() { return matches; }

	private char getChar(char c) {
		if (caseInsensitive) { c = Character.toLowerCase(c); }
		return splitters.contains(c) ? BreakChar : c;
	}
	
	public StateMachine(Reader reader, String splitters) throws IOException {
		
		this.splitters.add(BreakChar);
		states.put(BreakChar, null);
		
		
		for (char c: splitters.toCharArray()) { this.splitters.add(c); }
		
		int current_c = reader.read();
		int next_c = 0;
		int j = 0;
        do {
        	j = addTransition(getChar((char)current_c), j, getChar((char)(next_c = reader.read())));
    
        } while ((current_c = next_c) != -1);
	
	}
	
	private State addState(char c) {
		if (!getStates().containsKey(c)) { getStates().put(c, new State()); }
		return getStates().get(c);
	}

	private int addTransition(char current, int j, char next) {
		State current_state = addState(current);
		addState(next);
		
		if (current_state != null) {
			j = j+1;
			HashSet<Character> set = current_state.get(j);
			if (set == null) {
				set = new HashSet<Character>();
				current_state.put(j, set);
			}
			set.add(next);
			return j;
		} else {
			return 0;
		}
		

	}
	
	private int start(int c) { return start((char) c); }
	private int start(char current) {
		current = getChar(current);
		machine_state = getStates().get(current);
		sb = new StringBuilder().append(current);
		return machine_state == null ? -1: 1;
	}
	
	private int checkTransition(int j, int current) { return checkTransition(j,(char) current); }
	private int checkTransition(int j, char current) {
		State current_state = machine_state;
		current = getChar(current);
		
		if(current_state.containsKey(j) && current_state.get(j).contains(current)) {
			machine_state =  getStates().get(current);
			
			if(machine_state == null) {
				// FINISH
				getMatches().add(sb.toString());
				j = 0;
				
			} else {
				sb.append(current);
				j =  j+1;	
			}
		} else {
			machine_state = null;
			j = -1;
		}
		return j;
	}


	public HashSet<String> match(Reader reader) throws IOException {
		int r = 0;
		int j = 0;
        do {
        	r = reader.read();
			if   (j < 1 ) { j = start(r); } 
			else { j = checkTransition(j, r); }
        } while (r != -1);
        return matches;
	}



}
