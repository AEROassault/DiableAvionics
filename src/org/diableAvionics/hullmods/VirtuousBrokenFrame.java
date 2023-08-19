package org.diableAvionics.hullmods;

import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;
import static org.diableAvionics.util.StringManager.txt;

public class VirtuousBrokenFrame extends BaseHullMod {
    
    @Override
    public int getDisplaySortOrder() {
        return 0;
    }
    
    @Override
    public void applyEffectsBeforeShipCreation(HullSize hullSize, MutableShipStatsAPI stats, String id) {
        stats.getMaxCombatReadiness().modifyMult(id, 0);
    }
    
    @Override
    public String getDescriptionParam(int index, HullSize hullSize) {
        if (index == 0) return txt("hm_warning"); 
        return null;
    }
}
