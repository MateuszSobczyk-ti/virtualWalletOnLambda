package com.sobczyk.walletMicroservices.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.sobczyk.walletMicroservices.dto.PositionPerfValue;
import com.sobczyk.walletMicroservices.dto.PositionPerformanceDto;
import com.sobczyk.walletMicroservices.dto.responses.FinancialNewsResponse;
import com.sobczyk.walletMicroservices.dto.responses.PositionPerformanceResponse;
import com.sobczyk.walletMicroservices.dto.responses.TransactionResponse;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@Service
public class PerformancePDFGenerator {
    public void generatePdf(PositionPerformanceResponse response, String outputPath) {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(outputPath));

            document.open();
            addTitlePage(document);
            addPositionsTable(document, response.getCurrentPositions());
            addSummary(document, response.getOverall(), response.getCash());
            addTransactions(document, response.getTransactions());
            addLinkedImage(document, response.getPositionsNews());
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addTitlePage(Document document) throws DocumentException
    {
        Paragraph title = new Paragraph("Portfolio Performance Report");
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);
    }

    private void addPositionsTable(Document document, List<PositionPerformanceDto> positions) throws DocumentException {
        document.add(Chunk.NEWLINE);
        document.add(new Paragraph("Current positions:"));
        PdfPTable table = new PdfPTable(5);
        table.addCell("Date");
        table.addCell("Ticker");
        table.addCell("Quantity");
        table.addCell("Invested value");
        table.addCell("Market value");

        for (PositionPerformanceDto dto : positions) {
            table.addCell(dto.getDate().toString());
            table.addCell(dto.getTicker());
            table.addCell(dto.getQuantity().toString());
            table.addCell(dto.getInvestedValue().toString());
            table.addCell(dto.getMarketValue().toString());
        }
        document.add(table);
    }

    private void addSummary(Document document, PositionPerfValue overall, PositionPerfValue cash) throws DocumentException {
        document.add(Chunk.NEWLINE);
        document.add(new Paragraph("Summary:"));

        this.addLine(document, "Market value", overall.getMarketValue());
        this.addLine(document, "Invested Value", overall.getInvestedValue());
        this.addLine(document, "Available Cash", cash.getMarketValue());
        this.addLine(document, "TWR (Time Weighted Return)", overall.getTWR());
    }

    private void addLine(Document document, String label, BigDecimal value) throws DocumentException {
        Paragraph line = new Paragraph(" - " + label + ": ");
        Phrase phrase = new Phrase(String.valueOf(value));
        line.add(phrase);
        document.add(line);
    }

    private void addTransactions(Document document, List<TransactionResponse> transactions) throws DocumentException {
        document.add(Chunk.NEWLINE);
        document.add(new Paragraph("Last transactions:"));
        PdfPTable table = new PdfPTable(6);
        table.addCell("Ticker");
        table.addCell("Asset type");
        table.addCell("Transaction type");
        table.addCell("Quantity");
        table.addCell("price");
        table.addCell("date");

        for (TransactionResponse dto : transactions) {
            table.addCell(dto.ticker());
            table.addCell(dto.assetType());
            table.addCell(dto.transactionType());
            table.addCell(dto.quantity().toString());
            table.addCell(dto.price().toString());
            table.addCell(dto.date().toString());
        }
        document.add(table);
    }

    private void addLinkedImage(Document document, List<FinancialNewsResponse> newsResponses) throws DocumentException, IOException {
        for (FinancialNewsResponse news : newsResponses) {
            Paragraph paragraph = new Paragraph();
            Image image = Image.getInstance(news.imageUrl());
            paragraph.add(image);
            image.scaleToFit(150, 120);
            image.setAlignment(Image.ALIGN_CENTER);
            Anchor anchor = new Anchor(news.title());
            anchor.setReference(news.articleUrl());
            paragraph.add(anchor);
            paragraph.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(paragraph);
        }
    }

}
