package com.census;

import java.nio.file.Paths;

import org.junit.Assert;
import org.junit.Test;

public class StateCodeAnalyserTest {
	public static final String STATE_CODE_DATA = "src/StateCode1.csv";
	public static final String WRONG_STATE_CODE_DATA = "src/main/java/com/StateCode.csv";
	public static final String WRONG_STATE_CODE_DATA_HEADER = "StateCensus.csv";

	StateCensusAnalyser stateCensusAnalyser = new StateCensusAnalyser();

	@Test
	public void ensureNoOfRecordMatches() throws CensusException {
		int records = stateCensusAnalyser.loadCSVFileCode(Paths.get(STATE_CODE_DATA));
		Assert.assertEquals(37, records);
	}

	@Test
	public void checkWrongPath() throws CensusException {
		try {
			stateCensusAnalyser.loadCSVFileCode(Paths.get(WRONG_STATE_CODE_DATA));
		} catch (CensusException e) {
			Assert.assertEquals(CensusException.ExceptionType.WRONG_CSV, e.type);
		}
	}

	@Test
	public void checkWrongHeader() throws CensusException {
		try {
			stateCensusAnalyser.loadCSVFileCode(Paths.get(WRONG_STATE_CODE_DATA_HEADER));
		} catch (CensusException e) {
			Assert.assertEquals(CensusException.ExceptionType.WRONG_HEADER, e.type);

		}
	}
}
