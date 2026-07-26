package com.smartcareer.service;

import com.smartcareer.dto.ATSMatchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ATSMatchingService {

        @Autowired
        private NLPService nlpService;

        /*
         * ============================================================
         * CATEGORY WEIGHTS
         * ============================================================
         */

        private static final Map<String, Integer> CATEGORY_WEIGHTS = Map.of(
                        "Programming", 25,
                        "Frontend", 15,
                        "Backend", 20,
                        "Database", 15,
                        "Cloud", 10,
                        "DevOps", 10,
                        "Testing", 5,
                        "AI/ML", 10);

        /*
         * ============================================================
         * SKILL DATABASE
         * ============================================================
         */

        private static final Map<String, List<String>> SKILL_DATABASE = new LinkedHashMap<>();

        static {

                /*
                 * Programming Languages
                 */

                SKILL_DATABASE.put("Programming", Arrays.asList(

                                "java",
                                "python",
                                "c",
                                "c++",
                                "c#",
                                "javascript",
                                "typescript",
                                "kotlin",
                                "scala",
                                "go",
                                "golang",
                                "rust",
                                "php",
                                "ruby",
                                "swift"));

                /*
                 * Frontend
                 */

                SKILL_DATABASE.put("Frontend", Arrays.asList(

                                "html",
                                "html5",
                                "css",
                                "css3",
                                "bootstrap",
                                "tailwind",
                                "material ui",
                                "react",
                                "angular",
                                "vue",
                                "jquery",
                                "redux",
                                "next.js",
                                "nuxt.js"));

                /*
                 * Backend
                 */

                SKILL_DATABASE.put("Backend", Arrays.asList(

                                "spring",
                                "spring boot",
                                "spring mvc",
                                "spring security",
                                "hibernate",
                                "jpa",
                                "jdbc",
                                "servlet",
                                "jsp",
                                "rest",
                                "rest api",
                                "microservices",
                                "node.js",
                                "express",
                                "django",
                                "flask",
                                "laravel",
                                "asp.net",
                                ".net",
                                "graphql"));

                /*
                 * Database
                 */

                SKILL_DATABASE.put("Database", Arrays.asList(

                                "mysql",
                                "sql",
                                "oracle",
                                "postgresql",
                                "mongodb",
                                "firebase",
                                "sqlite",
                                "redis",
                                "mariadb",
                                "cassandra"));

                /*
                 * Cloud
                 */

                SKILL_DATABASE.put("Cloud", Arrays.asList(

                                "aws",
                                "amazon web services",
                                "azure",
                                "gcp",
                                "google cloud",
                                "digitalocean",
                                "cloud computing"));

                /*
                 * DevOps
                 */

                SKILL_DATABASE.put("DevOps", Arrays.asList(

                                "docker",
                                "kubernetes",
                                "git",
                                "github",
                                "gitlab",
                                "bitbucket",
                                "jenkins",
                                "maven",
                                "gradle",
                                "ansible",
                                "terraform",
                                "linux",
                                "bash"));

                /*
                 * Testing
                 */

                SKILL_DATABASE.put("Testing", Arrays.asList(

                                "junit",
                                "selenium",
                                "testng",
                                "postman",
                                "cypress",
                                "mockito",
                                "jmeter"));

                /*
                 * AI / ML
                 */

                SKILL_DATABASE.put("AI/ML", Arrays.asList(

                                "machine learning",
                                "deep learning",
                                "artificial intelligence",
                                "tensorflow",
                                "keras",
                                "pytorch",
                                "opencv",
                                "numpy",
                                "pandas",
                                "scikit-learn",
                                "nlp",
                                "computer vision",
                                "generative ai",
                                "llm",
                                "langchain",
                                "rag",
                                "hugging face"));

        }

        /*
         * ============================================================
         * SKILL SYNONYMS
         * ============================================================
         */

        private static final Map<String, String> SKILL_SYNONYMS = new HashMap<>();

        static {

                SKILL_SYNONYMS.put("js", "javascript");
                SKILL_SYNONYMS.put("ts", "typescript");
                SKILL_SYNONYMS.put("springboot", "spring boot");
                SKILL_SYNONYMS.put("spring-boot", "spring boot");
                SKILL_SYNONYMS.put("springboot3", "spring boot");
                SKILL_SYNONYMS.put("spring mvc", "spring mvc");
                SKILL_SYNONYMS.put("node", "node.js");
                SKILL_SYNONYMS.put("nodejs", "node.js");
                SKILL_SYNONYMS.put("reactjs", "react");
                SKILL_SYNONYMS.put("react.js", "react");
                SKILL_SYNONYMS.put("vuejs", "vue");
                SKILL_SYNONYMS.put("vue.js", "vue");
                SKILL_SYNONYMS.put("ml", "machine learning");
                SKILL_SYNONYMS.put("ai", "artificial intelligence");
                SKILL_SYNONYMS.put("aws cloud", "aws");
                SKILL_SYNONYMS.put("gcp cloud", "gcp");
                SKILL_SYNONYMS.put("postgres", "postgresql");
                SKILL_SYNONYMS.put("mongo", "mongodb");
                SKILL_SYNONYMS.put("restful api", "rest api");
                SKILL_SYNONYMS.put("restful services", "rest api");
        }

        /*
         * ============================================================
         * CONSTANTS
         * ============================================================
         */

        private static final Pattern CLEAN_PATTERN = Pattern.compile("[^a-zA-Z0-9+#.\\- ]");

        private static final Pattern MULTIPLE_SPACES = Pattern.compile("\\s+");

        /*
         * ============================================================
         * MAIN ATS MATCHING METHOD
         * ============================================================
         */

        public ATSMatchResult compareResumeWithJD(String resumeText,
                        String jobDescription) {

                // Null Safety
                if (resumeText == null) {
                        resumeText = "";
                }

                if (jobDescription == null) {
                        jobDescription = "";
                }

                // Normalize Text
                resumeText = normalizeText(resumeText);
                jobDescription = normalizeText(jobDescription);

                // Extract Skills
                Map<String, Set<String>> resumeSkills = extractSkillsByCategory(resumeText);

                Map<String, Set<String>> jdSkills = extractSkillsByCategory(jobDescription);

                Set<String> matchedSkills = new LinkedHashSet<>();
                Set<String> missingSkills = new LinkedHashSet<>();

                double earnedWeight = 0;
                double totalWeight = 0;

                /*
                 * Compare Category Wise
                 */

                for (String category : SKILL_DATABASE.keySet()) {

                        Set<String> jdCategorySkills = jdSkills.getOrDefault(category, Collections.emptySet());

                        Set<String> resumeCategorySkills = resumeSkills.getOrDefault(category, Collections.emptySet());

                        if (jdCategorySkills.isEmpty()) {
                                continue;
                        }

                        int categoryWeight = CATEGORY_WEIGHTS.getOrDefault(category, 10);

                        totalWeight += categoryWeight;

                        int matched = 0;

                        for (String skill : jdCategorySkills) {

                                if (resumeCategorySkills.contains(skill)) {

                                        matchedSkills.add(skill);
                                        matched++;

                                } else {

                                        missingSkills.add(skill);
                                }
                        }

                        double ratio = (double) matched / jdCategorySkills.size();

                        earnedWeight += ratio * categoryWeight;
                }

                /*
                 * ATS SCORE
                 */

                int score = 0;

                if (totalWeight > 0) {

                        score = (int) Math.round(
                                        (earnedWeight / totalWeight) * 100);
                }

                if (score > 100) {
                        score = 100;
                }

                if (score < 0) {
                        score = 0;
                }

                /*
                 * Build Suggestion
                 */

                StringBuilder suggestion = new StringBuilder();

                if (score >= 90) {

                        suggestion.append(
                                        "Excellent ATS Match. Your resume closely aligns with the job description.");

                } else if (score >= 75) {

                        suggestion.append(
                                        "Very Good ATS Match. Adding a few missing technical skills can further improve your chances.");

                } else if (score >= 60) {

                        suggestion.append(
                                        "Good ATS Match. Consider strengthening your resume by including more relevant technologies.");

                } else if (score >= 40) {

                        suggestion.append(
                                        "Average ATS Match. Your resume is missing several important technical skills.");

                } else {

                        suggestion.append(
                                        "Low ATS Match. Consider updating your resume according to the required technologies.");
                }

                /*
                 * Missing Skills Recommendation
                 */

                if (!missingSkills.isEmpty()) {

                        suggestion.append("\n\nRecommended Skills:\n");

                        int count = 0;

                        for (String skill : missingSkills) {

                                suggestion.append("• ")
                                                .append(skill)
                                                .append("\n");

                                count++;

                                if (count >= 10) {
                                        break;
                                }
                        }
                }

                /*
                 * Semantic Analysis
                 */

                try {

                        String semanticResult = nlpService.analyzeSemanticMatch(
                                        resumeText,
                                        jobDescription);

                        if (semanticResult != null &&
                                        !semanticResult.isBlank()) {

                                suggestion.append("\n\n")
                                                .append(semanticResult);
                        }

                } catch (Exception e) {

                        suggestion.append(
                                        "\n\nSemantic analysis could not be completed.");
                }

                /*
                 * Return Result
                 */

                return new ATSMatchResult(

                                score,

                                new ArrayList<>(matchedSkills),

                                new ArrayList<>(missingSkills),

                                suggestion.toString());
        }

        /*
         * ============================================================
         * NORMALIZE TEXT
         * ============================================================
         */

        private String normalizeText(String text) {

                text = text.toLowerCase();

                text = CLEAN_PATTERN
                                .matcher(text)
                                .replaceAll(" ");

                text = MULTIPLE_SPACES
                                .matcher(text)
                                .replaceAll(" ")
                                .trim();

                /*
                 * Replace synonyms with standard names
                 */

                for (Map.Entry<String, String> synonym : SKILL_SYNONYMS.entrySet()) {

                        String regex = "\\b" +
                                        Pattern.quote(synonym.getKey()) +
                                        "\\b";

                        text = text.replaceAll(
                                        regex,
                                        synonym.getValue());
                }

                return text;
        }

        /*
         * ============================================================
         * EXTRACT SKILLS CATEGORY WISE
         * ============================================================
         */

        private Map<String, Set<String>> extractSkillsByCategory(String text) {

                Map<String, Set<String>> extractedSkills = new LinkedHashMap<>();

                for (Map.Entry<String, List<String>> entry : SKILL_DATABASE.entrySet()) {

                        String category = entry.getKey();

                        List<String> skills = entry.getValue();

                        Set<String> matchedSkills = new LinkedHashSet<>();

                        for (String skill : skills) {

                                if (containsSkill(text, skill)) {

                                        matchedSkills.add(skill);
                                }
                        }

                        extractedSkills.put(category, matchedSkills);
                }

                return extractedSkills;
        }

        /*
         * ============================================================
         * REGEX SKILL MATCHING
         * ============================================================
         */

        private boolean containsSkill(String text, String skill) {

                /*
                 * Handle special skills
                 */

                if (skill.equalsIgnoreCase("c++")) {

                        return Pattern.compile(
                                        "(?i)(^|\\W)c\\+\\+(\\W|$)").matcher(text).find();
                }

                if (skill.equalsIgnoreCase("c#")) {

                        return Pattern.compile(
                                        "(?i)(^|\\W)c#(\\W|$)").matcher(text).find();
                }

                if (skill.equalsIgnoreCase(".net")) {

                        return Pattern.compile(
                                        "(?i)(^|\\W)\\.net(\\W|$)").matcher(text).find();
                }

                if (skill.equalsIgnoreCase("node.js")) {

                        return Pattern.compile(
                                        "(?i)(^|\\W)node(\\.js)?(\\W|$)").matcher(text).find();
                }

                if (skill.equalsIgnoreCase("react")) {

                        return Pattern.compile(
                                        "(?i)(^|\\W)react(\\.js)?(\\W|$)").matcher(text).find();
                }

                if (skill.equalsIgnoreCase("vue")) {

                        return Pattern.compile(
                                        "(?i)(^|\\W)vue(\\.js)?(\\W|$)").matcher(text).find();
                }

                /*
                 * Default word-boundary match
                 */

                String regex = "\\b" +
                                Pattern.quote(skill) +
                                "\\b";

                Pattern pattern = Pattern.compile(
                                regex,
                                Pattern.CASE_INSENSITIVE);

                Matcher matcher = pattern.matcher(text);

                return matcher.find();
        }

        /*
         * ============================================================
         * GET ATS RATING
         * ============================================================
         */

        private String getATSRating(int score) {

                if (score >= 90) {
                        return "★★★★★ Excellent";
                }

                if (score >= 80) {
                        return "★★★★☆ Very Good";
                }

                if (score >= 70) {
                        return "★★★☆☆ Good";
                }

                if (score >= 60) {
                        return "★★☆☆☆ Average";
                }

                return "★☆☆☆☆ Needs Improvement";
        }

        /*
         * ============================================================
         * CATEGORY COVERAGE
         * ============================================================
         */

        private Map<String, Integer> calculateCategoryCoverage(

                        Map<String, Set<String>> resumeSkills,

                        Map<String, Set<String>> jdSkills) {

                Map<String, Integer> coverage = new LinkedHashMap<>();

                for (String category : SKILL_DATABASE.keySet()) {

                        Set<String> jd = jdSkills.getOrDefault(category, Collections.emptySet());

                        Set<String> resume = resumeSkills.getOrDefault(category, Collections.emptySet());

                        if (jd.isEmpty()) {

                                coverage.put(category, 100);
                                continue;
                        }

                        int matched = 0;

                        for (String skill : jd) {

                                if (resume.contains(skill)) {

                                        matched++;
                                }
                        }

                        int percent = (matched * 100) / jd.size();

                        coverage.put(category, percent);
                }

                return coverage;
        }

        /*
         * ============================================================
         * TOP MISSING SKILLS
         * ============================================================
         */

        private List<String> getTopMissingSkills(Set<String> missingSkills,
                        int limit) {

                List<String> result = new ArrayList<>();

                for (String skill : missingSkills) {

                        result.add(skill);

                        if (result.size() >= limit) {
                                break;
                        }
                }

                return result;
        }

        /*
         * ============================================================
         * TOP MATCHED SKILLS
         * ============================================================
         */

        private List<String> getTopMatchedSkills(Set<String> matchedSkills,
                        int limit) {

                List<String> result = new ArrayList<>();

                for (String skill : matchedSkills) {

                        result.add(skill);

                        if (result.size() >= limit) {
                                break;
                        }
                }

                return result;
        }

        /*
         * ============================================================
         * EXPERIENCE DETECTOR
         * ============================================================
         */

        private int extractYearsOfExperience(String text) {

                Pattern pattern = Pattern.compile(
                                "(\\d+)\\+?\\s*(years|year|yrs|yr)",
                                Pattern.CASE_INSENSITIVE);

                Matcher matcher = pattern.matcher(text);

                int maxYears = 0;

                while (matcher.find()) {

                        try {

                                int years = Integer.parseInt(matcher.group(1));

                                if (years > maxYears) {

                                        maxYears = years;
                                }

                        } catch (Exception ignored) {

                        }
                }

                return maxYears;
        }

        /*
         * ============================================================
         * PROFESSIONAL ATS SUGGESTION
         * ============================================================
         */

        private String buildProfessionalSuggestion(

                        int score,

                        Set<String> matchedSkills,

                        Set<String> missingSkills,

                        String resume,

                        String jobDescription) {

                StringBuilder sb = new StringBuilder();

                sb.append("ATS Score : ")
                                .append(score)
                                .append("%\n");

                sb.append("Rating : ")
                                .append(getATSRating(score))
                                .append("\n\n");

                sb.append("Matched Skills : ")
                                .append(matchedSkills.size())
                                .append("\n");

                sb.append("Missing Skills : ")
                                .append(missingSkills.size())
                                .append("\n\n");

                int experience = extractYearsOfExperience(resume);

                if (experience > 0) {

                        sb.append("Detected Experience : ")
                                        .append(experience)
                                        .append(" Years\n\n");
                }

                if (!matchedSkills.isEmpty()) {

                        sb.append("Top Matching Skills\n");

                        for (String skill : getTopMatchedSkills(matchedSkills, 8)) {

                                sb.append("✔ ").append(skill).append("\n");
                        }

                        sb.append("\n");
                }

                if (!missingSkills.isEmpty()) {

                        sb.append("Recommended Skills\n");

                        for (String skill : getTopMissingSkills(missingSkills, 10)) {

                                sb.append("• ").append(skill).append("\n");
                        }
                }

                return sb.toString();
        }
}