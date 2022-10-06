package com.dankscripts.dankenchanter.nodes;

import com.dankscripts.api.framework.Node;
import com.dankscripts.api.framework.Priority;
import com.dankscripts.dankenchanter.DankEnchanter;
import com.epicbot.api.shared.entity.SceneObject;
import com.epicbot.api.shared.util.time.Time;

public class BankNode extends Node {

    private DankEnchanter script;

    public BankNode(DankEnchanter script) {
        this.script = script;
    }

    @Override
    public boolean validate() {
        return !script.getAPIContext().inventory().contains(script.getUserSettings().getItemToEnchant());
    }

    @Override
    public void execute() {
        if(script.getAPIContext().inventory().isItemSelected() && script.getAPIContext().inventory().deselectItem()) {
            Time.sleep(4000, () -> !script.getAPIContext().inventory().isItemSelected());
        }
        if(script.getAPIContext().magic().isSpellSelected() && script.getAPIContext().magic().cast(script.getUserSettings().getEnchantSpell())) {
            Time.sleep(4000, () -> !script.getAPIContext().magic().isSpellSelected());
        }
        if(script.getAPIContext().bank().isOpen()) {
            if(script.getAPIContext().bank().contains(script.getUserSettings().getItemToEnchant())) {
                if(script.getAPIContext().bank().depositAll(item -> !item.getName().equals("Cosmic rune"))) {
                    Time.sleep(4000, () -> script.getAPIContext().inventory().onlyContains("Cosmic rune"));
                }
                if(!script.getAPIContext().inventory().isFull() && script.getAPIContext().bank().withdrawAll(script.getUserSettings().getItemToEnchant())) {
                    Time.sleep(4000, () -> script.getAPIContext().inventory().contains(script.getUserSettings().getItemToEnchant()));
                }
            } else {
                script.stop("Ran out of items to enchant");
            }
        } else {
            if(script.getAPIContext().bank().open()) {
                Time.sleep(4000, script.getAPIContext().bank()::isOpen);
            }
        }
    }

    @Override
    public Priority priority() {
        return Priority.VERY_LOW;
    }

    @Override
    public String getName() {
        return "Banking";
    }
}
