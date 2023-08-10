
/**
 * Fonction de test 
 * 
 * @author Votre_Nom
 * @version 0.1
 */
public class Test2
{
  

    /**
     * Constructor for objects of class Test
     */
    private Test2() {}

    /**
     * Crée les sommets d'un parallélépipède rectangle centré sur l'origine en coordonnées homogènes et les mets das un tableau.
     * @return Tableau de Vecteur
     */
    private static Vecteur[] creerSommets()
    {
        Vecteur p1=new Vecteur(4);   // sommet arrière gauche bas
        p1.setCoordonnee(0,-2);
        p1.setCoordonnee(1,-1);
        p1.setCoordonnee(2,-2.5); 
        Vecteur p2=new Vecteur(4);   // sommet arrière gauche haut 
        p2.setCoordonnee(0,-2);
        p2.setCoordonnee(1,1);
        p2.setCoordonnee(2,-2.5); 
        Vecteur p3=new Vecteur(4);   // sommet arrière droite haute
        p3.setCoordonnee(0,2);
        p3.setCoordonnee(1,1);
        p3.setCoordonnee(2,-2.5); 
        Vecteur p4=new Vecteur(4);   //  sommet arrière droite bas
        p4.setCoordonnee(0,2);
        p4.setCoordonnee(1,-1);
        p4.setCoordonnee(2,-2.5); 
        Vecteur p5=new Vecteur(4);   // sommet avant gauche bas
        p5.setCoordonnee(0,-2);
        p5.setCoordonnee(1,-1);
        p5.setCoordonnee(2,2.5); 
        Vecteur p6=new Vecteur(4);   // sommet avant gauche haut 
        p6.setCoordonnee(0,-2); 
        p6.setCoordonnee(1,1);
        p6.setCoordonnee(2,2.5); 
        Vecteur p7=new Vecteur(4);   // sommet avant droite haute
        p7.setCoordonnee(0,2);
        p7.setCoordonnee(1,1);
        p7.setCoordonnee(2,2.5); 
        Vecteur p8=new Vecteur(4);   // sommet avant droite bas
        p8.setCoordonnee(0,2);
        p8.setCoordonnee(1,-1);
        p8.setCoordonnee(2,2.5); 
        
        Vecteur [] para = new Vecteur[]{p1,p2,p3,p4,p5,p6,p7,p8};
        for(int i=0;i<para.length;i++)
        {
            para[i].setCoordonnee(3,1);
        }
        return para;
    }
    
    /**
     * Dessine le Parallelepipede dont les sommets sont dans le tableau de Vecteur sur le Plan
     * @param pPlan : plan ou effectuer le dessin
     * @param pSommets : sommets du Parallelepipede
     */
    private static void dessinerParallelepipede(final Plan pPlan, final Vecteur[] pSommets)
    {
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
     * et met le résultat dans un nouveau tableau de vecteurs
     * @param pVecteurs : tableau de Vecteur à transformer
     * @param pTransformation : transformation linéaire à appliquer
     * @return tableau de vecteurs contenant les vecteurs transformés
     */
    private static Vecteur [] getTransformee(final Vecteur[] pVecteurs, final Matrice pTransformation)
    {
        Vecteur [] res = new Vecteur[pVecteurs.length];
        
        for(int i=0;i<pVecteurs.length;i++)
        {
            res[i] = pTransformation.multiplicationVectorielle(pVecteurs[i]);
        }  
        return res;
    }
   
    /**
     * Fonction de test
     */
    public static void test(){
        // On crée un objet plan
        Plan p = new Plan();
        // On crée les sommets du parallelepipede
        Vecteur [] obj= creerSommets();
        
        //
        
        Matrice projOrthOxy = new Matrice(3,4);
        Matrice translationOxy = new Matrice(4,4);
        Matrice resOxy         = new Matrice(3,4);
        
        Vecteur translation1 = new Vecteur(3);
        translation1.setCoordonnee(0,-3);
        translation1.setCoordonnee(1,-3);
        translation1.setCoordonnee(2, 0);
        
        projOrthOxy.setProjectionOrthoOxyHomogene3d();
        translationOxy.setTranslationHomogene3d(translation1);
        resOxy = projOrthOxy.produitMatriciel(translationOxy);
        
        dessinerParallelepipede(p, getTransformee(obj,  resOxy));
        
        //
        
        Matrice projOrthOxz = new Matrice(3,4);
        Matrice translationOxz = new Matrice(4,4);
        Matrice resOxz = new Matrice(3,4);
        
        Vecteur translation2 = new Vecteur(3);
        translation2.setCoordonnee(0,-3);
        translation2.setCoordonnee(1, -3);
        translation2.setCoordonnee(2, 3);
        
        
        projOrthOxz.setProjectionOrthoOxzHomogene3d();
        translationOxz.setTranslationHomogene3d(translation2);
        resOxz = projOrthOxz.produitMatriciel(translationOxz);
        dessinerParallelepipede(p, getTransformee(obj, resOxz));
        
        //
        
        Matrice projOrthOyz = new Matrice(3,4);
        Matrice translationOyz = new Matrice(4,4);
        Matrice resOyz = new Matrice(3,4);
        
        Vecteur translation3 = new Vecteur(3);
        translation3.setCoordonnee(0,3);
        translation3.setCoordonnee(1, 3);
        translation3.setCoordonnee(2, 3);
        
        projOrthOyz.setProjectionOrthoOyzHomogene3d();
        translationOyz.setTranslationHomogene3d(translation3);
        resOyz = projOrthOyz.produitMatriciel(translationOyz);
        dessinerParallelepipede(p, getTransformee(obj, resOyz));
        
        //
        Matrice projPersp = new Matrice(3, 4);
        Matrice translationPers = new Matrice(4,4);
        Matrice resPers = new Matrice(3,4);
        
        Vecteur translationP = new Vecteur(3);
        translationP.normaliseHomogene();
        translationP.setCoordonnee(0, 4);
        translationP.setCoordonnee(1, -4);
        translationP.setCoordonnee(2, 12);
        
        
        translationPers.setTranslationHomogene3d(translationP);
        projPersp.setProjectionPerspectiveOxyHomogene3d(8);
        resPers = projPersp.produitMatriciel(translationPers);           
        
        Vecteur [] objN =  getTransformee(obj, resPers);
        for(int i = 0; i < objN.length; i++)
        {
            objN[i].normaliseHomogene();
        }

        dessinerParallelepipede(p, objN);
    }
}
