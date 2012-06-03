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
		sessionFactory.getCurrentSession().saveOrUpdate(tmpResource);
	}
	
	public Resource findResourceByIsbn(int isbn) {
		return (Resource) sessionFactory.getCurrentSession().getNamedQuery("resource.byIsbn").setInteger("isbn", isbn).uniqueResult();
	}
	
	public Resource findResourceById(Long id) {
		return (Resource) sessionFactory.getCurrentSession().get(Resource.class, id);
	}
}
