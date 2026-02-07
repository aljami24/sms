package com.smha.sms.report;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.List;
import java.util.Map;


@Component
public class PdfGenerate {

    public byte[] generatePdf(String jrxmlPath, Map<String, Object> params, List<?> data
    ) throws Exception {

        InputStream stream =
                new ClassPathResource(jrxmlPath).getInputStream();

        JasperReport report =
                JasperCompileManager.compileReport(stream);

        JRBeanCollectionDataSource dataSource =
                new JRBeanCollectionDataSource(data);

        JasperPrint print =
                JasperFillManager.fillReport(report, params, dataSource);

        return JasperExportManager.exportReportToPdf(print);
    }

}
