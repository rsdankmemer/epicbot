package com.dankscripts.dankwoodcutter.nodes;


import com.epicbot.api.shared.util.time.Time;
import com.epicbot.api.shared.webwalking.model.RSBank;
import com.dankscripts.api.epicbot.DankBank;
import com.dankscripts.api.framework.Node;
import com.dankscripts.api.framework.Priority;
import com.dankscripts.dankwoodcutter.DankWoodcutter;

public class BankNode extends Node {

    private DankWoodcutter script;

    public BankNode(DankWoodcutter script) {
        this.script = script;
    }

    @Override
    public boolean validate() {
        return script.getAPIContext().inventory().isFull();
    }

    @Override
    public void execute() {
        RSBank bankLocation = DankBank.findNearestBank(script.getAPIContext());
        if (bankLocation.getTile().distanceTo(script.getAPIContext(), script.getAPIContext().localPlayer().getLocation()) > 7) {
            script.getAPIContext().webWalking().walkTo(bankLocation.getTile());
        } else {
            if (!script.getAPIContext().bank().isOpen()) {
                if (script.getAPIContext().bank().open()) {
                    Time.sleep(5000, () -> script.getAPIContext().bank().isOpen());
                }
            } else {
                if(script.getAPIContext().bank().depositAll(item -> item.getName().toLowerCase().contains("logs"))) {
                    Time.sleep(4000, 5000, () -> !script.getAPIContext().inventory().isFull());
                }
            }
        }
    }

    @Override
    public Priority priority() {
        return Priority.VERY_LOW;
    }

    @Override
    public String getName() {
        return "Banking...";
    }


}
