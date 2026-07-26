package com.smartcareer.service;

import java.io.ByteArrayOutputStream;

import org.springframework.stereotype.Service;

import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;

import com.itextpdf.layout.Document;

import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;

import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;

import com.smartcareer.model.ResumeRequest;

@Service
public class ResumePdfService {

    public byte[] generatePdf(
            ResumeRequest request
    ) throws Exception {

        ByteArrayOutputStream output =
                new ByteArrayOutputStream();

        PdfWriter writer =
                new PdfWriter(output);

        PdfDocument pdf =
                new PdfDocument(writer);

        Document document =
                new Document(pdf);

        document.setMargins(
                35,
                35,
                35,
                35
        );

        /* ===============================
                 HEADER
        ================================ */

        DeviceRgb primary =
                new DeviceRgb(
                        37,
                        99,
                        235
                );

        Table header =
                new Table(
                        UnitValue.createPercentArray(
                                new float[]{100}
                        )
                );

        header.useAllAvailableWidth();

        Cell headerCell =
                new Cell();

        headerCell
                .setBackgroundColor(primary)
                .setBorder(null)
                .setPadding(20);

        Paragraph name =
                new Paragraph(

                        request.getFullName()

                );

        name.setFontSize(26);

        name.setBold();

        name.setFontColor(
                ColorConstants.WHITE
        );

        headerCell.add(name);

        Paragraph role =
                new Paragraph(
                        "Software Engineer"
                );

        role.setFontSize(13);

        role.setFontColor(
                ColorConstants.WHITE
        );

        headerCell.add(role);

        header.addCell(headerCell);

        document.add(header);

        document.add(
                new Paragraph("\n")
        );

        /* ===============================
                CONTACT
        ================================ */

        Table contact =
                new Table(
                        UnitValue.createPercentArray(
                                new float[]{50,50}
                        )
                );

        contact.useAllAvailableWidth();

        Cell email =
                new Cell();

        email.add(

                new Paragraph(

                        "Email : "

                        +

                        request.getEmail()

                )

        );

        email.setBorder(null);

        contact.addCell(email);

        Cell phone =
                new Cell();

        phone.add(

                new Paragraph(

                        "Phone : "

                        +

                        request.getPhone()

                )

        );

        phone.setBorder(null);

        contact.addCell(phone);

        Cell linkedin =
                new Cell();

        linkedin.add(

                new Paragraph(

                        "LinkedIn : "

                        +

                        request.getLinkedin()

                )

        );

        linkedin.setBorder(null);

        contact.addCell(linkedin);

        Cell github =
                new Cell();

        github.add(

                new Paragraph(

                        "GitHub : "

                        +

                        request.getGithub()

                )

        );

        github.setBorder(null);

        contact.addCell(github);

        document.add(contact);

        document.add(
                new Paragraph("\n")
        );

                /* ===============================
            PROFESSIONAL SUMMARY
        ================================ */

        Paragraph summaryHeading =
                new Paragraph(
                        "PROFESSIONAL SUMMARY"
                );

        summaryHeading
                .setFontSize(16)
                .setBold()
                .setFontColor(primary);

        document.add(summaryHeading);

        Paragraph summary =
                new Paragraph(

                        request.getSummary() == null
                                ? ""
                                : request.getSummary()

                );

        summary.setFontSize(11);

        summary.setTextAlignment(
                TextAlignment.JUSTIFIED
        );

        document.add(summary);

        document.add(
                new Paragraph("\n")
        );

        /* ===============================
                EDUCATION
        ================================ */

        Paragraph educationHeading =
                new Paragraph(
                        "EDUCATION"
                );

        educationHeading
                .setFontSize(16)
                .setBold()
                .setFontColor(primary);

        document.add(
                educationHeading
        );

        Paragraph education =
                new Paragraph(

                        request.getEducation() == null
                                ? ""
                                : request.getEducation()

                );

        education.setFontSize(11);

        document.add(
                education
        );

        document.add(
                new Paragraph("\n")
        );

        /* ===============================
                 SKILLS
        ================================ */

        Paragraph skillHeading =
                new Paragraph(
                        "TECHNICAL SKILLS"
                );

        skillHeading
                .setFontSize(16)
                .setBold()
                .setFontColor(primary);

        document.add(
                skillHeading
        );

        String skills =

                request.getSkills() == null
                        ? ""
                        : request.getSkills();

        String skillArray[] =
                skills.split(",");

        for(String skill : skillArray){

            if(!skill.trim().isEmpty()){

                document.add(

                        new Paragraph(

                                "• "

                                +

                                skill.trim()

                        )

                        .setFontSize(11)

                );

            }

        }

        document.add(
                new Paragraph("\n")
        );

                /* ===============================
                 PROJECTS
        ================================ */

        Paragraph projectHeading =
                new Paragraph(
                        "PROJECTS"
                );

        projectHeading
                .setFontSize(16)
                .setBold()
                .setFontColor(primary);

        document.add(projectHeading);

        Paragraph projects =
                new Paragraph(

                        request.getProjects() == null
                                ? ""
                                : request.getProjects()

                );

        projects.setFontSize(11);

        projects.setTextAlignment(
                TextAlignment.JUSTIFIED
        );

        document.add(projects);

        document.add(
                new Paragraph("\n")
        );

        /* ===============================
                 EXPERIENCE
        ================================ */

        Paragraph experienceHeading =
                new Paragraph(
                        "EXPERIENCE"
                );

        experienceHeading
                .setFontSize(16)
                .setBold()
                .setFontColor(primary);

        document.add(
                experienceHeading
        );

        Paragraph experience =
                new Paragraph(

                        request.getExperience() == null
                                ? ""
                                : request.getExperience()

                );

        experience.setFontSize(11);

        experience.setTextAlignment(
                TextAlignment.JUSTIFIED
        );

        document.add(
                experience
        );

        document.add(
                new Paragraph("\n")
        );

        /* ===============================
              CERTIFICATIONS
        ================================ */

        Paragraph certificateHeading =
                new Paragraph(
                        "CERTIFICATIONS"
                );

        certificateHeading
                .setFontSize(16)
                .setBold()
                .setFontColor(primary);

        document.add(
                certificateHeading
        );

        String certifications =
                request.getCertifications() == null
                        ? ""
                        : request.getCertifications();

        String certificateArray[] =
                certifications.split(",");

        for(String certificate : certificateArray){

            if(!certificate.trim().isEmpty()){

                document.add(

                        new Paragraph(

                                "• "

                                +

                                certificate.trim()

                        )

                        .setFontSize(11)

                );

            }

        }

        /* ===============================
                  FOOTER
        ================================ */

        document.add(
                new Paragraph("\n")
        );

        Paragraph footer =
                new Paragraph(
                        "Generated by Smart Career Assistant"
                );

        footer.setTextAlignment(
                TextAlignment.CENTER
        );

        footer.setFontSize(10);

        footer.setFontColor(
                ColorConstants.GRAY
        );

        document.add(
                footer
        );

        /* ===============================
               CLOSE DOCUMENT
        ================================ */

        document.close();

        return output.toByteArray();

    }

}

