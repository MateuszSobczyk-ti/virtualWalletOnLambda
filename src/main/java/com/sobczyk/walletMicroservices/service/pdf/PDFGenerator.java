package com.sobczyk.walletMicroservices.service.pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public abstract class PDFGenerator<T> {

    public byte[] generatePdf(T resources) {
        Document document = new Document();
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            PdfWriter.getInstance(document, outputStream);
            document.open();
            addTitlePage(document);
            addContent(document, resources);
            document.close();
            return outputStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public abstract void addTitlePage(Document document) throws DocumentException;
    public abstract void addContent(Document document, T resources) throws DocumentException, IOException;

}
