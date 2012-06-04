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
	
	public Long addArchive(Archive archive) {
		archive.setId(null);
		return (Long) sessionFactory.getCurrentSession().save(archive);
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
	public int countArchive(int teamNumber) {
		List<Archive> listArchiveBy = sessionFactory.getCurrentSession().getNamedQuery("archive.byTeamNumber").setInteger("teamNumber", teamNumber).list();
		return listArchiveBy.size();
	}
	
	public List<Resource> getResourcesOfArchive(Archive archive) {
		archive =(Archive) sessionFactory.getCurrentSession().get(Archive.class,
				archive.getId());
		// lazy loading here - try this code without this (shallow) copying
		List<Resource> resources = new ArrayList<Resource>(archive.getResources());
		return resources;
	}
	
	public void updateArchive(Archive archive) {
		Archive tmpArchive = (Archive) sessionFactory.getCurrentSession().get(Archive.class,
				archive.getId());
		tmpArchive.setName(archive.getName());
		tmpArchive.setPhone(archive.getPhone());
		tmpArchive.setTeamNumber(archive.getTeamNumber());
		sessionFactory.getCurrentSession().saveOrUpdate(tmpArchive);
	}

}
