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
        // --- ANGL (Angul - ECoR) WAP-7 / WAG-9HC ---
        exactRoster.put(39637, new LocoProfile("WAP-7", "ANGL", "ECoR", "Standard IR White-Red Band (HOG)", false, false));
        exactRoster.put(39636, new LocoProfile("WAP-7", "ANGL", "ECoR", "Standard IR White-Red Band (HOG)", false, false));
        exactRoster.put(39638, new LocoProfile("WAP-7", "ANGL", "ECoR", "Standard IR White-Red Band (HOG)", false, false));
        exactRoster.put(37125, new LocoProfile("WAP-7", "ANGL", "ECoR", "Standard IR White-Red Band (HOG)", false, false));

        // --- SRC (Santragachi - SER) WAP-4 / WAP-7 ---
        exactRoster.put(22501, new LocoProfile("WAP-4", "SRC", "SER", "Santragachi Classic Maroon-Cream", false, false));
        exactRoster.put(22502, new LocoProfile("WAP-4", "SRC", "SER", "Classic Maroon-Cream", false, false));
        exactRoster.put(30451, new LocoProfile("WAP-7", "SRC", "SER", "Standard IR White-Red Band (HOG)", false, false));
        exactRoster.put(30452, new LocoProfile("WAP-7", "SRC", "SER", "Standard IR White-Red Band (HOG)", false, false));

        // --- VSKP (Visakhapatnam - ECoR) ---
        exactRoster.put(39210, new LocoProfile("WAP-7", "VSKP", "ECoR", "Standard IR White-Red Band (HOG)", false, false));
        exactRoster.put(39211, new LocoProfile("WAP-7", "VSKP", "ECoR", "Standard IR White-Red Band (HOG)", false, false));

        // --- SPJ / ECR Amrit Bharat Push-Pull WAP-7AD (Aerodynamic) ---
        exactRoster.put(37873, new LocoProfile("WAP-7AD (Amrit Bharat AB)", "SPJ", "ECR", "Amrit Bharat Orange-Dark Grey", true, true));
        exactRoster.put(37006, new LocoProfile("WAP-7AD (Amrit Bharat AB)", "SPJ", "ECR", "Amrit Bharat Orange-Dark Grey", true, true));
        exactRoster.put(37007, new LocoProfile("WAP-7AD (Amrit Bharat AB)", "SPJ", "ECR", "Amrit Bharat Orange-Dark Grey", true, true));

        // --- BRC (Vadodara - WR) Amrit Bharat Aerodynamic WAP-5 AB ---
        exactRoster.put(30163, new LocoProfile("WAP-5 (Amrit Bharat AB)", "BRC", "WR", "Amrit Bharat Orange-Dark Grey", true, true));
        exactRoster.put(30164, new LocoProfile("WAP-5 (Amrit Bharat AB)", "BRC", "WR", "Amrit Bharat Orange-Dark Grey", true, true));

        // --- GZB (Ghaziabad - NR) High Speed / Special Liveries ---
        exactRoster.put(30008, new LocoProfile("WAP-5", "GZB", "NR", "Amul Milk Duronto White-Blue", false, false));
        exactRoster.put(30201, new LocoProfile("WAP-7", "GZB", "NR", "Navkiran Commemorative Livery", false, false));

        // --- BNDM (Bondamunda - SER) & TATA (Tatanagar - SER) ---
        exactRoster.put(31825, new LocoProfile("WAG-9HC", "BNDM", "SER", "Standard Freight Green-Yellow", false, false));
        exactRoster.put(28500, new LocoProfile("WAG-7", "TATA", "SER", "Tiger Face Blue-Black", false, false));
    }

    public LocoProfile lookup(int locoNumber) {
        if (exactRoster.containsKey(locoNumber)) {
            return exactRoster.get(locoNumber);
        }
        // Systematic Fallback by Railway Range
        if (locoNumber >= 39600 && locoNumber <= 39699) {
            return new LocoProfile("WAP-7", "ANGL", "ECoR", "Standard IR White-Red Band (HOG)", false, false);
        }
        if (locoNumber >= 37100 && locoNumber <= 37160) {
            return new LocoProfile("WAP-7", "ANGL", "ECoR", "Standard IR White-Red Band (HOG)", false, false);
        }
        if (locoNumber >= 39200 && locoNumber <= 39250) {
            return new LocoProfile("WAP-7", "VSKP", "ECoR", "Standard IR White-Red Band (HOG)", false, false);
        }
        if (locoNumber >= 22200 && locoNumber <= 22999) {
            return new LocoProfile("WAP-4", "SRC", "SER", "Standard Red-White Band", false, false);
        }
        if (locoNumber >= 30160 && locoNumber <= 30175) {
            return new LocoProfile("WAP-5 (Amrit Bharat AB)", "BRC", "WR", "Amrit Bharat Orange-Dark Grey", true, true);
        }
        if (locoNumber >= 30000 && locoNumber <= 30159) {
            return new LocoProfile("WAP-5", "GZB", "NR", "Standard IR White-Red Band", false, false);
        }
        if (locoNumber >= 30200 && locoNumber <= 30899) {
            return new LocoProfile("WAP-7", "GZB", "NR", "Standard IR White-Red Band (HOG)", false, false);
        }
        if (locoNumber >= 31000 && locoNumber <= 34999) {
            return new LocoProfile("WAG-9HC", "BNDM", "SER", "Freight Green-Yellow", false, false);
        }
        if (locoNumber >= 60000 && locoNumber <= 60999) {
            return new LocoProfile("WAG-12B", "MGS", "ECR", "Alstom Prima Blue-Silver", false, false);
        }
        return new LocoProfile("WAP-7", "ANGL", "ECoR", "Standard IR Livery", false, false);
    }
}