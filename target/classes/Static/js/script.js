let uploadedResumeData = "";

async function uploadResume() {

    const fileInput =
        document.getElementById(
            "resumeFile"
        );

    if (
        !fileInput ||
        fileInput.files.length === 0
    ) {

        alert(
            "Please select a resume."
        );

        return;
    }

    const username =
        localStorage.getItem(
            "username"
        );

    if (!username) {

        alert(
            "Please login first."
        );

        window.location.href =
            "/login.html";

        return;
    }

    const loading =
        document.getElementById(
            "loading"
        );

    if(loading){
        loading.style.display =
            "block";
    }

    const formData =
        new FormData();

    formData.append(
        "file",
        fileInput.files[0]
    );

    formData.append(
        "username",
        username
    );

    try {

        const response =
            await fetch(

                "/api/resume/upload",

                {
                    method:"POST",

                    body:formData
                }
            );

        const responseText =
            await response.text();

        console.log(
            "Backend Response:",
            responseText
        );

        if(!response.ok){

            throw new Error(
                responseText
            );
        }

        if(
            responseText === null ||
            responseText.trim() === ""
        ){

            throw new Error(
                "Empty response received from backend."
            );
        }

        const data =
            JSON.parse(
                responseText
            );

        uploadedResumeData =
            JSON.stringify(data);

        localStorage.setItem(
            "resumeData",
            uploadedResumeData
        );

        if(data.interviewQuestions){

            localStorage.setItem(

                "interviewQuestions",

                data.interviewQuestions
            );
        }

        if(loading){
            loading.style.display =
                "none";
        }

        const resultContainer =
            document.getElementById(
                "resultContainer"
            );

        if(resultContainer){

            resultContainer.style.display =
                "block";
        }

        const score =
            document.getElementById(
                "score"
            );

        if(score){

            score.innerText =
                data.score || 0;
        }

        const atsScore =
            document.getElementById(
                "atsScore"
            );

        if(atsScore){

            atsScore.innerText =
                data.atsScore || 0;
        }

        const detectedSkills =
            document.getElementById(
                "detectedSkills"
            );

        if(detectedSkills){

            detectedSkills.innerHTML =
                "";

            if(
                data.detectedSkills &&
                Array.isArray(
                    data.detectedSkills
                )
            ){

                data.detectedSkills.forEach(
                    skill => {

                    detectedSkills.innerHTML +=

                        `<div class="skill detected">
                            ${skill}
                        </div>`;
                });
            }
        }

        const missingSkills =
            document.getElementById(
                "missingSkills"
            );

        if(missingSkills){

            missingSkills.innerHTML =
                "";

            if(
                data.missingSkills &&
                Array.isArray(
                    data.missingSkills
                )
            ){

                data.missingSkills.forEach(
                    skill => {

                    missingSkills.innerHTML +=

                        `<div class="skill missing">
                            ${skill}
                        </div>`;
                });
            }
        }

        const recommendedJobs =
            document.getElementById(
                "recommendedJobs"
            );

        if(recommendedJobs){

            recommendedJobs.innerHTML =
                "";

            if(
                data.recommendedJobs &&
                Array.isArray(
                    data.recommendedJobs
                )
            ){

                data.recommendedJobs.forEach(
                    job => {

                    recommendedJobs.innerHTML +=

                        `<div class="skill job">
                            ${job}
                        </div>`;
                });
            }
        }

        const suggestion =
            document.getElementById(
                "suggestion"
            );

        if(suggestion){

            suggestion.innerText =

                data.suggestion ||

                "No suggestion generated.";
        }

        window.scrollTo({

            top:
            resultContainer.offsetTop,

            behavior:"smooth"
        });

    } catch(error){

        console.error(error);

        if(loading){

            loading.style.display =
                "none";
        }

        alert(
            "Resume analysis failed.\n\n"
            +
            error.message
        );
    }
}

/*
    ATS MATCHING
*/

async function matchATS() {

    const jd =
        document.getElementById(
            "jobDescription"
        ).value;

    if(
        jd.trim() === ""
    ){

        alert(
            "Please enter Job Description."
        );

        return;
    }

    const fileInput =
        document.getElementById(
            "resumeFile"
        );

    if(
        !fileInput ||
        fileInput.files.length === 0
    ){

        alert(
            "Please upload resume."
        );

        return;
    }

    const formData =
        new FormData();

    formData.append(

        "file",

        fileInput.files[0]
    );

    formData.append(

        "jobDescription",

        jd
    );

    try{

        const response =
            await fetch(

                "/api/ats/match",

                {
                    method:"POST",

                    body:formData
                }
            );

        const text =
            await response.text();

        if(
            text.trim() === ""
        ){

            throw new Error(
                "Empty ATS response."
            );
        }

        const data =
            JSON.parse(text);

        const scoreElement =
            document.getElementById(
                "matchScore"
            );

        if(scoreElement){

            scoreElement.innerText =
                data.matchScore || 0;
        }

        const matchedSkills =
            document.getElementById(
                "matchedSkills"
            );

        if(matchedSkills){

            matchedSkills.innerHTML =
                "";

            data.matchedSkills.forEach(
                skill => {

                matchedSkills.innerHTML +=

                    `<div class="skill detected">
                        ${skill}
                    </div>`;
            });
        }

        const missingSkills =
            document.getElementById(
                "missingSkills"
            );

        if(missingSkills){

            missingSkills.innerHTML =
                "";

            data.missingSkills.forEach(
                skill => {

                missingSkills.innerHTML +=

                    `<div class="skill missing">
                        ${skill}
                    </div>`;
            });
        }

        const atsSuggestion =
            document.getElementById(
                "atsSuggestion"
            );

        if(atsSuggestion){

            atsSuggestion.innerText =
                data.suggestion;
        }

        const atsResult =
            document.getElementById(
                "atsResultContainer"
            );

        if(atsResult){

            atsResult.style.display =
                "block";
        }

    }catch(error){

        console.error(error);

        alert(
            "ATS Matching Failed.\n\n"
            +
            error.message
        );
    }
}

async function predictJobRole(){

    const fileInput =
        document.getElementById(
            "jobResumeFile"
        );

    if(
        !fileInput ||
        fileInput.files.length === 0
    ){

        alert(
            "Please upload a resume."
        );

        return;
    }

    const loading =
        document.getElementById(
            "jobLoading"
        );

    if(loading){

        loading.style.display =
            "block";
    }

    const formData =
        new FormData();

    formData.append(
        "file",
        fileInput.files[0]
    );

    try{

        const response =
            await fetch(

                "/api/job-role/predict",

                {
                    method:"POST",

                    body:formData
                }
            );

        const responseText =
            await response.text();

        console.log(
            "Job Predictor Response:",
            responseText
        );

        if(!response.ok){

            throw new Error(
                responseText
            );
        }

        if(
            responseText.trim() === ""
        ){

            throw new Error(
                "Empty response."
            );
        }

        const data =
            JSON.parse(
                responseText
            );

        if(loading){

            loading.style.display =
                "none";
        }

        const resultContainer =
            document.getElementById(
                "jobResultContainer"
            );

        if(resultContainer){

            resultContainer.style.display =
                "block";
        }

        const rolesContainer =
            document.getElementById(
                "predictedRoles"
            );

        if(rolesContainer){

            rolesContainer.innerHTML =
                "";

            if(
                data.roles &&
                Array.isArray(
                    data.roles
                )
            ){

                data.roles.forEach(
                    role => {

                    rolesContainer.innerHTML +=

                        `<div class="role-card">
                            ${role}
                        </div>`;
                });
            }
        }

        const explanation =
            document.getElementById(
                "roleExplanation"
            );

        if(explanation){

            explanation.innerText =

                data.explanation ||

                "No explanation generated.";
        }

    }catch(error){

        console.error(error);

        if(loading){

            loading.style.display =
                "none";
        }

        alert(
            "Job Role Prediction Failed.\n\n"
            +
            error.message
        );
    }

    /* =========================
   ROADMAP GENERATOR
========================= */

async function generateRoadmap(){

    const role =
        document.getElementById(
            "targetRole"
        ).value.trim();

    if(role === ""){

        alert(
            "Please enter target role."
        );

        return;
    }

    const loading =
        document.getElementById(
            "roadmapLoading"
        );

    const resultContainer =
        document.getElementById(
            "roadmapContainer"
        );

    const roadmapResult =
        document.getElementById(
            "roadmapResult"
        );

    try{

        if(loading){

            loading.style.display =
                "block";
        }

        if(resultContainer){

            resultContainer.style.display =
                "none";
        }

        const response =
            await fetch(

                "/api/roadmap/generate?role="
                +
                encodeURIComponent(role)

            );

        if(!response.ok){

            throw new Error(
                "Roadmap generation failed."
            );
        }

        const data =
            await response.text();

        roadmapResult.innerText =
            data;

        resultContainer.style.display =
            "block";

    }catch(error){

        console.error(error);

        alert(
            "Roadmap generation failed.\n\n"
            +
            error.message
        );

    }finally{

        if(loading){

            loading.style.display =
                "none";
        }
    }
}
}