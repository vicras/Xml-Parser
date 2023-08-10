package com.example.wppl;

import com.fasterxml.aalto.AsyncByteArrayFeeder;
import com.fasterxml.aalto.AsyncXMLInputFactory;
import com.fasterxml.aalto.AsyncXMLStreamReader;
import com.fasterxml.aalto.stax.InputFactoryImpl;

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import java.io.UnsupportedEncodingException;

public class AaltoAsync {
    public static void main(String[] args) throws XMLStreamException, UnsupportedEncodingException {
//        AsyncXMLInputFactory inputF = new InputFactoryImpl(); // sub-class of XMLStreamReader2
//
//        // two choices for input feeding: byte[] or ByteBuffer. Here we use former:
//        byte[] input_part1 = "<root>val".getBytes("UTF-8"); // would come from File, over the net etc
//
//        // can construct with initial data, or without; here we initialize with it
//        AsyncXMLStreamReader<AsyncByteArrayFeeder> parser = inputF.createAsyncFor(input_part1);
//
//// now can access couple of events
//        assertTokenType(XMLStreamConstants.START_DOCUMENT, parser.next());
//        assertTokenType(XMLStreamConstants.START_ELEMENT, parser.next());
//        assertEquals("root", parser.getLocalName());
//// since we have parts of CHARACTERS, we'll still get that first:
//        assertTokenType(XMLStreamConstants.CHARACTERS, parser.next());
//        assertEquals("val", parser.getText();
//// but that's all data we had so:
//        assertTokenType(AsyncXMLStreamReader.EVENT_INCOMPLETE, parser.next());
//
//// at this point, must feed more data:
//        byte[] input_part2 = "ue</root>".getBytes("UTF-8");
//        parser.getInputFeeder().feedInput(input_part2, 0, input_part2.length);
//
//// and can parse that
//        assertTokenType(XMLStreamConstants.CHARACTERS, parser.next());
//        assertEquals("ue", parser.getText();
//        assertTokenType(XMLStreamConstants.END_ELEMENT, parser.next());
//        assertEquals("root", parser.getLocalName());
//        assertTokenType(AsyncXMLStreamReader.EVENT_INCOMPLETE, parser.next());
//
//// and if we now ran out of data need to indicate that too
//        parser.getInputFeeder().endOfInput();
//// which lets us conclude parsing
//        assertTokenType(XMLStreamConstants.END_DOCUMENT, parser.next());
//        parser.close();
    }
}
