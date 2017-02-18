import client.InfluenceCell;
import client.InfluenceClient;
import client.InfluenceField;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by nicolas on 18/02/17.
 */
public class ProgramRapace {
    public static void main(String[] args)
    {
        Random r = new Random();
        InfluenceClient client = InfluenceClient.getInstance();

        client.connect("127.0.0.1", "[HTAG]RapaSS 5008");

        while (client.getStatus() == InfluenceClient.Status.ONGOING)
        {
            InfluenceField field = client.nextRound();
            if (client.getStatus() != InfluenceClient.Status.ONGOING)
            {
                break;
            }


            // Edit from here
            ArrayList<InfluenceCell> myCells;
            client.printLog("Attacking");
            myCells = client.getMyCells();

            int attLeft = 20;
            boolean ok = true;
            ArrayList<InfluenceCell> cellsToUpgrade = new ArrayList<>();

            if(myCells.size()==0)
                cellsToUpgrade.add(myCells.get(0));

            while (attLeft > 0) {
                ok = true;
                myCells = client.getMyCells();
                for (InfluenceCell c : myCells) {
                    if(c.getUnitsCount() >= 2) {
                        for (int i = -1; i <= 1 && ok; i++) {
                            for (int j = -1; j <= 1 && ok; j++) {
                                if (i != j) {
                                    int dx = c.getX() + i;
                                    int dy = c.getY() + j;
                                    if (dx >= 0 && dx < field.getWidth() && dy >= 0 && dy < field.getHeight()) {
                                        InfluenceCell cellToAttack = field.getCell(dx, dy);
                                        if (cellToAttack != null && cellToAttack.getOwner() == 0) {
                                            field = client.attack(c.getX(), c.getY(), cellToAttack.getX(), cellToAttack.getY());
                                            ok = false;
                                            attLeft--;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                if (ok) {
                    for (InfluenceCell c : myCells) {
                        if(c.getUnitsCount() >= 2) {
                            for (int i = -1; i <= 1 && ok; i++) {
                                for (int j = -1; j <= 1 && ok; j++) {
                                    if (i != j) {
                                        int dx = c.getX() + i;
                                        int dy = c.getY() + j;
                                        if (dx >= 0 && dx < field.getWidth() && dy >= 0 && dy < field.getHeight()) {
                                            InfluenceCell cellToAttack = field.getCell(dx, dy);
                                            if (cellToAttack != null && cellToAttack.getOwner() != client.getNumber()) {
                                                if (cellToAttack.getUnitsCount() < 2 + c.getUnitsCount()) {
                                                    field = client.attack(c.getX(), c.getY(), cellToAttack.getX(), cellToAttack.getY());
                                                    ok = false;
                                                    attLeft--;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                if(ok){
                    attLeft = 0;
                }
            }

            //PAS TOUCHE
            int unitsToAdd = client.endAttacks();
            //PAS TOUCHE

            ok = true;
            myCells = client.getMyCells();
            for(InfluenceCell c : myCells){
                for (int i = -1; i <= 1 && ok; i++) {
                    for (int j = -1; j <= 1 && ok; j++) {
                        if (i != j) {
                            int dx = c.getX() + i;
                            int dy = c.getY() + j;
                            if (dx >= 0 && dx < field.getWidth() && dy >= 0 && dy < field.getHeight()) {
                                InfluenceCell cellToAttack = field.getCell(dx, dy);
                                if (cellToAttack != null && cellToAttack.getOwner() != client.getNumber()) {
                                    ok = false;
                                    cellsToUpgrade.add(c);
                                }
                            }
                        }
                    }
                }
            }

            if(myCells.size() == 1){
                InfluenceCell c = myCells.get(0);
                client.addUnits(c, 1);
            } else {
                for (int i = 0; i < unitsToAdd; i++) {
                    InfluenceCell c = cellsToUpgrade.get(i % cellsToUpgrade.size());
                    client.addUnits(c, 1);
                }
            }
            /*myCells = client.getMyCells();
            for (int i = unitsToAdd-1; i >= unitsToAdd/2; i--)
            {
                InfluenceCell c = myCells.get(i);
                client.addUnits(c, 1);
            }*/


            //PAS TOUCHE
            client.endAddingUnits();
            //PAS TOUCHE

            //end here
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
