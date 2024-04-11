package com.rb.passin.service;

import com.rb.passin.domain.attendee.Attendee;
import com.rb.passin.domain.checkin.CheckIn;
import com.rb.passin.domain.checkin.exceptions.CheckInAlreadyRegister;
import com.rb.passin.repositories.CheckinRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CheckInService {

    private final CheckinRepository checkinRepository;

    public void registerCheckin (Attendee attendee){
        this.verifyIfCheckInExists(attendee.getId());

        CheckIn checkin = new CheckIn();
        checkin.setAttendee(attendee);
        checkin.setCreatedAt(LocalDateTime.now());
        checkinRepository.save(checkin);
    }

    private void verifyIfCheckInExists(String attendeeId) {
        this.getCheckin(attendeeId).orElseThrow(() -> new CheckInAlreadyRegister("Checkin already exists"));
    }

    public Optional<CheckIn> getCheckin(String attendeeId){
        return this.checkinRepository.findByAttendeeId(attendeeId);
    }
}
