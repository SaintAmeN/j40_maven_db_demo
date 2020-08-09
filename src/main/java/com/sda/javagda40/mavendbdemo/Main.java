package com.sda.javagda40.mavendbdemo;

import com.sda.javagda40.mavendbdemo.database.EntityDao;
import com.sda.javagda40.mavendbdemo.database.HibernateUtil;
import com.sda.javagda40.mavendbdemo.model.AppUser;

public class Main {
    public static void main(String[] args) {
        System.out.println("Initial version.");
        HibernateUtil.getOurSessionFactory();
        System.out.println("Tested hibernate.");

        EntityDao<AppUser> appUserEntityDao = new EntityDao<>();
        appUserEntityDao.saveOrUpdate(new AppUser("Marian", "Paździoch"));
    }
}
