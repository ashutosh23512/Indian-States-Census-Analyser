package com.census;

import java.io.IOException;
import java.io.Reader;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.StreamSupport;

import com.google.gson.Gson;

public class StateCensusAnalyser {

	List<CSVStateCensus> csvCensusList;
	List<StateCode> csvStateList;

	public StateCensusAnalyser() {
		this.csvCensusList = new ArrayList<CSVStateCensus>();
		this.csvStateList = new ArrayList<StateCode>();

	}

	public int loadIndiaCensusData(Path path) throws CensusException {

		try (Reader reader = Files.newBufferedReader(path);) {
			ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
			csvCensusList = csvBuilder.getCSVFileList(reader, CSVStateCensus.class);
			return csvCensusList.size();
		} catch (IOException e) {
			throw new CensusException("File not found", CensusException.ExceptionType.WRONG_CSV);
		} catch (RuntimeException e) {
			throw new CensusException("File data not proper", CensusException.ExceptionType.WRONG_CSV);

		} catch (CSVException e) {
			throw new CensusException(e.getMessage(), CensusException.ExceptionType.WRONG_HEADER);
		}
	}

	public int loadIndiaStateCodeData(Path path) throws CensusException {

		try (Reader reader = Files.newBufferedReader(path)) {
			ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
			csvStateList = csvBuilder.getCSVFileList(reader, StateCode.class);
			return csvStateList.size();
		} catch (IOException e) {
			throw new CensusException("File not found", CensusException.ExceptionType.WRONG_CSV);
		} catch (RuntimeException e) {
			throw new CensusException("File data not proper", CensusException.ExceptionType.WRONG_CSV);

		} catch (CSVException e) {
			throw new CensusException(e.getMessage(), CensusException.ExceptionType.WRONG_HEADER);
		}

	}

	private <E> int getCount(Iterator<E> iterator) {
		Iterable<E> csvIterable = () -> iterator;
		int numOfEntries = (int) StreamSupport.stream(csvIterable.spliterator(), false).count();
		return numOfEntries;
	}

	public String getSortedCensuByState() throws CensusException {
		if (csvCensusList == null || csvCensusList.size() == 0)

		{
			throw new CensusException("File is empty", CensusException.ExceptionType.WRONG_HEADER);
		}
		Comparator<CSVStateCensus> censusComparator = Comparator.comparing(census -> census.state);

		this.sort(censusComparator);
		String sortedStateCensus = new Gson().toJson(csvCensusList);
		return sortedStateCensus;
	}
	

	private void sort(Comparator<CSVStateCensus> censusComparator) {
		for (int i = 0; i < csvCensusList.size() - 1; i++) {
			for (int j = 0; j < csvCensusList.size() - 1 - i; j++) {
				CSVStateCensus census1 = csvCensusList.get(j);
				CSVStateCensus census2 = csvCensusList.get(j + 1);
				if (censusComparator.compare(census1, census2) > 0) {
					csvCensusList.set(j, census2);
					csvCensusList.set(j + 1, census1);
				}
			}
		}
	}

	public String getStateCodeWiseSortedCensusData() throws CensusException {
		if (csvStateList == null || csvStateList.size() == 0) {
			throw new CensusException("File is empty", CensusException.ExceptionType.WRONG_CSV);
		}
		Collections.sort(csvStateList, Comparator.comparing(code -> code.stateCode));
		return new Gson().toJson(csvStateList);
	}
	public String getStatePopulationWiseSortedCensusData() throws CensusException {
		if(csvCensusList == null || csvCensusList.size() == 0) {
			throw new CensusException("File is empty", CensusException.ExceptionType.WRONG_CSV);
		}
		Collections.sort(csvCensusList, Comparator.comparing(census -> census.getPopulationData()));
		System.out.println(csvCensusList);
		return new Gson().toJson(csvCensusList);
	}
	public String getStatePopulationDensityWiseSortedCensusData() throws CensusException {
		if(csvCensusList == null || csvCensusList.size() == 0) {
			throw new CensusException("File is empty", CensusException.ExceptionType.WRONG_CSV);
		}
		Collections.sort(csvCensusList, Comparator.comparing(census -> census.getPopulationDensityData()));
		System.out.println(csvCensusList);
		return new Gson().toJson(csvCensusList);
	}
}
