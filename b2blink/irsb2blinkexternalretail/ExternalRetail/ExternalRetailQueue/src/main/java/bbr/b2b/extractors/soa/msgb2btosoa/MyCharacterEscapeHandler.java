package bbr.b2b.extractors.soa.msgb2btosoa;

import java.io.IOException;
import java.io.Writer;

import com.sun.xml.bind.marshaller.CharacterEscapeHandler;

public class MyCharacterEscapeHandler implements CharacterEscapeHandler {

	public void escape(char[] ac, int i, int j, boolean flag, Writer writer) throws IOException {
		// do not escape
		writer.write(ac, i, j);
	}
}
