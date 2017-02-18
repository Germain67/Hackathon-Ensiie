package client;

import java.util.ArrayList;

/**
 * Classe permettant de représenter le plateau de jeu
 */
public class InfluenceField
{
    /**
     * Largeur du plateau (nombre de colonnes)
     */
    private int width;

    /**
     * Hauteur du plateau (nombre de lignes)
     */
    private int height;

    /**
     * Liste des cellules constituant le plateau
     */
    private ArrayList<InfluenceCell> cells;

    /**
     * Constructeur du plateau de jeu
     * @param width Largeur du plateau (nombre de colonnes)
     * @param height Hauteur du plateau (nombre de lignes)
     */
    public InfluenceField(int width, int height)
    {
        this.width = width;
        this.height = height;
        this.cells = new ArrayList<>(width * height);
    }

    /**
     * Permet d'accéder à la largeur du plateau
     * @return La largeur du plateau
     */
    public int getWidth()
    {
        return width;
    }

    /**
     * Permet d'accéder à la hauteur du plateau
     * @return La hauteur du plateau
     */
    public int getHeight()
    {
        return height;
    }

    /**
     * Permet d'accéder aux cellules du plateau
     * @return La liste des cellules du plateau
     */
    public ArrayList<InfluenceCell> getCells()
    {
        return cells;
    }

    /**
     * Permet de récupérer une cellule à partir de ses coordonnées (commencent en haut à gauche)
     * @param x Colonne de la cellule (commence à 0)
     * @param y Ligne de la cellule (commence à 0)
     * @return La cellule située à la yième ligne et xième colonne sur le plateau de jeu ou null si elle n'existe pas
     */
    public InfluenceCell getCell(int x, int y)
    {
        InfluenceCell cell = null;
        if (x >= 0 && x < this.width && y >= 0 && y < this.height)
        {
            cell = this.cells.get(y * width + x);
        }
        return cell;
    }

    public void setCell(int x, int y, InfluenceCell c)
    {
        this.cells.set(y * width + x, c);
    }

    public ArrayList<InfluenceCell> getFrontLine(ArrayList<InfluenceCell> myCells){
        ArrayList<InfluenceCell> frontLine = myCells;
        return frontLine;
    }

    public InfluenceCell getWeakestFrontLine(ArrayList<InfluenceCell> myCells, InfluenceField field){
        ArrayList<InfluenceCell> frontline = getFrontLine(myCells);
        InfluenceCell weakeast = field.getCell(frontline.get(0).getX(), frontline.get(0).getY());
        int min = weakeast.getUnitsCount();
        for(int i = 1; i < frontline.size(); i++)
        {
            InfluenceCell c = field.getCell(frontline.get(i).getX(), frontline.get(i).getY());
            if(c.getUnitsCount() < min)
            {
                weakeast = c;
                min = weakeast.getUnitsCount();
            }
        }
        return weakeast;
    }
}
