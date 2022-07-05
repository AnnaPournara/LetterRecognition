package gr.letter.recognition;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class LetterRecognitionTest {

	@Test
	void testRun() {
		LetterRecognition lr = new LetterRecognition();
		String path = "C:\\Users\\annpo\\eclipse-workspace\\LetterRecognition\\testZ.png";
		assertEquals('Z', lr.run(path));
	}

}
