package com.example.demo.Service;

import com.example.demo.Model.Staff;
import com.example.demo.Repository.StaffRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class StaffService {
    @Autowired
    StaffRepo staffRepo;

    public List<Staff> fetchAll(){
        return staffRepo.fetchAll();
    }
    public Staff addStaff(Staff s){
        return staffRepo.addStaff(s);
    }
    public Staff findStaffByID(int staff_id){
        return staffRepo.findStaffByID(staff_id);
    }
    public Boolean deleteStaff(int staff_id){
        return staffRepo.deleteStaff(staff_id);
    }
    public Staff updateStaff(int staff_id, Staff s){
        return staffRepo.updateStaff(staff_id, s);
    }
}
