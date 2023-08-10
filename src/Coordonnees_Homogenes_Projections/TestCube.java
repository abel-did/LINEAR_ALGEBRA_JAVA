
/**
 * Classe TestCube
 * 
 * @author Abel DIDOUH 
 * @version 20/10/2022
 */
public class TestCube
{

    /**
     * Constructor for objects of class Test
     */
    private TestCube() {}
    
    /**
     * Crée les sommets d'un cube centré sur l'origine et les mets das un tableau.
     * @return Tableau de Vecteur
     */
    private static Vecteur[] creerSommets()
    {
        Vecteur p1=new Vecteur(3);   // sommet arrière gauche bas
        p1.setCoordonnee(0,-1);
        p1.setCoordonnee(1,-1);
        p1.setCoordonnee(2,-1); 
        Vecteur p2=new Vecteur(3);   // sommet arrière gauche haut 
        p2.setCoordonnee(0,-1);
        p2.setCoordonnee(1,1);
        p2.setCoordonnee(2,-1); 
        Vecteur p3=new Vecteur(3);   // sommet arrière droite haute
        p3.setCoordonnee(0,1);
        p3.setCoordonnee(1,1);
        p3.setCoordonnee(2,-1); 
        Vecteur p4=new Vecteur(3);   //  sommet arrière droite bas
        p4.setCoordonnee(0,1);
        p4.setCoordonnee(1,-1);
        p4.setCoordonnee(2,-1); 
        Vecteur p5=new Vecteur(3);   // sommet avant gauche bas
        p5.setCoordonnee(0,-1);
        p5.setCoordonnee(1,-1);
        p5.setCoordonnee(2,1); 
        Vecteur p6=new Vecteur(3);   // sommet avant gauche haut 
        p6.setCoordonnee(0,-1); 
        p6.setCoordonnee(1,1);
        p6.setCoordonnee(2,1); 
        Vecteur p7=new Vecteur(3);   // sommet avant droite haute
        p7.setCoordonnee(0,1);
        p7.setCoordonnee(1,1);
        p7.setCoordonnee(2,1); 
        Vecteur p8=new Vecteur(3);   // sommet avant droite bas
        p8.setCoordonnee(0,1);
        p8.setCoordonnee(1,-1);
        p8.setCoordonnee(2,1); 
        
        return new Vecteur[]{p1,p2,p3,p4,p5,p6,p7,p8};
    }
    
    /**
     * Dessine sur le Plan les arêtes du cube dont les sommets sont dans le tableau de Vecteur;
     * @param pPlan : plan ou effectuer le dessin
     * @param pSommets : sommets du cube
     */
    private static void dessinerCube(final Plan pPlan, final Vecteur[] pSommets)
    {
        pPlan.effacer();
        //(p1,p2,p3,p4); // face arriere
        //(p5,p6,p7,p8); // face avant
        //(p1,p2,p6,p5); // face gauche
        //(p3,p4,p8,p7); // face droite
        //(p2,p3,p7,p6); // face haut
        //(p1,p4,p8,p5); // face bas
        
        // face arrière
        pPlan.dessinerSegmentEn2d(pSommets[0],pSommets[1]);
        pPlan.dessinerSegmentEn2d(pSommets[1],pSommets[2]);
        pPlan.dessinerSegmentEn2d(pSommets[2],pSommets[3]);
        pPlan.dessinerSegmentEn2d(pSommets[3],pSommets[0]);
        
        // face avant 
        pPlan.dessinerSegmentEn2d(pSommets[4],pSommets[5]);
        pPlan.dessinerSegmentEn2d(pSommets[5],pSommets[6]);
        pPlan.dessinerSegmentEn2d(pSommets[6],pSommets[7]);
        pPlan.dessinerSegmentEn2d(pSommets[7],pSommets[4]);
        
        // on relie la face avant à la face arrière
        pPlan.dessinerSegmentEn2d(pSommets[0],pSommets[4]);
        pPlan.dessinerSegmentEn2d(pSommets[1],pSommets[5]);
        pPlan.dessinerSegmentEn2d(pSommets[2],pSommets[6]);
        pPlan.dessinerSegmentEn2d(pSommets[3],pSommets[7]);
    }
    
    /**
     * Applique la transformation linéaire à l'ensemble des vecteurs du tableau
     * @param pVecteurs : tableau de Vecteur à transformer
     * @param pTransformation : transformation linéaire à appliquer
     */
    private static void appliquer(final Vecteur[] pVecteurs, final Matrice pTransformation)
    {    
        Vecteur vecteurEquation = new Vecteur(pTransformation.getNbColonnes());
        
        for(int i = 0; i < pVecteurs.length; i++)
        {
            vecteurEquation = pTransformation.multiplicationVectorielle(pVecteurs[i]);
            pVecteurs[i] = vecteurEquation;
        }
    }//appliquer
        
    /**
     * Procédure de test
     */
    public static void test(){
        // On crée un objet plan
        Plan p = new Plan();
        // On crée les sommets du cube
        Vecteur [] cube= creerSommets();
        
        //TP4 2 d
        //Matrice rotation
        Matrice rotation3 = new Matrice (3,3);
        rotation3.setRotation3dOx(Math.PI/4);
        appliquer(cube, rotation3);
        
        //TP4 2 e
        //Matrice Homothetie
        Matrice homothetie = new Matrice(3,3);
        homothetie.setHomothetie(4);
        appliquer(cube, homothetie);
        
        // On affiche le cube
        dessinerCube(p,cube);
    }
    
    /**
     * Fonction getRotation à trois paramètres réels alpha1, alpha2 et alpha3 
     * qui retourne la matrice correspondant à la composition des rotations d'angles 
     * autour alpha1, alpha2 et alpha3 autours respectivement des 3 axes 
     * (1 ; 0 ; 0), (0 ; 1 ; 0) et (0 ; 0 ; 1).
     */
    public Matrice getRotation(final double alpha1, final double alpha2, final double alpha3)
    {
        Matrice matriceRes = new Matrice(3,3);
        Vecteur vecteurAlpha = new Vecteur(3);
        
        vecteurAlpha.setCoordonnee(0, alpha1);
        vecteurAlpha.setCoordonnee(1, alpha2);
        vecteurAlpha.setCoordonnee(2, alpha2);
        
        matriceRes.setRotation3dOx(vecteurAlpha.getCoordonnee(0));
        //A finir 23/10/2022
        return matriceRes;
    }
}
