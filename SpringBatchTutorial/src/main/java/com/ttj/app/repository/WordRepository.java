package com.ttj.app.repository;

import com.ttj.app.domain.Word;
import org.springframework.data.repository.CrudRepository;

public interface WordRepository  extends CrudRepository<Word, Long> {

}
