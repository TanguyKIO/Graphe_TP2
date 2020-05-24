import java.util.ArrayList;

public class Sommet implements Cloneable{
    private int num;
    private ArrayList<Arc> l_adj=new ArrayList<Arc>();
    private boolean visite;
    private Sommet parent;
    private int degre;

    public Sommet(int n){
        visite=false;
        parent=null;
        num=n;
        degre=0;
    }

    public void addArc(Arc arc) {
        l_adj.add(arc);
    }

    public int getDegre(){return degre;}
    public void degPlus(){ degre++;}
    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public ArrayList<Arc> getL_adj() {
        return l_adj;
    }

    public void setL_adj(ArrayList<Arc> l_adj) {
        this.l_adj = l_adj;
    }

    public String toString(){
        return String.valueOf(num);
    }

    public Sommet getParent() {
        return parent;
    }

    public void setParent(Sommet parent) {
        this.parent = parent;
    }

    public boolean isVisite() {
        return visite;
    }

    public void setVisite(boolean visite) {
        this.visite = visite;
    }

    public Sommet clone() {
        Sommet s= null;
        try {
            s =(Sommet) super.clone();
        } catch(CloneNotSupportedException cnse) {
            cnse.printStackTrace(System.err);
        }
        return s;
    }

    public boolean equals(Sommet s){
        return s.getNum()==num;
    }
}
