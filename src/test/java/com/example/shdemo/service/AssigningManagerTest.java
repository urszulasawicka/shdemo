package com.example.shdemo.service;

import static org.junit.Assert.assertEquals;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
public class AssigningManagerTest {

	@Autowired
	AssigningManager assigningManager;
	@Autowired
	ArchiveManager archiveManager;
	@Autowired
	ResourceManager resourceManager;

	DateFormat dfm = new SimpleDateFormat("yyyy-MM-dd");
	private final static String NAME_1 = "Księgi ziemskie";
	private final static String AUTHOR_1 = "Przasnysiensia";
	private final static int ISBN_1 = 43;
	private final static Date DATE_1 = new Date();
	private final static int TEAMNUMBER_1 = 189;

	private final static String NAME_2 = "Akta malborskie";
	private final static String AUTHOR_2 = "Kanclerz Namiestnik";
	private final static int ISBN_2 = 67;
	private final static Date DATE_2 = new Date();

	private final static String NAME_3 = "Odczyty staromodne";
	private final static String AUTHOR_3 = "Kowalska";
	private final static int ISBN_3 = 89;
	private final static int DATE_3 = 1788;

	private final static String NAMEARCHIVE_1 = "Centrum";
	private final static int TEAMNUMBERARCHIVE_1 = 189;
	private final static String PHONEARCHIVE_1 = "(52) 339-54-01";

	private final static String NAMEARCHIVE_2 = "Oddział";
	private final static int TEAMNUMBERARCHIVE_2 = 178;
	private final static String PHONEARCHIVE_2 = "(78) 888-99-45";
	private final static int TEAMNUMBERARCHIVE_3 = 100;

	@Test
	public void assignResourceCheck() {

		List<Archive> retrievedArchives = archiveManager.getAllArchives();
		for (Archive archive : retrievedArchives) {
			if (archive.getTeamNumber() == TEAMNUMBER_1) {
				archiveManager.deleteArchive(archive);
			}
		}

		Archive archive = new Archive();
		archive.setName(NAMEARCHIVE_1);
		archive.setTeamNumber(TEAMNUMBERARCHIVE_1);
		archive.setPhone(PHONEARCHIVE_1);

		archiveManager.addArchive(archive);

		Archive retrievedArchive = archiveManager
				.findArchiveByTeamNumber(TEAMNUMBERARCHIVE_1);

		List<Resource> retrievedResources = resourceManager.getAllResources();

		for (Resource resource : retrievedResources) {
			if (resource.getIsbn() == ISBN_1) {
				resourceManager.deleteResource(resource);
			}
		}

		Resource resource = new Resource();
		resource.setName(NAME_1);
		resource.setAuthor(AUTHOR_1);
		resource.setIsbn(ISBN_1);
		resource.setIntroductionDate(DATE_1);
		resource.setAssigned(false);

		Long resourceId = resourceManager.addResource(resource);

		assigningManager.assignResource(retrievedArchive.getId(), resourceId);
		List<Resource> resourcesOfArchive = archiveManager
				.getResourcesOfArchive(retrievedArchive);
		assertEquals(1, resourcesOfArchive.size());
		assertEquals(NAME_1, resourcesOfArchive.get(0).getName());
		assertEquals(AUTHOR_1, resourcesOfArchive.get(0).getAuthor());
		assertEquals(ISBN_1, resourcesOfArchive.get(0).getIsbn());
		/*Date today;
		try {
			today = dfm.parse(dfm.format(DATE_1));*/
			assertEquals(DATE_1, resourcesOfArchive.get(0).getIntroductionDate());
		/*} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		assertEquals(true, resourcesOfArchive.get(0).getAssigned());
	}

	@Test
	public void disposeResourceCheck() {
		List<Archive> retrievedArchives = archiveManager.getAllArchives();
		for (Archive archive : retrievedArchives) {
			if (archive.getTeamNumber() == TEAMNUMBER_1) {
				archiveManager.deleteArchive(archive);
			}
		}

		Archive archive = new Archive();
		archive.setName(NAMEARCHIVE_1);
		archive.setTeamNumber(TEAMNUMBERARCHIVE_1);
		archive.setPhone(PHONEARCHIVE_1);

		archiveManager.addArchive(archive);

		Archive retrievedArchive = archiveManager
				.findArchiveByTeamNumber(TEAMNUMBERARCHIVE_1);

		List<Resource> retrievedResources = resourceManager.getAllResources();

		for (Resource resource : retrievedResources) {
			if (resource.getIsbn() == ISBN_1) {
				resourceManager.deleteResource(resource);
			}
		}

		Resource resource = new Resource();
		resource.setName(NAME_1);
		resource.setAuthor(AUTHOR_1);
		resource.setIsbn(ISBN_1);
		resource.setIntroductionDate(DATE_1);
		resource.setAssigned(false);

		Long resourceId = resourceManager.addResource(resource);

		assigningManager.assignResource(retrievedArchive.getId(), resourceId);
		List<Resource> resourcesOfArchive = archiveManager
				.getResourcesOfArchive(retrievedArchive);
		assertEquals(1, resourcesOfArchive.size());
		/*Date today;
		try {
			today = dfm.parse(dfm.format(DATE_1));*/
			assertEquals(NAME_1, resourcesOfArchive.get(0).getName());
			assertEquals(AUTHOR_1, resourcesOfArchive.get(0).getAuthor());
			assertEquals(ISBN_1, resourcesOfArchive.get(0).getIsbn());
			assertEquals(DATE_1, resourcesOfArchive.get(0).getIntroductionDate());
			assertEquals(true, resourcesOfArchive.get(0).getAssigned());
			
			assigningManager.disposeResource(retrievedArchive, resource);
			
			resourcesOfArchive = archiveManager
					.getResourcesOfArchive(retrievedArchive);
			assertEquals(0, resourcesOfArchive.size());
			
			Resource disposedResource = resourceManager
					.findResourceByIsbn(ISBN_1);
			assertEquals(NAME_1, disposedResource.getName());
			assertEquals(AUTHOR_1, disposedResource.getAuthor());
			assertEquals(ISBN_1, disposedResource.getIsbn());
			assertEquals(DATE_1, disposedResource.getIntroductionDate());
			assertEquals(false, disposedResource.getAssigned());
		/*} catch (ParseException e) {
			e.printStackTrace();
		}*/
	}
	
	@Test
	public void countResourcesForArchivesCheck() {
		
		Archive archive = new Archive();
		archive.setName(NAMEARCHIVE_1);
		archive.setTeamNumber(TEAMNUMBERARCHIVE_1);
		archive.setPhone(PHONEARCHIVE_1);

		archiveManager.addArchive(archive);

		Archive retrievedArchive = archiveManager
				.findArchiveByTeamNumber(TEAMNUMBERARCHIVE_1);
		
		Resource resource = new Resource();
		resource.setName(NAME_1);
		resource.setAuthor(AUTHOR_1);
		resource.setIsbn(ISBN_1);
		resource.setIntroductionDate(DATE_1);
		resource.setAssigned(false);

		Long resourceId = resourceManager.addResource(resource);
		
		Resource resource2 = new Resource();
		resource2.setName(NAME_2);
		resource2.setAuthor(AUTHOR_2);
		resource2.setIsbn(ISBN_2);
		resource2.setIntroductionDate(DATE_2);
		resource2.setAssigned(false);

		Long resourceId2 = resourceManager.addResource(resource2);
		assigningManager.assignResource(retrievedArchive.getId(), resourceId);
		assigningManager.assignResource(retrievedArchive.getId(), resourceId2);
		List<Archive> retrievedArchives = archiveManager.getAllArchives();
		Archive richestArchive = assigningManager.countResourcesForArchives(retrievedArchives);
		assertEquals(NAMEARCHIVE_1, richestArchive.getName());
		assertEquals(TEAMNUMBERARCHIVE_1, richestArchive.getTeamNumber());
		assertEquals(PHONEARCHIVE_1, richestArchive.getPhone());
	}
}