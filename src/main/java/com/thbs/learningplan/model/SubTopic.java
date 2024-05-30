package com.thbs.learningplan.model;

import jakarta.persistence.Entity;
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
    private Long subTopicId;

    @ManyToOne
    @JoinColumn(name = "topicId")
    private Topic topic;
}
