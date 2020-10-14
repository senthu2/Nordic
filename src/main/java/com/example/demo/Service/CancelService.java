package com.example.demo.Service;

import com.example.demo.Model.Cancel;
import com.example.demo.Model.Rental;
import com.example.demo.Repository.CancelRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CancelService {
    @Autowired
    CancelRepo cancelRepo;
    public List<Cancel> fetchAll()
    {
        return cancelRepo.fetchAll();
    }

    public Cancel createCancel(int rental_id, Cancel ca, Rental rental)
    {
        return cancelRepo.createCancel(rental_id, ca, rental);
    }

    public Cancel findCancelById(int cancel_id)
    {
        return cancelRepo.findCancelById(cancel_id);
    }

    public Boolean deleteCancel(int cancel_id)
    {
        return cancelRepo.deleteCancel(cancel_id);
    }
}
