package org.gradle.main;

import org.neo4j.ogm.session.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.config.Neo4jConfiguration;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

@Configuration
@EnableNeo4jRepositories(basePackages = "org.gradle.dataBaseRepositories")
public class ApplicationConfig extends Neo4jConfiguration{

//	public ApplicationConfig() {
//		setBasePackage("org.gradle.domain");
//	}
//
//	@Bean
//	GraphDatabaseService graphDatabaseService() {
//		return new GraphDatabaseFactory().newEmbeddedDatabase("/scratch/c_oual01/workspace_for_sts/MasterNode/newNeo4j.db");
//	}

	@Bean
	public org.neo4j.ogm.config.Configuration getConfiguration() {
		org.neo4j.ogm.config.Configuration config = new org.neo4j.ogm.config.Configuration();
		config.driverConfiguration().setDriverClassName("org.neo4j.ogm.drivers.http.driver.HttpDriver")
				.setURI("http://neo4j:password@localhost:7474");
		return config;
	}

	@Bean
	public SessionFactory getSessionFactory() {
		return new SessionFactory(getConfiguration(), "org.gradle.domain");
	}
}
