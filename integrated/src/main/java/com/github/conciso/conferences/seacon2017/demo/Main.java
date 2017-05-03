package com.github.conciso.conferences.seacon2017.demo;

import java.net.URL;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.wildfly.swarm.Swarm;
import org.wildfly.swarm.jaxrs.JAXRSArchive;

public class Main {

  public static void main(String[] args) throws Exception {

    ClassLoader cl = Main.class.getClassLoader();
    URL stageConfig = cl.getResource("project-defaults.yml");
    Swarm swarm = new Swarm(false)
      .withConfig(stageConfig);

    JAXRSArchive deployment = ShrinkWrap.create(JAXRSArchive.class, "seacon2017-demo.war");
    deployment
        .addResource(YellowRessource.class)
        .addResource(RedRessource.class)
        .addResource(GreenRessource.class)
        .addResource(BlueRessource.class);
    deployment.addAllDependencies();

    swarm.start();
    swarm.deploy(deployment);
  }

}
