package org.example.client;

import org.example.entities.*;

import java.util.List;

public class ComplaintEvent {
    private List<Complaint> complaintList;

    public ComplaintEvent(List<Complaint> complaintList) {
        this.complaintList = complaintList;
    }

    public List<Complaint> getComplaintList() {
        return complaintList;
    }
}