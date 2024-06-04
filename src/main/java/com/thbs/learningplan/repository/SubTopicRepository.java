package com.thbs.learningplan.repository;

import com.thbs.learningplan.model.SubTopic;
import com.thbs.learningplan.model.Topic;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubTopicRepository extends JpaRepository<SubTopic, Long> {

    List<SubTopic> findByTopic(Topic topic);

    boolean existsBySubTopicNameAndTopic(String subTopicName, Topic topic);

}
