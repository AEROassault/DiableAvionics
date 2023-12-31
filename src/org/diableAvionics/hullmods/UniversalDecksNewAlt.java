package org.diableAvionics.hullmods;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;
import com.fs.starfarer.api.combat.ShipVariantAPI;
//import com.fs.starfarer.api.impl.campaign.ids.Stats;
import com.fs.starfarer.api.ui.Alignment;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import org.diableAvionics.plugins.ModPlugin;

import java.awt.Color;
import static org.diableAvionics.util.StringManager.txt;

public class UniversalDecksNewAlt extends BaseHullMod {
    
    @Override
    public String getDescriptionParam(int index, HullSize hullSize) {
        if (index == 0) {
            return (int) (ModPlugin.GANTRY_TIME_MULT*100)+txt("%");
        }    
        return null;
    }
    
    private final Color HL=Global.getSettings().getColor("hColor");
    
    @Override
    public void addPostDescriptionSection(TooltipMakerAPI tooltip, ShipAPI.HullSize hullSize, ShipAPI ship, float width, boolean isForModSpec) {
        //title
        tooltip.addSectionHeading(txt("hm_gantry_0"), Alignment.MID, 15);        
        
        if(ship!=null && ship.getVariant()!=null){
            if( ship.getVariant().getNonBuiltInWings().isEmpty()){
                //no wing fitted
                tooltip.addPara(
                        txt("hm_gantry_1")
                        ,10
                        ,HL
                );
            } else if(!allWanzers(ship.getVariant())){
                //non wanzer wings installed
                tooltip.addPara(
                        txt("hm_gantry_2")
                        ,10
                        ,HL
                );
            } else {
                //effect applied
                String wings = String.valueOf((int)ship.getVariant().getNonBuiltInWings().size());
                tooltip.addPara(
                        txt("hm_gantry_9")
                        + wings
                        + txt("hm_gantry_5")
                        ,10
                        ,HL
                        ,wings
                );

                //list new wanzer replacement rates
                tooltip.addPara(
                        txt("hm_gantry_6")
                        ,10
                        ,HL
                );

                tooltip.setBulletedListMode("    - ");  

                for(String w : ship.getVariant().getNonBuiltInWings()){
                    String wingName = Global.getSettings().getFighterWingSpec(w).getWingName();
                    int newTime = (int)Global.getSettings().getFighterWingSpec(w).getRefitTime()/2;

                    tooltip.addPara(
                            wingName
                            + txt("hm_gantry_7")
                            + newTime
                            + txt("hm_gantry_8")
                            ,3
                            ,HL
                            ,""+newTime
                    );
                }
                tooltip.setBulletedListMode(null);
            }
        }
        
    }
    
    @Override
    public void applyEffectsBeforeShipCreation(HullSize hullSize, MutableShipStatsAPI stats, String id) {
        boolean all_wanzers=allWanzers(stats.getVariant());
        
        if(all_wanzers){
            //faster repairs
            stats.getFighterRefitTimeMult().modifyMult("wanzer_gantry", ModPlugin.GANTRY_TIME_MULT, "Wanzer Gantry bonus");
            //faster depletion of replacement rate
//            float depletion = ModPlugin.GANTRY_DEPLETION_PERCENT*stats.getVariant().getNonBuiltInWings().size();
//            stats.getDynamic().getStat(Stats.REPLACEMENT_RATE_DECREASE_MULT).modifyPercent(id, depletion);
            //debug
//            ship.getMutableStats().getFighterRefitTimeMult().modifyMult("wanzer_gantry", 0.01f, "Wanzer Gantry bonus");
        }
    }
    
    @Override
    public boolean isApplicableToShip(ShipAPI ship) {
        if(ship==null) return false;
        int bays = (int) ship.getMutableStats().getNumFighterBays().getBaseValue();
        return bays > 0; 
    }

    @Override
    public String getUnapplicableReason(ShipAPI ship) {
        return txt("hm_noBays");
    }

    private boolean allWanzers(ShipVariantAPI v){
        boolean all_wanzers=true;
        for(String w : v.getWings()){
            if (!ModPlugin.WANZERS.contains(w)){
                all_wanzers=false;
            }
        }
        return all_wanzers;
    }
}
