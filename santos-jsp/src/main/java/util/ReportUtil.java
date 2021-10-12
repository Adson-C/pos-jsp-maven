package util;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import jakarta.servlet.ServletContext;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@SuppressWarnings({"rawtypes", "unchecked"})
public class ReportUtil implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public byte[] geraRelatorioPDF( List listaDados, String nomeRelatorio, ServletContext servletContext) throws Exception {
		
		/*Cria a lista de dados que vem do nosso SQL da consulta feita*/
		JRBeanCollectionDataSource jrB = new JRBeanCollectionDataSource(listaDados); 
		
		String caminhoJasper = servletContext.getRealPath("relatorio") + File.separator + nomeRelatorio + ".jasper";
		
		
		JasperPrint impressoraJasper = JasperFillManager.fillReport(caminhoJasper, new HashMap(), jrB);
		
		return JasperExportManager.exportReportToPdf(impressoraJasper);
	}

}
