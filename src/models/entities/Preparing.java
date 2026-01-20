package models.entities;

import models.enums.BanksAreas;
import models.enums.RuinsAreas;
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

        return BanksAreas.ALKHARID.getArea().contains(Players.getLocal())
                && !Inventory.contains("Rune essence")
                || !Equipment.contains(RuinsAreas.FIRE.getEquipment());
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

        if (!Equipment.contains(RuinsAreas.FIRE.getEquipment())) {
            if (Bank.contains(RuinsAreas.FIRE.getEquipment())) {
                log("Action -> Withdrawing Tiara.");
                Bank.withdraw(RuinsAreas.FIRE.getEquipment(), 1);
                Sleep.sleepUntil(() -> Inventory.contains(RuinsAreas.FIRE.getEquipment()), 3000);

                if (Equipment.equip(EquipmentSlot.HAT, RuinsAreas.FIRE.getEquipment())) {
                    log("Action -> Equip Tiara.");
                    Sleep.sleepUntil(() -> !Inventory.contains(RuinsAreas.FIRE.getEquipment()), 3000);
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
