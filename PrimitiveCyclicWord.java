package boundary;

public class PrimitiveCyclicWord extends CyclicWord {
	public PrimitiveCyclicWord() {
	}
	public PrimitiveCyclicWord(int[] p, int b) {
		super(p, b);
	}
	public PrimitiveCyclicWord next(){
		CyclicWord v = super.next();
		while(true){
			if(v.isPrimitive()){
				return new PrimitiveCyclicWord(v.word, base);
			}
			v = v.next();
		}
	}
}
