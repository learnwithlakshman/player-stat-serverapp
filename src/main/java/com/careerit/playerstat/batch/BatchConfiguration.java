package com.careerit.playerstat.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.data.builder.MongoItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.careerit.playerstat.domain.Player;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	@Bean
	public FlatFileItemReader<PlayerDTO> reader() {
		return new FlatFileItemReaderBuilder<PlayerDTO>().name("playerItemReader")
				.resource(new ClassPathResource("ipl_2021_data.csv")).delimited()
				.names("name,role,country,team,price".split(","))
				.fieldSetMapper(new BeanWrapperFieldSetMapper<PlayerDTO>() {
					{
						setTargetType(PlayerDTO.class);
					}
				}).build();
	}

	@Bean
	public MongoItemWriter<Player> writer(MongoTemplate mongoTemplate) {
		return new MongoItemWriterBuilder<Player>().template(mongoTemplate).collection("player").build();
	}

	@Bean
	public PlayerProcessor processor() {
		return new PlayerProcessor();
	}

	@Bean
	public Step step1(FlatFileItemReader<PlayerDTO> itemReader, MongoItemWriter<Player> itemWriter) throws Exception {

		return this.stepBuilderFactory
				.get("step1").<PlayerDTO,Player>chunk(20).reader(itemReader).processor(processor())
				.writer(itemWriter).build();
	}

	@Bean
	public Job updateUserJob(JobCompletionNotificationListener listener, Step step1) throws Exception {

		return this.jobBuilderFactory.get("updateUserJob").incrementer(new RunIdIncrementer()).listener(listener)
				.start(step1).build();
	}

	
}