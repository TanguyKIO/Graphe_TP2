import java.io.*;
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

    public static void main(String[] args) {
        String fichier = "C:\\Users\\loko6\\IdeaProjects\\Graphe_TP2\\src\\Arbres";

        File file = new File(fichier);
        File[] files = file.listFiles();
        long start;
        long end;

        PrintWriter writer = null;
        try {
            writer = new PrintWriter("C:\\Users\\loko6\\IdeaProjects\\Graphe_TP2\\src" + "resultats" + ".csv", "UTF-8");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < files.length; i++) { //Parcour tout les fichiers
            try {
                System.out.println(files[i].getName());
                String s = fichier + "\\" + files[i].getName();
                Graph g = EcritureGraphe(s);

                start = System.currentTimeMillis();
                g.Kruskal1();
                end = System.currentTimeMillis();
                long T_k1 = end - start;

                start = System.currentTimeMillis();
                g.Kruskal2();
                end = System.currentTimeMillis();
                long T_k2 = end - start;

                start = System.currentTimeMillis();
                g.Prim();
                end = System.currentTimeMillis();
                long T_Pr = end - start;

                start = System.currentTimeMillis();
                g.dMST(2);
                end = System.currentTimeMillis();
                long T_dMST2 = end - start;

                int[] res = g.afficher();
                writer.println(files[i].getName() + ";" + res[0] + ";" + T_k1 + ";" + res[1] + ";" + T_k2 + ";" + res[2] + ";" + T_Pr + ";" + res[3] + ";" + T_dMST2);
            } catch (NullPointerException e) {
                System.out.println("Erreur sur le fichier : " + files[i].getName() + "\n" + e);
            } catch (Exception e) {
                System.out.println("Erreur sur le fichier : " + files[i].getName() + "\n" + e);
            }
        }
        writer.close();
        System.out.println("Fin du traitement");
    }
}
