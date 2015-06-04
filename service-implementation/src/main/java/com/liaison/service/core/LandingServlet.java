package com.liaison.service.core;


//Import required java libraries
import java.io.*;
import java.util.logging.Logger;

import javax.servlet.*;
import javax.servlet.http.*;

public class LandingServlet extends HttpServlet {

	private static final long serialVersionUID = -7052929245100745107L;
	private static Logger LOGGER = Logger.getLogger("Registration");

	public void init() throws ServletException {

	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		LOGGER.fine("Processing default landing request...");

		/*
		// GET POST to postData
		InputStream is = request.getInputStream();
		String postData = ReadStreamIntoStringUsingReader.read(is);
		try {
			is.close();
		} catch (Exception e) {
			LOGGER.severe("Exception reading message body:  " + e.getLocalizedMessage());
		}

		// THIS IS THE PERSISTENCE (relying on availability of logs)
		LOGGER.severe("Registered:  " + postData);

		// Set response content type
		response.setContentType("text/html");

		// Actual logic goes here.
		PrintWriter out = response.getWriter();
		out.println("Registered " + postData);

*/
		
		
		String redirectString = " <html xmlns=\"http://www.w3.org/1999/xhtml\">    "
				+ "<head>       <title>SSO-HACK</title>       <meta http-equiv=\"refresh\" content=\"0;URL='http://192.168.0.185:8080/login?app=5ddc1f24-7d2c-4933-9cd4-634ebcf7c8cc\'\" />  "
				+ " </head>    <body> "
  + "  <p>Redirecting to SSO login in 3 seconds <a href=\"http://192.168.0.185:8080/login?app=5ddc1f24-7d2c-4933-9cd4-634ebcf7c8cc\">...</a>.</p> "
 + " </body> </html>     ";
		
		PrintWriter out = response.getWriter();
		out.println(redirectString);

	}

	public void destroy() {
		// do nothing.
	}
}