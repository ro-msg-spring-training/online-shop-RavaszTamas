package ro.msg.learning.shop.utilities;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.springframework.core.GenericTypeResolver;
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
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public class CsvMessageConverter<T> extends AbstractGenericHttpMessageConverter<List<T>> {

    public CsvMessageConverter() {
        super(new MediaType("text", "csv"));
    }

    @Override
    protected List<T> readInternal(Class<? extends List<T>> aClass, HttpInputMessage httpInputMessage) throws IOException, HttpMessageNotReadableException {
        return fromCsv(toBeanType(aClass), httpInputMessage.getBody());
    }

    @Override
    protected void writeInternal(List<T> ts, Type type, HttpOutputMessage httpOutputMessage) throws IOException, HttpMessageNotWritableException {
        toCsv(toBeanType(toBeanType(ts.getClass().getGenericSuperclass())), ts, httpOutputMessage.getBody());
    }

    @Override
    public List<T> read(Type type, Class<?> aClass, HttpInputMessage httpInputMessage) throws IOException, HttpMessageNotReadableException {
        return fromCsv(toBeanType(GenericTypeResolver.resolveType(type, aClass)), httpInputMessage.getBody());

    }


    public List<T> fromCsv(Class<T> clazz, InputStream inputCsv) {
        CsvMapper mapper = new CsvMapper();
        CsvSchema schema = mapper.schemaFor(clazz).withHeader();

        try {

            MappingIterator<T> iterator = mapper.readerFor(clazz).with(schema).readValues(inputCsv);
            return iterator.readAll();

        } catch (IOException ex) {
            throw new ConverstionException("Failed to convert objects from csv!");
        }

    }

    public void toCsv(Class<T> clazz, List<T> items, OutputStream outputCsv) {
        CsvMapper mapper = new CsvMapper();
        CsvSchema schema = mapper.schemaFor(clazz);

        ObjectWriter writer = mapper.writer(schema.withLineSeparator("\n"));
        try {
            writer.writeValue(outputCsv, items);
        } catch (IOException e) {
            throw new ConverstionException("Failed to convert objects to csv!");
        }
    }

    @SuppressWarnings("unchecked")
    private Class<T> toBeanType(Type type) {
        return (Class<T>) ((ParameterizedType) type).getActualTypeArguments()[0];
    }

}
