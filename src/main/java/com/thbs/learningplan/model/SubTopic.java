package com.thbs.learningplan.model;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class SubTopic {

    @Id
    @GeneratedValue(generator = "sequence-generator-sub-topic")
    @GenericGenerator(name = "sequence-generator-sub-topic", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator", parameters = {
            @Parameter(name = "initial_value", value = "2000"),
            @Parameter(name = "increment_size", value = "1")
    })
    private Long subTopicId;

    private String subTopicName;

    @ManyToOne
    @JoinColumn(name = "topicId")
    private Topic topic;
}
