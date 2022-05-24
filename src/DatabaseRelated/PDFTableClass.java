package DatabaseRelated;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;

import java.awt.*;
import java.io.IOException;

public class PDFTableClass {
    private PDDocument document;
    private PDPageContentStream contentStream;
    private int[] colwidth;
    private int cellHeight;
    private int yPos;
    private int xPos;
    private int colPos;
    private int xInitialPos;
    private float fontSize;
    private PDFont font;
    private Color fontColor;

    public PDFTableClass(PDDocument document, PDPageContentStream contentStream) {
        this.document = document;
        this.contentStream = contentStream;
        this.colPos = 0;
    }


    void setTable(int[] colWidth, int cellHeight, int xPos, int yPos){
        this.colwidth = colWidth;
        this.cellHeight = cellHeight;
        this.xPos = yPos;
        this.yPos = xPos;
        this.xInitialPos = xPos;
    }

    void setTableFont(PDFont font, float fontSize, Color color){
        this.font = font;
        this.fontSize = fontSize;
        this.fontColor = color;
    }

    void addCell(String text, Color fillColor){
        try {
            contentStream.setStrokingColor(1f);
            if (fillColor!=null){
                contentStream.setNonStrokingColor(fillColor);
            }
            contentStream.addRect(xPos,yPos,colwidth[colPos],cellHeight);

            if (fillColor == null){
                contentStream.stroke();
            }
            else {
                contentStream.fillAndStroke();
            }

            contentStream.beginText();
            contentStream.setNonStrokingColor(fontColor);
            if (colPos == 3 || colPos == 1){
                float fontWidth = font.getStringWidth(text)/1000*fontSize;
                contentStream.newLineAtOffset(xPos+colwidth[colPos]-20-fontWidth,yPos+10);
            }else {
                contentStream.newLineAtOffset(xPos+20,yPos+10);
            }
            contentStream.showText(text);
            contentStream.endText();
            xPos = xPos+colwidth[colPos];
            colPos++;

            if (colPos == colwidth.length){
                colPos = 0;
                xPos = xInitialPos;
                yPos-=cellHeight;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
