package boundary;

public class Curve {
	SurfaceWord sw;
	public CyclicWord baseword;
	int[] word;
	public Curve(){
		
	}
	/**
	 * Constructor
	 *               
	@param  c  The CyclicWord that composes the Curve
	 *                                     
	@param  s  The SurfaceWord that composes the Curve                         
	@return      The curve where it's CyclicWord jth cyclic permutation                
	 */	
	public Curve(CyclicWord c, SurfaceWord s){
		baseword = c;
		sw = s;		
	}

	/**
	 * Return a curve after right permute j spaces     
	 *               
	@param  j  a integer represent the jth cyclic permutation
	 *                                     
	@return      The curve where it's CyclicWord jth cyclic permutation                
	 */
	
	public Curve permute(int j){
		return new Curve(new CyclicWord(baseword.permute(j),sw.length()), sw);
	}
	/**
	 * Return the amount of self intersections of the curve
	 * 
	@return      The amount of self intersections              
	 */
	public int selfIntersection(){
		int number = baseword.largestPower()-1;
		//System.out.println("POWER "+number);
		int[][] datos= links1();
		for (int i = 1; i < baseword.length(); i++) {
			for (int j = 0; j < i; j++) {
				number=number+Math.abs(datos[i][j]);
			}
		}
		return number;
	}
	/**
	 * Return the amount of self intersections.
	 * 
	 * If w is a cyclic reduced words, non-primitive, then we can write it as 
	 * w=v^n where n is a positive integer and v is a primitive cyclic reduced word. 
	 * In this case, SelfIntersection(w)=n^2 SelfIntersection(v)+n-1
	 * 
	@param  isPrimitive is the curve a primitive curve?
	 * 
	@return The amount of self intersections              
	 */
	public int selfIntersection(boolean isPrimitive){
		if(isPrimitive){ 
			int number = 0;
			int[][] datos=this.links1();
			for (int i = 1; i < length(); i++) {
				for (int j = 0; j < i; j++) {
					number=number+Math.abs(datos[i][j]);
				}
			}
			return number;
		}
		return selfIntersection();
	}
	
	/**
	 * Return the amount of intersection between this curve and input curve
	 * 
	 * 
	@param  vw2 input curve
	 * 
	@return The amount of intersections              
	 */
	public int intersection(Curve vw2){
		int number = 0;
		int[][] matrix= this.links2(vw2);
		int leng = vw2.length();
		for (int i = 0; i < length(); i++) {
			for (int j = 0; j < leng; j++) {
				number=number+Math.abs(matrix[i][j]);
					}
				}
		return number;
	}
	
	public int length(){
		return baseword.length();
	}
	/**
	 *Returns the matrix of intersections.
	 *
	 *Matrix of the size p.lenght x p.length, with 0 in a_{ij} if i>=j
	 *Then one chooses one representative (i,j) of each equivalence class for that,  
	 *a_{ij} is equal to the sign of the sing of the pair 
	 *(i-cyclic permutation of p, j cyclic permutation of p)
	 *All the rest of the entries are 0.
	 *
	 * @return the matrix of intersections.
	 */
	private int[][] links1() {
		
		int[][] links = new int[length()][length()];
		//choose a pair (i,j), where i>=2, j>=1 
		for (int i = 2; i < length(); i++) {
			for (int j = 1; j < i; j++) {
				
				int aux = CyclicWord.bar(baseword.get(j-1));
				if (baseword.get(i) != baseword.get(j) & baseword.get(i) != aux){
					Curve pc1 = this.permute(i);
					Curve pc2 = this.permute(j);
					//System.out.println(this.baseword.word);
					//System.out.println(pc2.word);
					links[i][j] = pc1.isLinkedTo(pc2);	
				} 
			}
		}
		//choose pairs of the form (1,j)
		for (int i = 1; i < length(); i++) {
			if(baseword.get(i) != baseword.get(0)) {
				Curve pc1 = this.permute(i);
				links[i][0] = pc1.isLinkedTo(this);
			} 
		}
		//NOTE: When i=0, there are no j<i, that is why those pairs are not studied.
		
		return links;
	}
	/**
	 *Returns the matrix of intersections of the current curve and input curve
	 *
	 *Matrix of the size p.lenght x q.length
	 *
	 * @return the matrix of intersections.
	 */
	private int[][] links2(Curve qq) {
		int[][] links;
		int[] p = baseword.word;
		int[] q = qq.baseword.word;
		links = new int[p.length][q.length];
		for(int i=0; i<p.length; i++){
			for(int j=0; j<q.length; j++){
				int aux = CyclicWord.bar(q[(j+q.length-1) % q.length]);
				if(p[i]!=q[j]& p[i] != aux ){
					Curve p1 = this.permute(i);
					Curve p2 = qq.permute(j);
					links[i][j]= p1.isLinkedTo(p2);
				}
			}
		}
		return links;
	}
	/**
	 *Returns the curve and the input curve's intersection type.
	 *
	 * @return the type of intersection
	 */
	public int isLinkedTo(Curve q) {
		Curve p1, p2, q1, q2;
		int s=1;
		if (this.isSmallerCLThan(new Curve(new CyclicWord(CyclicWord.bar(baseword.word),sw.length()),sw))) {
			p1 = this;
			p2 = new Curve(new CyclicWord(CyclicWord.bar(baseword.word),sw.length()),sw);
		} else {
			p1 = new Curve(new CyclicWord(CyclicWord.bar(baseword.word),sw.length()),sw);
			p2 = this;	
			s=-1;
		}
		
		if (q.isSmallerCLThan(new Curve(new CyclicWord(CyclicWord.bar(q.baseword.word),sw.length()),sw))) {
			q1 = q;
			q2 = new Curve(new CyclicWord(CyclicWord.bar(q.baseword.word),sw.length()),sw);		
		} else {
			q1 = new Curve(new CyclicWord(CyclicWord.bar(q.baseword.word),sw.length()),sw);
			q2 = q;
			s=s*(-1);
		}
		
		if (p1.isSmallerCLThan(q1) && q1.isSmallerCLThan(p2)
				&& p2.isSmallerCLThan(q2)){
			return s;
		}else{			
			if (q1.isSmallerCLThan(p1) && p1.isSmallerCLThan(q2) && q2.isSmallerCLThan(p2)){
				
				return -s;
			}else{
				return 0;
			}
		}
	}
	/**
	 *Returns if the curve is smaller than the input curve
	 *
	 * @return true if the curve is smaller than the input curve
	 */
	public boolean isSmallerCLThan(Curve qq)  {
		//Only part where surface word come in
		int[] p = sw.encode(baseword.word);
		int[] q = sw.encode(qq.baseword.word);
		//first we give the answer if the two words start in different letters.
		if (p[0] < q[0])
			return true;
		if (p[0] > q[0])
			return false;
		//If they start in the same letter, we count the maximal number of letters of 
		//which are equal (starting from the begining)
		int coincidence = 0;
		while (coincidence < 2*p.length+2*q.length && p[coincidence % p.length] == q[coincidence % q.length]) {
			coincidence++;
			//System.out.println(coincidence);
		}
		if(coincidence == 2*p.length+2*q.length) return false;
		
		int start = sw.encode(CyclicWord.bar(baseword.get((coincidence - 1) % baseword.length())));
		int coordAtVector = sw.remainderModSurfaceLength(p[coincidence % p.length] - start);
		int coordAtQ = sw.remainderModSurfaceLength(q[coincidence % q.length] - start);
	
		if (coordAtVector < coordAtQ)
			return true;
	
		return false;
	}
	
	/**
	 * Returns the next curve.
	 *
	 * The next curve is returned by finding the next cyclicword
	 * 
	 * @return the next curve.
	 */
	public Curve next(){
		return new Curve(baseword.next(), sw);
	}
	
	public String toString(){
		return baseword.toString();
	}
}
