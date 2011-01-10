package boundary;

/**
 * This class contains the basic operations related to a surface word. 
 * 
 * We design SurfaceWord as immutable objects
 * 
 * @author moira
 *
 */
public class SurfaceWord {
	int[] word;
	int[] encode;


	/**
	 *This constructor will make computations associated to the
	 *surface word sw.
	 *VERY IMPORTANT PRECONDITION: The string surface word must be a suruface
	 *word, that is, a word in whihc every letter and its bar have exactly only
	 *one appearance each.
	 * @param sw
	 */
	public SurfaceWord(int[] sw){
		
		word = sw;
		encode = new int[sw.length];
		for(int i = 0 ; i< length() ; i++){
			encode[word[i]] = i;
		}
	}	
	
	public int[] encode(int[] p){
		int[] q = new int[p.length];
		for(int i=0;i<q.length;i++){
			q[i] = encode[p[i]];
		}
		return q;
	}
	
	public int encode(int p){
		return encode[p];
	}
	
	public int remainderModSurfaceLength(int j){
		j = j % length();
		if (j >= 0)
			return j;
		else
			return length() + j;
	}
	
	/**
	 * @param s a string
	 * @return the vector corresponding to this string.
	 */
/*	public int[] toNumber(String aWord){
		int[] answer = new int[aWord.length()];
		for(int i=0 ; i < aWord.length() ; i++){
			answer[i]= toNumber.get(aWord.charAt(i));
		}
		return answer;
	}*/
	
	
	/**
	 * Returns the vector in letters.
	 * @param p
	 * @return
	 */
	/*public String toLetter(int[] p){
		String answer = "";
		if(p == null) return null;
		for(int i =0 ; i< p.length ; i++){
			answer=answer+ toLetter.get(p[i]);
		}
		return answer;
	}*/
	
	

	/**
	 *
	 * @return the length of the surface word.
	 */
	public int length() {
		return word.length;
	}

	/**
	 * 
	 * @return the surfaceWord as a string
	 */
	/*public String toString() {
		return surfaceWord;
	}*/

	/* Returns the bar of a word given in numbers.
	 * @param q
	 * @return
	 */
	public int[] barN(int[] q){
		int[] w = new int[q.length];
		for (int i = 0; i < q.length; i++) {
			w[i] = CyclicWord.bar((q[q.length - i - 1]));
		}
		return w;
	}
}
