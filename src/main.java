import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class main {
    static Graph EcritureGraphe(String chemin) {
        BufferedReader lecteur;
        try {
            lecteur = new BufferedReader(new InputStreamReader( new FileInputStream(chemin), "UTF8"));
            String ligne;
            char c;
            int nb_sommets=0;
            int nb_arcs=0;
            boolean tableau_genere=false;
            String [] mots;
            String all_lines = "";
            String[] lignes=null;
            while(!tableau_genere){ //
                ligne = lecteur.readLine();
                mots=ligne.split(" ");
                c=ligne.charAt(0);
                if(c=='p') {
                    nb_sommets=Integer.parseInt(mots[1]);
                    nb_arcs=Integer.parseInt(mots[2]);
                    tableau_genere=true;
                }
            }
            Sommet[] l_s=new Sommet[nb_sommets];
            Arc[] l_arc=new Arc[nb_arcs];
            int n_s1, n_s2, poids;
            ArrayList<Arc> l_arcs=new ArrayList<Arc>();
            HashMap<Sommet, ArrayList<Arc>> l_adj=new HashMap();
            int i=0;
            while((ligne=lecteur.readLine()) != null){
                c=ligne.charAt(0);
                mots=ligne.split(" ");
                if(c=='e') {
                    n_s1=(Integer.parseInt(mots[1]))-1;
                    if(l_s[n_s1]==null){
                        l_s[n_s1]=new Sommet(n_s1);
                    }
                    n_s2=(Integer.parseInt(mots[2]))-1;
                    if(l_s[n_s2]==null){
                        l_s[n_s2]=new Sommet(n_s2);
                    }
                    poids=Integer.parseInt(mots[3]);

                    l_arc[i]=new Arc(l_s[n_s1], l_s[n_s2], poids, i);
                    l_s[n_s1].addArc(l_arc[i]);
                    l_s[n_s2].addArc(l_arc[i]);
                    i++;
                }
            }

            ArrayList<Sommet> liste_s = new ArrayList<Sommet>(Arrays.asList(l_s));
            ArrayList<Arc> liste_a = new ArrayList<Arc>(Arrays.asList(l_arc));
            lecteur.close();
            Graph g=new Graph(liste_s,liste_a);
            return g;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    public static void main(String[] args){
        String fichier="crd301.mst";
        String s="src/"+fichier;
        Graph g=EcritureGraphe(s);
        g.Kruskal1();
        g.Kruskal2();
        g.Prim();
        g.dMST(2);
        g.afficher();
        Stack pile= new Stack();
        for(int i=0; i<20; i++){
            if(i%2==0) pile.push(i);
        }
    }
}
