package cdl.kata.checkout.service;

import java.io.IOException;
import java.io.InputStreamReader;

import org.springframework.stereotype.Service;

@Service
public class ReadingInput {

    public void readInput() throws IOException {
        InputStreamReader cin = new InputStreamReader(System.in);

		
		Integer test = cin.read();
		cin.toString();
    }

}
