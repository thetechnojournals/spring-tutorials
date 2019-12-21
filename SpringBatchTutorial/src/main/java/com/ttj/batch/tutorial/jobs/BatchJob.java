package com.ttj.batch.tutorial.jobs;

import com.ttj.app.domain.Language;
import com.ttj.app.domain.Word;
import com.ttj.app.repository.WordRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
public class BatchJob {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private WordRepository wordRepository;

    @Bean
    public Job importWordsJob(Step importStep, Step totalCountStep) {
        return jobBuilderFactory.get("importWordsJob")
                .incrementer(new RunIdIncrementer())
                .flow(importStep)
                .next(totalCountStep)
                .end()
                .build();
    }

    @Bean
    public Step importStep(ItemWriter<List<Word>> writer, PlatformTransactionManager appTransactionManager) {
        return stepBuilderFactory.get("importStep")
                .transactionManager(appTransactionManager)
                .<String, List<Word>> chunk(100)
                .reader(reader())
                .processor(processor())
                .writer(writer)
                .build();
    }

    @Bean
    public FlatFileItemReader<String> reader() {
        return new FlatFileItemReaderBuilder<String>()
                .name("fileReader")
                .resource(new ClassPathResource("words.txt"))
                .lineMapper(new LineMapper<String>() {
                    @Override
                    public String mapLine(String s, int i) throws Exception {
                        return s;
                    }
                })
                .build();
    }

    @Bean
    public ItemProcessor<String, List<Word>> processor() {
        return new ItemProcessor<String, List<Word>>(){
            @Override
            public List<Word> process(String s) throws Exception {
                if(s!=null && s.length()>0){
                    String[] arr = s.split("[\\s,=\\.*]");
                    if(arr!=null && arr.length>0){
                        List<Word> list = new ArrayList<>();
                        for (int i=0;i<arr.length;i++){
                            if(arr[i]!=null && arr[i].length()>0)
                                list.add(new Word(arr[i], Language.EN));
                        }
                        return list;
                    }
                }
                return null;
            }
        };
    }

    @Bean
    public ItemWriter<List<Word>> writer(@Qualifier("appEntityManagerFactory") EntityManagerFactory appEntityManagerFactory) {
        ItemWriter<List<Word>> writer = new ItemWriter<List<Word>>(){
            @Override
            @Transactional
            public void write(List<? extends List<Word>> items) {
                items.forEach(item->{
                        wordRepository.saveAll(item);
                });
            }
        };
        return writer;
    }
    @Bean
    public Step totalCountStep(){
        return stepBuilderFactory.get("totalCountStep")
                .tasklet(new Tasklet() {

                    @Override
                    public RepeatStatus execute(StepContribution contribution,
                                                ChunkContext chunkContext) throws Exception {

                        System.out.println("Total word count: "+wordRepository.count());
                        return RepeatStatus.FINISHED;
                    }
                }).build();
    }
}
