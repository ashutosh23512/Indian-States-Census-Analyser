package com.census;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

public class StateCensusAnalyser {
	public int loadCSVFileCensus(Path path) throws CensusException {
		try (Reader reader = Files.newBufferedReader(path)) {

			Iterator<CSVStateCensus> iterator = new OpenCSVBuilder().getCSVFileIterator(reader, CSVStateCensus.class);

			return filesize(iterator);
		} catch (IOException e) {
			throw new CensusException("File not found", CensusException.ExceptionType.WRONG_CSV);
		}
	}

	public int loadCSVFileCode(Path path) throws CensusException {
		try (Reader reader = Files.newBufferedReader(path)) {

			Iterator<StateCode> iterator = new OpenCSVBuilder().getCSVFileIterator(reader, StateCode.class);

			return filesize(iterator);
		} catch (IOException e) {
			throw new CensusException("File not found", CensusException.ExceptionType.WRONG_CSV);
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
