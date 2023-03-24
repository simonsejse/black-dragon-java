package dk.acto.blackdragon;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.bean.*;
import dk.acto.blackdragon.model.Model;
import dk.acto.blackdragon.service.ModelFactory;
import io.vavr.Value;
import io.vavr.collection.List;
import io.vavr.control.Option;
import io.vavr.control.Try;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;

public class ModelFactoryImpl implements ModelFactory<Model> {

    private static final int COLUMNS_PER_ROW = 4;

    @Override
    public List<Model> parse(String string) {
        List<Model> models = List.empty();

        models = Option.of(string)
                .map(StringReader::new)
                .map(CSVReader::new)
                .map(csvReader -> {
                    CsvToBean<Model> csvToBean = new CsvToBeanBuilder<Model>(csvReader)
                            .withType(Model.class)
                            .withSeparator(',')
                            .build();
                    csvToBean.setCsvReader(csvReader);
                    return csvToBean;
                })
                .toTry()
                .mapTry(CsvToBean::parse)
                .map(models::appendAll)
                .getOrElse(models);

        return models;
    }
}
