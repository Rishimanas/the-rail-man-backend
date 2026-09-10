package com.railapp.tracker.services;

import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
public class LocoMasterRosterService {

    public static class LocoProfile {
        public final String locoClass;
        public final String shedCode;
        public final String zone;
        public final String livery;
        public final boolean isPushPull;
        public final boolean isConverted;
        public final String specialFeature;

        public LocoProfile(String locoClass, String shedCode, String zone, String livery, boolean isPushPull, boolean isConverted, String specialFeature) {
            this.locoClass = locoClass;
            this.shedCode = shedCode;
            this.zone = zone;
            this.livery = livery;
            this.isPushPull = isPushPull;
            this.isConverted = isConverted;
            this.specialFeature = specialFeature;
        }
    }

    private final Map<Integer, LocoProfile> exactRoster = new HashMap<>();

    public LocoMasterRosterService() {
        // --- ANGL / ANGE (Angul - ECoR) ---
        exactRoster.put(39637, new LocoProfile("WAP-7", "ANGE (Angul)", "ECoR", "Standard IR White-Red Band (HOG)", false, false, "HOG Fitted"));
        exactRoster.put(39626, new LocoProfile("WAP-7", "ANGE (Angul)", "ECoR", "Standard IR White-Red Band (HOG)", false, false, "HOG Database Record"));
        exactRoster.put(39627, new LocoProfile("WAP-7", "ANGE (Angul)", "ECoR", "Standard IR White-Red Band (HOG)", false, false, "HOG Database Record"));

        // --- ED / EDE (Erode - SR) WAP-4 & WAP-5 ---
        exactRoster.put(22366, new LocoProfile("WAP-4", "EDE (Erode)", "SR", "Standard Red-White Band", false, false, "Passenger Air Brake"));
        exactRoster.put(22297, new LocoProfile("WAP-4", "BSLL (Bhusawal)", "CR", "Standard Red Livery", false, false, "2x130 kVA SIV"));
        exactRoster.put(22307, new LocoProfile("WAP-4", "BSLL (Bhusawal)", "CR", "Standard Red Livery", false, false, "2x130 kVA SIV"));

        // --- SRC / SRCE (Santragachi - SER) ---
        exactRoster.put(22501, new LocoProfile("WAP-4", "SRCE (Santragachi)", "SER", "Santragachi Classic Maroon-Cream", false, false, "Classic Dedicated Livery"));
        exactRoster.put(30451, new LocoProfile("WAP-7", "SRCE (Santragachi)", "SER", "Standard IR White-Red Band (HOG)", false, false, "HOG Fitted"));

        // --- AMRIT BHARAT / WAP-5AB / PUSH-PULL ---
        exactRoster.put(30146, new LocoProfile("WAP-5AB (Amrit Bharat)", "KJMD (KJM)", "SWR", "Amrit Bharat Orange-Grey", true, true, "IGBT Converted"));
        exactRoster.put(35037, new LocoProfile("WAP-5AB (Amrit Bharat)", "KJMD (KJM)", "SWR", "Amrit Bharat Orange-Grey", true, true, "HOG Fitted"));
        exactRoster.put(35045, new LocoProfile("WAP-5AB (Amrit Bharat)", "KJMD (KJM)", "SWR", "Amrit Bharat Orange-Grey", true, true, "HOG Fitted"));
        exactRoster.put(35046, new LocoProfile("WAP-5AB (Amrit Bharat)", "KJMD (KJM)", "SWR", "Amrit Bharat Orange-Grey", true, true, "HOG Fitted"));
        exactRoster.put(30164, new LocoProfile("WAP-5 (High Speed)", "GZBE (Ghaziabad)", "NR", "Standard White-Red Band", false, false, "200 km/h Rated"));

        // --- CONVERTED DIESEL TO ELECTRIC ---
        exactRoster.put(10001, new LocoProfile("WAG-10 (Converted)", "BNDL (Bondamunda)", "SER", "Special Freight Blue", false, true, "Converted from 2 ALCO WDM2 locos"));
        exactRoster.put(29001, new LocoProfile("WAG-11 (Converted)", "BNDL (Bondamunda)", "SER", "Special Freight Orange-Blue", false, true, "Converted from 2 HHP WDG4 locos"));

        // --- WAG-12B HEAVY FREIGHT (12,000 HP) ---
        exactRoster.put(60251, new LocoProfile("WAG-12B", "NEDA", "CR", "Alstom Prima Blue-Silver", true, false, "12,000 HP Twin Section"));
        exactRoster.put(60501, new LocoProfile("WAG-12B", "SBTD", "WR", "Alstom Prima Blue-Silver", true, false, "12,000 HP Twin Section"));
    }

    public LocoProfile lookup(int locoNumber) {
        if (exactRoster.containsKey(locoNumber)) {
            return exactRoster.get(locoNumber);
        }
        // Systemic Roster Fallback Rules
        if (locoNumber >= 39600 && locoNumber <= 39699) {
            return new LocoProfile("WAP-7", "ANGE (Angul)", "ECoR", "Standard IR White-Red Band (HOG)", false, false, "Standard HOG");
        }
        if (locoNumber >= 22200 && locoNumber <= 22400) {
            return new LocoProfile("WAP-4", "EDE (Erode)", "SR", "Standard Red-White Band", false, false, "Passenger Workhorse");
        }
        if (locoNumber >= 22401 && locoNumber <= 22600) {
            return new LocoProfile("WAP-4", "SRCE (Santragachi)", "SER", "Standard Red-White Band", false, false, "Passenger Workhorse");
        }
        if (locoNumber >= 35000 && locoNumber <= 35099) {
            return new LocoProfile("WAP-5AB (Amrit Bharat)", "KJMD (KJM)", "SWR", "Amrit Bharat Orange-Grey", true, true, "Push-Pull Aerodynamic");
        }
        if (locoNumber >= 60000 && locoNumber <= 60999) {
            return new LocoProfile("WAG-12B", "NEDA", "CR", "Alstom Prima Blue-Silver", true, false, "12,000 HP Freight");
        }
        return new LocoProfile("WAP-7", "ANGE (Angul)", "ECoR", "Standard IR Livery", false, false, "Indian Railways Active");
    }
}