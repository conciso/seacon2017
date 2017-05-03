package com.github.conciso.conferences.seacon2017.demo;

import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;

import java.io.IOException;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

@Path("/yellow")
@ApplicationScoped
public class YellowRessource {

  private static final Logger LOG = Logger.getAnonymousLogger();

  @Context
  SecurityContext sc;

  @HeaderParam(AUTHORIZATION)
  String authHeader;

  @GET
  @Produces("text/plain")
  public String get() throws IOException {

    String red;
    try(CloseableHttpClient httpclient = HttpClients.createDefault()) {
      HttpGet get = new HttpGet("http://integrated:8080/red");
      get.addHeader(AUTHORIZATION, authHeader);
      try(CloseableHttpResponse response = httpclient.execute(get)) {
        red = EntityUtils.toString(response.getEntity());
      }
    }
    LOG.info("yellow");
    return "yellow " + red;
  }

}
