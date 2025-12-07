package com.world.event_service.event_file;

import com.world.event_service.event.Event;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class EventFile {

    @Id
    @GeneratedValue
    private Integer id;
    private String fileName;
    private String filePath;
    private String contentType;

    @ManyToOne
    private Event event;

}
