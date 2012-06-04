package com.example.shdemo.service;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.example.shdemo.domain.Archive;
import com.example.shdemo.domain.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/beans.xml" })
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@Transactional
public class ResourceManagerTest {

	@Autowired
	ResourceManager resourceManager;
	@Autowired
	ArchiveManager archiveManager;
	@Autowired
	AssigningManager assigningManager;
	
	private final static String NAME_1 = "Księgi ziemskie";
	private final static String AUTHOR_1 = "Przasnysiensia";
	private final static int ISBN_1 = 43;
	private final static Date DATE_1 = new Date();
	
	private final static String NAME_2 = "Akta malborskie";
	private final static String AUTHOR_2 = "Kanclerz Namiestnik";
	private final static int ISBN_2 = 67;
	private final static Date DATE_2 = new Date();
	
	private final static String NAME_3 = "Odczyty staromodne";
	private final static String AUTHOR_3 = "Kowalska";
	private final static int ISBN_3 = 89;
	private final static int ISBN_4 = 54;
	
	private final static String NAMEARCHIVE_1 = "Centrum";
	private final static int TEAMNUMBERARCHIVE_1 = 189;
	private final static String PHONEARCHIVE_1 = "(52) 339-54-01";
	
	private final static String NAMEARCHIVE_2 = "Oddział";
	private final static int TEAMNUMBERARCHIVE_2 = 178;
	private final static String PHONEARCHIVE_2 = "(78) 888-99-45";
	private final static int TEAMNUMBERARCHIVE_3 = 100;

	//@Test
	public void addResourceCheck() {

		Resource resource = new Resource();
		resource.setName(NAME_1);
		resource.setAuthor(AUTHOR_1);
		resource.setIsbn(ISBN_1);
		resource.setIntroductionDate(DATE_1);
		resource.setAssigned(false);

		Long resourceId = resourceManager.addResource(resource);

		Resource retrievedResource = resourceManager.findResourceById(resourceId);
		assertEquals(NAME_1, retrievedResource.getName());
		assertEquals(AUTHOR_1, retrievedResource.getAuthor());
		assertEquals(ISBN_1, retrievedResource.getIsbn());
		assertEquals(DATE_1, retrievedResource.getIntroductionDate());
		assertEquals(false, retrievedResource.getAssigned());
	}
	
	//@Test
	public void deleteResourceCheck() {

		List<Resource> retrievedResources = resourceManager.getAllResources();

		for (Resource resource : retrievedResources) {
			if (resource.getIsbn() == ISBN_1 || resource.getIsbn() == ISBN_2) {
				resourceManager.deleteResource(resource);
			}
		}

		Archive archive1 = new Archive();
		archive1.setName(NAMEARCHIVE_1);
		archive1.setTeamNumber(TEAMNUMBERARCHIVE_1);
		archive1.setPhone(PHONEARCHIVE_1);
		
		Long archiveId1 = archiveManager.addArchive(archive1);
		
		Archive retrievedArchive1 = archiveManager.findArchiveByTeamNumber(TEAMNUMBERARCHIVE_1);
		
		assertEquals(NAMEARCHIVE_1, retrievedArchive1.getName());
		assertEquals(TEAMNUMBERARCHIVE_1, retrievedArchive1.getTeamNumber());
		assertEquals(PHONEARCHIVE_1, retrievedArchive1.getPhone());
		
		Resource resource1 = new Resource();
		resource1.setName(NAME_1);
		resource1.setAuthor(AUTHOR_1);
		resource1.setIsbn(ISBN_1);
		resource1.setIntroductionDate(DATE_1);
		resource1.setAssigned(false);

		Resource resource2 = new Resource();
		resource2.setName(NAME_2);
		resource2.setAuthor(AUTHOR_2);
		resource2.setIsbn(ISBN_2);
		resource2.setIntroductionDate(DATE_2);
		resource2.setAssigned(false);
		
		Long id1 = resourceManager.addResource(resource1);
		Long id2 = resourceManager.addResource(resource2);
		
		Resource retrievedResource1 = resourceManager.findResourceById(id1);
		Resource retrievedResource2 = resourceManager.findResourceById(id2);
		assertEquals(NAME_1, retrievedResource1.getName());
		assertEquals(AUTHOR_1, retrievedResource1.getAuthor());
		assertEquals(ISBN_1, retrievedResource1.getIsbn());
		assertEquals(DATE_1, retrievedResource1.getIntroductionDate());
		assertEquals(false, retrievedResource1.getAssigned());
		
		assertEquals(NAME_2, retrievedResource2.getName());
		assertEquals(AUTHOR_2, retrievedResource2.getAuthor());
		assertEquals(ISBN_2, retrievedResource2.getIsbn());
		assertEquals(DATE_2, retrievedResource2.getIntroductionDate());
		assertEquals(false, retrievedResource2.getAssigned());

		assigningManager.assignResource(archiveId1, id1);
		List<Archive> listArchive = archiveManager.getAllArchives();
		resourceManager.deleteResourceFromList(listArchive, id1);
		resourceManager.deleteResource(resource1);
		int count1 = resourceManager.countResource(ISBN_1);
		
		resourceManager.deleteResourceFromList(listArchive, id2);
		resourceManager.deleteResource(resource2);
		int count2 = resourceManager.countResource(ISBN_2);
		
		assertEquals(0, count1);
		assertEquals(0, count2);
	}
	@Test
	public void updateResourceCheck() {

		List<Resource> retrievedResources = resourceManager.getAllResources();

		for (Resource resource : retrievedResources) {
			if (resource.getIsbn() == ISBN_1 || resource.getIsbn() == ISBN_2) {
				resourceManager.deleteResource(resource);
			}
		}

		Archive archive1 = new Archive();
		archive1.setName(NAMEARCHIVE_1);
		archive1.setTeamNumber(TEAMNUMBERARCHIVE_1);
		archive1.setPhone(PHONEARCHIVE_1);

		Archive archive2 = new Archive();
		archive2.setName(NAMEARCHIVE_2);
		archive2.setTeamNumber(TEAMNUMBERARCHIVE_2);
		archive2.setPhone(PHONEARCHIVE_2);
		
		Resource resource1 = new Resource();
		resource1.setName(NAME_1);
		resource1.setAuthor(AUTHOR_1);
		resource1.setIsbn(ISBN_1);
		resource1.setIntroductionDate(DATE_1);
		resource1.setAssigned(false);

		Resource resource2 = new Resource();
		resource2.setName(NAME_2);
		resource2.setAuthor(AUTHOR_2);
		resource2.setIsbn(ISBN_2);
		resource2.setIntroductionDate(DATE_2);
		resource2.setAssigned(false);
		
		List<Archive> retrievedArchives = archiveManager.getAllArchives();

		for (Archive archive : retrievedArchives) {
			if (archive.getTeamNumber() == TEAMNUMBERARCHIVE_1 || archive.getTeamNumber() == TEAMNUMBERARCHIVE_2) {
				archiveManager.deleteArchive(archive);
			}
		}
		
		Long archiveId1 = archiveManager.addArchive(archive1);
		Long archiveId2 = archiveManager.addArchive(archive2);
		
		Archive retrievedArchive1 = archiveManager.findArchiveByTeamNumber(TEAMNUMBERARCHIVE_1);
		Archive retrievedArchive2 = archiveManager.findArchiveByTeamNumber(TEAMNUMBERARCHIVE_2);
		
		assertEquals(NAMEARCHIVE_1, retrievedArchive1.getName());
		assertEquals(TEAMNUMBERARCHIVE_1, retrievedArchive1.getTeamNumber());
		assertEquals(PHONEARCHIVE_1, retrievedArchive1.getPhone());
		
		assertEquals(NAMEARCHIVE_2, retrievedArchive2.getName());
		assertEquals(TEAMNUMBERARCHIVE_2, retrievedArchive2.getTeamNumber());
		assertEquals(PHONEARCHIVE_2, retrievedArchive2.getPhone());
		
		Long resourceId1 = resourceManager.addResource(resource1);

		Resource retrievedResource1 = resourceManager.findResourceById(resourceId1);
		assertEquals(NAME_1, retrievedResource1.getName());
		assertEquals(AUTHOR_1, retrievedResource1.getAuthor());
		assertEquals(ISBN_1, retrievedResource1.getIsbn());
		assertEquals(DATE_1, retrievedResource1.getIntroductionDate());
		assertEquals(false, retrievedResource1.getAssigned());
		
		Long resourceId2 = resourceManager.addResource(resource2);

		Resource retrievedResource2 = resourceManager.findResourceById(resourceId2);
		assertEquals(NAME_2, retrievedResource2.getName());
		assertEquals(AUTHOR_2, retrievedResource2.getAuthor());
		assertEquals(ISBN_2, retrievedResource2.getIsbn());
		assertEquals(DATE_2, retrievedResource2.getIntroductionDate());
		assertEquals(false, retrievedResource2.getAssigned());
		
		assigningManager.assignResource(archiveId1, resourceId1);
		
		resource1.setId(resourceId1);
		resource1.setName(NAME_3);
		resource1.setAuthor(AUTHOR_2);
		resource1.setIsbn(ISBN_4);
		resource1.setIntroductionDate(DATE_2);
		
		List<Archive> listArchive = archiveManager.getAllArchives();
		Archive archiveOwner = resourceManager.deleteResourceFromList(listArchive, resourceId1);
		if (archiveOwner != null){
			resource1.setAssigned(true);
			resourceManager.updateResource(resource1);
			assigningManager.assignResource(archiveOwner.getId(), resourceId2);
		}
		else {
			resource1.setAssigned(false);
			resourceManager.updateResource(resource1);
		}
		int count1 = resourceManager.countResource(ISBN_2);
		
		resource2.setId(resourceId2);
		resource2.setName(NAME_1);
		resource2.setAuthor(AUTHOR_1);
		resource2.setIsbn(ISBN_3);
		resource2.setIntroductionDate(DATE_1);
		
		List<Archive> listArchive2 = archiveManager.getAllArchives();
		Archive archiveOwner2 = resourceManager.deleteResourceFromList(listArchive2, resourceId2);
		if (archiveOwner2 != null){
			resource1.setAssigned(true);
			resourceManager.updateResource(resource2);
			assigningManager.assignResource(archiveOwner2.getId(), resourceId2);
		}
		else {
			resource1.setAssigned(false);
			resourceManager.updateResource(resource2);
		}
		int count2 = resourceManager.countResource(ISBN_3);
		
		assertEquals(1, count1);
		assertEquals(1, count2);
	}
}
