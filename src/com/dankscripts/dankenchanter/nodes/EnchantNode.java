package com.dankscripts.dankenchanter.nodes;

import com.dankscripts.api.epicbot.Dialogue;
import com.dankscripts.api.framework.Node;
import com.dankscripts.api.framework.Priority;
import com.dankscripts.dankenchanter.DankEnchanter;
import com.epicbot.api.shared.entity.Item;
import com.epicbot.api.shared.entity.ItemWidget;
import com.epicbot.api.shared.methods.ITabsAPI;
import com.epicbot.api.shared.model.Spell;
import com.epicbot.api.shared.util.time.Time;

public class EnchantNode extends Node {

    private DankEnchanter script;

    public EnchantNode(DankEnchanter script) {
        this.script = script;
    }

    @Override
    public boolean validate() {
        return script.getAPIContext().inventory().contains(script.getUserSettings().getItemToEnchant());
    }

    @Override
    public void execute() {
        if(script.getAPIContext().bank().isOpen()) {
            script.getAPIContext().keyboard().sendKey(27);
            Time.sleep(5000, () -> !script.getAPIContext().bank().isOpen());
        } else {
            if (script.getAPIContext().magic().isSpellSelected() && script.getAPIContext().inventory().deselectItem()) {
                Time.sleep(4000, () -> !script.getAPIContext().magic().isSpellSelected());
            } else {
                if (script.getAPIContext().inventory().contains("Cosmic rune")) {
                    if (script.getAPIContext().tabs().isOpen(ITabsAPI.Tabs.MAGIC)) {
                        if (script.getAPIContext().magic().cast(script.getUserSettings().getEnchantSpell()) && Time.sleep(3000, () -> script.getAPIContext().tabs().isOpen(ITabsAPI.Tabs.INVENTORY))) {
                            ItemWidget item = script.getAPIContext().inventory().getItem(script.getUserSettings().getItemToEnchant());
                            if (item != null && item.interact("Cast")) {
                                script.itemsEnchanted++;
                                Time.sleep(5000, () -> script.getAPIContext().tabs().isOpen(ITabsAPI.Tabs.MAGIC) || Dialogue.canContinue(script.getAPIContext()));
                            }
                        }
                    } else {
                        if (script.getAPIContext().tabs().open(ITabsAPI.Tabs.MAGIC)) {
                            Time.sleep(5000, () -> script.getAPIContext().tabs().isOpen(ITabsAPI.Tabs.MAGIC));
                        }
                    }
                } else {
                    script.stop("Ran out of cosmic runes");
                }
            }
        }
    }

    @Override
    public Priority priority() {
        return Priority.MINIMUM;
    }

    @Override
    public String getName() {
        return "Enchanting";
    }
}
