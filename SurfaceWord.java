package boundary;

import java.util.HashMap;
import java.util.Map;

/**
 * This class contains the basic operations related to a surface word. 
 * 
 * We design SurfaceWord as immutable objects
 * 
 * @author moira
 *
 */
public class SurfaceWord {
	private final String surfaceWord;
	protected final  int slength;
	protected final Map<Character, Integer> toNumber = new HashMap<Character, Integer>();
	public final Map<Integer, Character> toLetter= new HashMap<Integer, Character>();
    protected final Map<Character, Character> barL= new HashMap<Character, Character>();
	public final Map<Integer, Integer> barN= new HashMap<Integer,Integer>();
	//public   Map<Integer[], Integer> sr = new HashMap<Integer[], Integer>(); 


	
	public Map<Integer, Integer> getBarN() {
		return barN;
	}


	/**
	 *This constructor will make computations associated to the
	 *surface word sw.
	 *VERY IMPORTANT PRECONDITION: The string surface word must be a suruface
	 *word, that is, a word in whihc every letter and its bar have exactly only
	 *one appearance each.
	 * @param sw
	 */
	public SurfaceWord(String sw){
		surfaceWord = sw;
		slength = surfaceWord.length();
		//store the translations from letter to number and number to letter.
		for(int i = 0 ; i< slength ; i++){
			toLetter.put(i,surfaceWord.charAt(i));
			toNumber.put(surfaceWord.charAt(i),i);
		}
		//store the bar function for letters.
		
		for(int h = 65 ; h < 91 ; h++){
			 barL.put((char) h, (char) (h - 'A' + 'a'));
		}
		for(int h = 97 ; h < 123 ; h++){
			 barL.put((char) h, (char) (h - 'a' + 'A'));
		}
		
		//store the bar function for numbers
		for(int h =0 ; h < slength ; h++){
			barN.put(h, toNumber.get(barL.get(toLetter.get(h))));
		}
	}
	
	
	public int remainderModSurfaceLength(int j){
		j = j % this.slength;
		if (j >= 0)
			return j;
		else
			return this.slength + j;
	}
	
	/**
	 * @param s a string
	 * @return the vector corresponding to this string.
	 */
	public int[] toNumber(String aWord){
		int[] answer = new int[aWord.length()];
		for(int i=0 ; i < aWord.length() ; i++){
			answer[i]= toNumber.get(aWord.charAt(i));
		}
		return answer;
	}
	
	
	/**
	 * Returns the vector in letters.
	 * @param p
	 * @return
	 */
	public String toLetter(int[] p){
		String answer = "";
		if(p == null) return null;
		for(int i =0 ; i< p.length ; i++){
			answer=answer+ toLetter.get(p[i]);
		}
		return answer;
	}
	
	

	/**
	 *
	 * @return the length of the surface word.
	 */
	public int length() {
		return surfaceWord.length();
	}

	/**
	 * 
	 * @return the surfaceWord as a string
	 */
	public String toString() {
		return surfaceWord;
	}

	/* Returns the bar of a word given in numbers.
	 * @param q
	 * @return
	 */
	public int[] barN(int[] q){
		int[] w = new int[q.length];
		for (int i = 0; i < q.length; i++) {
			w[i] = this.barN.get(q[q.length - i - 1]);
		}
		return w;
	}
	

}
