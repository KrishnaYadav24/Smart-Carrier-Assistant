package com.smartcareer.service;

import org.springframework.stereotype.Service;

@Service
public class ATSService {

    public int calculateATSScore(

            String text) {

        text = text.toLowerCase();

        int score = 0;

        /*
         * COMMON RESUME SECTIONS
         */

        if (text.contains("education")) {

            score += 10;
        }

        if (text.contains("experience")) {

            score += 15;
        }

        if (text.contains("skills")) {

            score += 15;
        }

        if (text.contains("project")) {

            score += 10;
        }

        if (text.contains("certification")) {

            score += 10;
        }

        /*
         * SOFTWARE SKILLS
         */

        if (text.contains("java")
                ||
                text.contains("python")
                ||
                text.contains("sql")
                ||
                text.contains("html")
                ||
                text.contains("css")) {

            score += 15;
        }

        /*
         * CIVIL SKILLS
         */

        if (text.contains("autocad")
                ||
                text.contains("staad")
                ||
                text.contains("construction")
                ||
                text.contains("site")
                ||
                text.contains("surveying")) {

            score += 15;
        }

        /*
         * MBA SKILLS
         */

        if (text.contains("marketing")
                ||
                text.contains("finance")
                ||
                text.contains("sales")
                ||
                text.contains("business")) {

            score += 15;
        }

        /*
         * MEDICAL SKILLS
         */

        if (text.contains("medical")
                ||
                text.contains("hospital")
                ||
                text.contains("patient")) {

            score += 15;
        }

        /*
         * CONTACT DETAILS
         */

        if (text.contains("@")) {

            score += 5;
        }

        if (text.contains("+91")) {

            score += 5;
        }

        /*
         * LIMIT SCORE
         */

        if (score > 100) {

            score = 100;
        }

        return score;
    }
}