package com.bpshparis.wa;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.type.TypeReference;

/**
 * Servlet implementation class AppendSelectionsServlet
 */
@WebServlet(name = "GetHoraires", urlPatterns = { "/GetHoraires", "/getHoraires", "/gh" })
public class GetHorairesServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetHorairesServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Map<String, Object> result = new HashMap<String, Object>();

		try {
			
			result.put("CLIENT", request.getRemoteAddr() + ":" + request.getRemotePort());
			result.put("SERVER", request.getLocalAddr() + ":" + request.getLocalPort());
			
			result.put("FROM", this.getServletName());
			
			// String user = request.getUserPrincipal().getName();
			// result.put("USER", user);

			result.put("JSESSIONID", request.getSession().getId());
			
			Path context = Paths.get(getServletContext().getRealPath("/"));			
			result.put("CTX", context.toString());
			
			// Map<String, Object> parms = Tools.fromJSON(request.getInputStream());
            
            Path path = Paths.get(context + "/res/horaires.json");

			if(Files.exists(path)) {

                @SuppressWarnings("unchecked")
                List<Horaire>  horaires = (List<Horaire>) Tools.fromJSON(path.toFile(), new TypeReference<List<Horaire>>(){});

				result.put("DATAS", horaires);
				result.put("STATUS", "OK");
			}
			else {
				result.put("STATUS", "KO");
				result.put("ERROR", "Input parameters are not valid.");
				throw new Exception();
			}			
		}
		
		catch (Exception e) {
			// TODO Auto-generated catch block
			result.put("STATUS", "KO");
			result.put("EXCEPTION", e.getClass().getName());
			result.put("MESSAGE", e.getMessage());
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			result.put("STACKTRACE", sw.toString());
			e.printStackTrace(System.err);
		}

		finally{
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(Tools.toJSON(result));
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}