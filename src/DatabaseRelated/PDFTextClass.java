package DatabaseRelated;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;

import java.awt.*;
import java.io.IOException;

public class PDFTextClass {
    private PDDocument document;
    private PDPageContentStream contentStream;

    public PDFTextClass(PDDocument document, PDPageContentStream contentStream) {
        this.document = document;
        this.contentStream = contentStream;
    }

    void addLineOfText(String text, int xPos, int yPos, PDFont font, float fontSize, Color color) {
        try {
            contentStream.beginText();
            contentStream.setFont(font, fontSize);
            contentStream.setNonStrokingColor(color);
            contentStream.newLineAtOffset(xPos, yPos);
            contentStream.showText(text);
            contentStream.endText();
            contentStream.moveTo(0, 0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void addMultiLineText(String[] textArray, float leading, int xPos, int yPos, PDFont font, float fontSize, Color color) {
        try {
            contentStream.beginText();
            contentStream.setFont(font, fontSize);
            contentStream.setNonStrokingColor(color);
            contentStream.setLeading(leading);
            contentStream.newLineAtOffset(xPos, yPos);

            for (String t : textArray) {
                contentStream.showText(t);
                contentStream.newLine();
            }
            contentStream.endText();
            contentStream.moveTo(0,0);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    float getTextWidth(String text, PDFont font, float fontSize){
        try {
            return font.getStringWidth(text)/1000*fontSize;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }

}

