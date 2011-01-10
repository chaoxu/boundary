package boundary;
import java.util.*;
import java.io.*;
//This class read and write word lists

public final class CurveList<T extends Curve, C extends CyclicWord> {
	SurfaceWord sw;
	ArrayList<int[]> list = new ArrayList<int[]>();
	int index;

	Class<T> tClass;
	Class<C> cClass;
	/**
	 * The constructor of the CurveList
	 * 
	 * Java have type erasure feature that requires
	 * us to input the class of Curve and CyclicWords
	 * in order to actually use these classes in reflections
	 * 
	@param  s  a SurfaceWord associated to the list
	 *
	@param  t  The class of the Curve
	 *
	@param  c  The class of the CyclicWord
	 *
	@return The newly constructed CurveList              
	 */
	public CurveList(SurfaceWord s, Class<T> t, Class<C> c){
		sw = s;
		list = new ArrayList<int[]>();
		index = 0;
		tClass = t;
		cClass = c;
	}
	
	/**
	 * Populate the CurveList by reading from a file
	 * 
	@param  filename  The name of the file        
	 */
	public void readList(String filename){
	      try{
	    	  Scanner in = new Scanner(new FileReader(new File(filename)));
	    	  while(in.hasNext()){
	    		  list.add(CyclicWord.fromString(in.next(), sw.length()));
	    	  }
	      }catch (Exception e){//Catch exception if any
	    	  System.err.println("Error: " + e.getMessage());
	      }
	}
	/**
	 * Save the CurveList as a file
	 * 
	@param  filename  The name of the file        
	 */	
	public void writeList(String filename){
	      try{
	    	    // Create file 
	    	    FileWriter fstream = new FileWriter(filename);
	    	    BufferedWriter out = new BufferedWriter(fstream);
	    		for(int i=0;i<list.size();i++){
	    			CyclicWord t = new CyclicWord(list.get(i),sw.length());
	    			out.write(t.toString());
	    			out.write("\n");
	    		}
	    	    out.close();
	     }catch (Exception e){//Catch exception if any
	    	      System.err.println("Error: " + e.getMessage());
	   	 }
	}
	/**
	 * Return if there exist the next curve
	 *
	@return True if there is a next curve, false if there isn't    
	 */
	public boolean hasNext(){
		if(index>= list.size()){
			return false;
		}
		return true;
	}
	/**
	 * Return the next curve
	 *
	@return the next curve  
	 */	
	public T next() {
		if(index<list.size()){
			int[] p = list.get(index);
			index++;
			Object[] o = new Object[2];
			o[0] = (Object) p;
			o[1] = (Integer) sw.length();
			C c;
			try {
				c = cClass.newInstance();
				c.base = sw.length();
				c.word = new int[p.length];
				System.arraycopy(p, 0, c.word, 0, p.length);
				
				T t = tClass.newInstance();
				t.baseword = c;
				t.sw = sw;
				return t;
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	/*public int[] get(int i){
		return list.get(i);
	}*/
	/**
	 * Return the amount of curves in the CurveList
	 *
	@return the amount of curves in the CurveList
	 */	
	public int size(){
		return list.size();
	}
	/**
	 * Add another curve by it's array representation
	 * 
	 @param  p the curve's array representation
	 */	
	public void add(int[] p){
		list.add(p);
	}
	/**
	 * Add another curve by it's array representation
	 * 
	 @param  c the curve   
	 */	
	public void add(T c){
		list.add(c.baseword.word);
	}
}
