package com.thbs.learningplan.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;

import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name ="sub_topic")
public class SubTopic {
    private Long subTopicId;

    @ManyToOne
    @JoinColumn(name="topicId")
    private Topic topic;
}  
