package com.example.shdemo.service;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.example.shdemo.domain.Archive;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/beans.xml" })
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@Transactional
public class ArchiveManagerTest {

	@Autowired
	ArchiveManager archiveManager;

	private final static String NAME_1 = "Centrum";
	private final static int TEAMNUMBER_1 = 189;
	private final static String PHONE_1 = "(52) 339-54-01";
	
	private final static String NAME_2 = "Oddział";
	private final static int TEAMNUMBER_2 = 178;
	private final static String PHONE_2 = "(78) 888-99-45";
	
	private final static String NAME_3 = "Filia";
	private final static int TEAMNUMBER_3 = 159;
	private final static String PHONE_3 = "(78) 123-45-12";

	@Test
	public void addArchiveCheck() {

		List<Archive> retrievedArchives = archiveManager.getAllArchives();

		for (Archive archive : retrievedArchives) {
			if (archive.getTeamNumber() == TEAMNUMBER_1) {
				archiveManager.deleteArchive(archive);
			}
		}

		Archive archive = new Archive();
		archive.setName(NAME_1);
		archive.setTeamNumber(TEAMNUMBER_1);
		archive.setPhone(PHONE_1);

		archiveManager.addArchive(archive);

		Archive retrievedArchive = archiveManager.findArchiveByTeamNumber(TEAMNUMBER_1);

		assertEquals(NAME_1, retrievedArchive.getName());
		assertEquals(TEAMNUMBER_1, retrievedArchive.getTeamNumber());
		assertEquals(PHONE_1, retrievedArchive.getPhone());
	}
	
	@Test
	public void deleteArchiveCheck() {

		List<Archive> retrievedArchives = archiveManager.getAllArchives();

		for (Archive archive : retrievedArchives) {
			if (archive.getTeamNumber() == TEAMNUMBER_1 || archive.getTeamNumber() == TEAMNUMBER_2) {
				archiveManager.deleteArchive(archive);
			}
		}

		Archive archive1 = new Archive();
		archive1.setName(NAME_1);
		archive1.setTeamNumber(TEAMNUMBER_1);
		archive1.setPhone(PHONE_1);

		Archive archive2 = new Archive();
		archive2.setName(NAME_2);
		archive2.setTeamNumber(TEAMNUMBER_2);
		archive2.setPhone(PHONE_2);
		
		archiveManager.addArchive(archive1);
		archiveManager.addArchive(archive2);
		
		Archive retrievedArchive1 = archiveManager.findArchiveByTeamNumber(TEAMNUMBER_1);
		Archive retrievedArchive2 = archiveManager.findArchiveByTeamNumber(TEAMNUMBER_2);
		
		assertEquals(NAME_1, retrievedArchive1.getName());
		assertEquals(TEAMNUMBER_1, retrievedArchive1.getTeamNumber());
		assertEquals(PHONE_1, retrievedArchive1.getPhone());
		
		assertEquals(NAME_2, retrievedArchive2.getName());
		assertEquals(TEAMNUMBER_2, retrievedArchive2.getTeamNumber());
		assertEquals(PHONE_2, retrievedArchive2.getPhone());
		
		archiveManager.deleteArchive(archive1);
		int count1 = archiveManager.countArchive(TEAMNUMBER_1);
		
		archiveManager.deleteArchive(archive2);
		int count2 = archiveManager.countArchive(TEAMNUMBER_2);
		
		assertEquals(0, count1);
		assertEquals(0, count2);
	}
}
