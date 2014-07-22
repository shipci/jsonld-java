/**
 * 
 */
package com.github.jsonldjava.core;

import static org.junit.Assert.*;

import java.io.StringReader;
import java.io.StringWriter;

import org.junit.Test;
import org.openrdf.model.Model;
import org.openrdf.rio.RDFFormat;
import org.openrdf.rio.Rio;

import com.github.jsonldjava.impl.NQuadTripleCallback;

/**
 * Test for issue #118
 * 
 * @author Peter Ansell p_ansell@yahoo.com
 *
 * @see <a href="https://github.com/jsonld-java/jsonld-java/issues/118">Issue
 *      #118</a>
 */
public class BlankNodeListTest {

    @Test
    public void testDefault() throws Exception {
        Model parse = Rio.parse(this.getClass()
                .getResourceAsStream("/custom/blanknodelist-in.trig"), "", RDFFormat.TRIG);
        assertEquals(6, parse.size());
        assertEquals(2, parse.contexts().size());
        assertEquals(4, parse.subjects().size());
        assertEquals(4, parse.predicates().size());
        assertEquals(5, parse.objects().size());

        StringWriter writer = new StringWriter();
        Rio.write(parse, writer, RDFFormat.NQUADS);
        RDFDataset nQuads = RDFDatasetUtils.parseNQuads(writer.toString());

        JsonLdOptions options = new JsonLdOptions();
        final Object rval = new JsonLdApi(options).fromRDF(nQuads);

        final JsonLdApi api = new JsonLdApi(rval, options);
        final RDFDataset dataset = api.toRDF();

        String nQuadsString = RDFDatasetUtils.toNQuads(dataset);
        Model roundTripParse = Rio.parse(new StringReader(nQuadsString), "", RDFFormat.NQUADS);

        assertEquals(6, roundTripParse.size());
        assertEquals(2, roundTripParse.contexts().size());
        assertEquals(4, roundTripParse.subjects().size());
        assertEquals(4, roundTripParse.predicates().size());
        assertEquals(5, roundTripParse.objects().size());
    }

    @Test
    public void testExpanded() throws Exception {
        Model parse = Rio.parse(this.getClass()
                .getResourceAsStream("/custom/blanknodelist-in.trig"), "", RDFFormat.TRIG);
        assertEquals(6, parse.size());
        assertEquals(2, parse.contexts().size());
        assertEquals(4, parse.subjects().size());
        assertEquals(4, parse.predicates().size());
        assertEquals(5, parse.objects().size());

        StringWriter writer = new StringWriter();
        Rio.write(parse, writer, RDFFormat.NQUADS);
        RDFDataset nQuads = RDFDatasetUtils.parseNQuads(writer.toString());

        JsonLdOptions options = new JsonLdOptions();
        final Object rval = new JsonLdApi(options).fromRDF(nQuads);

        final Object expandedInput = JsonLdProcessor.expand(rval, options);

        final JsonLdApi api = new JsonLdApi(expandedInput, options);
        final RDFDataset dataset = api.toRDF();

        String nQuadsString = RDFDatasetUtils.toNQuads(dataset);
        Model roundTripParse = Rio.parse(new StringReader(nQuadsString), "", RDFFormat.NQUADS);

        assertEquals(6, roundTripParse.size());
        assertEquals(2, roundTripParse.contexts().size());
        assertEquals(4, roundTripParse.subjects().size());
        assertEquals(4, roundTripParse.predicates().size());
        assertEquals(5, roundTripParse.objects().size());
    }

    @Test
    public void testFlattened() throws Exception {
        Model parse = Rio.parse(this.getClass()
                .getResourceAsStream("/custom/blanknodelist-in.trig"), "", RDFFormat.TRIG);
        assertEquals(6, parse.size());
        assertEquals(2, parse.contexts().size());
        assertEquals(4, parse.subjects().size());
        assertEquals(4, parse.predicates().size());
        assertEquals(5, parse.objects().size());

        StringWriter writer = new StringWriter();
        Rio.write(parse, writer, RDFFormat.NQUADS);
        RDFDataset nQuads = RDFDatasetUtils.parseNQuads(writer.toString());

        JsonLdOptions options = new JsonLdOptions();
        final Object rval = new JsonLdApi(options).fromRDF(nQuads);

        final Object flattenedInput = JsonLdProcessor.flatten(rval, options);

        final JsonLdApi api = new JsonLdApi(flattenedInput, options);
        final RDFDataset dataset = api.toRDF();

        String nQuadsString = RDFDatasetUtils.toNQuads(dataset);
        Model roundTripParse = Rio.parse(new StringReader(nQuadsString), "", RDFFormat.NQUADS);

        assertEquals(6, roundTripParse.size());
        assertEquals(2, roundTripParse.contexts().size());
        assertEquals(4, roundTripParse.subjects().size());
        assertEquals(4, roundTripParse.predicates().size());
        assertEquals(5, roundTripParse.objects().size());
    }

    @Test
    public void testCompacted() throws Exception {
        Model parse = Rio.parse(this.getClass()
                .getResourceAsStream("/custom/blanknodelist-in.trig"), "", RDFFormat.TRIG);
        assertEquals(6, parse.size());
        assertEquals(2, parse.contexts().size());
        assertEquals(4, parse.subjects().size());
        assertEquals(4, parse.predicates().size());
        assertEquals(5, parse.objects().size());

        StringWriter writer = new StringWriter();
        Rio.write(parse, writer, RDFFormat.NQUADS);
        RDFDataset nQuads = RDFDatasetUtils.parseNQuads(writer.toString());

        JsonLdOptions options = new JsonLdOptions();
        final Object rval = new JsonLdApi(options).fromRDF(nQuads);
        final Object compactedInput = JsonLdProcessor.compact(rval, null, options);
        final Object expandedInput = JsonLdProcessor.expand(compactedInput, options);
        
        final JsonLdApi api = new JsonLdApi(expandedInput, options);
        final RDFDataset dataset = api.toRDF();

        String nQuadsString = RDFDatasetUtils.toNQuads(dataset);
        Model roundTripParse = Rio.parse(new StringReader(nQuadsString), "", RDFFormat.NQUADS);

        assertEquals(6, roundTripParse.size());
        assertEquals(2, roundTripParse.contexts().size());
        assertEquals(4, roundTripParse.subjects().size());
        assertEquals(4, roundTripParse.predicates().size());
        assertEquals(5, roundTripParse.objects().size());
    }

}
