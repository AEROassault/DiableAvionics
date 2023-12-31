package org.diableAvionics.shipsystems;

import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.impl.combat.BaseShipSystemScript;
import static org.diableAvionics.util.StringManager.txt;

public class BerserkStats extends BaseShipSystemScript {


    @Override
    public void apply(MutableShipStatsAPI stats, String id, State state, float effectLevel) {

        stats.getMaxSpeed().modifyMult(id, 1-(effectLevel*0.33f));
        stats.getMaxTurnRate().modifyMult(id, 1+effectLevel);
        stats.getArmorDamageTakenMult().modifyMult(id, 1-(effectLevel*0.33f));

        stats.getWeaponTurnRateBonus().modifyMult(id, 1+effectLevel);
        stats.getAutofireAimAccuracy().modifyMult(id, 1+effectLevel);
        stats.getMaxRecoilMult().modifyMult(id, 1-(effectLevel*0.33f));
        
        stats.getEnergyWeaponRangeBonus().modifyMult(id, 1+(effectLevel/2));        
        stats.getEnergyRoFMult().modifyMult(id, 1+(effectLevel*2));
        stats.getProjectileSpeedMult().modifyMult(id, 1+effectLevel);
        
        
        if(effectLevel<1){
            stats.getEnergyWeaponFluxCostMod().modifyFlat(id, 1000);
        } else {
            stats.getEnergyWeaponFluxCostMod().unmodify(id);
        }
    }

    @Override
    public void unapply(MutableShipStatsAPI stats, String id) {
        stats.getMaxSpeed().unmodify(id);
        stats.getMaxTurnRate().unmodify(id);
        stats.getArmorDamageTakenMult().unmodify(id);

        stats.getWeaponTurnRateBonus().unmodify(id);
        stats.getAutofireAimAccuracy().unmodify(id);
        stats.getMaxRecoilMult().unmodify(id);
        
        stats.getEnergyWeaponRangeBonus().unmodify(id);
        stats.getEnergyRoFMult().unmodify(id);
        stats.getProjectileSpeedMult().unmodify(id);
        
        stats.getEnergyWeaponFluxCostMod().unmodify(id);
    }
    

    private final String TXT1 = txt("berserker");
    private final String TXT2 = txt("%");
    @Override
    public StatusData getStatusData(int index, State state, float effectLevel) {
        float bonusPercent = (int) (effectLevel * 100f);
        if (index == 0) {
            return new StatusData(TXT1 + (int) bonusPercent + TXT2, false);
        }
        return null;
    }
}
