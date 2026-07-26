/* =====================================================
   AI RESUME BUILDER
   PART 1
===================================================== */

/* =====================================================
   DOM HELPERS
===================================================== */

function $(id){

    return document.getElementById(id);

}

function setText(id,value,defaultText){

    const element=$(id);

    if(element){

        element.innerText=value.trim() || defaultText;

    }

}

/* =====================================================
   INITIALIZE
===================================================== */

document.addEventListener(

    "DOMContentLoaded",

    function(){

        initializePhotoUpload();

        initializeLivePreview();

        initializeTemplateSelection();

    }

);

/* =====================================================
   PROFILE PHOTO
===================================================== */

function initializePhotoUpload(){

    const profilePhoto=$("profilePhoto");

    if(!profilePhoto){

        return;

    }

    profilePhoto.addEventListener(

        "change",

        function(event){

            const file=event.target.files[0];

            if(!file){

                return;

            }

            const reader=new FileReader();

            reader.onload=function(e){

                const image=e.target.result;

                if($("previewImage")){

                    $("previewImage").src=image;

                }

                if($("resumePreviewImage")){

                    $("resumePreviewImage").src=image;

                }

            };

            reader.readAsDataURL(file);

        }

    );

}

/* =====================================================
   LIVE PREVIEW
===================================================== */

function initializeLivePreview(){

    const fields=[

        "fullName",

        "email",

        "phone",

        "linkedin",

        "github",

        "summary",

        "education",

        "skills",

        "projects",

        "experience",

        "certifications"

    ];

    fields.forEach(id=>{

        const input=$(id);

        if(input){

            input.addEventListener(

                "input",

                updatePreview

            );

        }

    });

    updatePreview();

}

/* =====================================================
   UPDATE LIVE PREVIEW
===================================================== */

function updatePreview(){

    const name=

        $("fullName")?.value || "";

    const email=

        $("email")?.value || "";

    const phone=

        $("phone")?.value || "";

    const linkedin=

        $("linkedin")?.value || "";

    const github=

        $("github")?.value || "";

    const summary=

        $("summary")?.value || "";

    const education=

        $("education")?.value || "";

    const skills=

        $("skills")?.value || "";

    const projects=

        $("projects")?.value || "";

    const experience=

        $("experience")?.value || "";

    const certifications=

        $("certifications")?.value || "";

    /* NAME */

    const previewName=

        document.querySelector(".preview-name");

    if(previewName){

        previewName.innerText=

            name || "Your Name";

    }

    /* CONTACT */

    const contact=

        document.querySelectorAll(

            ".resume-contact span"

        );

    if(contact.length>=2){

        contact[0].innerHTML=

            '<i class="fa-solid fa-envelope"></i> '

            +

            (

                email ||

                "example@gmail.com"

            );

        contact[1].innerHTML=

            '<i class="fa-solid fa-phone"></i> '

            +

            (

                phone ||

                "+91 XXXXX XXXXX"

            );

    }

    /* SUMMARY */

    setText(

        "previewSummary",

        summary,

        "Professional Summary appears here."

    );

    /* EDUCATION */

    setText(

        "previewEducation",

        education,

        "Education appears here."

    );

    /* SKILLS */

    setText(

        "previewSkills",

        skills,

        "Skills appear here."

    );

    /* PROJECTS */

    setText(

        "previewProjects",

        projects,

        "Projects appear here."

    );

    /* EXPERIENCE */

    setText(

        "previewExperience",

        experience,

        "Experience appears here."

    );

    /* CERTIFICATIONS */

    setText(

        "previewCertifications",

        certifications,

        "Certifications appear here."

    );

}


/* =====================================================
   TEMPLATE SELECTION
===================================================== */

function initializeTemplateSelection(){

    const cards=document.querySelectorAll(".template-card");

    cards.forEach(card=>{

        card.addEventListener(

            "click",

            function(){

                cards.forEach(c=>{

                    c.classList.remove("active-template");

                });

                this.classList.add("active-template");

                const radio=

                    this.querySelector(

                        "input[type='radio']"

                    );

                if(radio){

                    radio.checked=true;

                }

            }

        );

    });

}

/* =====================================================
   LOADING
===================================================== */

function showLoading(){

    let loader=

        document.getElementById("loader");

    if(loader){

        loader.style.display="flex";

    }

}

function hideLoading(){

    let loader=

        document.getElementById("loader");

    if(loader){

        loader.style.display="none";

    }

}

/* =====================================================
   TOAST
===================================================== */

function showToast(message,color="#16a34a"){

    const toast=document.createElement("div");

    toast.innerText=message;

    toast.style.position="fixed";

    toast.style.bottom="30px";

    toast.style.right="30px";

    toast.style.background=color;

    toast.style.color="#fff";

    toast.style.padding="16px 28px";

    toast.style.borderRadius="14px";

    toast.style.fontWeight="600";

    toast.style.boxShadow="0 15px 30px rgba(0,0,0,.18)";

    toast.style.zIndex="9999";

    toast.style.animation="fadeUp .4s ease";

    document.body.appendChild(toast);

    setTimeout(()=>{

        toast.remove();

    },3000);

}

/* =====================================================
   GENERATE PDF
===================================================== */

async function generateResume(){

    showLoading();

    const selectedTemplate=

        document.querySelector(

            "input[name='template']:checked"

        )?.value || "modern";

    const resumeData={

        template:selectedTemplate,

        fullName:$("fullName").value,

        email:$("email").value,

        phone:$("phone").value,

        linkedin:$("linkedin").value,

        github:$("github").value,

        summary:$("summary").value,

        education:$("education").value,

        skills:$("skills").value,

        projects:$("projects").value,

        experience:$("experience").value,

        certifications:$("certifications").value

    };

    console.log(resumeData);

    try{

        const response=

            await fetch(

                "/api/resume-builder/generate",

                {

                    method:"POST",

                    headers:{

                        "Content-Type":"application/json"

                    },

                    body:JSON.stringify(

                        resumeData

                    )

                }

            );

        if(!response.ok){

            throw new Error(

                "Resume generation failed."

            );

        }

        const blob=

            await response.blob();

        const url=

            window.URL.createObjectURL(blob);

        const a=

            document.createElement("a");

        a.href=url;

        a.download="resume.pdf";

        document.body.appendChild(a);

        a.click();

        a.remove();

        window.URL.revokeObjectURL(url);

        hideLoading();

        showToast(

            "Resume Downloaded Successfully"

        );

    }

    catch(error){

        hideLoading();

        console.error(error);

        showToast(

            "Resume Generation Failed",

            "#dc2626"

        );

        alert(

            error.message

        );

    }

}

/* =====================================================
   RESET PREVIEW
===================================================== */

const form=document.getElementById("resumeForm");

if(form){

    form.addEventListener(

        "reset",

        function(){

            setTimeout(updatePreview,100);

        }

    );

}

