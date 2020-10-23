package com.census;

import java.util.List;
import java.io.IOException;
import java.io.Reader;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.stream.StreamSupport;

import com.opencsv.bean.CsvToBean;

import com.google.gson.Gson;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

public class StateCensusAnalyser {
	public int loadCSVFileCensus(Path path) throws CensusException, CSVException {
		try (Reader reader = Files.newBufferedReader(path)) {
			ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
			List<CSVStateCensus> stateCensusList = csvBuilder.getCSVFileList(reader, CSVStateCensus.class);
			return stateCensusList.size();
		} catch (IOException e) {
			throw new CensusException("File not found", CensusException.ExceptionType.WRONG_CSV);
		} catch (RuntimeException e) {
			throw new CensusException("File internal data not valid", CensusException.ExceptionType.WRONG_HEADER);
		} catch (CSVException e) {
			throw new CensusException("Unable to parse", CensusException.ExceptionType.UNABLE_TO_PARSE);
		}
	}

	public int loadCSVFileCode(Path path) throws CensusException, CSVException {
		try (Reader reader = Files.newBufferedReader(path)) {
			ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
			List<StateCode> stateCodeList = csvBuilder.getCSVFileList(reader, StateCode.class);
			return stateCodeList.size();
		} catch (IOException e) {
			throw new CensusException("File not found", CensusException.ExceptionType.WRONG_CSV);
		} catch (RuntimeException e) {
			throw new CensusException("File internal data not valid", CensusException.ExceptionType.WRONG_HEADER);
		} catch (CSVException e) {
			throw new CensusException("Unable to parse", CensusException.ExceptionType.UNABLE_TO_PARSE);
		}
	}

	public String getStateWiseSortedCensusData(Path path) throws CensusException, CSVException {
		try (Reader reader = Files.newBufferedReader(path)) {
			ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
			List<CSVStateCensus> stateCensusList = csvBuilder.getCSVFileList(reader, CSVStateCensus.class);
			if (stateCensusList == null || stateCensusList.size() == 0) {
				throw new CensusException("No census data", CensusException.ExceptionType.NO_CENSUS_DATA);
			}
			Comparator<CSVStateCensus> censusComparator = Comparator.comparing(census -> census.state);
			this.sort(stateCensusList, censusComparator);
			String sortedStateCensus = new Gson().toJson(stateCensusList);
			return sortedStateCensus;
		} catch (IOException e) {
			throw new CensusException("File not found", CensusException.ExceptionType.WRONG_CSV);
		} catch (RuntimeException e) {
			throw new CensusException("File internal data not valid", CensusException.ExceptionType.WRONG_HEADER);
		} catch (CSVException e) {
			throw new CensusException("Unable to parse", CensusException.ExceptionType.UNABLE_TO_PARSE);
		}
	}

	public String getStateWiseSortedCodeData(Path path) throws CensusException, CSVException {
		try (Reader reader = Files.newBufferedReader(path)) {
			ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
			List<StateCode> stateCodeList = csvBuilder.getCSVFileList(reader, StateCode.class);
			Comparator<StateCode> censusComparator = Comparator.comparing(census -> census.stateName);
			this.sort1(stateCodeList, censusComparator);
			String sortedStateCode = new Gson().toJson(stateCodeList);
			return sortedStateCode;
		} catch (IOException e) {
			throw new CensusException("File not found", CensusException.ExceptionType.WRONG_CSV);
		} catch (RuntimeException e) {
			throw new CensusException("File internal data not valid", CensusException.ExceptionType.WRONG_HEADER);
		} catch (CSVException e) {
			throw new CensusException("Unable to parse", CensusException.ExceptionType.UNABLE_TO_PARSE);
		}
	}

	private void sort(List<CSVStateCensus> stateCensusList, Comparator<CSVStateCensus> censusComparator) {
		for (int i = 0; i < stateCensusList.size() - 1; i++) {
			for (int j = 0; j < stateCensusList.size() - 1 - i; j++) {
				CSVStateCensus census1 = stateCensusList.get(j);
				CSVStateCensus census2 = stateCensusList.get(j + 1);
				if (censusComparator.compare(census1, census2) > 0) {
					stateCensusList.set(j, census2);
					stateCensusList.set(j + 1, census1);
				}
			}
		}
	}

	private void sort1(List<StateCode> codeList, Comparator<StateCode> censusComparator) {
		for (int i = 0; i < codeList.size() - 1; i++) {
			for (int j = 0; j < codeList.size() - 1 - i; j++) {
				StateCode code1 = codeList.get(j);
				StateCode code2 = codeList.get(j + 1);
				if (censusComparator.compare(code1, code2) > 0) {
					codeList.set(j, code2);
					codeList.set(j + 1, code1);
				}
			}
		}
	}

	private <E> int filesize(Iterator<E> iterator) {
		ArrayList<E> stateCensusList = new ArrayList<E>();
		while (iterator.hasNext()) {

			stateCensusList.add(iterator.next());

		}
		return stateCensusList.size();
	}
}
