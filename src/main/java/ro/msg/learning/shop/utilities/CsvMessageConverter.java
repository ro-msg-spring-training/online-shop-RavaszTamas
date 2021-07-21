package ro.msg.learning.shop.utilities;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractGenericHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import ro.msg.learning.shop.exceptions.ConverstionException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CsvMessageConverter<T> extends AbstractGenericHttpMessageConverter<T> {

    public CsvMessageConverter()
    {
        super(MediaType.TEXT_EVENT_STREAM);
    }

    public List<T> fromCsv(Class<T> clazz, InputStream inputCsv) {
        CsvMapper mapper = new CsvMapper();
        CsvSchema schema = mapper.schemaFor(clazz);


        List<T> result = new ArrayList<>();

        try {

            MappingIterator<T> iterator = mapper.readerFor(clazz).with(schema).readValues(inputCsv);

            while (iterator.hasNextValue()) {
                result.add(iterator.nextValue());
            }

        } catch (IOException ex) {
            throw new ConverstionException("Failed to convert objects from csv!");
        }

        return result;
    }

    public void toCsv(Class<T> clazz, List<T> items, OutputStream outputCsv) {
        CsvMapper mapper = new CsvMapper();
        CsvSchema schema = mapper.schemaFor(clazz);

        ObjectWriter writer = mapper.writer(schema);
        try {
            writer.writeValue(outputCsv,items);
        } catch (IOException e) {
            throw new ConverstionException("Failed to convert objects to csv!");
        }
    }

    @Override
    protected void writeInternal(T t, Type type, HttpOutputMessage httpOutputMessage) throws IOException, HttpMessageNotWritableException {

    }

    @Override
    protected T readInternal(Class<? extends T> aClass, HttpInputMessage httpInputMessage) throws IOException, HttpMessageNotReadableException {
        return null;
    }

    @Override
    public T read(Type type, Class<?> aClass, HttpInputMessage httpInputMessage) throws IOException, HttpMessageNotReadableException {
        return null;
    }
}
