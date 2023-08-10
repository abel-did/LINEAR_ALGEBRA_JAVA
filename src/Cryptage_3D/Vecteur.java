/**
 * Classe Vecteur
 *
 * @author DIDOUH Abel
 * @version 25/10/2022
 * @version Exercice TP6
 */
public class Vecteur
{
    private int aDimension;
    private double[] aTableau;
    
    public Vecteur(final int pDimension)
    {
        this.aTableau  = new double[pDimension];
        for(int i = 0; i < aDimension; i++)
        {
            this.aTableau[i] = 0;
        }
    }//Vecteur()
    /**
     * Accesseur indice i de la coordonnée à retourner
     * @param de type int 
     */
    public double getCoordonnee(final int pI)
    {
        return this.aTableau[pI];
    }//getCoordonnee
    /**
     * Accesseur retourne la dimension de l'espace de definition du vecteur
     */
    public int getDimension()
    {
        return this.aTableau.length;
    }//getDimension
    /**
     * Modificateur 
     * @param int specifie l'indice de la coordonnees de l'instance 
     * @param double specifie la valeur à affecter à cette coordonnée
     */
    public void setCoordonnee(final int pI, final double pCoordonnee)
    {
        this.aTableau[pI] = pCoordonnee;
    }//setCoordonnee
    /**
     * Accesseur X
     * @return double
     */
    public double getX()
    {
        return this.aTableau[0];
    }//getX
    /**
     * Accesseur Y
     * @return double
     */
    public double getY()
    {
        return this.aTableau[1];
    }//getY
    /**
     * Procedure publique à un parametre qui 
     * multiplie l'instance courante du Vecteur par le paramètre 
     */
    public void multiplicationScalaire(final double pScalaire)
    {
        for(int i = 0; i < this.getDimension(); i++)
        {
            this.aTableau[i] = pScalaire * this.aTableau[i];
        }
    }//multiplicationScalaire
    /**
     * Methode publique norme 
     */
    public double norme()
    {
        double vSomme = 0.0;
        for (int i = 0; i < getDimension(); i++)
        {
            vSomme = vSomme + this.aTableau[i]*this.aTableau[i];
        }
        return Math.sqrt(vSomme);
    }//norme
    /**
     * Procedure publique normaliser 
     */
    public void normaliser()
    {
        double vNorme = this.norme();
        for (int i = 0; i < getDimension(); i++)
        {
            this.aTableau[i] = this.aTableau[i] / vNorme;
        }
    }//normaliser
    /**
     * Somme vectorielle
     */
    public void sommeVectorielle(final Vecteur pVecteur)
    {
        for (int i = 0; i < getDimension(); i++)
        {
            this.aTableau[i] = pVecteur.aTableau[i] + this.aTableau[i];
        }
    }//sommeVectorielle
    /**
     * Produit scalaire
     */
    public double produitScalaire(final Vecteur pVecteur)
    {
        double vRes = 0.0;
        for (int i = 0; i < getDimension(); i++)
        {
            vRes = vRes +  (pVecteur.aTableau[i] * this.aTableau[i]);
        }
        return vRes;        
    }
    /**
     * Produit vectoriel en 3 dimensions
     */
    public Vecteur produitVectoriel3d(final Vecteur pVecteur)
    {
        if(this.getDimension() == 3 && pVecteur.getDimension() == 3)
        {
            Vecteur vecteur = new Vecteur(3);
            vecteur.setCoordonnee(0,this.getCoordonnee(1)*pVecteur.getCoordonnee(2) - this.getCoordonnee(2)*pVecteur.getCoordonnee(1));
            vecteur.setCoordonnee(1,this.getCoordonnee(2)*pVecteur.getCoordonnee(0) - this.getCoordonnee(0)*pVecteur.getCoordonnee(2));
            vecteur.setCoordonnee(2,this.getCoordonnee(0)*pVecteur.getCoordonnee(1) - this.getCoordonnee(1)*pVecteur.getCoordonnee(0));
            return vecteur;
        }
        else 
        {
            return null;
        }
    }
    /**
     * Orthogonalite
     */
    public boolean estOrthogonal(final Vecteur pVecteur)
    {
        return this.approche(produitScalaire(pVecteur), 0.0, 0.0001);
    }
    /**
     * Colinearite
     */
    public boolean estColineaire(final Vecteur pVecteur)
    {
        return this.approche(this.getX() * pVecteur.getY(), this.getY() * pVecteur.getX(), 0.00001); 
    }
    /**
     * Approche
     */
    public static boolean approche(final double pA, final double pB, final double pEpsilon)
    {
      if(Math.abs(pA-pB)<pEpsilon)
      {
           return true;
      }
      else
      {
           return false;
      }
    }//approche
    /**
     * Coplanarité en 3 dimensions
     */
    public boolean estCoplanaire3d(final Vecteur pVecteur1, final Vecteur pVecteur2)
    {
        Vecteur vVecteur = pVecteur1.produitVectoriel3d(pVecteur2);
        return this.estOrthogonal(vVecteur);
    }//estCoplanaire
    /**
     * procédure publique normaliseHomogene sans paramètre 
     * qui normalise la coordonnée homogène du vecteur courant.
     */
    public void normaliseHomogene()
    {
        for(int i =0; i < this.getDimension(); i++)
        {
            this.setCoordonnee(i, 1/this.getCoordonnee(this.getDimension()-1)*this.getCoordonnee(i));
        }
    }
}//Vecteur
