package com.example.shdemo.service;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.shdemo.domain.Archive;
import com.example.shdemo.domain.Car;
import com.example.shdemo.domain.Resource;

@Component
@Transactional
public class ResourceManager {

	AssigningManager assigningManager;
	ArchiveManager archiveManager;
	@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public Long addResource(Resource resource) {
		resource.setId(null);
		return (Long) sessionFactory.getCurrentSession().save(resource);
	}
	@SuppressWarnings("unused")
	public Archive deleteResourceFromList(List<Archive> listArchive, long id1){
		Archive archiveOwner = new Archive();
		Resource toDelete = new Resource();
		for(Archive a : listArchive){
				List<Resource> listResource = a.getResources();
				for(Resource r : listResource){
					if(r.getId() == id1){
						archiveOwner = a;
						toDelete = r ;
					}
				}
		}
		if (toDelete != null){
			archiveOwner.getResources().remove(toDelete);
			return archiveOwner;
		}
		return null;
	}
	public void deleteResource(Resource resource) {
		Resource tmpResource = (Resource) sessionFactory.getCurrentSession().get(Resource.class,
				resource.getId());
		sessionFactory.getCurrentSession().delete(tmpResource);
	}

	@SuppressWarnings("unchecked")
	public List<Resource> getAllResources(){
		return sessionFactory.getCurrentSession().getNamedQuery("resource.notAssigned").list();
	}

	public void updateResource(Resource resource) {		
		Resource tmpResource = (Resource) sessionFactory.getCurrentSession().get(Resource.class,
				resource.getId());
			tmpResource.setName(resource.getName());
			tmpResource.setAuthor(resource.getAuthor());
			tmpResource.setIntroductionDate(resource.getIntroductionDate());
			tmpResource.setIsbn(resource.getIsbn());
			tmpResource.setAssigned(resource.getAssigned());
			sessionFactory.getCurrentSession().saveOrUpdate(tmpResource);
	}
	
	public Resource findResourceByIsbn(int isbn) {
		return (Resource) sessionFactory.getCurrentSession().getNamedQuery("resource.byIsbn").setInteger("isbn", isbn).uniqueResult();
	}
	
	public Resource findResourceById(Long id) {
		return (Resource) sessionFactory.getCurrentSession().get(Resource.class, id);
	}
	
	public int countResource(int isbn) {
		List<Resource> listResourceBy = sessionFactory.getCurrentSession().getNamedQuery("resource.byIsbn").setInteger("isbn", isbn).list();
		return listResourceBy.size();
	}
}
