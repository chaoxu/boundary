package boundary;

public final class Word {
	public static int bar(int i){
		if(i%2==0){
			return i+1;
		}
		return i-1;
	}
	
	/*
	public static CyclicWord next(CyclicWord w){
		int[] v = w.word;
		int base = w.base;
		int count=0;
		for(int i=0;i<v.length;i++){
			if(v[i]==base-1){
				count++;
			}
		}
		if(count==v.length){
			w.word = new int[v.length+1];
			return w;
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
		return w;
	}
	
	public static boolean isReduced(CyclicWord w){
		for(int i = 0 ; i < w.length() ; i++){
			if(w.get(i) == bar(w.get(i+1))){
				return false;
			}
		}
		return true;
	}
	
	public static boolean isSmallest(CyclicWord w){
		for(int i = 1 ; i < w.length() ; i++){
			for(int j=0;j<w.length();j++){
				if(w.get(i+j)>w.get(j)){
					break;
				}
				if(w.get(i+j)<w.get(j)){
					return false;
				}
			}
		}
		return true;
	}

	public static boolean isDoublySmallest(CyclicWord w){
		boolean r = false;
		CyclicWord v = w.clone();
		for(int i=0;i<w.length();i++){
			v.word[i] = bar(w.word[w.length()-1-i]);			
		}
		for(int i = 0 ; i < w.length() ; i++){
			for(int j=0;j<w.length();j++){
				if(v.get(i+j)>w.get(j)){
					break;
				}
				if(v.get(i+j)<w.get(j)){
					return false;
				}
			}
		}
		return isSmallest(w);
	}
	
	public static boolean isPrimitive(CyclicWord w) {
		for(int n = 1; n <= w.length()/2;n++){
			if(w.length()%n==0){
				boolean h = true;
				for(int j=0;j<w.length()/n;j++){
					boolean hit = true;
					for(int i=0;i<n;i++){
						if(w.get(i)!=w.get(j*n+i)){
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
		
	}*/
}
