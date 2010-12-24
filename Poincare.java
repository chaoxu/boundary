package boundary;

public class Poincare {
	protected final int[] p;
	
	
	public int[] getP() {
		return p;
	}



	public Poincare(int[] pp) {
		p=pp;
		
	}
	
	/**
	 * Returns true if p^\infty < q^\infty in the cyclic lexicographic order. False otherwise.
	 * @param p
	 * @param q
	 * @return boolean
	 * Precondition: p and q are not power of the same word.
	 */
	public  boolean isSmallerCLThan(Poincare qq, SurfaceWord sw)  {
		int[] p = this.getP();
		int[] q = qq.getP();
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
		int start = (Integer) sw.barN.get(p[(coincidence - 1) % p.length]);
		int coordAtVector = sw.remainderModSurfaceLength(p[coincidence % p.length] - start);
		int coordAtQ = sw.remainderModSurfaceLength(q[coincidence % q.length] - start);
	
		if (coordAtVector < coordAtQ)
			return true;
	
		return false;
	}
	
	
	
	
	/**
	 * Returns 0 if the vectors are not linked
	 * Returns 1 if they are linked with positive sign
	 * Returns -1 if they are linked with negative sign.
	 * @param p
	 * @param q
	 * @return integer
	 */
	public  int isLinkedTo(Poincare q, SurfaceWord sw) {
		Poincare p1, p2, q1, q2;
		int s=1;
		if (this.isSmallerCLThan(new Poincare(sw.barN(p)),sw)) {
			p1 = this;
			p2 = new Poincare(sw.barN(p));
		} else {
			p1 = new Poincare(sw.barN(p));
			p2 = this;	
			s=-1;
		}
		
		if (q.isSmallerCLThan(new Poincare(sw.barN(q.getP())),  sw)) {
			q1 = q;
			q2 = new Poincare(sw.barN(q.getP()));		
		} else {
			q1 = new Poincare(sw.barN(q.getP()));
			q2 = q;
			s=s*(-1);
		}
		
		if (p1.isSmallerCLThan(q1,sw) && q1.isSmallerCLThan( p2,sw)
				&& p2.isSmallerCLThan( q2,sw)){
			return s;
		}else{			
			if (q1.isSmallerCLThan(p1,sw) && p1.isSmallerCLThan(q2,sw) && q2.isSmallerCLThan(p2,sw)){
				
				return -s;
			}else{
				return 0;
			}
		}
	}
		

	
	

	

	/**
	 *Returns a matrix of size p.lenght x p.length, with 0 in a_{ij} if i>=j
	 *Then one choses one representative (i,j) of each equivalence class for that,  
	 *a_{ij} is equalt to the sign of the sing of the pair 
	 *(i-cyclic permutatin of p, j cyclic permuation of p)
	 *All the rest of the entries are 0.
	 * @param p
	 * @return
	 */
	public int[][] links1(SurfaceWord sw) {
		
		int[][] links = new int[p.length][p.length];
		//choose a pair (i,j), where i>=2, j>=1 
		for (int i = 2; i < p.length; i++) {
			for (int j = 1; j < i; j++) {
				int aux = (Integer) sw.barN.get(p[j - 1]);
				if (p[i] != p[j] & p[i] != aux){
					Poincare pc1 = this.permute(i);
					Poincare pc2 = this.permute(j);
					links[i][j] = pc1.isLinkedTo(pc2,sw);	
				} 
			}
		}
		//choose pairs of the form (1,j)
		for (int i = 1; i < p.length; i++) {
			if (p[i] != p[0]) {
				Poincare pc1 = this.permute(i);
				links[i][0] = pc1.isLinkedTo( this,sw);
			} 
		}
		//NOTE: When i=0, there are no j<i, that is why those pairs are not studied.
		
		return links;
	}
	
	/**
	 *Returns a matrix of size (p.lenght x q.length), 
	 *Then one choses one reprensentant (i,j) of each equivalence class for that,  
	 *a_{ij} is equalt tothe sign of the sing of the pair 
	 *(i-cyclic permutatin of p, j cyclic permuation of q)
	 *All the rest of the entries are 0.
	 * @param p
	 * @return the matrix
	 */
	public int[][] links2(Poincare qq, SurfaceWord sw) {
		int[][] links;
		int[] p = this.getP();
		int[] q = qq.getP();
		links = new int[p.length][q.length];
		for(int i=0; i<p.length; i++){
			for(int j=0; j<q.length; j++){
				int aux = (Integer)sw.barN.get(q[rem(j - 1,q.length)]);
				if(p[i]!=q[j]& p[i] != aux ){
					Poincare p1 = this.permute(i);
					Poincare p2 = qq.permute(j);
					links[i][j]= p1.isLinkedTo(p2,sw);
				}
			}
		}
		return links;
	}
	
	/**
	 * Returns k such that v=w^k with w primitive.
	 * 
	 * @param p
	 * @return int
	 */
	public  int largestPower() {
		// check first the pure powers.
		int count = 1;
		while (count < p.length) {
			if (p[0] == p[count])
				count++;
			else
				count = 10000;
		}

		if (count == p.length)
			return p.length;
//now check non pure powers
		for (int i = p.length - 1; i >= 2; i--) {
			// We will see if v = w^i, where length(w)=period.
			if (p.length % i == 0) {
				int period = p.length / i;
				int d = 1;
				int r = 0;
				boolean isAMultiple = true;
				while (isAMultiple) {
					isAMultiple = (p[r] == p[d * period + r]);
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
	


	/**
	 * Permutes p by i
	 * @param p
	 * @param i
	 * @return
	 * PRECONDITION: This method assumes that i< length of p
	 */
	public Poincare  permute(int i) {
		int[] p = this.getP();
		int[] q = new int[p.length];
		System.arraycopy(p, i, q, 0, p.length - i);
		System.arraycopy(p, 0, q, p.length - i, i);
		return new Poincare(q);
		
		
	}
	
	public static int rem(int j, int p) {
		j = j % p;
		if (j >= 0)
			return j;
		else
			return p + j;
	}
	
	
}
