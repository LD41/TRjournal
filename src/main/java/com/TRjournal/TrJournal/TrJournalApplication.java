package com.TRjournal.TrJournal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.TRjournal.TrJournal.Repository")
@EntityScan(basePackages = "com.TRjournal.TrJournal.model")
public class TrJournalApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrJournalApplication.class, args);
	}

}
