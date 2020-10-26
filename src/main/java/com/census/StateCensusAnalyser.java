package com.census;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.StreamSupport;


public class StateCensusAnalyser {
	
	List<CSVStateCensus> csvCensusList;
	List<StateCode> csvStateList;

	public StateCensusAnalyser() {
		this.csvCensusList = new ArrayList<CSVStateCensus>();
		this.csvStateList = new ArrayList<StateCode>();

	}

	public int loadIndiaCensusData(String csvFilePath) throws CensusException {
		if (!csvFilePath.contains(".csv")) {
			throw new CensusException("Not CSV file",
					CensusException.ExceptionType.WRONG_TYPE);
		}
		try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
			ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
			csvCensusList = csvBuilder.getCSVFileList(reader, CSVStateCensus.class);
			return csvCensusList.size();
		} catch (IOException e) {
			throw new CensusException("File not found",
					CensusException.ExceptionType.WRONG_CSV);
		} catch (RuntimeException e) {
			throw new CensusException("File data not proper",
					CensusException.ExceptionType.UNABLE_TO_PARSE);

		}catch (CSVException e) {
			throw new CensusException(e.getMessage(), CensusException.ExceptionType.UNABLE_TO_PARSE);
		}
	}

	public int loadIndiaStateCodeData(String csvFilePath) throws CensusException {
		if (!csvFilePath.contains(".csv")) {
			throw new CensusException("Not CSV file",
					CensusException.ExceptionType.WRONG_TYPE);
		}
		try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
			ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
			csvStateList = csvBuilder.getCSVFileList(reader, StateCode.class);
			return csvStateList.size();
		} catch (IOException e) {
			throw new CensusException("File not found",
					CensusException.ExceptionType.WRONG_CSV);
		} catch (RuntimeException e) {
			throw new CensusException("File data not proper",
					CensusException.ExceptionType.UNABLE_TO_PARSE);
		}catch (CSVException e) {
			throw new CensusException(e.getMessage(), CensusException.ExceptionType.UNABLE_TO_PARSE);
		}

	}

	private <E> int getCount(Iterator<E> iterator) {
		Iterable<E> csvIterable = () -> iterator;
		int numOfEntries = (int) StreamSupport.stream(csvIterable.spliterator(), false).count();
		return numOfEntries;
	}
}
