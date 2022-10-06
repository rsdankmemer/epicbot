package com.dankscripts.dankenchanter;

import com.dankscripts.api.framework.Controller;
import com.dankscripts.api.framework.Node;
import com.dankscripts.api.utils.Calculations;
import com.dankscripts.api.utils.Timer;
import com.dankscripts.dankenchanter.nodes.BankNode;
import com.dankscripts.dankenchanter.nodes.EnchantNode;
import com.dankscripts.dankenchanter.usersettings.UserSettings;
import com.epicbot.api.shared.APIContext;
import com.epicbot.api.shared.GameType;
import com.epicbot.api.shared.model.Spell;
import com.epicbot.api.shared.script.LoopScript;
import com.epicbot.api.shared.script.ScriptManifest;
import com.epicbot.api.shared.util.paint.frame.PaintFrame;

import java.awt.*;

@ScriptManifest(gameType = GameType.OS, name = "Dank Enchanter")
public class DankEnchanter extends LoopScript{

    private Controller controller;
    private UserSettings userSettings;
    private PaintFrame paintFrame;
    private long startTime;
    private int startExp, startLevel;

    public int itemsEnchanted = 0;

    @Override
    protected int loop() {
        Node n = controller.getCurrentNode();
        if(n != null)
            n.execute();
        return 50;
    }

    @Override
    protected void onPaint(Graphics2D a, APIContext a2) {
        long timeElapsed = System.currentTimeMillis() - startTime;
        paintFrame = new PaintFrame("Dank Enchanter");
        paintFrame.addLine("Time Running: ", Timer.formatTime(timeElapsed));
        paintFrame.addLine("Items Enchanted: ", itemsEnchanted + " (" + Calculations.getHourly(itemsEnchanted, timeElapsed) + ")");
        paintFrame.addLine("Exp Gained: ", a2.skills().magic().getExperience() - startExp);
        paintFrame.addLine("Level: ", a2.skills().magic().getRealLevel());
        paintFrame.addLine("Status: ", currentNode());
        paintFrame.draw(a, 10, 40, a2);
    }

    @Override
    public boolean onStart(String... strings) {
        userSettings = new UserSettings();
        userSettings.setEnchantSpell(Spell.Modern.LEVEL_1_ENCHANT);
        userSettings.setItemToEnchant("Sapphire ring");
        controller = new Controller(new BankNode(this), new EnchantNode(this));
        startTime = System.currentTimeMillis();
        startExp = getAPIContext().skills().magic().getExperience();
        startLevel = getAPIContext().skills().magic().getRealLevel();
        return true;
    }

    public UserSettings getUserSettings() {
        return userSettings;
    }

    private String currentNode() {
        if(controller.getCurrentNode() != null) {
            return controller.getCurrentNode().getName();
        }
        return "Waiting...";
    }
}
