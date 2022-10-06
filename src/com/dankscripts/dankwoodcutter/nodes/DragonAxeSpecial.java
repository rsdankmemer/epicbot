package com.dankscripts.dankwoodcutter.nodes;

import com.dankscripts.api.framework.Node;
import com.dankscripts.api.framework.Priority;
import com.dankscripts.dankwoodcutter.DankWoodcutter;
import com.epicbot.api.shared.util.time.Time;

public class DragonAxeSpecial extends Node {

    private DankWoodcutter script;

    public DragonAxeSpecial(DankWoodcutter script) {
        this.script = script;
    }

    @Override
    public boolean validate() {
        return script.getAPIContext().combat().getSpecialAttackEnergy() == 100;
    }

    @Override
    public void execute() {
        Time.sleep(500, 10000);
        if(script.getAPIContext().combat().toggleSpecialAttack(true)) {
            script.getAPIContext().mouse().moveOffScreen();
            Time.sleep(500, 1000, () -> script.getAPIContext().combat().getSpecialAttackEnergy() != 100);
        }
    }

    @Override
    public Priority priority() {
        return Priority.MEDIUM;
    }

    @Override
    public String getName() {
        return "Activating Special...";
    }

}
