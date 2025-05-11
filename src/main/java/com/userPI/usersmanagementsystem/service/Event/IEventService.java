package com.userPI.usersmanagementsystem.service.Event;




import com.userPI.usersmanagementsystem.dto.EventDTO;
import com.userPI.usersmanagementsystem.entity.Event;

import java.util.List;

public interface IEventService {
    EventDTO addEvent(EventDTO eventDTO);
    public EventDTO updateEvent(Long id, EventDTO eventDTO);
    public void deleteEvent(Long id);
    public List<Event> getUpcomingEvents();

    List<EventDTO> getReservationsByUserId(Long userId);


}
