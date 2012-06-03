package com.example.shdemo.service;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.shdemo.domain.Archive;
import com.example.shdemo.domain.Resource;

@Component
@Transactional
public class AssigningManager {

	@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void assignResource(Long archiveId, Long resourceId) {
		Archive archive = (Archive) sessionFactory.getCurrentSession().get(
				Archive.class, archiveId);
		Resource resource = (Resource) sessionFactory.getCurrentSession()
				.get(Resource.class, resourceId);
		resource.setAssigned(true);

		archive.getResources().add(resource);
	}
	
	@SuppressWarnings("unchecked")
	public List<Resource> getObtainableResources() {
		return sessionFactory.getCurrentSession().getNamedQuery("resource.notAssigned")
				.list();
	}

	public void disposeResource(Archive archive, Resource resource) {

		archive = (Archive) sessionFactory.getCurrentSession().get(
				Archive.class, archive.getId());
		resource = (Resource) sessionFactory.getCurrentSession()
				.get(Resource.class, resource.getId());
		Resource toDelete = null;
		// lazy loading here (person.getCars)
		for (Resource r : archive.getResources())
			if (r.getId().compareTo(resource.getId()) == 0) {
				toDelete = r;
				break;
			}

		if (toDelete != null)
			archive.getResources().remove(toDelete);

		resource.setAssigned(false);
	}
	
	public Resource findResourceById(Long id) {
		return (Resource) sessionFactory.getCurrentSession().get(Resource.class, id);
	}
}
