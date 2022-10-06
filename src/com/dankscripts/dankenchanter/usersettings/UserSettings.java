package com.dankscripts.dankenchanter.usersettings;

import com.epicbot.api.shared.model.Spell;

public class UserSettings {

    private String itemToEnchant;
    private Spell enchantSpell;

    public String getItemToEnchant() {
        return itemToEnchant;
    }

    public void setItemToEnchant(String itemToEnchant) {
        this.itemToEnchant = itemToEnchant;
    }

    public Spell getEnchantSpell() {
        return enchantSpell;
    }

    public void setEnchantSpell(Spell enchantSpell) {
        this.enchantSpell = enchantSpell;
    }
}
