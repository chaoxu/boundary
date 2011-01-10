package boundary;

public class PrimitiveDoublyCyclicWord extends PrimitiveCyclicWord{
	public PrimitiveDoublyCyclicWord(){
	}
	public PrimitiveDoublyCyclicWord(int[] p, int b) {
		super(p, b);
	}
	public PrimitiveDoublyCyclicWord next(){
		PrimitiveCyclicWord v = super.next();
		while(true){
			if(v.isDoublySmallest()){
				return new PrimitiveDoublyCyclicWord(v.word,base);
			}
			v = v.next();
		}
	}
}