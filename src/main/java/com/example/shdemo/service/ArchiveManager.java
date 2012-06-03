package com.example.shdemo.service;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.shdemo.domain.Archive;
import com.example.shdemo.domain.Resource;

@Component
@Transactional
public class ArchiveManager {

	@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public void addArchive(Archive archive) {
		archive.setId(null);
		sessionFactory.getCurrentSession().persist(archive);
	}

	public void deleteArchive(Archive archive) {
		archive = (Archive) sessionFactory.getCurrentSession().get(Archive.class,
				archive.getId());
		for (Resource resource : archive.getResources()) {
			resource.setAssigned(false);
			sessionFactory.getCurrentSession().update(resource);
		}
		sessionFactory.getCurrentSession().delete(archive);
	}
	
	public void deleteArchive1(Archive archive) {
		Archive tmpArchive = (Archive) sessionFactory.getCurrentSession().get(Archive.class,
				archive.getId());
		sessionFactory.getCurrentSession().delete(tmpArchive);
	}

	@SuppressWarnings("unchecked")
	public List<Archive> getAllArchives() {
		return sessionFactory.getCurrentSession().getNamedQuery("archive.all")
				.list();
	}

	public Archive findArchiveByName(String name) {
		return (Archive) sessionFactory.getCurrentSession().getNamedQuery("archive.byName").setString("name", name).uniqueResult();
	}
	
	public Archive findArchiveByTeamNumber(int teamNumber) {
		return (Archive) sessionFactory.getCurrentSession().getNamedQuery("archive.byTeamNumber").setInteger("teamNumber", teamNumber).uniqueResult();
	}
	
	public List<Resource> getResourcesOfArchive(Archive archive) {
		archive =(Archive) sessionFactory.getCurrentSession().get(Archive.class,
				archive.getId());
		// lazy loading here - try this code without this (shallow) copying
		List<Resource> resources = new ArrayList<Resource>(archive.getResources());
		return resources;
	}
	
	public void updateArchive(Long archiveId, Archive a) {
		Archive nA = new Archive(archiveId, a.getName(),
				 a.getTeamNumber(), a.getPhone());		
		sessionFactory.getCurrentSession().saveOrUpdate(nA);
	}

}
