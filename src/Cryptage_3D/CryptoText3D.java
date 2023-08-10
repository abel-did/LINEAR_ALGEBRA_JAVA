import java.awt.Font;
import java.awt.Shape;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;
/**
 * 
 * Classe CryptoText3D
 * @author 
 * @version 0.1
 */
public class CryptoText3D implements Scene.MouseListener
{
     
    // objet 3D a afficher
    private  Vecteur [] t;
   
    //Distance de la camera
    private double distanceCamera = 8;
    
    // Projection perspective dans le plan de la camera
    private  Matrice projPersp;
      
    // Zone de dessin
    private  Scene scene;
    
    // Générationd de nombres aleatoires
    private Random rnd = new Random();
    

    /**
     * Constructor for objects of class CryptoText3D
     */
    public CryptoText3D() 
    {
        // On cree la scene
        scene = new Scene();
        // On genere l'objet 3D represantant le texte
        t= creerTexte("Le professeur\nBrunau Seide\nAvec sa\ntasse à café\nEn salle\n 6553.");
        // Matrice de projection perspective
        projPersp = new Matrice(3,4);
        Matrice RotationR1 = new Matrice(4,4);
        Matrice RotationR2 = new Matrice(4,4);
        Matrice ResRotationR = new Matrice(4,4);
        Matrice Res = new Matrice(3,4);
        
        projPersp.setProjectionPerspectiveOxyHomogene3d(3);
        
        
        
        
        //d
        double r1 = rnd.nextDouble()*-Math.PI+Math.PI/2;
        double r2 = rnd.nextDouble()*-Math.PI+Math.PI/2;
        RotationR1.setRotationOxHomogene3d(r1);
        RotationR2.setRotationOyHomogene3d(r2);
        ResRotationR = RotationR2.produitMatriciel(RotationR1);
        
        
        
        // Cryptage du texte
        t=getTransformee(cryptage(t),ResRotationR);
        
        
        
        
        
        
        // Gestion du péplacement de la souris
        scene.addMouseListener(this);
    }

       /**
     * Procedure appelee lorsque la souris bouge. Les coordonnees de la souris 
     * sont donnees sous forme de coordonnees geographiques: c-a-d longitude + latitude
     * où on considère que l'espace de la fenêtre correspond à une demi-sphère.
     * 
     * En d'autres termes, lorsque la souris se trouve en haut (resp. en bas) de l'écran, 
     * elle est au pole nord (resp. pole sud). Un déplacement vers la gauche (resp. a droite) 
     * de l'écran correspond à un déplacement vers l'ouest (resp. l'est).
     * 
     * Les coordonnees des quatre coin de l'ecran sont:
     *  - coin supérieur gauche : (-pi/2,pi/2)
     *  - coin supérieur droit : (pi/2,pi/2)
     *  - coin inférieur gauche : (-pi/2,-pi/2)
     *  - coin inférieur droit : (pi/2,-pi/2)
     * 
     * @param u longitude comprise entre -pi/2 et pi/2 (correspond a la position en x sur la fenetre)
     * @param v latitude comprise entre -pi/2 et pi/2 (correspond a la position en y sur la fenetre)
     */
     public void deplacementObservateur(double u, double v)
    {

         Vecteur [] t1=t;
         
         //Vecteur Translation
         Vecteur translationDistCamera = new Vecteur(3);
         translationDistCamera.setCoordonnee(0, 0);
         translationDistCamera.setCoordonnee(1, 0);
         translationDistCamera.setCoordonnee(2, distanceCamera);
         
         //Matrice de transformation
         Matrice matriceTranslation     = new Matrice(4,4);
         Matrice resultatTranslation    = new Matrice(3,4);
         
         Matrice resultatRotationOx     = new Matrice(3,4);
         Matrice resultatRotG           = new Matrice(3,4);
         
         Matrice resultatRotation     = new Matrice(3,4);
         Matrice rotationOy             = new Matrice(4,4);
         Matrice rotationOx             = new Matrice(4,4);
         
         //Creation des rotations
         rotationOy.setRotationOxHomogene3d(v); //v
         rotationOx.setRotationOyHomogene3d(u); //u
         
         resultatRotG = rotationOy.produitMatriciel(rotationOx);
         
         matriceTranslation.setTranslationHomogene3d(translationDistCamera);
         
         resultatTranslation = matriceTranslation.produitMatriciel(resultatRotG );
         resultatRotation = projPersp.produitMatriciel(resultatTranslation);
        
         t1=getTransformee(t1,resultatRotation);
         
         dessiner(t1);
    }
    
    /**
     * Déplace alétoirement le vecteur selon sa ligne de perspective
     * @param pv Vecteur à transformer
     * @return nouveau vecteur aléatoir sur la ligne de perspective de pv
     */
    public Vecteur deplacementAleatoire(Vecteur pv)
    {
        //1
        double d=rnd.nextDouble()*15.0+8.0; // d est un nombre aléatoire compris entre 8 et 23
        //2
        Matrice matriceProj = new Matrice(3,4);
        Matrice matriceVecteur = new Matrice(4,1);
        Matrice resultatPM = new Matrice(3,1);
        
        matriceProj.setProjectionPerspectiveOxyHomogene3d(d);
        
        matriceVecteur.setCoefficient(0,0, pv.getCoordonnee(0));
        matriceVecteur.setCoefficient(1,0, pv.getCoordonnee(1));
        matriceVecteur.setCoefficient(2,0, pv.getCoordonnee(2));
        matriceVecteur.setCoefficient(3,0, 1);
        
        resultatPM = matriceProj.produitMatriciel(matriceVecteur);
        
        //3
        Vecteur vectP = new Vecteur(4);
        double w = resultatPM.getCoefficient(2, 0);
        vectP.setCoordonnee(0, resultatPM.getCoefficient(0,0)/w);
        vectP.setCoordonnee(1, resultatPM.getCoefficient(1,0)/w);
        vectP.setCoordonnee(2, d);
        vectP.setCoordonnee(3, 1);
        //4
        return vectP;
    }
    
    /**
     * La fonction de cryptage prend en paramètre un objet 3D et renvoie une version altérée de cette objet.
     * @param pVecteurs tableau de vecteurs à crypter
     * @return tableau de vecteurs cryptés
     */
    private  Vecteur[] cryptage(final Vecteur[] pVecteurs)
    {
        // 1
        Matrice matriceTranslation = new Matrice(4,4);
        Vecteur vectTranslation = new Vecteur(3);
        vectTranslation.setCoordonnee(0, 0);
        vectTranslation.setCoordonnee(1, 0);
        vectTranslation.setCoordonnee(2, distanceCamera);
        matriceTranslation.setTranslationHomogene3d(vectTranslation);
        
        Vecteur [] objN = getTransformee(pVecteurs, matriceTranslation);
        
        //2
        
        for(int i = 0; i < objN.length; i++)
        {
            objN[i] = deplacementAleatoire(objN[i]);
        }
        //3
        Vecteur vectTranslation0 = new Vecteur(3);
        vectTranslation0.setCoordonnee(0, 0);
        vectTranslation0.setCoordonnee(1, 0);
        vectTranslation0.setCoordonnee(2, -distanceCamera);
        Matrice matriceTranslation0 = new Matrice(4,4);
        matriceTranslation0.setTranslationHomogene3d(vectTranslation0);
        
        Vecteur [] objDA0 = getTransformee(objN, matriceTranslation0);
        
        return objDA0;
    }
    

    
    /**
     * Dessine l'objet 3D stockés dans un tableau de vecteur
     * @param obj coordonnées des points des segments
     */
    public void dessiner(Vecteur [] obj)
    {
         scene.effacer();
         for(int i=0;i<obj.length;i++)
             obj[i].normaliseHomogene();
             
         dessinerTexte(scene,obj);
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
            res[i]=pTransformation.multiplicationVectorielle(pVecteurs[i]);
        return res;
    }
    
    /**
     * Créé un objet 3D représentant un texte
     * @param text : texte à transformer
     * @return tableau contenant les extrémités des segments composants l'objet 3D
     */
    private  Vecteur[] creerTexte(String text)
    {   
        ArrayList<Vecteur> list = new ArrayList<Vecteur>();
        Font f = new Font("Courier", Font.PLAIN, 12);
        FontRenderContext frc = new FontRenderContext(null,true,false);
        
        String [] line=text.split("\n");
        double h=0;
        double w=0;
        double lsep=2;
        for(int i=0;i<line.length;i++)
        {
            String str= line[i];
            GlyphVector gv = f.createGlyphVector(frc, str);
            Rectangle2D r = gv.getVisualBounds();
            h+=r.getHeight();
            if(i!=line.length-1)
                h+=lsep;
            w=Math.max(w, r.getWidth());
        }
        double ch= -h/2;
        for(int j=0;j<line.length;j++)
        {
            String str= line[j];
            GlyphVector gv = f.createGlyphVector(frc, str);
            Rectangle2D r = gv.getVisualBounds();
            AffineTransform translation = AffineTransform.getTranslateInstance(-r.getWidth()/2.0,ch);
            ch+=r.getHeight()+lsep;
            AffineTransform sym = new AffineTransform(0.2,0.0,0.0,-0.2,0.0,0.0);
            translation.preConcatenate(sym);
            for(int i=0;i<gv.getNumGlyphs();i++)
            {
                Point2D p = gv.getGlyphPosition(i);
                Shape s = gv.getGlyphOutline(i);
                PathIterator pi = s.getPathIterator(translation,0.1);
                Vecteur v=null;
                while(!pi.isDone())
                {
                    double [] coords = new double[6];
                    int segtype = pi.currentSegment(coords);
                    switch(segtype){
                        case PathIterator.SEG_CLOSE:
                            break;
                        case PathIterator.SEG_LINETO:
                            list.add(v);
                            v=new Vecteur(4);
                            v.setCoordonnee(0,coords[0]);
                            v.setCoordonnee(1,coords[1]);
                            v.setCoordonnee(2,0);
                            v.setCoordonnee(3,1);
                            list.add(v);
                            break;
                        case PathIterator.SEG_MOVETO:
                            v=new Vecteur(4);
                            v.setCoordonnee(0,coords[0]);
                            v.setCoordonnee(1,coords[1]);
                            v.setCoordonnee(2,0);
                            v.setCoordonnee(3,1);
                        break;
                    }
                    pi.next();
                }
            }
        }
        return list.toArray(new Vecteur[]{});
    }
    
    /**
     * Dessine le texte dont les sommets sont dans le tableau de Vecteur sur le Plan
     * @param pPlan : plan ou effectuer le dessin
     * @param pSommets : sommets du texte
     */
    private  void dessinerTexte(final Scene scene, final Vecteur[] pSommets)
    {
        for(int i=0;i<pSommets.length;i+=2)
            scene.dessinerSegmentEn2d(pSommets[i],pSommets[i+1]);
    }
}
