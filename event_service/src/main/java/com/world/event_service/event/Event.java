package com.world.event_service.event;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.world.event_service.event_file.EventFile;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Event {
    
    @Id
    @GeneratedValue
    private Integer id;
    private String title;
    private String description;
    private LocalDateTime time;
    private String location;
    
    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<EventFile> files;
    
    private List<Integer> participantIDs;


    public void add(EventFile eventFile){

        if(files == null)
        files = new ArrayList<>();

        files.add(eventFile);

        //eventFile.setEvent(this);
    }
}
