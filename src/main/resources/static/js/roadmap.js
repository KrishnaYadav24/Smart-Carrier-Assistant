async function generateRoadmap() {

    const role = document
        .getElementById("targetRole")
        .value
        .trim();

    if (!role) {

        alert("Please enter a target role.");

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

    try {

        if (loading) {

            loading.style.display =
                "block";
        }

        if (resultContainer) {

            resultContainer.style.display =
                "none";
        }

        const response =
            await fetch(
                "/api/roadmap/generate?role=" +
                encodeURIComponent(role)
            );

        if (!response.ok) {

            throw new Error(
                "Failed to generate roadmap"
            );
        }

        const data =
            await response.text();

        roadmapResult.innerText =
            data;

        resultContainer.style.display =
            "block";

    } catch (error) {

        console.error(error);

        alert(
            "Roadmap generation failed.\n\n" +
            error.message
        );

    } finally {

        if (loading) {

            loading.style.display =
                "none";
        }
    }
}