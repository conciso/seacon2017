package com.github.conciso.conferences.seacon2017.demo;

import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;

@Path("/blue")
@ApplicationScoped
public class BlueRessource {

  private static final Logger LOG = Logger.getAnonymousLogger();

  @Context
  SecurityContext sc;

  @GET
  @Produces("text/plain")
  public String get() {
    LOG.info("blue");
    return "blue";
  }

}
