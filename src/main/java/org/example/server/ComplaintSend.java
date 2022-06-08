package org.example.server;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import  org.example.entities.*;

public class ComplaintSend implements Job{

    private static Session session;

    private static SessionFactory getSessionFactory() throws HibernateException {
        org.hibernate.cfg.Configuration configuration = new Configuration();

        configuration.addAnnotatedClass(Complaint.class);
        ServiceRegistry serviceRegistry = new
                StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties())
                .build();

        return configuration.buildSessionFactory(serviceRegistry);
    }

    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("executing.....");
        ArrayList<Complaint> complaintList= getComplaint();//ArrayList<Complaint> getComplaint()
        for(Complaint cm:complaintList){
            LocalDate ld=LocalDate.now();
            LocalTime lt=LocalTime.now();
            if((cm.getDate().plusDays(1).equals(ld)||ld.isAfter(cm.getDate()))&&(lt.isAfter(cm.getSendTime())||lt.equals(cm.getSendTime()))&&cm.isStatus().equals("Not answered")){

                cm.setStatus("Refund");
                UpdateComplaint(cm);
            }
        }
    }
    public static ArrayList<Complaint> getComplaint() {
        System.out.println("getting all the complaints");
        SessionFactory sessionFactory = getSessionFactory();
        session = sessionFactory.openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Complaint> query = builder.createQuery(Complaint.class);
        query.from(Complaint.class);
        System.out.println(LocalDate.now());
        ArrayList<Complaint> data = (ArrayList<Complaint>) session.createQuery(query).getResultList();
        ArrayList<Complaint> wantedData = new ArrayList<>();
        for (Complaint cm : data) {
            if (cm.getDate().equals(LocalDate.now().minusDays(1)))
                wantedData.add(cm);
        }
        System.out.println(wantedData.size());
        if (session != null) {
            session.close();
        }
        return wantedData;

    }
    public static void UpdateComplaint(Complaint cm) {
        SessionFactory sessionFactory = getSessionFactory();
        session = sessionFactory.openSession();
        session.beginTransaction();
        session.update(cm);
        session.flush();
        session.getTransaction().commit();
        System.out.println("updating the complaint is sent attribute");
        if (session != null) {
            session.close();
        }
    }

}