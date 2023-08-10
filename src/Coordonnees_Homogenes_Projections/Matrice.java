/**
 * Classe Matrice
 *
 * @author DIDOUH Abel
 * @version 24/10/2022
 */
public class Matrice
{
    /**
     * Attributs
     */
    
    private double[][]  aMatrice;
    private int         aLigne;
    private int         aColonne;
    
    /**
     * Constructeur
     */
    public Matrice(final int pLigne, final int pColonne)
    {
        this.aMatrice  = new double[pLigne][pColonne];
        for(int m = 0; m < pLigne; m++)
        {
            for(int n = 0; n < pColonne; n++)
            {
                if(m == n)
                {
                    this.aMatrice[m][n] = 1.0;
                }
                else
                {
                   this.aMatrice[m][n] = 0.0; 
                }
            }
        }
    }//Matrice()
    /**
     * Accesseur
     */
    public double getCoefficient(final int pLigne, final int pColonne)
    {
        return this.aMatrice[pLigne][pColonne];
    }
    public int getNbColonnes()
    {
        return this.aMatrice[0].length;
    }
    public int getNbLignes()
    {
        return this.aMatrice.length;
    }
    /**
     * Modificateur
     * 
     */
    public void setCoefficient(final int pLigne, final int pColonne, final double pCoord)
    {
        this.aMatrice[pLigne][pColonne] = pCoord;
    }
    /**
     * Methode getVecteurLigne
     */
    public Vecteur getVecteurLigne(final int pLigne)
    {
        Vecteur vecteur = new Vecteur(this.getNbColonnes());
        for(int i = 0; i < this.getNbColonnes(); i++)
        {
            vecteur.setCoordonnee(i, this.aMatrice[pLigne][i]);
        }
        return vecteur;
    }//getVecteurLigne
    /**
     * Multiplication Vectorielle
     */
    public Vecteur multiplicationVectorielle(final Vecteur pVecteur)
    {
        Vecteur resVecteur = new Vecteur(this.getNbLignes());
        
        for(int i = 0; i < this.getNbLignes(); i++)
        {
            resVecteur.setCoordonnee(i, pVecteur.produitScalaire(this.getVecteurLigne(i)));
        }
        return resVecteur;
    }
    /**
     * Procedure identite
     */
    public void setIdentite()
    {
        for(int i = 0; i < getNbLignes(); i++)
        {
            for(int j = 0; j < getNbColonnes(); j++)
            {
                if(i == j)
                {
                    this.setCoefficient(i, j, 1.0);
                }
                else
                {
                    this.setCoefficient(i, j, 0.0);
                }
            }
        }
    }//setIdentite
    /**
     * Procedure homothetie
     */
    public void setHomothetie(final double k)
    {
        if(getNbLignes() == getNbColonnes())
        {
            for(int i = 0; i < getNbLignes(); i++)
            {
                for(int j = 0; j < getNbColonnes(); j++)
                {
                    if(i == j)
                    {
                        this.setCoefficient(i,j, k);
                    }
                    else
                    {
                        this.setCoefficient(i, j, 0.0);
                    }
                }
            }
        }
    }//setHomothetie
    /**
     * Procedure symétrie centrale
     */
    public void setSymetrieCentrale()
    {
        this.setIdentite();
        if(getNbLignes() == getNbColonnes())
        {
            for(int i = 0; i < getNbLignes(); i++)
            {
                for(int j = 0; j < getNbColonnes(); j++)
                {
                    this.setCoefficient(i,j,-this.getCoefficient(i,j));                    
                }
            }
        }
    }//setSymetrieCentrale 
    /**
     * Procedure Réflexion 2D 
     */
    public void setReflexionOx()
    {
        this.setIdentite();
        this.setCoefficient(1,1,-1.0);
        
        // Vecteur vVecteurAxe = new Vecteur(2);
        // vVecteurAxe.setCoordonnee(0, 1);
        // vVecteurAxe.setCoordonnee(1, 0);
        
        // if(getNbLignes() == 2 || getNbColonnes() == 2)
        // {
            // this.multiplicationVectorielle(vVecteurAxe);
        // }
    }//setReflexion
    /**
     * Procedure Reflexion 3D
     */
    public void setReflexionOxOy()
    {
        this.setIdentite();
        this.setCoefficient(2, 2, -1.0);
        
        Vecteur vVecteurAxe1 = new Vecteur(3);
        vVecteurAxe1.setCoordonnee(0, 1);
        vVecteurAxe1.setCoordonnee(1, 0);
        vVecteurAxe1.setCoordonnee(2, 0);
        
        Vecteur vVecteurAxe2 = new Vecteur(3);
        vVecteurAxe2.setCoordonnee(0, 0);
        vVecteurAxe2.setCoordonnee(1, 1);
        vVecteurAxe2.setCoordonnee(2, 0);
        
        this.multiplicationVectorielle(vVecteurAxe1);
        this.multiplicationVectorielle(vVecteurAxe2);
        
    }
    /**
     * Procedure Rotation 2D
     */
    public void setRotation2d(final double alpha)
    {
        this.setIdentite();
        double vCosR = Math.cos(alpha);
        double vSinR = Math.sin(alpha);
             
        this.setCoefficient(0, 0, vCosR);
        this.setCoefficient(0, 1, -1*vSinR);
        this.setCoefficient(1, 0, vSinR);
        this.setCoefficient(1, 1, vCosR);
    }//setRotation2d
    /**
     * Procedure Rotation 3D
     */
    public void setRotation3dOx(final double alpha)
    {
        this.setIdentite();
        
        double vCosR = Math.cos(alpha);
        double vSinR = Math.sin(alpha);
        
        this.setCoefficient(1, 1, vCosR);
        this.setCoefficient(1, 2, -1*vSinR);
        this.setCoefficient(2, 1, vSinR);
        this.setCoefficient(2, 2, vCosR);
    }//setRotation3dOx
        
    /**
     * Methode publique getVecteurColonne
     */
    public Vecteur getVecteurColonne(final int pColonne)
    {
        Vecteur vVecteurCoord = new Vecteur(this.getNbLignes());
        for(int i = 0; i < this.getNbLignes(); i++)
        {
            vVecteurCoord.setCoordonnee(i, this.getCoefficient(i, pColonne));
        }
        return vVecteurCoord;
    }//getVecteurColonne
    /**
     * Une fonction publique produitMatriciel à un paramètre de type Matrice
     */
    public Matrice produitMatriciel(final Matrice pMatrice)
    {
        if(this.getNbColonnes() == pMatrice.getNbLignes())
        {
            Matrice vMatriceRes = new Matrice(this.getNbLignes(), pMatrice.getNbColonnes());
            
            for(int i = 0; i < this.getNbLignes(); i++)
            {
                for(int j = 0; j < pMatrice.getNbColonnes(); j++)
                {
                    vMatriceRes.setCoefficient(i, j, this.getVecteurLigne(i).produitScalaire(pMatrice.getVecteurColonne(j)));
                }
            }
            return vMatriceRes;
        }
        else
        {
            return null;
        }
    }//produitMatriciel
    /**
     * Procédure publique setHomothetieHomogene3d à un paramètre k de type 
     * double qui modifie l'instance courante de la classe Matrice afin qu'elle 
     * corresponde à l'homothétie de rapport k dans l'espace 3d homogène
     */
    public void setHomothetieHomogene3d(final double pK)
    {
        this.setIdentite();
        
        for(int i = 0; i < this.getNbLignes(); i++)
        {
            for(int j = 0; j < this.getNbColonnes(); j++)
            {
                if(i == j)
                {
                    this.setCoefficient(i, j, pK);
                }
            }
        }
        this.setCoefficient(this.getNbLignes()-1, this.getNbColonnes()-1, 1);
    }//setHomothetieHomogene3d
    /**
     *  Procédure publique setTranslationHomogene3d à un paramètre t de 
     *  type Vecteur qui modifie l'instance courante de la classe Matrice 
     *  afin qu'elle corresponde à la translation de vecteur t dans 
     *  l'espace 3d homogène
     */
    public void setTranslationHomogene3d(final Vecteur pT)
    {
        this.setIdentite();
        
        for(int i = 0; i < this.getNbLignes()-1; i++)
        {
            this.setCoefficient(i, this.getNbColonnes()-1, pT.getCoordonnee(i));
        }
    }//setTranslationHomogene3d
    /**
     *  Procédure publique setRotationOxHomogene3d à un paramètre alpha de type 
     *  double qui modifie l'instance courante de la classe Matrice afin qu'elle 
     *  corresponde à la rotation d'angle alpha radian autour du vecteur 
     *  ( 1, 0, 0 )dans l'espace 3d homogène
     */
    public void setRotationOxHomogene3d(final double pAlpha)
    {
        this.setIdentite();
        
        double vCosR = Math.cos(pAlpha);
        double vSinR = Math.sin(pAlpha);
        
        this.setCoefficient(1, 1, vCosR);
        this.setCoefficient(2, 2, vCosR);
        
        this.setCoefficient(1, 2, -vSinR);
        this.setCoefficient(2, 1, vSinR);
    }//setRotationOxHomogene3d
    /**
     * Procédure publique setRotationOyHomogene3d à un paramètre alpha de 
     * type double qui modifie l'instance courante de la classe Matrice 
     * afin qu'elle corresponde à la rotation d'angle alpha radian autour 
     * du vecteur ( 0, 1, 0 )dans l'espace 3d homogène
     */
    public void setRotationOyHomogene3d(final double pAlpha)
    {
        this.setIdentite();
        
        double vCosR = Math.cos(pAlpha);
        double vSinR = Math.sin(pAlpha);
        
        this.setCoefficient(0, 0, vCosR);
        this.setCoefficient(2, 2, vCosR);
        
        this.setCoefficient(0, 2, vSinR);
        this.setCoefficient(2, 0, -vSinR);
    }//setRotationOyHomogene3d
    /**
     * Procédure publique setRotationOzHomogene3d à un paramètre alpha de type 
     * double qui modifie l'instance courante de la classe Matrice afin 
     * qu'elle corresponde à la rotation d'angle alpha radian autour 
     * du vecteur (0,0,1)dans l'espace 3d homogène 
     */
    public void setRotationOzHomogene3d(final double pAlpha)
    {
        this.setIdentite();
        
        double vCosR = Math.cos(pAlpha);
        double vSinR = Math.sin(pAlpha);
        
        this.setCoefficient(0, 0, vCosR);
        this.setCoefficient(1, 1, vCosR);
        
        this.setCoefficient(0, 1, -vSinR);
        this.setCoefficient(1, 0, vSinR);
    }//setRotationOzHomogene3d
    /**
     * Procédure publique setProjectionOrthoOxyHomogene3d 
     * sans paramètre qui modifie l'instance courante de la classe Matrice 
     * afin qu'elle corresponde à la projection orthogonale de l'espace 3d 
     * homogène dans l'espace 2d homogène correspondant au plan défini par 
     * les vecteurs ( 1, 0, 0 ) et ( 0, 1, 0 )
     */
    public void setProjectionOrthoOxyHomogene3d()
    {
        for(int i = 0; i < getNbLignes(); i++)
        {
            for(int j = 0; j < getNbColonnes(); j++)
            {
                this.setCoefficient(i, j, 0);
            }
        }
        this.setCoefficient(0,0, 1);
        this.setCoefficient(1,1, 1);
        this.setCoefficient(2,3, 1);
    }
    /**
     * Procédure publique setProjectionOrthoOxzHomogene3d
     */
    public void setProjectionOrthoOxzHomogene3d()
    {
        for(int i = 0; i < getNbLignes(); i++)
        {
            for(int j = 0; j < getNbColonnes(); j++)
            {
                this.setCoefficient(i, j, 0);
            }
        }
        this.setCoefficient(0,0, 1);
        this.setCoefficient(1,2, 1);
        this.setCoefficient(2,3, 1);
    }//setProjectionOrthoOxzHomogene3d()
    /**
     * Procédure publique setProjectionOrthoOyzHomogene3d
     */
    public void setProjectionOrthoOyzHomogene3d()
    {
        for(int i = 0; i < getNbLignes(); i++)
        {
            for(int j = 0; j < getNbColonnes(); j++)
            {
                this.setCoefficient(i, j, 0);
            }
        }
        this.setCoefficient(0,1, 1);
        this.setCoefficient(1,2, 1);
        this.setCoefficient(2,3, 1);
    }//setProjectionOrthoOyzHomogene3d()
    /**
     * Procédure publique setProjectionPerspectiveOxyHomogene3d
     */
    public void setProjectionPerspectiveOxyHomogene3d(final double pScalaire)
    {
        for(int i = 0; i < getNbLignes(); i++)
        {
            for(int j = 0; j < getNbColonnes(); j++)
            {
                this.setCoefficient(i, j, 0);
            }
        }
        this.setCoefficient(0,0, 1);
        this.setCoefficient(1,1, 1);
        this.setCoefficient(2,2, 1/pScalaire);
    }//setProjectionPerspectiveOxyHomogene3d
}//Matrice
