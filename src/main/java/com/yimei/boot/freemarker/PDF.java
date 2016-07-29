package com.yimei.boot.freemarker;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.BaseFont;
import org.pegdown.PegDownProcessor;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by joe on 1/11/15.
 */
public class PDF {

    public static PegDownProcessor processor = new PegDownProcessor();

    public static String markdownToHtml(String markdown) {
        /**
         * A clean and lightweight Markdown-to-HTML filter based on a PEG parser implemented with parboiled.
         * Note: A PegDownProcessor is not thread-safe (since it internally reused the parboiled parser instance).
         * If you need to process markdown source in parallel create one PegDownProcessor per thread!
         */
        return processor.markdownToHtml(markdown);
    }

    /**
     *
      * @param markdown  markdown字符串
     * @return
     * @throws IOException
     * @throws DocumentException
     */
    public static File create(String markdown) throws IOException, DocumentException {
        String html = "<html><head><style> body {font-family: SimSun;page-break-inside: avoid;}</style></head><body>" +
                markdownToHtml(markdown) +
                "</body></html>";
        ITextRenderer renderer = new ITextRenderer();
        renderer.getFontResolver().addFont("simsun.ttc", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
        renderer.setDocumentFromString(html);
        renderer.layout();
        File file = File.createTempFile("pdf", "pdf");
        renderer.createPDF(new FileOutputStream(file));
        return file;
    }

    /**
     *
     * @param html  html字符串
     * @return
     * @throws IOException
     * @throws DocumentException
     */
    public static File createByHtml(String html) throws IOException, DocumentException {
        ITextRenderer renderer = new ITextRenderer();
        renderer.getFontResolver().addFont("simsun.ttc", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
        renderer.setDocumentFromString(html);
        renderer.layout();
        File file = File.createTempFile("pdf", "pdf");
        renderer.createPDF(new FileOutputStream(file));
        return file;
    }

}
