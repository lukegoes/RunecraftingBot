package models.entities;

import models.enums.BanksAreas;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.container.impl.bank.Bank;
import org.dreambot.api.methods.container.impl.equipment.Equipment;
import org.dreambot.api.methods.container.impl.equipment.EquipmentSlot;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.script.ScriptManager;
import org.dreambot.api.utilities.Sleep;

import static org.dreambot.api.utilities.Logger.log;

public class Preparing extends Tasks{

    @Override
    public boolean accept() {

        return BanksAreas.FALADOR.getArea().contains(Players.getLocal())
                && !Inventory.contains("Rune essence")
                || !Equipment.contains("Air tiara");
    }

    @Override
    public int execute() {
        log("Script -> Getting items.");
        if (!Bank.isOpen()) {
            Bank.open();
            Sleep.sleepUntil(Bank::isOpen, 5000);
            return 600;
        }

        if (!Inventory.isEmpty()) {
            Bank.depositAllItems();
            Sleep.sleepUntil(Inventory::isEmpty, 3000);
        }

        if (!Equipment.contains("Air tiara")) {
            if (Bank.contains("Air tiara")) {
                log("Action -> Withdrawing Tiara.");
                Bank.withdraw("Air tiara", 1);
                Sleep.sleepUntil(() -> Inventory.contains("Air tiara"), 3000);

                if (Equipment.equip(EquipmentSlot.HAT, "Air tiara")) {
                    log("Action -> Equip Tiara.");
                    Sleep.sleepUntil(() -> !Inventory.contains("Air tiara"), 3000);
                }
            } else {
                log("Sem Air tiara no corpo e sem estoque no banco!");
                ScriptManager.getScriptManager().stop();
                return -1;
            }
        }

            if (Bank.contains("Rune essence")) {
                Bank.withdrawAll("Rune essence");
                log("Action -> Getting Rune essence.");
                Sleep.sleepUntil(() -> Inventory.contains("Rune essence"), 2500);
            } else {
                log("Erro crítico: Rune essence não encontrado no banco!");
                ScriptManager.getScriptManager().stop();
                return -1;
            }

        Bank.close();
        return Utils.getGaussian(451, 1194);
    }
}
