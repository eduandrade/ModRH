package br.com.splgenerator.web.servlets.monitoring;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.splgenerator.web.servlets.monitoring.xml.ModRhStatsXml;

@WebServlet("/monitoring")
public class MonitoringServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
    public MonitoringServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//IModRhStats stats = new ModRhStatsTxt();
		IModRhStats stats = new ModRhStatsXml();
		response.getWriter().println(stats.printStats());
	}

}
