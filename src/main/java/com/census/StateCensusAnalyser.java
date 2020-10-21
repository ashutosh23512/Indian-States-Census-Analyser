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
		try {

			Reader reader = Files.newBufferedReader(path);
			Iterator<CSVStateCensus> iterator = this.getCSVFileIterator(reader, CSVStateCensus.class);

			ArrayList<CSVStateCensus> stateCensusList = new ArrayList<CSVStateCensus>();
			while (iterator.hasNext()) {

				stateCensusList.add(iterator.next());

			}

			return stateCensusList.size();
		} catch (IOException e) {
			throw new CensusException("File not found", CensusException.ExceptionType.WRONG_CSV);
		}
	}

	public int loadCSVFileCode(Path path) throws CensusException {
		try {
			Reader reader = Files.newBufferedReader(path);

			Iterator<StateCode> iterator = this.getCSVFileIterator(reader, StateCode.class);

			ArrayList<StateCode> stateCensusList = new ArrayList<StateCode>();
			while (iterator.hasNext()) {

				stateCensusList.add(iterator.next());

			}

			return stateCensusList.size();
		} catch (IOException e) {
			throw new CensusException("File not found", CensusException.ExceptionType.WRONG_CSV);
		}
	}

	private <E> Iterator getCSVFileIterator(Reader reader, Class csvClass) throws CensusException {
		try {
			CsvToBean<E> csvToBean = new CsvToBeanBuilder(reader).withType(csvClass).withIgnoreLeadingWhiteSpace(true)
					.build();
			return csvToBean.iterator();
		} catch (RuntimeException e) {
			throw new CensusException("File internal data not valid", CensusException.ExceptionType.WRONG_HEADER);
		}
	}
}
