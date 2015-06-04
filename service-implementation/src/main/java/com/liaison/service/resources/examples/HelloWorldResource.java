/*
 * Copyright 2013 Netflix, Inc.
 *
 *      Licensed under the Apache License, Version 2.0 (the "License");
 *      you may not use this file except in compliance with the License.
 *      You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 *      Unless required by applicable law or agreed to in writing, software
 *      distributed under the License is distributed on an "AS IS" BASIS,
 *      WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *      See the License for the specific language governing permissions and
 *      limitations under the License.
 */

package com.liaison.service.resources.examples;

import com.wordnik.swagger.annotations.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * HelloWorldResource
 *
 * <P>
 * Simple HelloWorld REST service example
 *
 * <P>
 * For dynamically described endpoints, @see
 * com.liaison.framework.dynamic.DynamicServicesServlet
 *
 * @author Robert.Christian
 * @version 1.0
 */
@Api(value = "v1/hello", description = "hello world resource")
// swagger resource annotation
@Path("v1/hello")
public class HelloWorldResource {

	private static final Logger logger = LoggerFactory
			.getLogger(HelloWorldResource.class);

	//
	//
	// HACKING THIS!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	//
	//

	@ApiOperation(value = "hello to given name", notes = "this typically returns a string of greeting")
	@Path("/to/{name}")
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Response helloTo(
			@ApiParam(value = "name of the person who is to be greeted", required = true) @PathParam("name") String name) {

		try {

			// TODO make request to user info
			String userInfo = sendPost(name);

			JSONObject response = new JSONObject(userInfo);

			// response.put("User Info From Token: " + userInfo);

			return Response.ok(response.toString()).build();
		} catch (JSONException e) {

			logger.error("Error creating json response.", e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.build();
		}
	}

	private static final String POST_URL = "http://192.168.0.185:9000/api/getsignedinuser";

	private String sendPost(String authToken) {

		try {

			String JSON_STRING = "{\"oneTimeToken\" : \"{TOKEN}\"}".replace(
					"{TOKEN}", authToken);

			PostMethod postMethod = new PostMethod(POST_URL);
			postMethod.setRequestBody(JSON_STRING);

			// Must send "Content-Type:application/json"
			Header header = new Header("Content-Type", "application/json");
			
			postMethod.addRequestHeader(header);
			
			HttpClient httpClient = new HttpClient();

			int statusCode = httpClient.executeMethod(postMethod);

			String response = new String(postMethod.getResponseBody());

			postMethod.releaseConnection();

			return response;

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/*
	 * private String send_POST_OLD_VERSION_USING_APACHE_CLIENT4(String
	 * authToken) throws IOException {
	 * 
	 * CloseableHttpClient httpClient = HttpClients.createDefault(); HttpPost
	 * httpPost = new HttpPost(POST_URL); httpPost.addHeader("User-Agent",
	 * USER_AGENT);
	 * 
	 * String tokenString =
	 * "{\"oneTimeToken\" : \"{TOKEN}\"}".replace("{TOKEN}", authToken);
	 * StringEntity input = new StringEntity(tokenString);
	 * 
	 * input.setContentType("application/json"); postRequest.setEntity(input);
	 * 
	 * CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
	 * 
	 * System.out.println("POST Response Status:: " +
	 * httpResponse.getStatusLine().getStatusCode());
	 * 
	 * BufferedReader reader = new BufferedReader(new InputStreamReader(
	 * httpResponse.getEntity().getContent()));
	 * 
	 * String inputLine; StringBuffer response = new StringBuffer();
	 * 
	 * while ((inputLine = reader.readLine()) != null) {
	 * response.append(inputLine); } reader.close();
	 * 
	 * // print result return response.toString(); httpClient.close();
	 * 
	 * }
	 */

	@ApiOperation(value = "hello to the world", notes = "this returns a well known programming trope")
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Response hello() {
		JSONObject response = new JSONObject();
		try {
			response.put("Message", "Hello world!");
			return Response.ok(response.toString()).build();
		} catch (JSONException e) {

			logger.error("Error creating json response.", e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.build();
		}
	}

}
