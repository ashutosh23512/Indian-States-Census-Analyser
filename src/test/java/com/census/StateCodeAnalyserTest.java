package com.census;

import java.nio.file.Paths;

import org.junit.Assert;
import org.junit.Test;



import com.google.gson.Gson;

public class StateCodeAnalyserTest {
	public static final String STATE_CODE_DATA = "StateCode.csv";
	public static final String WRONG_STATE_CODE_DATA = "src/main/java/com/StateCensusAnalyser/StateCode.csv";
	public static final String WRONG_STATE_CODE_DATA_HEADER = "StateCensus.csv";
	public static final String WRONG_STATE_CODE_DATA_TYPE = "StateCode.txt";

	StateCensusAnalyser stateCensusAnalyser = new StateCensusAnalyser();

	@Test
	public void ensureNoOfRecordMatches() throws CensusException, CSVException {
		int records = stateCensusAnalyser.loadIndiaStateCodeData(Paths.get(STATE_CODE_DATA));
		Assert.assertEquals(37, records);
	}

	@Test
	public void checkWrongPath() throws CensusException, CSVException {
		try {
			stateCensusAnalyser.loadIndiaStateCodeData(Paths.get(WRONG_STATE_CODE_DATA));
		} catch (CensusException e) {
			Assert.assertEquals(CensusException.ExceptionType.WRONG_CSV, e.type);
		}
	}

	@Test
	public void checkWrongHeader() throws CensusException, CSVException {
		try {
			stateCensusAnalyser.loadIndiaStateCodeData(Paths.get(WRONG_STATE_CODE_DATA_HEADER));
		} catch (CensusException e) {
			Assert.assertEquals(CensusException.ExceptionType.WRONG_CSV, e.type);
			;
		}
	}

	@Test
	public void checkWrongType() throws CensusException, CSVException {
		try {
			stateCensusAnalyser.loadIndiaStateCodeData(Paths.get(WRONG_STATE_CODE_DATA_TYPE));
		} catch (CensusException e) {
			Assert.assertEquals(CensusException.ExceptionType.WRONG_CSV, e.type);
			;
		}
	}
	@Test
	public void censusSortedOnStateCode() throws CensusException {
		stateCensusAnalyser.loadIndiaStateCodeData(Paths.get(STATE_CODE_DATA));
		String sortedCensusData = stateCensusAnalyser.getStateCodeWiseSortedCensusData();
		StateCode[] censusCsv = new Gson().fromJson(sortedCensusData, StateCode[].class);
		Assert.assertEquals("AD", censusCsv[0].stateCode);
	}

	
}