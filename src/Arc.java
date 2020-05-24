public class Arc implements Comparable<Arc>{
    private int poids, num_arc;
    private Sommet s1, s2;

    public Arc(Sommet s1, Sommet s2, int poids, int n){
        this.s1=s1;
        this.s2=s2;
        this.poids=poids;
        num_arc=n;
    }
    public Arc(){
        poids=(int) Double.POSITIVE_INFINITY;
    }

    public int getPoids(){
        return this.poids;
    }
    public String toString(){
        return String.valueOf(poids);
    }

    public void setNum (int n){
        num_arc=n;
    }

    public int compareTo(Arc a) {
        if(a.getPoids()>poids) return -1;
        else if(a.getPoids()<poids) return 1;
        else return 0;
    }

    public Sommet getSommet1() {
        return s1;
    }

    public Sommet getSommet2() {
        return s2;
    }

    public Sommet getS(Sommet s){
        if(s.equals(s1)) return s2;
        else if(s.equals(s2)) return s1;
        else return null;
    }

    public void setPoids(int p) {
        poids=p;
    }
}
