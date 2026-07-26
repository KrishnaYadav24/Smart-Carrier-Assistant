/* ==========================================================
                    GLOBAL VARIABLES
========================================================== */

let currentQuestion = 1;

let totalQuestions = 10;

let currentQuestionText = "";

let resumeText = "";

let interviewCompleted = false;

let interviewResults = [];


/* ==========================================================
                CHARACTER COUNTER
========================================================== */

const answerBox =
document.getElementById("answer");

const charCount =
document.getElementById("charCount");

if(answerBox){

    answerBox.addEventListener(

        "input",

        function(){

            charCount.innerHTML =

                answerBox.value.length

                +

                " / 2500 Characters";

        }

    );

}


/* ==========================================================
                RESUME UPLOAD
========================================================== */

const resumeFile =
document.getElementById("resumeFile");

if(resumeFile){

    resumeFile.addEventListener(

        "change",

        function(event){

            const file =

                event.target.files[0];

            if(!file){

                return;

            }

            const reader =
                new FileReader();

            reader.onload =

                function(e){

                    resumeText =
                        e.target.result;

                };

            reader.readAsText(file);

        }

    );

}


/* ==========================================================
                START INTERVIEW
========================================================== */

async function startInterview(){

    currentQuestion = 1;

    interviewCompleted = false;

    interviewResults = [];

    totalQuestions = parseInt(

        document.getElementById(
            "totalQuestions"
        ).value

    );

    const request = {

        domain:

        document.getElementById(
            "domain"
        ).value,

        experience:

        document.getElementById(
            "experience"
        ).value,

        difficulty:

        document.getElementById(
            "difficulty"
        ).value,

        totalQuestions:

        totalQuestions,

        resumeText:

        resumeText

    };

    document.getElementById(

        "loading"

    ).style.display = "block";

    try{

        const response =

            await fetch(

                "/api/interview/start",

                {

                    method:"POST",

                    headers:{

                        "Content-Type":

                        "application/json"

                    },

                    body:JSON.stringify(

                        request

                    )

                }

            );

        const question =

            await response.text();

        currentQuestionText =

            question;

        document.getElementById(

            "question"

        ).innerHTML =

            question;

        document.getElementById(

            "interviewCard"

        ).style.display = "block";

        document.getElementById(

            "loading"

        ).style.display = "none";

        updateProgress();

    }

    catch(error){

        console.log(error);

        alert(

            "Unable to start interview."

        );

    }

}


/* ==========================================================
                    UPDATE PROGRESS
========================================================== */

function updateProgress(){

    document.getElementById(

        "questionCounter"

    ).innerHTML =

        "Question "

        +

        currentQuestion

        +

        " / "

        +

        totalQuestions;

    const percent =

        (currentQuestion/totalQuestions)

        *100;

    document.getElementById(

        "progressBar"

    ).style.width =

        percent

        +

        "%";

}

/* ==========================================================
                SUBMIT ANSWER
========================================================== */

async function submitAnswer(){

    const answer =

        document.getElementById(
            "answer"
        ).value.trim();

    if(answer.length < 10){

        alert(

            "Please write a meaningful answer."

        );

        return;

    }

    document.getElementById(

        "loading"

    ).style.display = "block";

    document.getElementById(

        "feedbackCard"

    ).style.display = "none";

    try{

        const request = {

            domain:

            document.getElementById(
                "domain"
            ).value,

            difficulty:

            document.getElementById(
                "difficulty"
            ).value,

            question:

            currentQuestionText,

            answer:

            answer

        };

        const response =

            await fetch(

                "/api/interview/evaluate",

                {

                    method:"POST",

                    headers:{

                        "Content-Type":

                        "application/json"

                    },

                    body:

                    JSON.stringify(
                        request
                    )

                }

            );

        if(!response.ok){

            throw new Error(

                "Interview evaluation failed."

            );

        }

        const result =

            await response.text();

        interviewResults.push(result);

        displayEvaluation(result);

    }

    catch(error){

        console.log(error);

        alert(

            error.message

        );

    }

    finally{

        document.getElementById(

            "loading"

        ).style.display = "none";

    }

}



/* ==========================================================
            DISPLAY AI EVALUATION
========================================================== */

function displayEvaluation(result){

    document.getElementById(

        "feedbackCard"

    ).style.display =

        "block";


    function extract(title){

        const regex =

            new RegExp(

                title +

                "\\s*:?([\\s\\S]*?)(?=Interview Score|Correctness|Missing Concepts|Ideal Answer|Communication|Confidence Level|Improvement Tips|Motivation|Next Interview Question|$)",

                "i"

            );

        const match =

            result.match(regex);

        return match ?

            match[1].trim()

            :

            "-";

    }


    document.getElementById(

        "score"

    ).innerHTML =

        extract("Interview Score");


    document.getElementById(

        "correctness"

    ).innerHTML =

        extract("Correctness");


    document.getElementById(

        "missingConcepts"

    ).innerHTML =

        extract("Missing Concepts");


    document.getElementById(

        "idealAnswer"

    ).innerHTML =

        extract("Ideal Answer");


    document.getElementById(

        "communication"

    ).innerHTML =

        extract("Communication");


    document.getElementById(

        "confidence"

    ).innerHTML =

        extract("Confidence Level");


    document.getElementById(

        "tips"

    ).innerHTML =

        extract("Improvement Tips");


    document.getElementById(

        "motivation"

    ).innerHTML =

        extract("Motivation");


    currentQuestionText =

        extract("Next Interview Question");


    if(currentQuestion < totalQuestions){

        document.getElementById(

            "nextBtn"

        ).style.display =

            "inline-block";

    }

    else{

        document.getElementById(

            "finishBtn"

        ).style.display =

            "inline-block";

    }

}

/* ==========================================================
                    NEXT QUESTION
========================================================== */

function nextQuestion(){

    currentQuestion++;

    updateProgress();

    document.getElementById("question").innerHTML =
        currentQuestionText;

    document.getElementById("answer").value = "";

    document.getElementById("charCount").innerHTML =
        "0 / 2500 Characters";

    document.getElementById("feedbackCard").style.display =
        "none";

    document.getElementById("nextBtn").style.display =
        "none";

    window.scrollTo({

        top:0,

        behavior:"smooth"

    });

}



/* ==========================================================
                    FINISH INTERVIEW
========================================================== */

function finishInterview(){

    interviewCompleted = true;

    document.getElementById("interviewCard").style.display =
        "none";

    document.getElementById("reportCard").style.display =
        "block";

    generateFinalReport();

}



/* ==========================================================
                GENERATE FINAL REPORT
========================================================== */

function generateFinalReport(){

    let totalScore = 0;

    interviewResults.forEach(result => {

        const match =

            result.match(

                /Interview Score\s*:?\s*([0-9.]+)/i

            );

        if(match){

            totalScore += parseFloat(match[1]);

        }

    });

    let average = 0;

    if(interviewResults.length > 0){

        average =

            totalScore /

            interviewResults.length;

    }

    document.getElementById("overallScore").innerHTML =

        average.toFixed(1)

        +

        "/10";


    if(average >= 8){

        document.getElementById("technicalScore").innerHTML =
            "Excellent";

    }

    else if(average >= 6){

        document.getElementById("technicalScore").innerHTML =
            "Good";

    }

    else{

        document.getElementById("technicalScore").innerHTML =
            "Needs Improvement";

    }


    document.getElementById("communicationScore").innerHTML =
        "Good";

    document.getElementById("confidenceScore").innerHTML =
        "Intermediate";

    document.getElementById("strongAreas").innerHTML =
        "Java, Problem Solving, Logical Thinking";

    document.getElementById("weakAreas").innerHTML =
        "Advanced Concepts, Communication, Real-Time Scenarios";

    document.getElementById("recommendation").innerHTML =
        "Continue practicing daily. Focus on weak concepts and solve coding problems regularly.";

    document.getElementById("finalMotivation").innerHTML =
        "Excellent effort! You completed the AI Mock Interview successfully. Keep learning and stay confident. Every interview is a step toward your dream job.";

}



/* ==========================================================
                DOWNLOAD REPORT
========================================================== */

async function downloadInterviewReport(){

    alert(

        "PDF Download feature will be connected with Spring Boot in the next step."

    );

}



/* ==========================================================
                RESTART INTERVIEW
========================================================== */

function restartInterview(){

    currentQuestion = 1;

    interviewCompleted = false;

    interviewResults = [];

    currentQuestionText = "";

    document.getElementById("answer").value = "";

    document.getElementById("feedbackCard").style.display =
        "none";

    document.getElementById("reportCard").style.display =
        "none";

    document.getElementById("interviewCard").style.display =
        "none";

    document.getElementById("nextBtn").style.display =
        "none";

    document.getElementById("finishBtn").style.display =
        "none";

    document.getElementById("question").innerHTML =
        "Click Start Interview";

    document.getElementById("charCount").innerHTML =
        "0 / 2500 Characters";

    updateProgress();

    window.scrollTo({

        top:0,

        behavior:"smooth"

    });

}
