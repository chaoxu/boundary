package boundary;

public class WordVector extends Poincare {
	public WordVector(int[] pp) {
		super(pp);
		
	}
		
	public int[] getVectorBar(SurfaceWord sw){
		return sw.barN(p);
		
	}
	
	public boolean firstisa(){
		return p[0]==0;
	}
	
	public int selfIntersection(SurfaceWord sw){
		int number = this.largestPower()-1;
		int[][] datos=this.links1(sw);
		for (int i = 1; i < p.length; i++) {
			for (int j = 0; j < i; j++) {
				number=number+Math.abs(datos[i][j]);
			}
		}
		return number;
	}	
	
	
	
	public void printSI(SurfaceWord sw){
		System.out.println("SI( "+this.toLetter(sw)+" )= "+this.selfIntersection(sw));
	}
	
	public int selfIntersection(SurfaceWord sw, boolean isPrimitive){
		if(isPrimitive){ 
		int number = 0;
		int[][] datos=this.links1(sw);
		for (int i = 1; i < p.length; i++) {
			for (int j = 0; j < i; j++) {
				number=number+Math.abs(datos[i][j]);
			}
		}
		return number;
		}
		return selfIntersection(sw);
	}	
	public int intersection(WordVector vw2,SurfaceWord sw){
		int number = 0;
		int[][] matrix= this.links2(vw2, sw);
		int leng = vw2.getP().length;
		for (int i = 0; i < p.length; i++) {
			for (int j = 0; j < leng; j++) {
				number=number+Math.abs(matrix[i][j]);
					}
				}
		return number;

		
	}
	
	
	/**
	 * @param q
	 * @return
	 */
	public boolean isSmallerThan(WordVector q){
		
		int[] qq = q.getP();
		if(p[0] != qq[0]) return (p[0]<qq[0]);
		int coincidence=0;
		while(coincidence<p.length && p[coincidence]==qq[coincidence] ){
			coincidence++;
		}
		if(coincidence==p.length){ 
			return false;
		}
		
		return p[coincidence] < qq[coincidence];
			
		
		
	}
	/**
	 *Note that if a word is not primitive, then it is never the smallest of its cyclic permutations,
	 *(because it is always equal to some of its cylic permutations).
	 *Returns true if the vector is primitive and is the smallest of the cyclic permutations of itself.
	 *Returns false if the vector is not smallest or not primitive.
	 * @return
	 */
	public boolean isSmallestAndPrimitive(){
		for(int h = 1 ; h < p.length  ; h++){
			
			if(!this.isSmallerThan(this.permute(h))){
				return false;
			}
		}
		return true;
	}
	
	public boolean isSmallestOfAllDirectionsAndPrimitive(SurfaceWord sw){
		if(p.length==1) return (p[0]< sw.barN.get(p[0]));
		for(int h = 1 ; h < p.length  ; h++){
			WordVector wv = this.permute(h);
			if(!this.isSmallerThan(wv)){
				return false;
			}
			
			if(!this.isSmallerThan(wv.bar(sw))){
				return false;
			}
			
		}
		
		return true;
	}
	
	/**
	 *Returns true if the vector is primitive and is the smallest of the cyclic permutations of itself.
	 *Returns false if the vector is not smallest or not primitive.
	 * @return
	 */
	public boolean isSmallestAndNotPrimitive(){
		int len= this.largestPower();
		if(len==1) return false; //the word is primitive
		int[] qq = new int[p.length/len];
		System.arraycopy(p, 0, qq, 0, p.length/len);
		WordVector q = new WordVector(qq);
		return(q.isSmallestAndPrimitive());
	}
	
	
	/**
	 * 
	 * @return
	 */
	public boolean isSmallestUnderPermutingabyb(){
		//int len= this.largestPower();
		//if(len==1) return false; //the word is primitive
		int[] qq = new int[p.length];
		for(int h = 0 ; h < p.length ; h++ ){
			qq[h] = (p[h]+2) % 4;
		}
		
		WordVector q = new WordVector(qq);
		return(this.isSmallerThan(q));
	}
	
	/**
	 *Returns true if the vector is primitive and is the smallest of the cyclic permutations of itself.
	 *Returns false if the vector is not smallest or not primitive.
	 * @return
	 */
	public boolean isSmallestInAllDirectionsAndNotPrimitive(SurfaceWord sw){
		int len= this.largestPower();
		if(len==1) return false; //the word is primitive
		int[] qq = new int[p.length/len];
		System.arraycopy(p, 0, qq, 0, p.length/len);
		WordVector q = new WordVector(qq);
		return(q.isSmallestOfAllDirectionsAndPrimitive(sw));
	}
	
	public  boolean isCyclicallyReduced(SurfaceWord sw){
		int[] theVector = this.getP();
		for(int h = 0 ; h < theVector.length-1 ; h++){
			if(theVector[h]==sw.barN.get(theVector[h+1]) ) return false;
		}
		if(theVector[0]== sw.barN.get(theVector[theVector.length-1])) return false;
		return true;
	}
	
	public  boolean isReduced(SurfaceWord sw){
		int[] theVector = this.getP();
		for(int h = 0 ; h < theVector.length-1 ; h++){
			if(theVector[h]==sw.barN.get(theVector[h+1]) ) return false;
		}
		return true;
	}
	
	
	public boolean isPrimitive(){
		int[] v = this.getP();
		for (int i = 2; i <= v.length; i++) {
			// We will see if v = w^i, where length(w)=period.
			if (v.length % i == 0) {
				int period = v.length / i;
				int d = 1;
				int r = 0;
				boolean isAMultiple = true;
				while (isAMultiple) {
					isAMultiple = (v[r] == v[d * period + r]);
					if (r < period - 1)
						r++;
					else if (d < i - 1) {
						r = 0;
						d++;
					} else if (isAMultiple) {
						return false;
					}
				}
			}
		}
		return true;
	}
	
	public int largestPower(){
		int[] theVector = this.getP(); 
		// check first the pure powers.
		int count = 1;
		while (count < theVector.length) {
			if (theVector[0] == theVector[count])
				count++;
			else
				count = 10000;
		}

		if (count == theVector.length)
			return theVector.length;
//now check non pure powers
		for (int i = theVector.length - 1; i >= 2; i--) {
			// We will see if v = w^i, where length(w)=period.
			if (theVector.length % i == 0) {
				int period = theVector.length / i;
				int d = 1;
				int r = 0;
				boolean isAMultiple = true;
				while (isAMultiple) {
					isAMultiple = (theVector[r] == theVector[d * period + r]);
					if (r < period - 1)
						r++;
					else if (d < i - 1) {
						r = 0;
						d++;
					} else if (isAMultiple) {
						return i;
					}
				}
			}
		}
		return 1;
	}
	
	public String  toLetter(SurfaceWord sw){
		int[] theVector = this.getP();
		String answer = "";
		if(theVector == null) return null;
		for(int i =0 ; i< theVector.length ; i++){
			answer=answer+ sw.toLetter.get(theVector[i]);
		}
		return answer;
	}
	
	public WordVector  bar(SurfaceWord sw){
		return new WordVector(sw.barN(this.getP()));
	}
	
	/**
	 * Permutes p by i
	 * @param p
	 * @param i
	 * @return
	 * PRECONDITION: This method assumes that i< length of p
	 */
	public WordVector  permute(int i) {
		int[] p = this.getP();
		int[] q = new int[p.length];
		System.arraycopy(p, i, q, 0, p.length - i);
		System.arraycopy(p, 0, q, p.length - i, i);
		return new WordVector(q);
		
		
	}
	
	/**
	 * This method adds one in a certain basis to a vector v.
	 * IT DOES NOT WORK IF V=[BASE-1,BASE-1..., BASE-1]
	 * @param v
	 * @param base
	 * @return
	 */
	private  int[] addOne(int base){
		int[] v = this.getP();
		
		
		int h = 1;
		
		while (h <v.length & v[v.length-h] == base-1){
			v[v.length-h]=0;
			h++;
		}
		if(h==v.length & v[0]==base-1) v[0]=0;
		else if(h <=v.length) 
			v[v.length-h]=v[v.length-h]+1;
		
		
		return v;
	}	
	
	public WordVector getNextCyclicallyReduced(SurfaceWord sw){
		WordVector wn = new WordVector(addOne(sw.slength)); 
		while(!wn.isCyclicallyReduced(sw) ){
			wn =new WordVector(wn.addOne(sw.slength)); 
		}
			
		return wn;
		
	}
	
	public boolean isAllZeros(SurfaceWord sw){
		boolean allZero=true;
		int u=0;
		while(u<p.length && allZero){
			allZero = allZero && (p[u]==0);	
			u++;
		}
		return allZero;
	}

}