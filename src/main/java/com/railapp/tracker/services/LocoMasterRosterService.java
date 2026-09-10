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

        public LocoProfile(String locoClass, String shedCode, String zone, String livery, boolean isPushPull, boolean isConverted) {
            this.locoClass = locoClass;
            this.shedCode = shedCode;
            this.zone = zone;
            this.livery = livery;
            this.isPushPull = isPushPull;
            this.isConverted = isConverted;
        }
    }

    private final Map<Integer, LocoProfile> exactRoster = new HashMap<>();

    public LocoMasterRosterService() {
        // --- ED (Erode - SR) WAP-4 ---
        exactRoster.put(22366, new LocoProfile("WAP-4", "ED", "SR", "Standard Red-White Band", false, false));
        exactRoster.put(22262, new LocoProfile("WAP-4", "ED", "SR", "Standard Red-White Band", false, false));

        // --- ANGL (Angul - ECoR) WAP-7 ---
        exactRoster.put(39637, new LocoProfile("WAP-7", "ANGL", "ECoR", "Standard IR White-Red Band (HOG)", false, false));
        exactRoster.put(39636, new LocoProfile("WAP-7", "ANGL", "ECoR", "Standard IR White-Red Band (HOG)", false, false));
        exactRoster.put(39638, new LocoProfile("WAP-7", "ANGL", "ECoR", "Standard IR White-Red Band (HOG)", false, false));
        exactRoster.put(37125, new LocoProfile("WAP-7", "ANGL", "ECoR", "Standard IR White-Red Band (HOG)", false, false));

        // --- SRC (Santragachi - SER) WAP-4 / WAP-7 ---
        exactRoster.put(22501, new LocoProfile("WAP-4", "SRC", "SER", "Santragachi Classic Maroon-Cream", false, false));
        exactRoster.put(22502, new LocoProfile("WAP-4", "SRC", "SER", "Classic Maroon-Cream", false, false));
        exactRoster.put(30451, new LocoProfile("WAP-7", "SRC", "SER", "Standard IR White-Red Band (HOG)", false, false));

        // --- VSKP (Visakhapatnam - ECoR) ---
        exactRoster.put(39210, new LocoProfile("WAP-7", "VSKP", "ECoR", "Standard IR White-Red Band (HOG)", false, false));
        exactRoster.put(22950, new LocoProfile("WAP-4", "VSKP", "ECoR", "Standard Red-White Band", false, false));

        // --- SPJ / ECR Amrit Bharat Push-Pull WAP-7AD ---
        exactRoster.put(37873, new LocoProfile("WAP-7AD (Amrit Bharat AB)", "SPJ", "ECR", "Amrit Bharat Orange-Dark Grey", true, true));
        exactRoster.put(37006, new LocoProfile("WAP-7AD (Amrit Bharat AB)", "SPJ", "ECR", "Amrit Bharat Orange-Dark Grey", true, true));

        // --- BRC (Vadodara - WR) Amrit Bharat WAP-5 AB ---
        exactRoster.put(30163, new LocoProfile("WAP-5 (Amrit Bharat AB)", "BRC", "WR", "Amrit Bharat Orange-Dark Grey", true, true));
        exactRoster.put(30164, new LocoProfile("WAP-5 (Amrit Bharat AB)", "BRC", "WR", "Amrit Bharat Orange-Dark Grey", true, true));
    }

    public LocoProfile lookup(int locoNumber) {
        if (exactRoster.containsKey(locoNumber)) {
            return exactRoster.get(locoNumber);
        }
        if (locoNumber >= 22200 && locoNumber <= 22400) {
            return new LocoProfile("WAP-4", "ED", "SR", "Standard Red-White Band", false, false);
        }
        if (locoNumber >= 22401 && locoNumber <= 22600) {
            return new LocoProfile("WAP-4", "SRC", "SER", "Standard Red-White Band", false, false);
        }
        if (locoNumber >= 39600 && locoNumber <= 39699) {
            return new LocoProfile("WAP-7", "ANGL", "ECoR", "Standard IR White-Red Band (HOG)", false, false);
        }
        if (locoNumber >= 37100 && locoNumber <= 37160) {
            return new LocoProfile("WAP-7", "ANGL", "ECoR", "Standard IR White-Red Band (HOG)", false, false);
        }
        if (locoNumber >= 39200 && locoNumber <= 39250) {
            return new LocoProfile("WAP-7", "VSKP", "ECoR", "Standard IR White-Red Band (HOG)", false, false);
        }
        if (locoNumber >= 30160 && locoNumber <= 30175) {
            return new LocoProfile("WAP-5 (Amrit Bharat AB)", "BRC", "WR", "Amrit Bharat Orange-Dark Grey", true, true);
        }
        return new LocoProfile("WAP-4", "ED", "SR", "Standard Indian Railways Livery", false, false);
    }
}