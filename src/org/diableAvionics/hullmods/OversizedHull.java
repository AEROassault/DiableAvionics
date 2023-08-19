package org.diableAvionics.hullmods;

import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;
import static org.diableAvionics.util.StringManager.txt;

public class OversizedHull extends BaseHullMod {
    
    private final int MALUS = 50;
    
    @Override
    public String getDescriptionParam(int index, HullSize hullSize) {
        if (index == 0) {
            return MALUS+txt("%");
        }
        return null;
    }
    
    @Override
    public void applyEffectsBeforeShipCreation(HullSize hullSize, MutableShipStatsAPI stats, String id) {
        stats.getSuppliesToRecover().modifyPercent(id, MALUS);
    }
}