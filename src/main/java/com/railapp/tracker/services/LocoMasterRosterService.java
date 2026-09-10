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

    private final Map<Integer, LocoProfile> exactMap = new HashMap<>();

    public LocoMasterRosterService() {
        // VSKP (Visakhapatnam - ECoR)
        exactMap.put(30710, new LocoProfile("WAP-7", "VSKP (Visakhapatnam)", "ECoR", "Standard IR White-Red Band (HOG)", false, false, "ECoR Workhorse"));
        exactMap.put(39210, new LocoProfile("WAP-7", "VSKP (Visakhapatnam)", "ECoR", "Standard IR White-Red Band (HOG)", false, false, "HOG"));

        // SRC (Santragachi - SER)
        exactMap.put(39184, new LocoProfile("WAP-7", "SRC (Santragachi)", "SER", "Standard IR White-Red Band (HOG)", false, false, "SER Intercity"));
        exactMap.put(22501, new LocoProfile("WAP-4", "SRC (Santragachi)", "SER", "Santragachi Classic Maroon-Cream", false, false, "Classic Dedicated Livery"));
        exactMap.put(30451, new LocoProfile("WAP-7", "SRC (Santragachi)", "SER", "Standard IR White-Red Band (HOG)", false, false, "HOG"));

        // ANGL (Angul - ECoR)
        exactMap.put(39637, new LocoProfile("WAP-7", "ANGL (Angul)", "ECoR", "Standard IR White-Red Band (HOG)", false, false, "HOG"));
        exactMap.put(39626, new LocoProfile("WAP-7", "ANGL (Angul)", "ECoR", "Standard IR White-Red Band (HOG)", false, false, "HOG"));

        // ED (Erode - SR)
        exactMap.put(22366, new LocoProfile("WAP-4", "ED (Erode)", "SR", "Standard Red-White Band", false, false, "Air Brake"));

        // Amrit Bharat Units
        exactMap.put(30146, new LocoProfile("WAP-5AB (Amrit Bharat)", "KJM", "SWR", "Amrit Bharat Orange-Grey", true, true, "Push-Pull"));
        exactMap.put(30163, new LocoProfile("WAP-5 (Amrit Bharat AB)", "BRC", "WR", "Amrit Bharat Orange-Dark Grey", true, true, "Push-Pull Aerodynamic"));
        exactMap.put(30164, new LocoProfile("WAP-5 (Amrit Bharat AB)", "BRC", "WR", "Amrit Bharat Orange-Dark Grey", true, true, "Push-Pull Aerodynamic"));
        exactMap.put(37873, new LocoProfile("WAP-7AD (Amrit Bharat AB)", "SPJ", "ECR", "Amrit Bharat Orange-Dark Grey", true, true, "Aerodynamic Push-Pull"));
    }

    public LocoProfile lookup(int locoNumber) {
        if (exactMap.containsKey(locoNumber)) {
            return exactMap.get(locoNumber);
        }

        // VSKP WAP-7 range (30700-30720, 39200-39250)
        if ((locoNumber >= 30700 && locoNumber <= 30720) || (locoNumber >= 39200 && locoNumber <= 39250)) {
            return new LocoProfile("WAP-7", "VSKP (Visakhapatnam)", "ECoR", "Standard IR White-Red Band (HOG)", false, false, "Passenger");
        }
        // SRC WAP-7 range
        if (locoNumber >= 39180 && locoNumber <= 39199) {
            return new LocoProfile("WAP-7", "SRC (Santragachi)", "SER", "Standard IR White-Red Band (HOG)", false, false, "Passenger");
        }
        // ANGL WAP-7 range
        if (locoNumber >= 39600 && locoNumber <= 39699) {
            return new LocoProfile("WAP-7", "ANGL (Angul)", "ECoR", "Standard IR White-Red Band (HOG)", false, false, "Passenger");
        }
        // WAP-4 series
        if (locoNumber >= 22200 && locoNumber <= 22399) {
            return new LocoProfile("WAP-4", "ED (Erode)", "SR", "Standard Red-White Band", false, false, "Passenger");
        }
        if (locoNumber >= 22400 && locoNumber <= 22600) {
            return new LocoProfile("WAP-4", "SRC (Santragachi)", "SER", "Standard Red-White Band", false, false, "Passenger");
        }
        // Amrit Bharat WAP-5AB
        if (locoNumber >= 30160 && locoNumber <= 30175) {
            return new LocoProfile("WAP-5 (Amrit Bharat AB)", "BRC", "WR", "Amrit Bharat Orange-Dark Grey", true, true, "Push-Pull");
        }
        // WAG-12B
        if (locoNumber >= 60000 && locoNumber <= 60999) {
            return new LocoProfile("WAG-12B", "NEDA", "CR", "Alstom Prima Blue-Silver", true, false, "Freight");
        }

        return new LocoProfile("WAP-7", "IR Active Fleet", "IR", "Standard IR Livery", false, false, "Active");
    }
}