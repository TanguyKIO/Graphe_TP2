import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Stack;

public class Graph {
    private ArrayList<Arc> l_arcs = new ArrayList<>();
    private HashMap<Integer,Sommet> map_s = new HashMap<>();
    private ArrayList<Sommet> l_s = new ArrayList<>();
    private ArrayList<Arc> T_K1,T_K2, T_P, T_D;
    private Arc[] liste_a;

    public Graph(ArrayList<Sommet> l_s, ArrayList<Arc> l_arcs){
        this.l_s=l_s;
        int i=0;
        for(Sommet s : l_s){
            map_s.put(i,s);
            i++;
        }
        this.l_arcs=l_arcs;
    }

    private void triArc (int n){
        if(n==0) Collections.sort(l_arcs);
        if(n==1) Collections.sort(l_arcs, Collections.reverseOrder());
        int i = 0;
        liste_a = new Arc[l_arcs.size()];
        for (Arc a : l_arcs) {
            a.setNum(i);
            liste_a[i] = a;
            i++;
        }
    }

    public void Kruskal1(){
        for(Sommet s : l_s){
            makeSet(s);
        }
        triArc(0);
        int i=0;
        T_K1 = new ArrayList<>();
        while(T_K1.size()<l_s.size()-1){
            if(!find(liste_a[i].getSommet1()).equals(find(liste_a[i].getSommet2()))){
                T_K1.add(liste_a[i]);
                union(liste_a[i].getSommet1(), liste_a[i].getSommet2());
            }
            i++;
        }
    }

    public void Kruskal2(){
        triArc(1);
        int i=0;
        T_K2 = (ArrayList<Arc>) l_arcs.clone();
        while(T_K2.size()>=l_s.size()){
            if(testConnexe(T_K2,liste_a[i])){
                T_K2.remove(liste_a[i]);
            }
            i++;
        }
    }

    public void Prim(){
        Sommet z=null;
        int poids_min;
        Arc a=new Arc();
        int x =(int) (Math.random()*l_s.size());
        Sommet s = map_s.get(x);
        ArrayList<Sommet> R = new ArrayList<Sommet>();
        T_P = new ArrayList<Arc>();
        ArrayList<Arc> l_arc = (ArrayList<Arc>) l_arcs.clone();
        R.add(s);
        while(l_s.size()!=R.size()){
            poids_min=(int) Double.POSITIVE_INFINITY;
            for(Arc arc : l_arc){
                if(!R.contains(arc.getSommet1()) && R.contains(arc.getSommet2()) && arc.getPoids()<poids_min){
                    a = arc;
                    poids_min = arc.getPoids();
                    z = arc.getSommet1();
                }
                else if(R.contains(arc.getSommet1()) && !R.contains(arc.getSommet2()) && arc.getPoids()<poids_min){
                        a=arc;
                        poids_min=arc.getPoids();
                        z=arc.getSommet2();
                }
            }
            R.add(z);
            l_arc.remove(a);
            T_P.add(a);
        }
    }

    public void dMST(int d){
        Sommet z=null;
        int poids_min;
        Arc a=new Arc();
        int x =(int) (Math.random()*l_s.size());
        Sommet s = map_s.get(x);
        ArrayList<Sommet> R = new ArrayList<Sommet>();
        T_D = new ArrayList<Arc>();
        ArrayList<Arc> l_arc = (ArrayList<Arc>) l_arcs.clone();
        R.add(s);
        while(l_s.size()!=R.size()){
            poids_min=(int) Double.POSITIVE_INFINITY;
            for(Arc arc : l_arc){
                if(!R.contains(arc.getSommet1()) && R.contains(arc.getSommet2()) && arc.getPoids()<poids_min && arc.getSommet2().getDegre()<d){
                        a=arc;
                        poids_min=arc.getPoids();
                        z=arc.getSommet1();
                }
                if(!R.contains(arc.getSommet2()) && R.contains(arc.getSommet1()) && arc.getPoids()<poids_min && arc.getSommet1().getDegre()<d){
                    a=arc;
                    poids_min=arc.getPoids();
                    z=arc.getSommet2();
                }
            }
            if(!R.contains(z)) {
                R.add(z);
                a.getS(z).degPlus();
                z.degPlus();
                l_arc.remove(a);
                T_D.add(a);
            }
        }
    }

    public boolean testConnexe(ArrayList<Arc> T,  Arc ac){
        for(Sommet s : l_s){
            s.setVisite(false);
        }
        Sommet a = map_s.get(0);
        ArrayList<Arc> T1= (ArrayList<Arc>) T.clone();
        T1.remove(ac);
        ArrayList<Sommet> l =new ArrayList<>();
        Sommet parent,x, y;
        Stack P=new Stack();
        P.push(a);
        a.setVisite(true);
        l.add(a);
        while(!P.isEmpty()){
            x=(Sommet)P.peek();
            P.pop();
            for(Arc arc : x.getL_adj()) {
                y = arc.getS(x);
                if(T1.contains(arc) && !y.isVisite()) {
                    y.setVisite(true);
                    l.add(y);
                    P.push(y);
                }
            }
            x.setVisite(true);
        }
        return l.size()==l_s.size();
    }

    private void makeSet(Sommet x){
        x.setParent(null);
    }

    private Sommet find (Sommet x){
        if(x.getParent()==null){
            return x;
        }
        return find(x.getParent());
    }

    private void union (Sommet x, Sommet y){
        if(!find(x).equals(find(y))){
            find(x).setParent(find(y));
        }
    }

    public int[] afficher(){
        int s_k1=0;
        int s_k2=0;
        int s_p=0;
        int s_d=0;
        int i=0;
        int j=0;
        int k=0;
        int l=0;
        for(Arc a : T_K1){
            i++;
            s_k1+=a.getPoids();
        }
        for(Arc a : T_K2){
            j++;
            s_k2+=a.getPoids();
        }
        for(Arc a : T_P){
            k++;
            s_p+=a.getPoids();
        }
        for(Arc a : T_D){
            l++;
            s_d+=a.getPoids();
        }

        System.out.println("Kruskal1 : "+s_k1);
        System.out.println("Kruskal2 : "+s_k2);
        System.out.println("Prim : "+s_p);
        System.out.println("d-MST : "+s_d);
        int[] res = { s_k1, s_k2, s_p,s_d};
        return res;
    }
}
