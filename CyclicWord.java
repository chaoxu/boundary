package boundary;

public class CyclicWord{
	public int[] word;
	public int base;
	public CyclicWord(){
		
	}
	/**
	 * Constructor
	 *               
	@param  p  The array form of the CyclicWord
	 *                                     
	@param  b  The base the CyclicWord is in, the base is the SurfaceWord's length                       
	@return    The CyclicWord            
	 */	
	public CyclicWord(int[] p, int b){
		word = new int[p.length];
		base = b;
		System.arraycopy(p, 0, word, 0, p.length);
	}
	
	/**
	 * Returns the string form of the CyclicWord
	 * 
	 * This fails as base is larger than 52. This is highly unlikely
	@return    The String that represent the CyclicWord           
	 */	
	
	//This works up to 52 edges. I assume no one will go that far
	public String toString(){
		char[] c = new char[word.length];
		for(int i=0;i<c.length;i++){
			if(word[i]%2==0){
				c[i] = (char) (word[i]/2 + 97);
			}else{
				c[i] = (char) (word[i]/2 + 65);
			}
		}
		return String.valueOf(c);
	}
	/**
	 * Returns the clone of this CyclicWord
	 * 
	@return   A clone of the CyclicWord         
	 */	
	public CyclicWord clone(){
		CyclicWord v = new CyclicWord(word,base);
		return v;
	}
	
	/**
	 * If u=v^k, where u is this CyclicWord and v is some other CyclicWord then return the largest k.
	 * 
	@return    The largest k     
	 */	
	public int largestPower(){
		//n = partition size
		for(int n = 1; n<length()/2+1;n++){
			if(length()%n==0){
				boolean h = true;
				for(int j=0;j<length()/n;j++){
					boolean hit = true;
					for(int i=0;i<n;i++){
						if(get(i)!=get(j*n+i)){
							hit = false;
							break;
						}
					}
					if(hit == false){
						h = false;
						break;
					}
				}
				if(h){
					return length()/n;
				}
			}
		}
		return 1;
	}
	/**
	 * Return the length of the CyclicWord
	 * 
	@return    The length of the CyclicWord    
	 */	
	public int length(){
		return word.length;
	}
	

	/**
	 * Return the ith cyclic permutation of the Cyclicword in array form
	 * 
	@return  the ith cyclic permutation of the Cyclicword in array form
	 */	
	public int[] permute(int i) {
		int[] q = new int[word.length];
		System.arraycopy(word, i, q, 0, word.length - i);
		System.arraycopy(word, 0, q, word.length - i, i);
		return q;
	}
	
	/**
	 * Return the ith element in the CyclicWord
	 *
	 * If the length of the Cyclicword is n, then it returns i%n-th element
	 * 
	 @param i integer i
	 *
	@return  Return the ith element in the CyclicWord
	 */	
	public int get(int i){
		i = (i%word.length + word.length)%word.length;
		return word[i];
	}
	
	
	/**
	 * Set the ith element to be j
	 * 
	 * If the length of the Cyclicword is n, it set the i%n-th element
	@param i integer i as index
	 * 
	@param j integer j as element
	 */	
	public void set(int i, int j){
		i = (i%word.length + word.length)%word.length;
		word[i] = j;
	}
	
	/**
	 * Set the ith element to be j
	 * 
	 * If the length of the Cyclicword is n, it set the i%n-th element
	@param i integer i as index
	 * 
	@param j integer j as element
	 */		
	/*public CyclicWord replace(int[] table){
		CyclicWord v2 = this.clone();
		int[] v = v2.word;
		for(int i=0;i<v.length;i++){
			v[i] = table[v[i]];
		}
		return v2;
	}*/
	
	/**
	 * Return the next CyclicWord
	 * 
	 @return the next CyclicWord
	 */		
	public CyclicWord next(){
		CyclicWord v = this.clone();
		while(true){
			v.addOne();
			//check if reduced and smallest
			if(v.isReduced()&&v.isSmallest()){
				return v;
			}
		}
	}
	/**
	 * Find the bar of i
	 * 
	@param i element i
	 * 
	@return element bar(i)
	 */		
	public static int bar(int i){
		if(i%2==0){
			return i+1;
		}
		return i-1;
	}
	/**
	 * Find the bar of an word in array form
	 * 
	@param q array form of a word
	 * 
	@return bar(q)
	 */		
	public static int[] bar(int[] q){
		int[] qq = new int[q.length];
		for(int i=0;i<q.length;i++){
			qq[q.length-1-i] = bar(q[i]);
		}
		return qq;
	}
	
	/**
	 * Mutates the CyclicWord by add one to it's array representation
	 * 
	 */		
	private void addOne(){
		int[] v = word;
		int count=0;
		for(int i=0;i<v.length;i++){
			if(v[i]==base-1){
				count++;
			}
		}
		if(count==v.length){
			word = new int[v.length+1];
			return;
		}
		int h = 1;
			
		while (h <v.length & v[v.length-h] == base-1){
			v[v.length-h]=0;
			h++;
		}
		if(h==v.length & v[0]==base-1){
			v[0]=0;
		}else if(h <=v.length) {
			v[v.length-h]=v[v.length-h]+1;
		}
	}
	/**
	 * Return if the bar(i)i exists
	 * 
	@return true if reduced, false otherwise.
	 */		
	private boolean isReduced(){
		for(int i = 0 ; i < length() ; i++){
			if(get(i) == bar(get(i+1))){
				return false;
			}
		}
		return true;
	}
	/**
	 * Return if the CyclicWord is the smallest of all cyclic permutations
	 * 
	@return true if smallest, false otherwise.
	 */		
	private boolean isSmallest(){
		for(int i = 0 ; i < length() ; i++){
			for(int j=0;j<length();j++){
				if(get(i+j)>get(j)){
					break;
				}
				if(get(i+j)<get(j)){
					return false;
				}
			}
		}
		return true;
	}
	/**
	 * Return if the CyclicWord is the smallest of all cyclic permutations in both directions
	 * 
	@return true if smallest, false otherwise.
	 */	
	public boolean isDoublySmallest(){
		CyclicWord v = clone();
		for(int i=0;i<length();i++){
			v.word[i] = bar(word[length()-1-i]);			
		}
		for(int i = 0 ; i < length() ; i++){
			for(int j=0;j<length();j++){
				if(v.get(i+j)>get(j)){
					break;
				}
				if(v.get(i+j)<get(j)){
					return false;
				}
			}
		}
		return isSmallest();
	}
	/**
	 * Return if the CyclicWord is not in u = v^k form, where k>1
	 * 
	@return true if primitive, false otherwise.
	 */	
	public boolean isPrimitive() {
		for(int n = 1; n <= length()/2;n++){
			if(length()%n==0){
				boolean h = true;
				for(int j=0;j<length()/n;j++){
					boolean hit = true;
					for(int i=0;i<n;i++){
						if(get(i)!=get(j*n+i)){
							hit = false;
							break;
						}
					}
					if(hit == false){
						h = false;
						break;
					}
				}
				if(h){
					return false;
				}
			}
		}
		return true;
	}
	/**
	 * Return if the CyclicWord's array representation is all zeros
	 * 
	@return true if it's all zeros, false otherwise.
	 */		
	public boolean isAllZeros(){
		for(int i=0;i < length();i++){
			if(word[i]!=0){
				return false;
			}
		}
		return true;
	}
	/**
	 * Create a CyclicWord's array representation from String
	 * 
	@param s the String
	 *
	@param base the base
	 *
	@return array representation from a String
	 */	
	public static int[] fromString(String s,int base){
		int[] p = new int[s.length()];
		for(int i=0;i<p.length;i++){
			p[i] = (int) s.charAt(i);
			if(p[i] >= 97){
				p[i]-=97;
				p[i]=p[i]*2;
			}else{
				p[i]-=65;
				p[i]=p[i]*2+1;
			}
		}
		return p;
		
	}
}
