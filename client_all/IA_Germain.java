import client.InfluenceCell;
import client.InfluenceClient;
import client.InfluenceField;

import java.awt.*;
import java.io.Console;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by brice on 08/02/17.
 */
public class IA_Germain
{
    public static void main(String[] args)
    {
        Random r = new Random();
        InfluenceClient client = InfluenceClient.getInstance();

        client.connect("10.3.1.160", "[HT@G] G3rm41n67"); //arno
        //client.connect("10.3.6.227", "[HT@G] G3rm41n67"); //hans

        while (client.getStatus() == InfluenceClient.Status.ONGOING)
        {
            InfluenceField field = client.nextRound();
            if (client.getStatus() != InfluenceClient.Status.ONGOING)
            {
                break;
            }

            ArrayList<InfluenceCell> myCells;
            client.printLog("Attacking");
            for (int i = 0; i < 99999; i++)
            {
                myCells = client.getMyCells();
                InfluenceCell c =  myCells.get(r.nextInt(myCells.size()));
                int val = c.getUnitsCount();
                if (val >= 2)
                {
                    int dx = c.getX() + r.nextInt(3) - 1;
                    int dy = c.getY() + r.nextInt(3) - 1;
                    if (dx >= 0 && dx < field.getWidth() && dy >= 0 && dy < field.getHeight())
                    {
                        InfluenceCell cellToAttack = field.getCell(dx, dy);
                        if (cellToAttack != null && cellToAttack.getOwner() != client.getNumber())
                        {
                            field = client.attack(c.getX(), c.getY(), cellToAttack.getX(), cellToAttack.getY());
                        }
                    }
                }
            }

            int unitsToAdd = client.endAttacks();

            //Boost frontline
            myCells = client.getMyCells();
            for (int i = 0; i < unitsToAdd; i++)
            {
                ArrayList<InfluenceCell> field_lows = field.getLows(myCells);
                InfluenceCell c;
                c = field.getWeakestFrontLine(myCells, client);
                client.addUnits(c, 1);
                c.incrementUnitCount();
                field.setCell(c.getX(), c.getY(), c);
            }
            client.endAddingUnits();
        }

        switch (client.getStatus())
        {
            case VICTORY:
                System.out.println("YOU WON!");
                break;

            case DEFEAT:
                System.out.println("YOU LOST!");
                break;

            case CONNECTION_LOST:
                System.out.println("YOU LOST BECAUSE OF YOUR CONNECTION");
                break;

            default:
                System.out.println("NOT REACHABLE");
                break;
        }
    }
}

